package ygorgarofalo.SpringU2W3D1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ygorgarofalo.SpringU2W3D1.entities.Role;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.BadRequestExc;
import ygorgarofalo.SpringU2W3D1.exceptions.UnhautorizedExc;
import ygorgarofalo.SpringU2W3D1.payloads.UserLoginDTO;
import ygorgarofalo.SpringU2W3D1.payloads.UserPayloadDTO;
import ygorgarofalo.SpringU2W3D1.repositories.UserDAO;
import ygorgarofalo.SpringU2W3D1.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authUser(UserLoginDTO payload) {
        User userFound = userService.findByEmail(payload.email());

        if (passwordEncoder.matches(payload.password(), userFound.getPassword())) {
            return jwtTools.createToken(userFound);
        } else {
            throw new UnhautorizedExc("Credenziali non valide");
        }

    }


    // metodo richiamato dalla Post /users con payload

    public User saveUser(UserPayloadDTO payload) {
        User newUser = new User();
        newUser.setRole(Role.USER);
        newUser.setSurname(payload.surname());
        newUser.setName(payload.name());
        newUser.setEmail(payload.email());
        newUser.setUsername(payload.username());
        newUser.setPassword(passwordEncoder.encode(payload.password()));
        // controlli sull'esistenza sul db di email e username tramite due metodi booleani creati sul dao di User
        if (userDAO.existsByEmail(payload.email())) {
            throw new BadRequestExc("L'email " + payload.email() + " è gia presente nel sistema.");
        } else if (userDAO.existsByUsername(payload.username())) {
            throw new BadRequestExc("Lo username " + payload.username() + " è gia presente nel sistema.");
        } else {

            return userDAO.save(newUser);
        }

    }

}
