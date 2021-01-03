package progi.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import progi.projekt.security.jwt.JwtRequestFilter;

@EnableWebSecurity //ukljucivanje provjere razine pristupa
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final KorisnikUserDetailsService korisnikUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(KorisnikUserDetailsService korisnikUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.korisnikUserDetailsService = korisnikUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(korisnikUserDetailsService);
    }

    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(korisnikUserDetailsService);

        //ako zelimo koristiti custom auth provider:
        //auth.authenticationProvider(myAuthProvider());
        super.configure(auth);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(korisnikUserDetailsService);

        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.httpBasic();
        http.headers().frameOptions().sameOrigin(); //za h2, nama ne treba?
        http.csrf().disable();

        //Authority permission mozemo postaviti i za putanju, ne samo za metodu
        http.authorizeRequests()
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user").hasAuthority("ROLE_STUDENT")
                .antMatchers("/hello").permitAll()
                // dozvolila sam sve auth da se prijave
                .antMatchers("/auth/*").permitAll()
                .antMatchers("/oglas/list").permitAll()
                .antMatchers("/lajk/*").permitAll()
                .antMatchers("/trazimSobu/*").permitAll()
        //.antMatchers("/").permitAll() //yes? no?
        ;

        //warning: ovo hoce bacati errore ako je neko od svojstava vec definirano
        super.configure(http);
    }
}
