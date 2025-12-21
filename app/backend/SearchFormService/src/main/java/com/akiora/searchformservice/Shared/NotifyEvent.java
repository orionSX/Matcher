package com.akiora.searchformservice.Shared;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyEvent {
    public String user_id;
    
    public String message;
}
