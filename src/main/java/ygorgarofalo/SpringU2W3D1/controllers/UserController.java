package ygorgarofalo.SpringU2W3D1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.BadRequestExc;
import ygorgarofalo.SpringU2W3D1.payloads.UserPayloadDTO;
import ygorgarofalo.SpringU2W3D1.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String order) {
        return userService.getUsers(page, size, order);
    }


    // endpoints /me


    // con authenticationprincipal possiamo accedere direttamente ai dati dell'utente autenticato perchè
    // nel token vi è l'id dello user

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User user) {
        return user;
    }


    @DeleteMapping("/me")
    public void getMeAnDelete(@AuthenticationPrincipal User currentUser) {
        userService.findByidAndDelete(currentUser.getId());
    }


    //******************//

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return userService.findById(id);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody UserPayloadDTO updatedUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestExc(bindingResult.getAllErrors());
        } else {
            return userService.findByIdAndUpdate(id, updatedUser);
        }


    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.findByidAndDelete(id);
    }


    //PATCH per uploadare immagine e ricevere la stringa da salvare nel db

    @PatchMapping("/{userId}/upload")
    public String uploadAvatarImg(@RequestParam("image") MultipartFile file, @PathVariable long userId) throws Exception {
        return userService.uploadImage(file, userId);
    }


}
