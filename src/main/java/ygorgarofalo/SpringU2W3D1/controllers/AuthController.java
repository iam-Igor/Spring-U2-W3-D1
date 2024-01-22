package ygorgarofalo.SpringU2W3D1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ygorgarofalo.SpringU2W3D1.payloads.UserLoginDTO;
import ygorgarofalo.SpringU2W3D1.payloads.UserLoginResponseDTO;
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

}
