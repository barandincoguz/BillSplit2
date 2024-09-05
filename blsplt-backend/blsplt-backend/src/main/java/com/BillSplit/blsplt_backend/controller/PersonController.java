package com.BillSplit.blsplt_backend.controller;

import com.BillSplit.blsplt_backend.DTO.PersonDTO;
import com.BillSplit.blsplt_backend.entity.Event;
import com.BillSplit.blsplt_backend.entity.Person;
import com.BillSplit.blsplt_backend.service.EventService;
import com.BillSplit.blsplt_backend.service.IEventService;
import com.BillSplit.blsplt_backend.service.IPersonService;
import com.BillSplit.blsplt_backend.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/person")
@CrossOrigin
public class  PersonController {


private final IPersonService personService;
private final IEventService eventService;

    @PostMapping("/createPerson")
    public ResponseEntity<Person> createPerson(@RequestBody PersonDTO personDTO, @RequestParam(name = "eventId") Long eventId) {
        if (personDTO == null || eventId == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            // Event nesnesini al ve kişiye set et
            Optional<Event> event = eventService.getEventById(eventId);
            if (event.isPresent()) {
                Person person = new Person();
                person.setAd(personDTO.getAd());
                person.setSoyad(personDTO.getSoyad());
                person.setOdedigiTutar(personDTO.getOdedigiTutar());
                person.setEvent(event.get());
                Person createdPerson = personService.createPerson(person);
                System.out.println(createdPerson.toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


@GetMapping("/event/getList")
public List<Person> getPersonListByEvent(@RequestParam Long eventId) {
    return personService.getPersonListByEventId(eventId);
}


    @SneakyThrows
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
    public ResponseEntity<List<String>> processPersonList(@RequestParam Long eventId) {
        return ResponseEntity.ok(personService.processPersonList(eventId));
    }
}
