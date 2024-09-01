package com.BillSplit.blsplt_backend.repository;
import com.BillSplit.blsplt_backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findAllByEventId(Long eventId);
}
