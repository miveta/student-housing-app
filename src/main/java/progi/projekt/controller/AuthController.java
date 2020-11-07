package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import progi.projekt.security.*;
import progi.projekt.security.jwt.JwtUtil;
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


    //'@AuthenticationPrincipal WebRequest request' je argument za citanje cijelog requesta
    //'@RequestBody LoginForm loginForm' je argument za citanje cijelog bodya requesta
    //'@AuthenticationPrincipal User userLoginData' vraca podatke o korisniku u security contextu

    @GetMapping("/userInfo")
    public String showLoggedInUser(@AuthenticationPrincipal User loggedInUser) {
        String username = loggedInUser.getUsername();
        Collection<GrantedAuthority> auths = loggedInUser.getAuthorities();
        String authsPrint = auths.toString();

        return "Username: " + username + " Auths: " + authsPrint;
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
    @PostMapping(value="/checklogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseBody
    public ResponseEntity<?> loginResponse(@RequestBody loginForm data) {

        String username = data.getLogin();
        String password = data.getPassword();

        try {
            UserDetails userDetails = this.studentUserDetailsService.loadUserByUsername(username);

            Authentication auth = authenticationHandler.authenticate(data);

            //if successful
            Map<String, String> propsSucc = new HashMap<>();
            propsSucc.put("status", "200");
            return new ResponseEntity<>(propsSucc, HttpStatus.OK);

        } catch (Exception e) {
            String originalMessage = e.getMessage();

            //if unsuccessful (user doesnt exist or login and password dont match
            Map<String, String> propsFail = new HashMap<>();
            propsFail.put("status", "401");
            propsFail.put("error", originalMessage);
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
