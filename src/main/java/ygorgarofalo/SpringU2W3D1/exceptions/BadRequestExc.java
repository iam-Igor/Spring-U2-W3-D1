package ygorgarofalo.SpringU2W3D1.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestExc extends RuntimeException {

    private List<ObjectError> errorsList;

    public BadRequestExc(String message) {
        super(message);
    }


    public BadRequestExc(List<ObjectError> errorsList) {
        super("Errori nel body");
        this.errorsList = errorsList;
    }

}