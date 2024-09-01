package com.BillSplit.blsplt_backend.service;

import com.BillSplit.blsplt_backend.DTO.PersonDto;
import com.BillSplit.blsplt_backend.entity.Person;
import com.BillSplit.blsplt_backend.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService implements IPersonService {
    
    private final PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) {
        System.out.println("Person  " + person.toString()+ " created.");
         personRepository.save(person);
        return person;
    }


    @Override
    public List<String> processPersonList() {
        List<String> messageList = new ArrayList<>();
        List<Person> personList = personRepository.findAll();
        BigDecimal total = personList.stream()
                .map(person -> BigDecimal.valueOf(person.getOdedigiTutar()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avg;
        if (personList.isEmpty()) {
            avg = BigDecimal.ZERO; // or any default value you prefer
        } else {
            avg = total.divide(BigDecimal.valueOf(personList.size()), 2, RoundingMode.HALF_UP);
        }

        // DTO dönüşüm ve personDto listesi kısmı
        List<PersonDto> personDtoList = new ArrayList<>();
        for (Person person : personList) {
            PersonDto personDto = new PersonDto();
            personDto.setAd(person.getAd());
            personDto.setSoyad(person.getSoyad());
            personDto.setOdedigiTutar(person.getOdedigiTutar());

            BigDecimal balance = BigDecimal.valueOf(person.getOdedigiTutar()).subtract(avg);
            personDto.setBalans(balance.doubleValue());

            personDtoList.add(personDto);
        }

        System.out.println(personList.size());
        assert !personDtoList.isEmpty();
        Stack<PersonDto> lessthenAVG = personDtoList.stream()
                .filter(w -> w.getBalans() < 0)
                .collect(Collectors.toCollection(Stack::new));
        Stack<PersonDto> morethenAVG = personDtoList.stream()
                .filter(w -> w.getBalans() > 0)
                .collect(Collectors.toCollection(Stack::new));
        System.out.println("ortalama : " + avg);
        messageList.add("Hesap Toplamı : " + total + " TL" );
        messageList.add("Kişi Başı Ödeme : " + avg + " TL"  );
        // BİLLSPLİT ALGORİTMASI
        while (!morethenAVG.isEmpty() && !lessthenAVG.isEmpty()) {

            PersonDto more = morethenAVG.peek();
            PersonDto less = lessthenAVG.peek();

            System.out.println("Şu anda işlem görüyor: " + more.getAd());
            System.out.println("Şu anda işlem görüyor: " + less.getAd());

            BigDecimal moreBalans = BigDecimal.valueOf(more.getBalans());
            BigDecimal lessBalans = BigDecimal.valueOf(less.getBalans()).negate();

            // Eğer alacak ve verecek miktarı eşitse
            if (moreBalans.compareTo(lessBalans) == 0) {
                messageList.add(less.getAd() + " " + less.getSoyad() + "  →    →    →  " + more.getAd() +
                        " " + more.getSoyad() +
                        "\t\n" + lessBalans.abs() + " TL" ); // lessBalans negatif olduğundan abs() alındı.
                System.out.println("silindi :  " + more.getAd() + " " + less.getAd());
                morethenAVG.pop();
                lessthenAVG.pop();
                continue;
            }
            // Alacak miktarı, verecek miktardan büyükse
            if (moreBalans.compareTo(lessBalans) > 0) {
                more.setBalans(moreBalans.subtract(lessBalans).doubleValue());

                messageList.add(less.getAd() + " " + less.getSoyad() + "  →    →    →  " + more.getAd() +
                        " " + more.getSoyad() +
                        "\t\n " + lessBalans.abs() + " TL" );

                System.out.println(more.getAd() + " kalan alacağı para : " + moreBalans);
                lessthenAVG.pop();
            } else {
                // Borç miktarı, alacak miktarından büyükse
                less.setBalans(lessBalans.subtract(moreBalans).negate().doubleValue());

                messageList.add(less.getAd() + " " + less.getSoyad() + "  →    →    →  " + more.getAd() +
                        " " + more.getSoyad() +
                        "\t\n" + moreBalans.doubleValue() + " TL" );

                System.out.println(less.getAd() + " vereceği para : " + lessBalans.doubleValue());
                morethenAVG.pop();
            }
        }
        return messageList.stream().toList();
    }

    @Override
    public void updatePerson(int index, Person person) {
            personRepository.save(person);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
        System.out.println("Person with ID " + id + " deleted.");
    }

    @Override
    public List<Person> getPersonList() {

        List<Person> personList = personRepository.findAll();
        return personList.stream().toList();
    }
    public List<Person> getPersonsByEventId(Long eventId) {
        return personRepository.findAllByEventId(eventId);
    }


 
}
