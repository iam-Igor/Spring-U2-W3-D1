package ygorgarofalo.SpringU2W3D1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.UnhautorizedExc;
import ygorgarofalo.SpringU2W3D1.payloads.UserLoginDTO;
import ygorgarofalo.SpringU2W3D1.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;


    public String authUser(UserLoginDTO payload) {
        User userFound = userService.findByEmail(payload.email());

        if (payload.password().equals(userFound.getPassword())) {
            return jwtTools.createToken(userFound);
        } else {
            throw new UnhautorizedExc("Credenziali non valide");
        }

    }

}
