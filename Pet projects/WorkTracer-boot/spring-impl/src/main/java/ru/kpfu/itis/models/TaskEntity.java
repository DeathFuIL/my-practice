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
public class TaskEntity {

    private UUID id;

    private String name;

    private String description;

    private Date dateOfCreating;

    private Date dateOfUpdating;

    private Date endDate;

    private Date dateOfApproval;

    private String status;

    private AccountEntity assigner;

    private List<AccountEntity> executors;
}
