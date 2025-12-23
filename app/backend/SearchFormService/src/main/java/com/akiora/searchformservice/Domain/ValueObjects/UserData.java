package com.akiora.searchformservice.Domain.ValueObjects;


import com.akiora.searchformservice.Domain.Enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData{
    private Integer age;
    private String nickname;
    @Builder.Default
    private List<String> socials = new ArrayList<>();

    private Gender gender;
    private String email;
}