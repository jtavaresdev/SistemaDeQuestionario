package org.example.services;

import org.example.domain.Answer;
import org.example.domain.Person;
import org.example.domain.Question;
import org.example.dto.AnswerRequestDTO;
import org.example.repository.AnswerRepository;
import org.example.repository.PersonRepository;
import org.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<Object> save(@RequestBody @Valid AnswerRequestDTO answerRequestDTO) {
        Optional<Person> person = personRepository.findById(answerRequestDTO.getPerson_id());
        Optional<Question> question = questionRepository.findById(answerRequestDTO.getQuestion_id());

        if (person.isEmpty() || question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person id or Question id not foud");
        }
        Answer entity = answerRequestDTO.toEntity(person.get(), question.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(answerRepository.save(entity));
    }

    public ResponseEntity<List<Answer>> findById(UUID id) {
        Optional<Answer> collectList = answerRepository.findById(id);
        if (collectList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>(List.of(collectList.get())));
    }

    public ResponseEntity<Object> update(UUID id, @RequestBody @Valid AnswerRequestDTO answerRequestDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer id not found");
        }
        Answer answer = optionalAnswer.get();
        answer.setAnswer(answerRequestDTO.getAnswer());
        return ResponseEntity.status(HttpStatus.OK).body(answerRepository.save(answer));
    }

    public ResponseEntity<Object> delete(UUID id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer id not found");
        }
        answerRepository.delete(optionalAnswer.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Answer deleted");

    }
}