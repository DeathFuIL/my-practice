package ru.kpfu.itis.dto.response;

import java.sql.Date;
import java.util.UUID;

public record MessageResponse(UUID id,
                              AccountResponse sender,
                              String text,
                              Date dateOfCreating,
                              Date dateOfUpdating,
                              UUID replyTo){
}
