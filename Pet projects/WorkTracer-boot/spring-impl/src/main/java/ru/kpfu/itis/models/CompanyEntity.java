package ru.kpfu.itis.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CompanyEntity {

    private UUID id;

    private String name;

    private Date foundingDate;

    private AddressEntity address;

    private String email;

    private String industry;

    private String phoneNumber;

}
