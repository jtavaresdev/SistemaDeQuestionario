package org.example.controller;

import org.assertj.core.api.Assertions;
import org.example.domain.Answer;
import org.example.dto.AnswerRequestDTO;
import org.example.services.AnswerService;
import org.example.util.answer.AnswerCreator;
import org.example.util.answer.AnswerRequestCreator;
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
class AnswerControllerTest {

    @InjectMocks
    AnswerController answerController;

    @Mock
    AnswerService answerServiceMock;


    @Test
    @DisplayName("Save should persist answer when successful")
    void save_ShouldPersistAnswer_WhenSuccessful(){
        AnswerRequestDTO requestDTO = AnswerRequestCreator.createValidAnswer();

        BDDMockito.when(answerServiceMock.save(ArgumentMatchers.any(AnswerRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(AnswerCreator.createAnswerValid(), HttpStatus.OK));

        ResponseEntity<Object> responseEntity = answerController.save(requestDTO);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    @DisplayName("FInd by id should return a list with one answer when successful")
    void findById_ShouldReturnAnswer_WhenSuccessful(){

        BDDMockito.when(answerServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(List.of(AnswerCreator.createAnswerValid()), HttpStatus.OK));

        ResponseEntity<List<Answer>> responseEntity = answerController.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).hasSize(1);
    }
    @Test
    @DisplayName("FInd by id should return a list with one answer when successful")
    void findById_ShouldReturnHttpStatusNotFound_WhenNotFoundId(){

        BDDMockito.when(answerServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<> (HttpStatus.NOT_FOUND));

        ResponseEntity<List<Answer>> responseEntity = answerController.findById(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    @DisplayName("Update should return HttpStataus.Ok when successful")
    void update_ShouldHttpStatusOk_WhenSuccessful(){
        AnswerRequestDTO requestDTO = AnswerRequestCreator.createValidAnswer();

        BDDMockito.when(answerServiceMock.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(AnswerRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(AnswerCreator.createAnswerValid(), HttpStatus.OK));

        ResponseEntity<Object> responseEntity = answerController.update(UUID.randomUUID(), requestDTO);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();

    }
    @Test
    @DisplayName("Update should return HttpStatusNotFound when not find ")
    void update_ShouldHttpStatusNotFound_WhenSuccessful(){
        AnswerRequestDTO requestDTO = AnswerRequestCreator.createValidAnswer();

        BDDMockito.when(answerServiceMock.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(AnswerRequestDTO.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = answerController.update(UUID.randomUUID(), requestDTO);

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete should return HttpStataus No Content when successful")
    void delete_ShouldHttpStatusNoContent_WhenSuccessful(){

        BDDMockito.when(answerServiceMock.delete(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Object> responseEntity = answerController.delete(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete should return HttpStataus No Content when successful")
    void delete_ShouldHttpStatusNotFound_WhenAnswerIdIsWrong(){

        BDDMockito.when(answerServiceMock.delete(ArgumentMatchers.any(UUID.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<Object> responseEntity = answerController.delete(UUID.randomUUID());

        Assertions.assertThat(responseEntity.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }


}