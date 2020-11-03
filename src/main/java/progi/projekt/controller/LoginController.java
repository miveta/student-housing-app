package progi.projekt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import progi.projekt.model.Student;
import progi.projekt.security.StudentUserDetailsService;
import progi.projekt.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//tko uopce hendla sessione? Kako uskladiti permission management sa autentifikacijom sessiona?
//ako Spring hendla session, frontend autentifikaciju preko state managementa, a backend permissione (automatski
// preko StudentUserDetalisService?), sto sprjecava korisnika da se ulogira svojim accountom, dobije permission i onda
// rucno posalje request sa drugacijim usernamom tako otvori neciji tudji account page?

//sadrzi li svaki request user i pass onda? Rjesava li to Spring ili mi?

//ako svaki request sadrzi user i pass, rade li Spring ili React automatski hashing ili se login salje kao plain text
// u zahtjevu?



@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentUserDetailsService studentUserDetailsService;

	//'WebRequest request' je argument za citanje cijelog requesta




	@GetMapping("")
	public String showLoggedInUser(@AuthenticationPrincipal User loggedInUser) {
		return "Tu nije nista implementirano";
	}



	//front mora poslati 'User' objekt
	@PostMapping("")
	public ResponseEntity<?> loginResponse(@AuthenticationPrincipal User userLoginData) {
		String username = userLoginData.getUsername();
		String passHash = userLoginData.getPassword();

		try{
			UserDetails studentUBazi = studentUserDetailsService.loadUserByUsername(username);

			//if successful
			Map<String, String> propsSucc = new HashMap<>();
			propsSucc.put("status", "200");
			return new ResponseEntity<>(propsSucc, HttpStatus.OK);
		}
		catch (UsernameNotFoundException e){
			String originalMessage = e.getMessage();

			//if unsuccessful
			Map<String, String> propsFail = new HashMap<>();
			propsFail.put("status", "401");
			//propsFail.put("error", originalMessage);
			return new ResponseEntity<>(propsFail, HttpStatus.UNAUTHORIZED);
		}

	}

}
