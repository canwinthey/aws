package com.canwinthey.aws.controller;

import com.canwinthey.aws.entity.Person;
import com.canwinthey.aws.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @GetMapping("/")
    public String welcome(){
        return "Hello Prasanna !!";
    }

    @PostMapping("/savePerson")
    public Person savePerson(@RequestBody Person person) {
        return repository.addPerson(person);
    }

    @GetMapping("/getPerson/{personId}")
    public Person findPerson(@PathVariable String personId) {
        return repository.findPersonByPersonId(personId);
    }

    @DeleteMapping("/deletePerson")
    public String deletePerson(@RequestBody Person person) {
        return repository.deletePerson(person);
    }

    @PutMapping("/editPerson")
    public String updatePerson(@RequestBody Person person) {
        return repository.editPerson(person);
    }

}
