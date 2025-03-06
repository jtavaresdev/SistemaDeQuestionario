package org.example.integration;

import org.assertj.core.api.Assertions;
import org.example.domain.Question;
import org.example.dto.QuestionDTO;
import org.example.repository.QuestionRepository;
import org.example.util.question.QuestionCreator;
import org.example.util.question.QuestionDtoCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuestionControlerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("Save should persist question when successful")
    void save_ShouldPersistQuestion_WhenSuccessful() {

        QuestionDTO build = QuestionDtoCreator.createQuestionToBeSaved();


        ResponseEntity<Question> responseEntity = testRestTemplate.postForEntity("/question/", build
                , Question.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Save should return a HttpStatus Bad Request  when QuestionDto is wrong")
    void save_ShouldReturnBadRequest_WhenQuestionDtoIsWrong() {

        QuestionDTO build = new QuestionDTO();


        ResponseEntity<Question> responseEntity = testRestTemplate.postForEntity("/question", build
                , Question.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST);
        ;
    }


    @Test
    @DisplayName("Find All should returns all questions when successful")
    void findAll_ShouldReturnListOfQuestions_WhenSuccessful() {
        questionRepository.save(QuestionCreator.createQuestionToBeSaved());

        ResponseEntity<List<Question>> responseEntity = testRestTemplate.exchange("/question", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Question>>() {
                });

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().get(0).getQuestion()).isNotNull().isEqualTo(QuestionCreator.createQuestionValid().getQuestion());
    }

    @Test
    @DisplayName("Find by id  should return questions with the right id when successful")
    void findById_ShouldReturnQuestion_WhenSuccessful() {

        Question savedQuestion = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        UUID id = savedQuestion.getId();

        ResponseEntity<Object> responseEntity = testRestTemplate.exchange("/question/{id}", HttpMethod.GET, null,
                new ParameterizedTypeReference<Object>() {
                }, id);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Find by id  should return HttpStatus Not Foud when not find by id")
    void findById_ShouldReturnHttpStatusNotFound_WhenNotFindQuestion() {

        UUID nonExistentId = UUID.randomUUID();


        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                "/question/{id}",
                HttpMethod.GET,
                null,
                String.class,
                nonExistentId
        );

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update  should return updated question  when successful")
    void update_ShouldReturnUpdatedQuestion_WhenSuccessful() {
        QuestionDTO questionToBeSaved = QuestionDTO.builder().question("Nova question").build();
        Question questionSaved = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        UUID id = questionSaved.getId();

        ResponseEntity<Object> entity = testRestTemplate.exchange("/question/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(questionToBeSaved),
                new ParameterizedTypeReference<Object>(){},id);

        Assertions.assertThat(entity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(entity.getBody()).isNotNull();
    }


    @Test
    @DisplayName("Update  should return updated question  when successful")
    void update_ShouldReturnHttpStatusNotFound_WhenNotFindQuestionToUpdate() {
        QuestionDTO questionToBeSaved = QuestionDTO.builder().question("Nova question").build();
        Question questionSaved = questionRepository.save(QuestionCreator.createQuestionToBeSaved());
        UUID nonExistId = UUID.randomUUID();

        ResponseEntity<String> entity = testRestTemplate.exchange("/question/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(questionToBeSaved),
                String.class,nonExistId);

        Assertions.assertThat(entity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update  should return updated question  when successful")
    void delete_ShouldReturnHttpStatusNoContent_WhenSuccessful() {

        Question questionSaved = questionRepository.save(QuestionCreator.createQuestionToBeSaved());

        ResponseEntity<Question> question = testRestTemplate.exchange("/question/{id}", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Question>() {
                }, questionSaved.getId());

        Assertions.assertThat(question.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Update  should return updated question  when successful")
    void delete_ShouldReturnHttpStatusNoFound_WhenQuestionIdIsWrong() {

        Question questionSaved = questionRepository.save(QuestionCreator.createQuestionToBeSaved());

        ResponseEntity<String> question = testRestTemplate.exchange("/question/{id}", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<String>() {
                }, UUID.randomUUID());

        Assertions.assertThat(question.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }


}
