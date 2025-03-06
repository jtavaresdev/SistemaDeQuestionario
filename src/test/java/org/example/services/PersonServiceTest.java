package org.example.services;

import org.assertj.core.api.Assertions;
import org.example.domain.Person;
import org.example.dto.PersonDTO;
import org.example.repository.PersonRepository;
import org.example.util.person.PersonCreator;
import org.example.util.person.PersonDtoCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    PersonService personServiceMock;

    @Mock
    PersonRepository personRepositoryMock;


    @Test
    @DisplayName("Save should persist anime when successful")
    void save_ShouldPersistPerson_WhenSuccessful(){
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        BDDMockito.when(personRepositoryMock.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreator.createPersonValid());

        ResponseEntity<Person> savedPerson = personServiceMock.save(validPersonDto);

        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(savedPerson.getBody().getId()).isNotNull();
        Assertions.assertThat(savedPerson.getBody().getName()).isNotNull().isEqualTo(validPersonDto.getName());
    }

    @Test
    @DisplayName("Find by id  should return anime when successful")
    void findById_ShouldReturnPerson_WhenSuccessful(){

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(PersonCreator.createPersonValid()));

        ResponseEntity<Object> responseEntity = personServiceMock.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Save should return a list anime when successful")
    void findAll_ShouldReturnListOfPerson_WhenSuccessful(){

        BDDMockito.when(personRepositoryMock.findAll())
                .thenReturn(List.of(PersonCreator.createPersonValid()));

        List<Person> savedPersonList = personServiceMock.findAll();

        Assertions.assertThat(savedPersonList).isNotEmpty().isNotNull();
        Assertions.assertThat(savedPersonList.get(0).getId()).isNotNull();
    }
    @Test
    @DisplayName("Find by id  should return Http Status Not found when not find anime")
    void findById_ShouldReturnHttpStatusNotFound_WhenSuccessful(){

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = personServiceMock.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("update return anime updated when successful")
    void update_ShouldUpdatePerson_WhenSuccessful(){
        Person personToBeUpdated = PersonCreator.createPersonValid();
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(PersonCreator.createPersonValid()));

        BDDMockito.when(personRepositoryMock.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreator.createPersonValid());

        ResponseEntity<Object> update = personServiceMock.update(personToBeUpdated.getId(), validPersonDto);
        Assertions.assertThat(update).isNotNull();
        Assertions.assertThat(update.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update return Http Status Not Found when person not found")
    void update_ShouldReturnHttpStatusNotFound_WhenPersonNotFound(){
        Person personToBeUpdated = PersonCreator.createPersonValid();
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> update = personServiceMock.update(personToBeUpdated.getId(), validPersonDto);

        Assertions.assertThat(update).isNotNull();
        Assertions.assertThat(update.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("update return Http Status Not Found when person not found")
    void delete_ShouldDeletePerson_WhenSuccessful(){
        Person personToBeUpdated = PersonCreator.createPersonValid();

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(PersonCreator.createPersonValid()));

        ResponseEntity<Object> delete = personServiceMock.delete(personToBeUpdated.getId());

        Assertions.assertThat(delete).isNotNull();
        Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("update return Http Status Not Found when person not found")
    void delete_ShouldReturnHttpStatusNotFound_WhenNotFoundPerson(){
        Person personToBeUpdated = PersonCreator.createPersonValid();

        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> delete = personServiceMock.delete(personToBeUpdated.getId());

        Assertions.assertThat(delete).isNotNull();
        Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}