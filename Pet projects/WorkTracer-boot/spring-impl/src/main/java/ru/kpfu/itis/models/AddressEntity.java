package ru.kpfu.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AddressEntity {

    private UUID id;

    private String city;

    private String state;

    private String country;

    private int postalCode;

}
