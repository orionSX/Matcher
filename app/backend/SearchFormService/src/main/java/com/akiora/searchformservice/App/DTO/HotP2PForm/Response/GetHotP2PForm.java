package com.akiora.searchformservice.App.DTO.HotP2PForm.Response;

import com.akiora.searchformservice.Domain.ValueObjects.GamePreferences;
import com.akiora.searchformservice.Domain.ValueObjects.PersonPreferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetHotP2PForm {
    
    private UUID id;    
    private UUID creatorId;
    private GamePreferences gamePreferences;
    private PersonPreferences personPreferences;
    private String description;
    private Instant createdAt;
}
