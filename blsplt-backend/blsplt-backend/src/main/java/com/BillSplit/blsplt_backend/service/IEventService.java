package com.BillSplit.blsplt_backend.service;

import com.BillSplit.blsplt_backend.Exceptions.EventNotFoundException;
import com.BillSplit.blsplt_backend.entity.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    Event createEvent(Event event);
    List<Event> getAllEvents();
    void deleteEvent(Long id);
    Event updateEvent(Long id, Event event) throws EventNotFoundException;
    Optional<Event> getEventById(Long id);
    
}
