package com.akiora.searchformservice.Events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountParseEvent {
    private String formId;
    private String accountName;
    private String accountTag;
    private String accountServer;
}
