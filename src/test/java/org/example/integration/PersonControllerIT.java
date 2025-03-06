package org.example.integration;


import org.assertj.core.api.Assertions;
import org.example.domain.Person;
import org.example.dto.PersonDTO;
import org.example.repository.PersonRepository;
import org.example.util.person.PersonCreator;
import org.example.util.person.PersonDtoCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Save should persist person when successful")
    void save_ShouldPersistPerson_WhenSuccessful() {
        PersonDTO validPersonDto = PersonDtoCreator.createValidPersonDto();

        ResponseEntity<Person> responseEntity = testRestTemplate.postForEntity("/person", validPersonDto, Person.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Save should return HttpStatus Bad Request when person request is wrong")
    void save_ShouldReturnHttpStatusBadRequest_WhenRequestIsWrong() {
        PersonDTO validPersonDto = PersonDtoCreator.createInvalidPersonDto();

        ResponseEntity<Person> responseEntity = testRestTemplate.postForEntity("/person", validPersonDto, Person.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    @DisplayName("Find all should return all person when successful")
    void findAll_ShouldReturnAllPerson_WhenSuccessful() {
        Person savedPerson = personRepository.save(PersonCreator.createPersonToBeSaved());

        ResponseEntity<List<Person>> responseEntity = testRestTemplate.exchange("/person", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Person>>() {
                });

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().get(0).getId()).isNotNull();
    }

    @Test
    @DisplayName("Find by person id  should return  person when successful")
    void findByPersonId_ShouldReturnPerson_WhenSuccessful() {
        Person savedPerson = personRepository.save(PersonCreator.createPersonToBeSaved());

        ResponseEntity<Person> responseEntity = testRestTemplate.exchange("/person/{id}", HttpMethod.GET, null,
                new ParameterizedTypeReference<Person>() {
                }, savedPerson.getId());


        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(savedPerson);
    }

    @Test
    @DisplayName("Find by person id  should return HttpStatus not found when id is wrong")
    void findByPersonId_ShouldReturnHttpStatusNotFound_WhenNotSuccessful() {
        UUID nonExistId = UUID.randomUUID();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/person/{id}", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }, nonExistId);


        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    @DisplayName("Update should return HttpStatus Ok when successful")
    void update_ShouldHttpStatusOk_WhenSuccessful() {
        Person personBefore = personRepository.save(PersonCreator.createPersonToBeSaved());

        PersonDTO personDTO = PersonDTO.builder()
                .name("Beltrano")
                .email("beltrano@email.com")
                .idade(18)
                .altura(1.83).build();

        ResponseEntity<Person> responseEntity = testRestTemplate.exchange("/person/{id}", HttpMethod.PUT, new HttpEntity<>(personDTO)
                , new ParameterizedTypeReference<Person>() {
                }, personBefore.getId());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().getId()).isEqualTo(personBefore.getId());

    }

    @Test
    @DisplayName("Update should return HttpStatus not found when not found the person")
    void update_ShouldHttpStatusNotFound_WhenNotFindPerson(){
        PersonDTO personDTO = PersonDTO.builder()
                .name("Beltrano")
                .email("beltrano@email.com")
                .idade(18)
                .altura(1.83).build();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/person/{id}", HttpMethod.PUT, new HttpEntity<>(personDTO)
                , new ParameterizedTypeReference<String>() {
                }, UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete should return HttpStatus No Content when successful")
    void delete_ShouldHttpStatusNoContent_WhenSuccessful(){
        Person personBefore = personRepository.save(PersonCreator.createPersonToBeSaved());

        ResponseEntity<Person> responseEntity = testRestTemplate.exchange("/person/{id}", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Person>() {
                }, personBefore.getId());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Update should return HttpStatus No Content when successful")
    void delete_ShouldHttpStatusNotFound_WhenNotFindPerson(){
        UUID nonExistId = UUID.randomUUID();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/person/{id}", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<String>() {
                }, nonExistId);


        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
