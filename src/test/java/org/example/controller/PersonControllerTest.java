package org.example.controller;

import org.assertj.core.api.Assertions;
import org.example.domain.Person;
import org.example.dto.PersonDTO;
import org.example.services.PersonService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @InjectMocks
    PersonController personController;
    @Mock
    PersonService personService;

    @Test
    @DisplayName("Save should persist person when successful")
    void save_ShouldPersistPerson_WhenSuccessful(){
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();
        Person personValid = PersonCreator.createPersonValid();

        BDDMockito.when(personService.save(ArgumentMatchers.any(PersonDTO.class)))
                .thenReturn(new ResponseEntity<>(PersonCreator.createPersonValid(), HttpStatus.CREATED));

        ResponseEntity<Person> responseEntity = personController.save(validPersonDto);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(personValid);
    }

    @Test
    @DisplayName("Find all should return all person when successful")
    void findAll_ShouldReturnAllPerson_WhenSuccessful(){
        Person personValid = PersonCreator.createPersonValid();

        BDDMockito.when(personService.findAll())
                .thenReturn(new ArrayList<>(List.of(personValid)));

        ResponseEntity<List<Person>> responseEntity = personController.findAll();

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().get(0)).isEqualTo(personValid);
    }

    @Test
    @DisplayName("Find by person id  should return  person when successful")
    void findByPersonId_ShouldReturnPerson_WhenSuccessful(){

        BDDMockito.when(personService.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(PersonCreator.createPersonValid(), HttpStatus.OK));

        ResponseEntity<Object> responseEntity = personController.findPersonById(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Find by person id  should return HttpStatus not found when id is wrong")
    void findByPersonId_ShouldReturnHttpStatusNotFound_WhenNotSuccessful(){

        BDDMockito.when(personService.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = personController.findPersonById(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update should return HttpStatus Ok when successful")
    void update_ShouldHttpStatusOk_WhenSuccessful(){
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();
        Person personValid = PersonCreator.createPersonValid();

        BDDMockito.when(personService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(PersonDTO.class)))
                .thenReturn(new ResponseEntity<>(PersonCreator.createPersonValid(), HttpStatus.OK));

        ResponseEntity<Object> responseEntity = personController.update(UUID.randomUUID(), validPersonDto);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(personValid);

    }

    @Test
    @DisplayName("Update should return HttpStatus not found when not found the person")
    void update_ShouldHttpStatusNotFound_WhenNotSuccessful(){
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        BDDMockito.when(personService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(PersonDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = personController.update(UUID.randomUUID(), validPersonDto);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete should return HttpStatus No Content when successful")
    void delete_ShouldHttpStatusNoContent_WhenSuccessful(){
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        BDDMockito.when(personService.delete(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));


        ResponseEntity<Object> responseEntity = personController.delete(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Update should return HttpStatus No Content when successful")
    void delete_ShouldHttpStatusNotFound_WhenNotFindPerson(){
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        BDDMockito.when(personService.delete(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));


        ResponseEntity<Object> responseEntity = personController.delete(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}