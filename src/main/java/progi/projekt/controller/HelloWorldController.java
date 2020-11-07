package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import progi.projekt.security.AuthenticationRequest;
import progi.projekt.security.AuthenticationResponse;
import progi.projekt.security.StudentUserDetailsService;
import progi.projekt.security.jwt.JwtUtil;

//testiranje za jwt sessione
// /authenticate koji dobije username i password (AuthenticationRequest), a vraca jwt (AuthenticationResponse)

@RestController
class HelloWorldController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private StudentUserDetailsService studentUserDetailsService;


    @GetMapping("/")
    public String demo() {
        return "Hello there! I'm running.";
    }

    @RequestMapping({"/hello"})
    public String firstPage() {
        return "Hello World";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping({"/helloAdmin"})
    public String helloAdmin() {
        return "Hello Admin";
    }

    @Secured("ROLE_USER")
    @RequestMapping({"/helloUser"})
    public String helloUser() {
        return "Hello User";
    }

    @RequestMapping({"/admin"})
    public String userPage() {
        return "Admin page";
    }

    @RequestMapping({"/user"})
    public String adminPage() {
        return "User page";
    }


}
