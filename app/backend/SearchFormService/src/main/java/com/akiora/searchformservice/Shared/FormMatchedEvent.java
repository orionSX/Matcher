package com.akiora.searchformservice.Shared;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormMatchedEvent{
    @JsonProperty("form_id")
    public String formId;
    @JsonProperty("form_creator")
    public String formCreator;
    @JsonProperty("like_sender")
    public String likeSender;
    @JsonProperty("timestamp")
    @Builder.Default
    public Instant timestamp = Instant.now();
    
}
