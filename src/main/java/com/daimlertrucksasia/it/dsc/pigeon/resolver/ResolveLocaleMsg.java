package com.daimlertrucksasia.it.dsc.pigeon.resolver;

import com.daimlertrucksasia.it.dsc.pigeon.localization.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResolveLocaleMsg {

    private MessageService messageService;

    public String getResolvedMsg(String msgTemplateId, Object[] args, Locale locale){
        return messageService.getMessage(msgTemplateId, args, locale);}

}
