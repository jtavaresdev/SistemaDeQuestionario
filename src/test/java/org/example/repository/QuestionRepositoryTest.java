package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.domain.Question;
import org.example.util.question.QuestionCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;


    @Test
    @DisplayName("Save persist question when successful")
    void save_PersistQuestion_WhenSuccessful(){
        Question questionToBeSaved = QuestionCreator.createQuestionToBeSaved();

        Question savedQuestion = questionRepository.save(questionToBeSaved);

        Assertions.assertThat(savedQuestion).isNotNull();
        Assertions.assertThat(savedQuestion.getQuestion()).isNotNull().isEqualTo(questionToBeSaved.getQuestion());
        Assertions.assertThat(savedQuestion.getId()).isNotNull();

    }
    @Test
    @DisplayName("Find all returns List of question when successful")
    void findAll_ReturnListQuestion_WhenSuccessful(){
        Question questionToBeSaved = QuestionCreator.createQuestionToBeSaved();
        questionRepository.save(questionToBeSaved);
        List<Question> savedQuestion = questionRepository.findAll();

        Assertions.assertThat(savedQuestion).isNotNull().isNotEmpty();
        Assertions.assertThat(savedQuestion.get(0).getQuestion()).isNotNull().isEqualTo(questionToBeSaved.getQuestion());
        Assertions.assertThat(savedQuestion.get(0).getId()).isNotNull();

    }
    @Test
    @DisplayName("find by id returns List of question when successful")
    void findById_ReturnQuestion_WhenSuccessful(){
        Question questionToBeSaved = QuestionCreator.createQuestionToBeSaved();
        questionRepository.save(questionToBeSaved);
        Optional<Question> savedQuestion = questionRepository.findById(questionToBeSaved.getId());

        Assertions.assertThat(savedQuestion).isNotNull().isNotEmpty();
        Assertions.assertThat(savedQuestion.get().getQuestion()).isNotNull().isEqualTo(questionToBeSaved.getQuestion());
        Assertions.assertThat(savedQuestion.get().getId()).isNotNull();

    }

    @Test
    @DisplayName("Delete returns void when successful")
    void delete_ReturnVoid_WhenSuccessful(){
        Question questionToBeSaved = QuestionCreator.createQuestionToBeSaved();
        Question saved = questionRepository.save(questionToBeSaved);
        questionRepository.delete(saved);
        Optional<Question> savedQuestion = questionRepository.findById(saved.getId());

        Assertions.assertThat(savedQuestion).isEmpty();

    }
}