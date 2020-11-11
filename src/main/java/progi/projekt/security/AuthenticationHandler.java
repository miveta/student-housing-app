package progi.projekt.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import progi.projekt.forms.LoginForm;

@Component
public class AuthenticationHandler {
    private final KorisnikUserDetailsService korisnikUserDetailsService;

    private final PasswordEncoder pswdEncoder;

    public AuthenticationHandler(KorisnikUserDetailsService korisnikUserDetailsService, PasswordEncoder pswdEncoder) {
        this.korisnikUserDetailsService = korisnikUserDetailsService;
        this.pswdEncoder = pswdEncoder;
    }

    public Authentication authenticate(LoginForm data) {
        UserDetails userDetails = korisnikUserDetailsService.loadUserByUsername(data.getUsername());

        if (userDetails == null)
            throw new BadCredentialsException("Username not provided");

        if (!pswdEncoder.matches(data.getPassword(), userDetails.getPassword()))
            throw new BadCredentialsException("Username or password is wrong");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
