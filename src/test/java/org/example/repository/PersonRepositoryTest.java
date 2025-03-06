package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.domain.Person;
import org.example.util.person.PersonCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Test for PersonRepositoryTest")
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Save a person when successful")
    void save_PersistPerson_whenSuccessful(){
        Person personToBeSaved = PersonCreator.createPersonToBeSaved();

        Person savedPerson = personRepository.save(personToBeSaved);

        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson.getId()).isNotNull();
        Assertions.assertThat(savedPerson.getName()).isEqualTo(personToBeSaved.getName());

    }

    @Test
    @DisplayName("Find all returns a list of Anime when successful")
    void findAll_ReturnListOfAnime_whenSuccessful(){
        Person savedPerson = personRepository.save(PersonCreator.createPersonToBeSaved());
        List<Person> allPerson = personRepository.findAll();

        Assertions.assertThat(allPerson).isNotEmpty();
        Assertions.assertThat(allPerson.get(0)).isNotNull();
        Assertions.assertThat(allPerson.get(0).getId()).isNotNull();

    }

    @Test
    @DisplayName("Find by id returns a list of Anime when successful")
    void findById_ReturnAnime_whenSuccessful(){
        Person personId = PersonCreator.createPersonToBeSaved();
        personRepository.save(personId);
        Optional<Person> personOptional = personRepository.findById(personId.getId());

        Assertions.assertThat(personOptional).isNotEmpty();
        Assertions.assertThat(personOptional.get()).isNotNull().isEqualTo(personId);

    }
    @Test
    @DisplayName("Delete return void when successful")
    void delete_ReturnVoid_whenSuccessful(){
        Person personId = PersonCreator.createPersonToBeSaved();
        personRepository.save(personId);

        personRepository.delete(personId);
        Optional<Person> personOptional = personRepository.findById(personId.getId());

        Assertions.assertThat(personOptional).isEmpty();

    }


}