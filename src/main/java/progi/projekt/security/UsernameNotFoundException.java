package progi.projekt.security;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class UsernameNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 10L;

	public UsernameNotFoundException(String message) {
		super(message);
	}
}
