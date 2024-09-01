package com.BillSplit.blsplt_backend.DTO;

import com.BillSplit.blsplt_backend.entity.Event;
import com.BillSplit.blsplt_backend.entity.Person;

import java.util.List;

public class EventDTO {
    private Event event;
    private List<Person> personList;
    private List<String > messageList;
}
