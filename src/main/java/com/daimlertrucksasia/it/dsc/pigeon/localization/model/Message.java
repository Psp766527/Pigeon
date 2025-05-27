package com.daimlertrucksasia.it.dsc.pigeon.localization.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String requestId;           // Unique ID to correlate request/response
    private String sourceService;       // Who is sending the message
    private String targetService;       // Intended recipient service
    private String locale;              // "en", "ja", etc.
    private Object[] args;              // Values to be substituted in {0}, {1}, etc.
    private String content;
    private String resolvedMessage;     // Final message after localization (used in response)
    private long timestamp;

}
