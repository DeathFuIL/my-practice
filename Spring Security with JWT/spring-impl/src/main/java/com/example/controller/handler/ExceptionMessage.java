package com.example.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class ExceptionMessage {

    private final String exceptionName;

    private final String message;

}
