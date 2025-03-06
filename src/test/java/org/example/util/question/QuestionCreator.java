package org.example.util.question;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.domain.Question;

import java.util.UUID;


@AllArgsConstructor
public class QuestionCreator {

    public static Question createQuestionValid(){
        return Question.builder()
                .id(UUID.fromString("cff1eb1c-d785-48f7-a666-c7e79f266dfa"))
                .question("Qual sua cor favorita?")
                .build();
    }
    public static Question createQuestionToBeSaved(){
        return Question.builder()
                .question("Qual sua cor favorita?")
                .build();
    }
}
