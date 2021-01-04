package progi.projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progi.projekt.model.Student;
import progi.projekt.model.TrazeniUvjeti;

public interface UvjetiRepository extends JpaRepository<TrazeniUvjeti, Long> {
	TrazeniUvjeti findByTraziStudent(Student student);
}
