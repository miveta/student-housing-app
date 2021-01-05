package progi.projekt.forms;

import progi.projekt.model.Student;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {
    @NotBlank(message = "Morate unijeti svoje ime!")
    private String ime;

    @NotBlank(message = "Morate unijeti svoje prezime!")
    private String prezime;

    @Size(min = 10, max = 10, message = "JMBAG se mora sastojati od 10 znakova!")
    @Pattern(regexp = "\\d+", message = "JMBAG smije sadržavati samo znamenke!")
    private String jmbag;

    @NotBlank(message = "Morate unijeti svoje korisničko ime!")
    private String username;

    @Email(message = "Vaš mail mora imati strukturu maila!")
    private String email;

    @Size(min = 5)
    private String lozinka;

    private boolean obavijestiNaMail;

    public RegisterForm() {
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public boolean isObavijestiNaMail() {
        return obavijestiNaMail;
    }

    public void setObavijestiNaMail(boolean obavijestiNaMail) {
        this.obavijestiNaMail = obavijestiNaMail;
    }

    public Student fromRegisterForm(String passhash) {
        Student student = new Student();
        student.setIme(this.ime);
        student.setPrezime(this.prezime);
        student.setJmbag(this.jmbag);
        student.setKorisnickoIme(this.username);
        student.setEmail(this.email);

        student.setLozinka(passhash);

        // po defaultu stavi da dobiva obavijesti na mail - hardkodirano
        student.setObavijestiNaMail(this.obavijestiNaMail);
        return student;
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", jmbag='" + jmbag + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", obavijestiNaMail=" + obavijestiNaMail +
                '}';
    }
}
