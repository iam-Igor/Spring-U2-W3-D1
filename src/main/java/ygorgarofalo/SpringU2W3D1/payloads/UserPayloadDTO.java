package ygorgarofalo.SpringU2W3D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserPayloadDTO(

        @NotEmpty(message = "Campo obbligatorio per la proprietà name.")
        String name,

        @NotEmpty(message = "Campo obbligatorio per la proprietà surname.")
        String surname,

        @Email
        @NotEmpty(message = "L'indirizzo email inserito non è un indirizzo valido.")
        String email,

        @NotEmpty(message = "Lo username inserito è gia esistente")
        String username) {
}
