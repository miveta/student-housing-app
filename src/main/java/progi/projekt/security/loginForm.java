package progi.projekt.security;

public class loginForm {
	String login = null;
	String password = null;

	public loginForm(String name, String passhash) {
		this.login = name;
		this.password = passhash;
	}

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

	@Override
	public String toString() {
		return "loginForm{" +
				"name='" + login + '\'' +
				", passhash='" + password + '\'' +
				'}';
	}
}
