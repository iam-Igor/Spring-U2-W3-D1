package ygorgarofalo.SpringU2W3D1.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserPayloadDTO(

        @NotEmpty(message = "Campo obbligatorio per la proprietà name.")
        String name,

        @NotEmpty(message = "Campo obbligatorio per la proprietà surname.")
        String surname,

        @Email
        @NotEmpty(message = "L'indirizzo email inserito non è un indirizzo valido.")
        String email,

        @NotEmpty(message = "Lo username inserito è gia esistente")
        String username,

        @NotEmpty(message = "La password non può essere vuota.")
        @Size(min = 6, message = "La password deve avere una lunghezza minima di 6 caratteri.")
        String password


) {
}
