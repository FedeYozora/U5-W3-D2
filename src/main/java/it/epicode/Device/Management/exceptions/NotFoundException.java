package it.epicode.Device.Management.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        super("Sorry, il device con id: " + id + " non è stato trovato...");
    }

    public NotFoundException(UUID uuid) {
        super("Sorry, l'utente con uuid: " + uuid + " non è stato trovato...");
    }
}
