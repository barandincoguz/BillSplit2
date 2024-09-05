package com.BillSplit.blsplt_backend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PersonDTO {
    private String ad;
    private String soyad;
    private double odedigiTutar;
    private double balans;

}
