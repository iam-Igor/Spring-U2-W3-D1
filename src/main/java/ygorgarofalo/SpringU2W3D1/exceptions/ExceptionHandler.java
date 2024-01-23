package ygorgarofalo.SpringU2W3D1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ygorgarofalo.SpringU2W3D1.payloads.errors.ErrorsPayloadWithList;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestExc.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayloadWithList handleBadRequest(BadRequestExc e) {
        List<String> errorsMessages = new ArrayList<>();
        if (e.getErrorsList() != null)
            errorsMessages = e.getErrorsList().stream().map(err -> err.getDefaultMessage()).toList();
        return new ErrorsPayloadWithList(e.getMessage(), errorsMessages);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnhautorizedExc.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsBody handleUnauthorized(UnhautorizedExc e) {
        return new ErrorsBody(e.getMessage());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundExc.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsBody handleNotFoundExc(NotFoundExc ex) {
        return new ErrorsBody(ex.getMessage());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorsBody handleAccessDenied(AccessDeniedException ex) {
        return new ErrorsBody("Il tuo ruolo non permette di accedere a questa funzionalità!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsBody handleGenericError(Exception ex) {
        ex.printStackTrace();
        return new ErrorsBody("Problema lato server.");
    }
}

