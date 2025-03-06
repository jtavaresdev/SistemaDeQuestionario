package org.example.services;

import org.assertj.core.api.Assertions;
import org.example.domain.Question;
import org.example.dto.QuestionDTO;
import org.example.repository.QuestionRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionServiceMock;

    @Mock
    QuestionRepository questionRepositoryMock;


    @Test
    @DisplayName("Find all should return list of question when successful")
    void findAll_ShouldReturnListOfQuestions_WhenSuccessful(){

        BDDMockito.when(questionRepositoryMock.findAll())
                .thenReturn(List.of(QuestionCreator.createQuestionValid()));

        List<Question> all = questionServiceMock.findAll();

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all.get(0)).isNotNull();

    }

    @Test
    @DisplayName("Find all should return list of question when successful")
    void findById_ShouldReturnLQuestion_WhenSuccessful(){

        BDDMockito.when(questionRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(QuestionCreator.createQuestionValid()));

        ResponseEntity<Object> responseEntity = questionServiceMock.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    @DisplayName("Find all Should Return HttpStatus NotFound when not found")
    void findById_ShouldReturnHttpStatusNotFound_WhenNotFoundQuestion(){

        BDDMockito.when(questionRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = questionServiceMock.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Find all should return list of question when successful")
    void update_ShouldReturnQuestionUpdated_WhenSuccessful(){

        Question questionValid = QuestionCreator.createQuestionValid();

        BDDMockito.when(questionRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(QuestionCreator.createQuestionValid()));


        ResponseEntity<Object> updade = questionServiceMock.updade(questionValid.getId(), QuestionDtoCreator.createQuestionToBeSaved());

        Assertions.assertThat(updade).isNotNull();
        Assertions.assertThat(updade.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Find all should return list of question when successful")
    void update_ShouldReturnHttpStatusNotFound_WhenNotSuccessful(){

        Question questionValid = QuestionCreator.createQuestionValid();

        BDDMockito.when(questionRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());


        ResponseEntity<Object> updade = questionServiceMock.updade(questionValid.getId(), QuestionDtoCreator.createQuestionToBeSaved());

        Assertions.assertThat(updade).isNotNull();
        Assertions.assertThat(updade.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Find all should return list of question when successful")
    void delete_ShouldReturnHttpStatusOk_WhenSuccessful(){

        Question questionValid = QuestionCreator.createQuestionValid();

        BDDMockito.when(questionRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(questionValid));

        ResponseEntity<Object> delete = questionServiceMock.delete(questionValid.getId());

        Assertions.assertThat(delete).isNotNull();
        Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Find all should return list of question when successful")
    void delete_ShouldReturnHttpStatusNotFound_WhenNotFound(){

        Question questionValid = QuestionCreator.createQuestionValid();

        BDDMockito.when(questionRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> delete = questionServiceMock.delete(questionValid.getId());

        Assertions.assertThat(delete).isNotNull();
        Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}