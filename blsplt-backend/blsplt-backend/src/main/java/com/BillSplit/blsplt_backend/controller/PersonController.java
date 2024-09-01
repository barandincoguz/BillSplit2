package com.BillSplit.blsplt_backend.controller;

import com.BillSplit.blsplt_backend.entity.Person;
import com.BillSplit.blsplt_backend.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/person")
@CrossOrigin
public class PersonController {


private final PersonService personService;

    @PostMapping("/createPerson")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        if (person == null) {
            return ResponseEntity.badRequest().build();
        }
        try {

            Person createdPerson = personService.createPerson(person);
            System.out.println(createdPerson.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
        } catch (Exception e) {
            // Hata mesajını loglayabilirsiniz
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/event/{eventId}")
    public List<Person> getPersonsByEvent(@PathVariable Long eventId) {
        return personService.getPersonsByEventId(eventId);
    }


    @PutMapping("/update/{index}")
    public ResponseEntity<Void> updatePerson(@PathVariable int index, @RequestBody Person person) {
        personService.updatePerson(index, person);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/process")
    public ResponseEntity<List<String>> processPersonList() {
        return ResponseEntity.ok(personService.processPersonList());
    }
    @GetMapping("/list")
    public ResponseEntity<List<Person>> getPersonList() {
        return ResponseEntity.ok(personService.getPersonList());
    }

}
