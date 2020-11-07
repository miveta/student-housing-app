package progi.projekt.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import progi.projekt.security.StudentUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//za debugging koristiti Postman jer browseri pohrane i securityContext uz jwt pa se 2. i 3. if ne izvrse:
//1. napraviti POST /authenticate sa
//Header: Content-Type=application/json
//body:
/*
    {
            "login": "user",
            "password": "pass"
    }
*/
//2. dobiveni response (jwt) kopirati
//3. napraviti novi header Authorization=Bearer {$jwt}
//note: sada vise Content-Type header i body nisu potrebni

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private StudentUserDetailsService studentUserDetailsService;

    public JwtRequestFilter(JwtUtil jwtTokenUtil, StudentUserDetailsService studentUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.studentUserDetailsService = studentUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //reads the "authorization" header
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        //extracts data from the authorization header/jwt
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtTokenUtil.extractUsername(jwt);
        }

        //this is the only difference between this and the default implementation:
        //manually setting the security context with the data extracted from jwt
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.studentUserDetailsService.loadUserByUsername(username);

            //if the username matches and the token is not expired (if the jwt data is valid)
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        //continue the security filter chain
        chain.doFilter(request, response);
    }

}
