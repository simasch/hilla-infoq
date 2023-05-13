package ch.martinelli.demo.hilla.repository;

import ch.martinelli.demo.hilla.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
