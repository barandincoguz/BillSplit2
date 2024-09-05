package com.BillSplit.blsplt_backend.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PersonDto {
    private String ad;
    private String soyad;
    private double odedigiTutar;
    private double balans;

}
