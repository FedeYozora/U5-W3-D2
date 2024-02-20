package it.epicode.Device.Management.payloads;

import it.epicode.Device.Management.enums.StatusDevices;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewDeviceDTO(
        @NotNull(message = "Il tipo del device é obbligatorio")
        @Size(min = 3, message = "Inserire almeno 3 caratteri")
        String type,
        @NotNull
        StatusDevices statusDevices) {
}
