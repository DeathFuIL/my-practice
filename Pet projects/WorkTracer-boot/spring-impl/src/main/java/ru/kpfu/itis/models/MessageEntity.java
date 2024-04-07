package ru.kpfu.itis.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageEntity {

    UUID id;

    AccountEntity sender;

    String text;

    Date dateOfCreating;

    Date dateOfUpdating;

    UUID replyTo;

}
