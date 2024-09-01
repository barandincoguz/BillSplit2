package com.BillSplit.blsplt_backend.service;

import com.BillSplit.blsplt_backend.entity.Event;
import com.BillSplit.blsplt_backend.entity.Person;

import java.util.List;

public interface IEventService {
    Event createEvent(Event event);
    List<Event> getAllEvents();
    void deleteEvent(Long id);
    Event updateEvent(Long id, Event event);
    
}
