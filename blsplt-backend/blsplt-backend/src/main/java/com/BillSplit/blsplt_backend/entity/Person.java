package com.BillSplit.blsplt_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "person_billsplit")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ad", nullable = false)
    private String ad;
    @Column(name = "soyad", nullable = false)
    private String soyad;
    @Column(name ="odedigiTutar")
    private double odedigiTutar;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", odedigiTutar=" + odedigiTutar +
                '}';
    }
}
