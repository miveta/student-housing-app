package progi.projekt.security;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import progi.projekt.security.exception.JmbagNotFoundException;
import progi.projekt.security.exception.SavingException;
import progi.projekt.security.exception.UsernameNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

//@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {
    //zar nije vec ResponseEntity definiran sa @ResponseStatus unutar same klase exceptiona?


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<?> handleUsernameNotFound(Exception e, WebRequest request) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(JmbagNotFoundException.class)
    protected ResponseEntity<?> handleJmbagNotFound(Exception e, WebRequest request) {
        return getResponseEntity(e);
    }

    private ResponseEntity<?> getResponseEntity(Exception e) {
        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", "407");
        props.put("error", "Bad Request");
        return new ResponseEntity<>(props, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SavingException.class)
    protected ResponseEntity<?> handleSaving(Exception e, WebRequest request) {
        return getResponseEntity(e);
    }


}
