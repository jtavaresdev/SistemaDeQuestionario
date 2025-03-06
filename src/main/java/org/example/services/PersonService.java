package org.example.services;

import org.example.domain.Person;
import org.example.dto.PersonDTO;
import org.example.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
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
public class PersonService {
    @Autowired
    private PersonRepository personRepository;


    public ResponseEntity<Person> save(@RequestBody @Valid PersonDTO personDTO) {
        Person person = Person.builder().build();
        BeanUtils.copyProperties(personDTO, person);
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(person));
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public ResponseEntity<Object> findById(UUID id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(person.get());
    }

    public ResponseEntity<Object> update(UUID id, @RequestBody @Valid PersonDTO personDTO) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        Person person = personOptional.get();
        BeanUtils.copyProperties(personDTO, person);
        return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(person));
    }

    public ResponseEntity<Object> delete(UUID id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        personRepository.delete(person.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Person deleted");
    }
}
