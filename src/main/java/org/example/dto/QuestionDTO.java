package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.domain.Question;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    @NotBlank
    private String question;


    public String getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDTO that = (QuestionDTO) o;
        return Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(question);
    }

    public Question toEntity(){
        Question questionClass = Question.builder().build();
        questionClass.setQuestion(this.question);
        return questionClass;
    }
}
