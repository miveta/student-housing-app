package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import progi.projekt.forms.LoginForm;
import progi.projekt.forms.RegisterForm;
import progi.projekt.model.Student;
import progi.projekt.security.AuthenticationHandler;
import progi.projekt.security.StudentUserDetailsService;
import progi.projekt.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private StudentUserDetailsService studentUserDetailsService;

    @Autowired
    private AuthenticationHandler authenticationHandler;

    @Autowired
    private StudentService studentService;

    //'@AuthenticationPrincipal WebRequest request' je argument za citanje cijelog requesta
    //'@RequestBody LoginForm loginForm' je argument za citanje cijelog bodya requesta
    //'@AuthenticationPrincipal User userLoginData' vraca podatke o korisniku u security contextu
    @CrossOrigin
    @GetMapping("/userInfo")
    public String showLoggedInUser(@AuthenticationPrincipal User loggedInUser) {
        String username = loggedInUser.getUsername();
        Collection<GrantedAuthority> auths = loggedInUser.getAuthorities();
        String authsPrint = auths.toString();

        return "Username: " + username + " Auths: " + authsPrint;
    }

    @CrossOrigin
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Validated RegisterForm registerForm) {
        boolean alreadyExists = studentService.findBykorisnickoIme(registerForm.getUsername()).isPresent();

        Map<String, String> props = new HashMap<>();
        if (alreadyExists) {
            props.put("error", "User with this name already exists!");
            return new ResponseEntity<>(props, HttpStatus.BAD_REQUEST);
        }

        Student student = registerForm.fromRegisterForm();
        student = studentService.createStudent(student);

        return new ResponseEntity<>(props, HttpStatus.OK);
    }

    //front mora poslati 'loginForm' objekt, controller vraca 200 za dobre podatke, 401 za lose
    //Header: Content-Type=application/json
    //body:
    /*
        {
            "login": "user",
            "password": "pass"
        }
    */
    @CrossOrigin
    @PostMapping(value = "/checklogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseBody
    public ResponseEntity<?> loginResponse(@RequestBody LoginForm data) {
        try {
            Authentication auth = authenticationHandler.authenticate(data);

            //if successful
            Map<String, String> propsSucc = new HashMap<>();
            propsSucc.put("status", "200");
            return new ResponseEntity<>(propsSucc, HttpStatus.OK);

        } catch (Exception e) {
            //if unsuccessful (user doesnt exist or login and password dont match
            Map<String, String> propsFail = new HashMap<>();
            propsFail.put("status", "401");
            propsFail.put("error", e.getMessage());
            return new ResponseEntity<>(propsFail, HttpStatus.UNAUTHORIZED);
        }
    }



    /*
    @PostMapping("/register")
    public ResponseEntity<?> registerResponse(@AuthenticationPrincipal User userRegisterData) {
        // TODO
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_IMPLEMENTED);
    }
    */

}
