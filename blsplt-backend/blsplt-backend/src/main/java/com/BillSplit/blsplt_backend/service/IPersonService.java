package com.BillSplit.blsplt_backend.service;


import com.BillSplit.blsplt_backend.entity.Person;

import java.util.List;

public interface IPersonService {
Person createPerson(Person person);
List<String>  processPersonList();
void updatePerson( int index,  Person person);
void deletePerson(Long id);
List<Person> getPersonList();
List<Person> getPersonsByEventId(Long eventId);
}
