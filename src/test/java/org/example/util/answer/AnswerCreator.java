package org.example.util.answer;

import org.example.domain.Answer;
import org.example.util.person.PersonCreator;
import org.example.util.question.QuestionCreator;

import java.util.UUID;

public class AnswerCreator {

    public static Answer createAnswerValid() {
        return Answer.builder()
                .id(UUID.fromString("47681662-dc48-440b-9130-7cc18dbf9f63"))
                .answer("Rosa")
                .question(QuestionCreator.createQuestionValid())
                .person(PersonCreator.createPersonValid())
                .build();
    }

    public static Answer createAnswerToBeSaved() {
        return Answer.builder()
                .answer("Rosa")
                .question(QuestionCreator.createQuestionValid())
                .person(PersonCreator.createPersonValid())
                .build();
    }
}
