package org.example.controller;

import org.example.domain.Question;
import org.example.dto.QuestionDTO;
import org.example.repository.QuestionRepository;
import org.example.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/question")
    public ResponseEntity<Question> save(@RequestBody @Valid QuestionDTO questionDTO) {
        Question question = questionDTO.toEntity();
        return ResponseEntity.status(HttpStatus.CREATED).body(questionRepository.save(question));
    }

    @GetMapping("/question")
    public ResponseEntity<List<Question>> findAll() {
        List<Question> allQuestion = questionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allQuestion);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Object> findById(@PathVariable(name = "id") UUID id) {
        return questionService.findById(id);

    }

    @PutMapping("/question/{id}")
    public ResponseEntity<Object> update(@PathVariable(name = "id") UUID id,
                                         @RequestBody @Valid QuestionDTO questionDTO) {
        return questionService.updade(id, questionDTO);
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") UUID id) {
        return questionService.delete(id);
    }


}
