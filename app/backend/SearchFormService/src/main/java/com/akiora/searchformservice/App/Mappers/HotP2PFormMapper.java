package com.akiora.searchformservice.App.Mappers;


import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.Domain.ValueObjects.GamePreferences;

import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class HotP2PFormMapper {

    public GamePreferences getGamePreferences(CreateHotP2PForm form) {
        return new GamePreferences(form.getMode(),
                form.getLookingForRanks(),
                form.getMyRoles(),
                form.getTeammateRoles(),
                form.getSmurfOnly());
    }
    public PersonPreferences getPersonPreferences(CreateHotP2PForm form) {
        return new PersonPreferences(form.getMinAge(),
                form.getMaxAge(),
                form.getGender(),
                form.getVoice());
    }
}
