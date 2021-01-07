package progi.projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    @Bean
    public PasswordEncoder pswdEncoder() {
        //return NoOpPasswordEncoder.getInstance(); //ako zelimo pospremati plaintext password umjesto hasha
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
