package it.epicode.Device.Management.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = "L'username é obbligatorio")
        @Size(min = 3, message = "L'username deve essere almeno di 3 caratteri")
        String username,
        @NotEmpty(message = "Il nome é obbligatorio")
        @Size(min = 2, message = "Il nome deve avere almeno 2 caratteri")
        String name,
        @NotEmpty(message = "Il cognome é obbligatorio")
        @Size(min = 2, message = "Il cognome deve avere almeno 2 caratteri")
        String surname,
        @NotEmpty(message = "La email é obbligatoria")
        @Email(message = "Inserire una mail valida")
        String email,
        String password,
        String avatar) {
}
