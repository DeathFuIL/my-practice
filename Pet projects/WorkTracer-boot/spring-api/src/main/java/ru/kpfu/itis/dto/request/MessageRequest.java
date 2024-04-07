package ru.kpfu.itis.dto.request;

import java.util.UUID;

public record MessageRequest(String text,
                             UUID replyTo) {
}
