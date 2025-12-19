package com.akiora.searchformservice.App.DTO.HotP2PForm.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DislikeFormRequest {
    
    @NotBlank
    @Schema(name = "form_id", description = "ID формы для дизлайка", requiredMode = Schema.RequiredMode.REQUIRED)
    private String formId;
    
    @NotBlank
    @Schema(name = "user_id", description = "ID пользователя, который дизлайкает", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
}
