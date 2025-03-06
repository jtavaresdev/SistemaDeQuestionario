package org.example.controller;


import org.example.domain.Person;
import org.example.dto.PersonDTO;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<Person> save(@RequestBody @Valid PersonDTO personDTO) {
        return personService.save(personDTO);
    }

    @GetMapping("/person")
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAll());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Object> findPersonById(@PathVariable(name = "id") UUID id) {
        return personService.findById(id);

    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Object> update(@PathVariable(name = "id") UUID id,
                                         @RequestBody @Valid PersonDTO personDTO) {
        return personService.update(id, personDTO);
    }

    @DeleteMapping("person/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") UUID id) {
        return personService.delete(id);
    }


}
