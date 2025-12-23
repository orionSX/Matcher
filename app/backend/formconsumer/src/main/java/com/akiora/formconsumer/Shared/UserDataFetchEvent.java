package com.akiora.formconsumer.Shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataFetchEvent {
    private String formId;
    private String creatorId;
}
