package com.BillSplit.blsplt_backend.controller;

import com.BillSplit.blsplt_backend.entity.Event;
import com.BillSplit.blsplt_backend.service.EventService;
import com.BillSplit.blsplt_backend.service.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/event")
@CrossOrigin
public class EventController {
    private final EventService eventService;

    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event == null) {
            return ResponseEntity.badRequest().build(); // Eğer gönderilen veri boşsa 400 Bad Request döndür
        }
        try {
            System.out.println("tetiklendi");
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent); // 201 Created döndür ve oluşturulan nesneyi gövdeye ekle
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error döndür
        }
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = eventService.getAllEvents();
            if (events.isEmpty()) {
                // Eğer etkinlik listesi boşsa 204 No Content döndür
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(events); // 200 OK ve etkinlik listesini döndür
        } catch (Exception e) {
            // Hata yönetimi ve loglama
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error döndür
        }
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id,event) ;
        return ResponseEntity.status(HttpStatus.OK).body(updatedEvent);
    }
}

