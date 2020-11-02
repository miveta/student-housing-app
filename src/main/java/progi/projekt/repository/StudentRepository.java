package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {
	//ovo se autogenerira?
	Student findByJmbag(String jmbag);

	Student findByKorisnickoIme(String korisnickoIme);
}
