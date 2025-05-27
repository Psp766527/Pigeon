package com.daimlertrucksasia.it.dsc.pigeon.localization.infra;


import com.daimlertrucksasia.it.dsc.pigeon.localization.model.LocalizedMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<LocalizedMessageEntity,Long> {

    @Query(value = "{ 'msgTemplateID': ?0, 'locale': ?1 }", fields = "{ 'message': 1, '_id': 0 }")
    LocalizedMessageEntity findMessageByCodeAndLocale(String msgTemplateID, String locale);

}
