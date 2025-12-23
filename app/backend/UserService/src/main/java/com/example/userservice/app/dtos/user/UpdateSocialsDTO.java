package com.example.userservice.app.dtos.user;

import com.example.userservice.domain.models.Social;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public class UpdateSocialsDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    private Map<String, Social> socials;

    public UpdateSocialsDTO() {
    }

    public UpdateSocialsDTO(UUID userId, Map<String, Social> socials) {
        this.userId = userId;
        this.socials = socials;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Map<String, Social> getSocials() {
        return socials;
    }

    public void setSocials(Map<String, Social> socials) {
        this.socials = socials;
    }
}
