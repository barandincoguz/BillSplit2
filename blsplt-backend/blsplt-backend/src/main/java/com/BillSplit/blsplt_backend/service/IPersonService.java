package com.BillSplit.blsplt_backend.service;


import com.BillSplit.blsplt_backend.Exceptions.EventNotFoundException;
import com.BillSplit.blsplt_backend.Exceptions.PersonNotFoundException;
import com.BillSplit.blsplt_backend.entity.Person;

import java.util.List;

public interface IPersonService {
Person createPerson(Person person);
List<String>  processPersonList(Long eventId);
void updatePerson( int index,  Person person) throws PersonNotFoundException, EventNotFoundException;
void deletePerson(Long id);
List<Person> getPersonListByEventId(Long eventId);
}
