package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.domain.Answer;
import org.example.util.answer.AnswerCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Test for Answer Repository")
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;


    @Test
    @DisplayName("Save persist answer when successful")
    void save_PersistAnswer_WhenSuccessful(){
        Answer answerToBeSaved = AnswerCreator.createAnswerToBeSaved();
        Answer saved = answerRepository.save(AnswerCreator.createAnswerToBeSaved());

        //AnswerToBeSaved nao tem ID.

        Assertions.assertThat(saved.getAnswer()).isNotNull().isEqualTo(answerToBeSaved.getAnswer());
        Assertions.assertThat(saved.getQuestion()).isNotNull().isEqualTo(answerToBeSaved.getQuestion());
        Assertions.assertThat(saved.getPerson()).isNotNull().isEqualTo(answerToBeSaved.getPerson());

    }

    @Test
    @DisplayName("Find by id return anime when successful")
    void findById_ReturnAnime_WhenSuccessful(){
        Answer answerToBeSaved = AnswerCreator.createAnswerToBeSaved();
        Answer savedAnswer = answerRepository.save(answerToBeSaved);

        Optional<Answer> optionalAnswer = answerRepository.findById(answerToBeSaved.getId());

        Assertions.assertThat(optionalAnswer).isNotEmpty();
        Assertions.assertThat(optionalAnswer.get()).isNotNull();
        Assertions.assertThat(optionalAnswer.get().getPerson()).isNotNull().isEqualTo(answerToBeSaved.getPerson());
        Assertions.assertThat(optionalAnswer.get().getAnswer()).isNotNull().isEqualTo(answerToBeSaved.getAnswer());
        Assertions.assertThat(optionalAnswer.get().getQuestion()).isNotNull().isEqualTo(answerToBeSaved.getQuestion());

    }

    @Test
    @DisplayName("delete a answer when successful")
    void delete_ReturnListOfAnswer_WhenSuccessful(){
        Assertions.assertThatCode(() -> answerRepository.delete(AnswerCreator.createAnswerToBeSaved()))
                .doesNotThrowAnyException();

    }

}