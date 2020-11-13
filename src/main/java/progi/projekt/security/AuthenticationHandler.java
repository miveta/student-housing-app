package progi.projekt.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            throw new BadCredentialsException("Korisničko ime nije upisano.");

        String requested = data.getPassword();
        String stored = userDetails.getPassword();

        //debug
        /*
        System.out.println("requested (plaintext): " + requested);
        System.out.println("stored (salted hash): " + stored);
        System.out.println("matches (boolean): " + pswdEncoder.matches(requested, stored));
        */

        if (!pswdEncoder.matches(requested, stored)) {
            if (!requested.equals(stored)) //dok hashing u seedu ne radi
                throw new BadCredentialsException("Unijeli ste netočno korisničko ime ili lozinku.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
