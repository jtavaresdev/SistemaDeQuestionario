package org.example.util.answer;

import org.example.dto.AnswerRequestDTO;
import org.example.util.person.PersonCreator;
import org.example.util.question.QuestionCreator;

public class AnswerRequestCreator {

    public static AnswerRequestDTO createValidAnswer(){
        return  AnswerRequestDTO.builder()
                .person_id(PersonCreator.createPersonValid().getId())
                .question_id(QuestionCreator.createQuestionValid().getId())
                .answer("Rosa")
                .build();
    }
}
