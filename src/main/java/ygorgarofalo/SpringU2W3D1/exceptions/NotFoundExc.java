package ygorgarofalo.SpringU2W3D1.exceptions;

public class NotFoundExc extends RuntimeException {

    public NotFoundExc(long id) {
        super("Item with id: " + id + " not found");
    }


    public NotFoundExc(String email) {
        super("Utente con l'email " + email + "non trovato.");
    }
}