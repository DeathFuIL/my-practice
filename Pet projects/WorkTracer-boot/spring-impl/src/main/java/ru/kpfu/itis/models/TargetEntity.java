package ru.kpfu.itis.models;


import javax.persistence.*;
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
public class TargetEntity {

    private UUID id;

    private String name;

    private String description;

    private Date dateOfCreating;

    private Date dateOfUpdating;

    private Date approximateDeadline;

    private Date actualDeadline;

    private List<TaskEntity> tasks;

    private List<AccountEntity> responsibles;

    private List<AccountEntity> executors;

    private List<AccountEntity> spectators;

}
