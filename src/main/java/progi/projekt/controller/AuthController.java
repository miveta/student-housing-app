package progi.projekt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import progi.projekt.dto.KorisnikDTO;
import progi.projekt.forms.LoginForm;
import progi.projekt.forms.RegisterForm;
import progi.projekt.model.Korisnik;
import progi.projekt.model.Student;
import progi.projekt.security.AuthenticationHandler;
import progi.projekt.security.KorisnikUserDetailsService;
import progi.projekt.service.StudentService;
import progi.projekt.service.ZaposlenikSCService;


import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final KorisnikUserDetailsService korisnikUserDetailsService;

	private final AuthenticationHandler authenticationHandler;

	private final StudentService studentService;

	private final ZaposlenikSCService zaposlenikscService;

	private final PasswordEncoder pswdEncoder;

	public AuthController(KorisnikUserDetailsService korisnikUserDetailsService,
						  AuthenticationHandler authenticationHandler, StudentService studentService,
						  ZaposlenikSCService zaposlenikscService, PasswordEncoder pswdEncoder) {
		this.korisnikUserDetailsService = korisnikUserDetailsService;
		this.authenticationHandler = authenticationHandler;
		this.studentService = studentService;
		this.zaposlenikscService = zaposlenikscService;
		this.pswdEncoder = pswdEncoder;
	}

	//'@AuthenticationPrincipal WebRequest request' je argument za citanje cijelog requesta
	//'@RequestBody LoginForm loginForm' je argument za citanje objekta iz bodya requesta
	//'@AuthenticationPrincipal User userLoginData' vraca podatke o korisniku u security contextu

	//region Backend testiranje sessiona
	// not currently used
    /*
    @GetMapping("/userInfo")
    public ResponseEntity<?> showLoggedInUser(@AuthenticationPrincipal User loggedInUser) {
        if (Objects.isNull(loggedInUser)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nema ulogiranog korisnika!");
        }
        //String username = loggedInUser.getUsername();
        //Collection<GrantedAuthority> auths = loggedInUser.getAuthorities();
        //String authsPrint = auths.toString();
        Student student = studentService.findBykorisnickoIme(loggedInUser.getUsername()).get();
        KorisnikDTO studentDTO = new KorisnikDTO(student);
        return ResponseEntity.ok(studentDTO);
    }
    */
	//endregion

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody @Validated RegisterForm registerForm) {
		if (studentService.studentExists(registerForm.getUsername())) {
			return ResponseEntity.badRequest().body("Već postoji korisnik s tim imenom!");

		}

		// ne provjeravamo zaposlenike zato što se oni unose u bazu preko backenda

		//izrada Student modela
		String passhash = pswdEncoder.encode(registerForm.getLozinka());
		Student student = registerForm.fromRegisterForm(passhash);
		student = studentService.createStudent(student);

		//login nakon registracije
		LoginForm data = new LoginForm(registerForm.getUsername(), registerForm.getLozinka());
		Authentication auth = authenticationHandler.authenticate(data);
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);

		KorisnikDTO studentDTO = new KorisnikDTO(student);
		return ResponseEntity.ok(studentDTO);
	}

	/*
	//front mora poslati 'loginForm' objekt, controller vraca 200 za dobre podatke, 401 za lose
	//body responsa je orignalna poruka o gresci
	//postman:
	//Header: Content-Type=application/json
	//body:
		{
			"username": "user",
			"password": "pass"
		}
	*/
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody LoginForm data) {
		try {
			Authentication auth = authenticationHandler.authenticate(data);

			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);

			//nije potrebno dok koristimo Springove sessione
			//HttpSession session = req.getSession(true);
			//session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

			//if successful
			// authentication handler will throw a badcredentials exception if the student doesnt exist before it reaches this step
			Korisnik korisnik;

            Optional<Student> student = studentService.findByKorisnickoIme(data.getUsername());
			korisnik = student.isPresent() ? student.get() : zaposlenikscService.findBykorisnickoIme(data.getUsername()).get();

			KorisnikDTO korisnikDTO = new KorisnikDTO(korisnik);

			return ResponseEntity.ok(korisnikDTO);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
