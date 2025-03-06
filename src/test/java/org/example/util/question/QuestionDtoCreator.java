package org.example.util.question;

import org.example.dto.QuestionDTO;

public class QuestionDtoCreator {

    public static QuestionDTO createQuestionToBeSaved(){
        return QuestionDTO.builder()
                .question("Qual sua cor favorita?")
                .build();
    }
}
