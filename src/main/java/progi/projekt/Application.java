package progi.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import progi.projekt.security.StudentUserDetailsService;

@SpringBootApplication
public class Application {

    @Bean
    public PasswordEncoder pswdEncoder() {
        //privremeno ne radim hash passworda dok frontend ne implementira hashing sa svoje strane
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
