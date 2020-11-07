package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import progi.projekt.security.AuthenticationRequest;
import progi.projekt.security.AuthenticationResponse;
import progi.projekt.security.StudentUserDetailsService;
import progi.projekt.security.jwt.JwtUtil;
import progi.projekt.service.StudentService;

import java.util.Collection;

@RestController
//@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentUserDetailsService studentUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    //'WebRequest request' je argument za citanje cijelog requesta


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        //try to authenticate the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException("Incorrect username or password", e);
        }

        //if the user successfully authenticated (correct login and pass) we return a jwt token:
        //get UserDetals from login
        final UserDetails userDetails = studentUserDetailsService
                .loadUserByUsername(authenticationRequest.getLogin());

        //generate a new jwt token from UserData
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        //return 200 OK with the jwt in the request body
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/userInfo")
    public String showLoggedInUser(@AuthenticationPrincipal User loggedInUser) {
        String username = loggedInUser.getUsername();
        Collection<GrantedAuthority> auths = loggedInUser.getAuthorities();
        String authsPrint = auths.toString();

        return "Username: " + username + " Auths: " + authsPrint;
    }

    /*TODO?
    @PostMapping("/login")
    public ResponseEntity<?> loginResponse(@AuthenticationPrincipal User userLoginData) {

        try {
            //TODO?


            //if successful
            Map<String, String> propsSucc = new HashMap<>();
            propsSucc.put("status", "200");
            return new ResponseEntity<>(propsSucc, HttpStatus.OK);
        }
        catch (UsernameNotFoundException e) {
            String originalMessage = e.getMessage();


            //if unsuccessful
            Map<String, String> propsFail = new HashMap<>();
            propsFail.put("status", "408");
            //propsFail.put("error", originalMessage);
            return new ResponseEntity<>(propsFail, HttpStatus.LENGTH_REQUIRED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerResponse(@AuthenticationPrincipal User userRegisterData) {
        // TODO
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_IMPLEMENTED);
    }
     */
}
