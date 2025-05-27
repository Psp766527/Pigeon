package com.daimlertrucksasia.it.dsc.pigeon.localization.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Data
@Document(collection = "Localized_Message")
public class LocalizedMessageEntity {

    @Id
    private ObjectId id;

    private String msgTemplateID;

    private String locale;

    private String message;

    private String serviceProviderID;

    private String serviceConsumerID;

}
