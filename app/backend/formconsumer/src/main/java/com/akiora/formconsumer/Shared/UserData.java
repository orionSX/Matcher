package com.akiora.formconsumer.Shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private List<String> socials;
    private Integer age;
    private String gender;
    private String nickname;
    private String email;
}
