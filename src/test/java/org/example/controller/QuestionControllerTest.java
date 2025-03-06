package org.example.controller;

import org.assertj.core.api.Assertions;
import org.example.domain.Question;
import org.example.dto.QuestionDTO;
import org.example.repository.QuestionRepository;
import org.example.services.QuestionService;
import org.example.util.question.QuestionCreator;
import org.example.util.question.QuestionDtoCreator;
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
import java.util.UUID;


@ExtendWith(SpringExtension.class)
class QuestionControllerTest {

    @InjectMocks
    QuestionController questionController;
    @Mock
    QuestionService questionServiceMock;
    @Mock
    QuestionRepository questionRepositoryMock;

    @Test
    @DisplayName("Save should persist question when successful")
    void save_ShouldPersistQuestion_WhenSuccessful() {
        QuestionDTO questionToBeSaved = QuestionDtoCreator.createQuestionToBeSaved();
        Question questionValid = QuestionCreator.createQuestionValid();

        BDDMockito.when(questionRepositoryMock.save(ArgumentMatchers.any(Question.class)))
                .thenReturn(QuestionCreator.createQuestionValid());

        ResponseEntity<Question> responseEntity = questionController.save(questionToBeSaved);

        Assertions.assertThat(responseEntity.getBody()).isNotNull().isEqualTo(questionValid);
    }

    @Test
    @DisplayName("Find All should returns all questions when successful")
    void findAll_ShouldReturnListOfQuestions_WhenSuccessful() {

        BDDMockito.when(questionServiceMock.findAll())
                .thenReturn(List.of(QuestionCreator.createQuestionValid()));

        ResponseEntity<List<Question>> responseEntity = questionController.findAll();

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().get(0)).isNotNull().isEqualTo(QuestionCreator.createQuestionValid());
    }

    @Test
    @DisplayName("Find by id  should return questions with the right id when successful")
    void findById_ShouldReturnQuestion_WhenSuccessful() {

        BDDMockito.when(questionServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(QuestionCreator.createQuestionValid(), HttpStatus.OK));

        ResponseEntity<Object> responseEntity = questionController.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull().isEqualTo(QuestionCreator.createQuestionValid());
    }

    @Test
    @DisplayName("Find by id  should return HttpStatus Not Foud when not find by id")
    void findById_ShouldReturnHttpStatusNotFound_WhenNotFindQuestion() {

        BDDMockito.when(questionServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = questionController.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update  should return updated question  when successful")
    void update_ShouldReturnUpdatedQuestion_WhenSuccessful() {
        QuestionDTO questionToBeSaved = QuestionDtoCreator.createQuestionToBeSaved();

        BDDMockito.when(questionServiceMock.updade(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(QuestionDTO.class)))
                .thenReturn(new ResponseEntity<>(QuestionCreator.createQuestionValid(), HttpStatus.OK));

        ResponseEntity<Object> responseEntity = questionController.update(UUID.randomUUID(), questionToBeSaved);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull().isEqualTo(QuestionCreator.createQuestionValid());
    }


    @Test
    @DisplayName("Update  should return updated question  when successful")
    void update_ShouldReturnHttpStatusNotFound_WhenNotFindQuestionToUpdate() {
        QuestionDTO questionToBeSaved = QuestionDtoCreator.createQuestionToBeSaved();

        BDDMockito.when(questionServiceMock.updade(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(QuestionDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = questionController.update(UUID.randomUUID(), questionToBeSaved);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update  should return updated question  when successful")
    void delete_ShouldReturnHttpStatusNoContent_WhenSuccessful() {

        BDDMockito.when(questionServiceMock.delete(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Object> responseEntity = questionController.delete(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Update  should return updated question  when successful")
    void delete_ShouldReturnHttpStatusNoFound_WhenQuestionIdIsWrong() {

        BDDMockito.when(questionServiceMock.delete(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = questionController.delete(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

}