package it.epicode.Device.Management.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("C'é stato un errore nella tua request,controlla i dati inseriti.");
    }
}