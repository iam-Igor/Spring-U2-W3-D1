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
import ygorgarofalo.SpringU2W3D1.responses.UserResponse;
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
    public UserResponse saveUser(@RequestBody @Validated UserPayloadDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestExc(bindingResult.getAllErrors());
        } else {
            User newuser = authService.saveUser(user);
            return new UserResponse(newuser.getId());
        }
    }


}
