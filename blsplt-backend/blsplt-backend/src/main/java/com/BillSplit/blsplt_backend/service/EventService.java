package com.BillSplit.blsplt_backend.service;

import com.BillSplit.blsplt_backend.entity.Event;
import com.BillSplit.blsplt_backend.entity.Person;
import com.BillSplit.blsplt_backend.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class EventService implements IEventService{
    private EventRepository eventRepository;
    @Override
    public Event createEvent(Event event) {
    return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll().stream().toList();
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return eventRepository.save(event);
    }
}
