package com.daimlertrucksasia.it.dsc.pigeon.localization.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Data
@Document(collection = "Localized_Message")
@CompoundIndex(name = "msg_locale_unique_idx", def = "{'msgTemplateID' : 1, 'locale': 1}", unique = true)
public class LocalizedMessageEntity {

    @Id
    private ObjectId id;

    private String msgTemplateID;

    private String locale;

    private String message;

    private String serviceProviderID;

    private String serviceConsumerID;

}
