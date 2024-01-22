package ygorgarofalo.SpringU2W3D1.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.BadRequestExc;
import ygorgarofalo.SpringU2W3D1.exceptions.NotFoundExc;
import ygorgarofalo.SpringU2W3D1.payloads.UserPayloadDTO;
import ygorgarofalo.SpringU2W3D1.reposiories.UserDAO;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    private Cloudinary cloudinary;


    // metodo richiamato dal get /users
    public Page<User> getUsers(int page, int size, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return userDAO.findAll(pageable);
    }


    // metodo richiamato dal Get /users/id

    public User findById(long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundExc(id));
    }


    // metodo richiamato dalla Post /users con payload

    public User saveUser(UserPayloadDTO payload) {


        User newUser = new User(payload.name(), payload.surname(), payload.email(), payload.username());

        // controlli sull'esistenza sul db di email e username tramite due metodi booleani creati sul dao di User
        if (userDAO.existsByEmail(payload.email())) {
            throw new BadRequestExc("L'email " + payload.email() + " è gia presente nel sistema.");
        } else if (userDAO.existsByUsername(payload.username())) {
            throw new BadRequestExc("Lo username " + payload.username() + " è gia presente nel sistema.");
        } else {

            return userDAO.save(newUser);
        }

    }


    //metodo richiamato da PUT /users/id

    public User findByIdAndUpdate(long id, UserPayloadDTO payload) {

        User foundUser = this.findById(id);

        if (userDAO.existsByEmail(payload.email())) {
            throw new BadRequestExc("L'email " + payload.email() + " è gia presente nel sistema.");
        } else if (userDAO.existsByUsername(payload.username())) {
            throw new BadRequestExc("Lo username " + payload.username() + " è gia presente nel sistema.");
        } else {
            foundUser.setName(payload.name());
            foundUser.setSurname(payload.surname());
            foundUser.setEmail(payload.email());
            foundUser.setUsername(payload.username());

            return userDAO.save(foundUser);
        }


    }


    // metodo richiamato da Delete /users/id

    public void findByidAndDelete(long id) {
        User foundUser = this.findById(id);

        userDAO.delete(foundUser);

    }


    // metodo richiamato dal PATCH con id e multipart file

    public String uploadImage(MultipartFile file, long userId) throws IOException {

        User found = this.findById(userId);

        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        found.setAvatarUrl(url);

        userDAO.save(found);

        return url;
    }

    public User findByEmail(String email) {

        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundExc(email));
    }

}
