package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.domain.Answer;
import org.example.domain.Person;
import org.example.domain.Question;

import java.util.Objects;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDTO {
    private String answer;
    private UUID person_id;
    private UUID question_id;

    public String getAnswer() {
        return answer;
    }

    public UUID getPerson_id() {
        return person_id;
    }

    public UUID getQuestion_id() {
        return question_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AnswerRequestDTO that = (AnswerRequestDTO) o;
        return Objects.equals(answer, that.answer) && Objects.equals(person_id, that.person_id) && Objects.equals(question_id, that.question_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, person_id, question_id);
    }

    public Answer toEntity(Person person, Question question){
        Answer answer = Answer.builder().build();
        answer.setAnswer(this.answer);
        answer.setPerson(person);
        answer.setQuestion(question);
        return answer;
    }
}
