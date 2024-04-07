package ru.kpfu.itis.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountEntity {

    private UUID id;

    private String name;

    private String surname;

    private Date birthday;

    private String email;

    private AddressEntity address;

    private String profilePhoto;

    private Date dateOfCreating;

    private String position;

    private String phoneNumber;

    private List<AccountEntity> friends;

    private String password;

}
