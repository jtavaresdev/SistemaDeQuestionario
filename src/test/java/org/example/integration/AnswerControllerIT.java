package org.example.integration;

import org.assertj.core.api.Assertions;
import org.example.domain.Answer;
import org.example.domain.Person;
import org.example.domain.Question;
import org.example.dto.AnswerRequestDTO;
import org.example.repository.AnswerRepository;
import org.example.repository.PersonRepository;
import org.example.repository.QuestionRepository;
import org.example.util.person.PersonCreator;
import org.example.util.question.QuestionCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnswerControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @Test
    @DisplayName("Save should persist answer when successful")
    void save_ShouldPersistAnswer_WhenSuccessful() {
        Person person = personRepository.save(PersonCreator.createPersonToBeSaved());
        Question question = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        AnswerRequestDTO requestDTO = AnswerRequestDTO.builder()
                .person_id(person.getId())
                .question_id(question.getId())
                .answer("Answer test")
                .build();


        ResponseEntity<Answer> responseEntity = testRestTemplate.postForEntity("/answer", requestDTO, Answer.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody().getId()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getAnswer()).isNotNull().isEqualTo(requestDTO.getAnswer());
    }

    @Test
    @DisplayName("Save should return HttpStatus Not Found when some id is wrong")
    void save_ShouldHttpStatusNotFound_WhenSomeIdIsWrong() {
        Person person = personRepository.save(PersonCreator.createPersonToBeSaved());
        Question question = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        AnswerRequestDTO requestDTO = AnswerRequestDTO.builder()
                .person_id(UUID.randomUUID())
                .question_id(UUID.randomUUID())
                .answer("Answer test")
                .build();


        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/answer", requestDTO, String.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    @DisplayName("FInd by id should return a list with one answer when successful")
    void findById_ShouldReturnListOfAnswerOfOnePerson_WhenSuccessful() {

        Person person = personRepository.save(PersonCreator.createPersonToBeSaved());
        Question question = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        AnswerRequestDTO requestDTO = AnswerRequestDTO.builder()
                .person_id(person.getId())
                .question_id(question.getId())
                .answer("Answer test")
                .build();


        Answer answerSaved = testRestTemplate.postForEntity("/answer", requestDTO, Answer.class).getBody();

        ResponseEntity<List<Answer>> responseEntity = testRestTemplate.exchange("/answer/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Answer>>() {
                }, answerSaved.getId());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().get(0)).isNotNull().isEqualTo(answerSaved);
    }

    @Test
    @DisplayName("FInd by id should return HttpStatus not find when id is wrong")
    void findById_ShouldReturnHttpStatusNotFound_WhenIdIsWrong() {

        Person person = personRepository.save(PersonCreator.createPersonToBeSaved());
        Question question = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        AnswerRequestDTO requestDTO = AnswerRequestDTO.builder()
                .person_id(person.getId())
                .question_id(question.getId())
                .answer("Answer test")
                .build();

        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/answer/{id}",
                HttpMethod.GET,
                null,
                String.class,
                UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update should return HttpStataus.Ok when successful")
    void update_ShouldHttpStatusOk_WhenSuccessful() {
        Person person = personRepository.save(PersonCreator.createPersonToBeSaved());
        Question question = questionRepository.save(QuestionCreator.createQuestionToBeSaved());

        AnswerRequestDTO requestDtoBefore = AnswerRequestDTO.builder()
                .person_id(person.getId())
                .question_id(question.getId())
                .answer("Answer test")
                .build();

        Answer answerBefore = testRestTemplate.postForEntity("/answer", requestDtoBefore, Answer.class).getBody();

        AnswerRequestDTO requestDtoAfter = AnswerRequestDTO.builder()
                .answer("New Answer test")
                .person_id(person.getId())
                .question_id(question.getId())
                .build();

        ResponseEntity<Answer> answerAfter = testRestTemplate.exchange("/answer/{id}", HttpMethod.PUT,
                new HttpEntity<>(requestDtoAfter),
                new ParameterizedTypeReference<Answer>() {
                }, answerBefore.getId());

        Assertions.assertThat(answerAfter.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(answerAfter.getBody().getAnswer()).isEqualTo(requestDtoAfter.getAnswer());
        Assertions.assertThat(answerAfter.getBody().getId()).isEqualTo(answerBefore.getId());

    }

    @Test
    @DisplayName("Delete should return HttpStataus No Content when successful")
    void delete_ShouldHttpStatusNoContent_WhenSuccessful() {
        Person person = personRepository.save(PersonCreator.createPersonToBeSaved());
        Question question = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        AnswerRequestDTO requestDTO = AnswerRequestDTO.builder()
                .person_id(person.getId())
                .question_id(question.getId())
                .answer("Answer test")
                .build();

        Answer answer = testRestTemplate.postForEntity("/answer", requestDTO, Answer.class).getBody();

        ResponseEntity<Void> responseEntity =
                testRestTemplate.exchange(
                        "/answer/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        answer.getId());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete should return HttpStataus Not Found  when id is wrong")
    void delete_ShouldHttpStatusNotFound_WhenAnswerIdIsWrong() {

        ResponseEntity<Void> responseEntity =
                testRestTemplate.exchange(
                        "/answer/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

}
