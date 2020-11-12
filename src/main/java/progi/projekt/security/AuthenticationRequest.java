package progi.projekt.security;

import java.io.Serializable;

//trebati ce za jwt sessione

public class AuthenticationRequest implements Serializable {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //need default constructor for JSON Parsing
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.setLogin(username);
        this.setPassword(password);
    }

    /*
    postman:
    Headers: Content-Type=application/json
    body:   {
                "login": "user",
                "password": "pass"
            }
     */
}
