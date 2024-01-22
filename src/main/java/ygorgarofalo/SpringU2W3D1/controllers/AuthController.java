package ygorgarofalo.SpringU2W3D1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.BadRequestExc;
import ygorgarofalo.SpringU2W3D1.payloads.UserLoginDTO;
import ygorgarofalo.SpringU2W3D1.payloads.UserLoginResponseDTO;
import ygorgarofalo.SpringU2W3D1.payloads.UserPayloadDTO;
import ygorgarofalo.SpringU2W3D1.payloads.UserResponseDTO;
import ygorgarofalo.SpringU2W3D1.services.AuthService;
import ygorgarofalo.SpringU2W3D1.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {


        String accessToken = authService.authUser(payload);
        return new UserLoginResponseDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Validated UserPayloadDTO newUserPayload, BindingResult validation) {
        // Per completare la validazione devo in qualche maniera fare un controllo del tipo: se ci sono errori -> manda risposta con 400 Bad Request
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestExc("Ci sono errori nel payload!"); // L'eccezione arriverà agli error handlers tra i quali c'è quello che manderà la risposta con status code 400
        } else {
            User newUser = userService.saveUser(newUserPayload);

            return new UserResponseDTO(newUser.getId());
        }
    }
}
