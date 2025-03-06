package org.example.controller;

import org.example.domain.Answer;
import org.example.dto.AnswerRequestDTO;
import org.example.repository.AnswerRepository;
import org.example.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Controller
public class AnswerController {


    @Autowired
    private AnswerService answerService;


    @PostMapping("/answer")
    public ResponseEntity<Object> save (@RequestBody @Valid AnswerRequestDTO answerRequestDTO){
        return answerService.save(answerRequestDTO);
    }
    @GetMapping("/answer/{id}")
    public ResponseEntity<List<Answer>> findById(@PathVariable(name = "id") UUID id){
        return answerService.findById(id);
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<Object> update(@PathVariable(name = "id") UUID id,
                                         @RequestBody @Valid AnswerRequestDTO answerRequestDTO){
        return answerService.update(id, answerRequestDTO);
    }

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") UUID id){
       return answerService.delete(id);
    }
}
