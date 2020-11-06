package progi.projekt.security;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import progi.projekt.security.exception.JmbagNotFoundException;
import progi.projekt.security.exception.SavingException;
import progi.projekt.security.exception.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class SecurityExceptionHandler {

    //zar nije vec ResponseEntity definiran sa @ResponseStatus unutar same klase exceptiona?

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<?> handleUsernameNotFound(Exception e, WebRequest request) {
        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", "407");
        props.put("error", "Bad Request");
        return new ResponseEntity<>(props, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JmbagNotFoundException.class)
    protected ResponseEntity<?> handleJmbagNotFound(Exception e, WebRequest request) {
        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", "407");
        props.put("error", "Bad Request");
        return new ResponseEntity<>(props, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SavingException.class)
    protected ResponseEntity<?> handleSaving(Exception e, WebRequest request) {
        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", "407");
        props.put("error", "Bad Request");
        return new ResponseEntity<>(props, HttpStatus.BAD_REQUEST);
    }
}
