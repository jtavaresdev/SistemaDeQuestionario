package org.example.services;

import org.assertj.core.api.Assertions;
import org.example.domain.Answer;
import org.example.dto.AnswerRequestDTO;
import org.example.repository.AnswerRepository;
import org.example.repository.PersonRepository;
import org.example.repository.QuestionRepository;
import org.example.util.answer.AnswerCreator;
import org.example.util.answer.AnswerRequestCreator;
import org.example.util.person.PersonCreator;
import org.example.util.question.QuestionCreator;
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
class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    AnswerRepository answerRepositoryMock;
    @Mock
    PersonRepository personRepositoryMock;
    @Mock
    QuestionRepository questionRepositoryMock;


    @Test
    @DisplayName("Save persistAnswer when Successful")
    void save_PersistAnswer_WhenSuccessful() {
        AnswerRequestDTO answerToBeSaved = AnswerRequestCreator.createValidAnswer();

        BDDMockito.when(personRepositoryMock.findById(answerToBeSaved.getPerson_id()))
                .thenReturn(Optional.of(PersonCreator.createPersonValid()));
        BDDMockito.when(questionRepositoryMock.findById(answerToBeSaved.getQuestion_id()))
                .thenReturn(Optional.of(QuestionCreator.createQuestionValid()));
        BDDMockito.when(answerRepositoryMock.save(ArgumentMatchers.any(Answer.class)))
                .thenReturn(AnswerCreator.createAnswerValid());

        ResponseEntity<Object> response = answerService.save(answerToBeSaved);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull().isEqualTo(AnswerCreator.createAnswerValid());
    }

    @Test
    @DisplayName("Save should return a response HTTP NOT FOUD when some ID is not found")
    void save_ReturnResponseNotFound_WhenNotFindPersonOrQuestionId() {
        AnswerRequestDTO answerToBeSaved = AnswerRequestCreator.createValidAnswer();

        BDDMockito.when(personRepositoryMock.findById(answerToBeSaved.getPerson_id()))
                .thenReturn(Optional.empty());
        BDDMockito.when(questionRepositoryMock.findById(answerToBeSaved.getQuestion_id()))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> response = answerService.save(answerToBeSaved);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Find by id return answer when Successful")
    void findById_ReturnAnswer_WhenSuccessful() {
        Answer answerValid = AnswerCreator.createAnswerValid();

        BDDMockito.when(answerRepositoryMock.findById(answerValid.getId()))
                .thenReturn(Optional.of(answerValid));


        ResponseEntity<List<Answer>> answerResponse = answerService.findById(answerValid.getId());

        Assertions.assertThat(answerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(answerResponse.getBody()).isNotNull().isEqualTo(List.of(AnswerCreator.createAnswerValid()));
    }

    @Test
    @DisplayName("Find by id  return a response HTTP NOT_FOUND when Successful")
    void findById_ReturnResponseHttpStatusNotFound_WhenDontFindId() {
        Answer answerValid = AnswerCreator.createAnswerValid();

        BDDMockito.when(answerRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());


        ResponseEntity<List<Answer>> answerResponse = answerService.findById(answerValid.getId());

        Assertions.assertThat(answerResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    @DisplayName("Update should return answer updated when Successful")
    void update_ShouldUpdateAnswer_WhenSuccessful() {
        Answer answerValid = AnswerCreator.createAnswerValid();
        AnswerRequestDTO answerRequestValid = AnswerRequestCreator.createValidAnswer();

        BDDMockito.when(answerRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(answerValid));
        BDDMockito.when(answerRepositoryMock.save(ArgumentMatchers.any(Answer.class)))
                .thenReturn(answerValid);


        ResponseEntity<Object> answerResponse = answerService.update(answerValid.getId(), answerRequestValid);

        Assertions.assertThat(answerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(answerResponse.getBody()).isNotNull().isEqualTo(AnswerCreator.createAnswerValid());
    }

    @Test
    @DisplayName("Update should return answer updated when Successful")
    void update_ShouldReturnResponseHttpNotFound_WhenNotFindTheAnswer() {
        AnswerRequestDTO answerRequestValid = AnswerRequestCreator.createValidAnswer();
        Answer answerValid = AnswerCreator.createAnswerValid();

        BDDMockito.when(answerRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> answerResponse = answerService.update(answerValid.getId(), answerRequestValid);

        Assertions.assertThat(answerResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete should return HTTP STATUS OK updated when Successful")
    void delete_ShouldReturnHttpStatusOk_WhenAnswerIsDeleted() {
        Answer answerValid = AnswerCreator.createAnswerValid();

        BDDMockito.when(answerRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(answerValid));

        ResponseEntity<Object> answerResponse = answerService.delete(answerValid.getId());

        Assertions.assertThat(answerResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete should return HTTP STATUS NOT FOUND  when not Successful")
    void delete_ShouldReturnHttpStatusNotFound_WhenAnswerIsNotFound() {
        Answer answerValid = AnswerCreator.createAnswerValid();

        BDDMockito.when(answerRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<Object> answerResponse = answerService.delete(answerValid.getId());

        Assertions.assertThat(answerResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
