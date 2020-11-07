package progi.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import progi.projekt.security.jwt.JwtRequestFilter;

@EnableWebSecurity //ukljucivanje provjere razine pristupa
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private StudentUserDetailsService studentUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentUserDetailsService);
        super.configure(auth);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(studentUserDetailsService);

        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.httpBasic();
        http.headers().frameOptions().sameOrigin(); //za h2, nama ne treba?
        http.csrf().disable();


        //sami radimo jwt sessione pa ih Spring ne mora voditi za nas:
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //s obzirom da nema session, trebamo filter koji provjerava Authorization header svakog requesta i postavlja
        // security context:
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //Authority permission mozemo postaviti i za putanju, ne samo za metodu
        http.authorizeRequests()
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user").hasAuthority("ROLE_STUDENT")
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/hello").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();

        //formLogin(); ne radi out of the box kod sa jwt


        //httpSecurity.exceptionHandling());


    }

}

//dodati @Secured("ROLE_ADMIN") na metode koje poziva admin, @Secured("ROLE_STUDENT") na metode koje poziva student
