package com.daimlertrucksasia.it.dsc.pigeon.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String errorCode;
    private String message;
    private String path;
}
