package com.example.userservice.app.dtos.user;

import com.example.userservice.domain.models.Social;
import java.util.Map;

public class UpdateSocialsDTO {
    private Map<String, Social> socials;

    public UpdateSocialsDTO() {
    }

    public UpdateSocialsDTO(Map<String, Social> socials) {
        this.socials = socials;
    }

    public Map<String, Social> getSocials() {
        return socials;
    }

    public void setSocials(Map<String, Social> socials) {
        this.socials = socials;
    }
}
