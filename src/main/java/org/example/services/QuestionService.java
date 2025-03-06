package org.example.services;


import org.example.domain.Question;
import org.example.dto.QuestionDTO;
import org.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;


    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public ResponseEntity<Object> findById(UUID id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(question.get());
    }

    public ResponseEntity<Object> updade(UUID id, @RequestBody @Valid QuestionDTO questionDTO) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        Question question = questionOptional.get();
        question.setQuestion(questionDTO.getQuestion());
        return ResponseEntity.status(HttpStatus.OK).body(questionRepository.save(question));
    }

    public ResponseEntity<Object> delete(UUID id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        questionRepository.delete(question.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Question deleted");
    }
}
