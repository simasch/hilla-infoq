package ch.martinelli.demo.hilla.endpoint;

import ch.martinelli.demo.hilla.entity.Person;
import ch.martinelli.demo.hilla.repository.PersonRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class PersonEndpoint {

    private final PersonRepository repository;

    @Autowired
    public PersonEndpoint(PersonRepository repository) {
        this.repository = repository;
    }

    @Nonnull
    public List<@Nonnull Person> findAll() {
        return repository.findAll(Sort.by("firstName", "lastName"));
    }

    @Nonnull
    public Person save(@Nonnull Person entity) {
        return repository.save(entity);
    }
}
