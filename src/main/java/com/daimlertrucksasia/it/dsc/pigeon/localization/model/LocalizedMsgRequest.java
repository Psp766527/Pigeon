package com.daimlertrucksasia.it.dsc.pigeon.localization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocalizedMsgRequest {

    @JsonProperty("messageTemplateID")
    @NotNull
    private String msgTemplateID;

    @JsonProperty("locale")
    @NotNull
    private String locale;

    @JsonProperty("message")
    @NotNull
    private String message;

    @JsonProperty("serviceProviderID")
    @NotNull
    private String serviceProviderID;

    @JsonProperty("serviceConsumerID")
    @NotNull
    private String serviceConsumerID;


}
