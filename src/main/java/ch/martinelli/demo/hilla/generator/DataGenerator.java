package ch.martinelli.demo.hilla.generator;

import ch.martinelli.demo.hilla.entity.Person;
import ch.martinelli.demo.hilla.repository.PersonRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataGenerator implements ApplicationRunner {

    private final PersonRepository repository;

    public DataGenerator(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        if (repository.count() != 0L) {
            logger.info("Using existing database");
            return;
        }
        int seed = 123;

        logger.info("Generating demo data");
        logger.info("... generating 100 Sample Person entities...");

        ExampleDataGenerator<Person> samplePersonRepositoryGenerator = new ExampleDataGenerator<>(
                Person.class, LocalDateTime.of(2022, 8, 29, 0, 0, 0));
        samplePersonRepositoryGenerator.setData(Person::setFirstName, DataType.FIRST_NAME);
        samplePersonRepositoryGenerator.setData(Person::setLastName, DataType.LAST_NAME);
        samplePersonRepositoryGenerator.setData(Person::setEmail, DataType.EMAIL);
        repository.saveAll(samplePersonRepositoryGenerator.create(100, seed));

        logger.info("Generated demo data");
    }
}
