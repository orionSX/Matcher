package com.akiora.searchformservice.Domain.ValueObjects;


import com.akiora.searchformservice.Domain.Enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;




@Data
@AllArgsConstructor
public class UserData{
    private Integer age;
    private String name; 
    private Socials socials;
    private Gender gender;
}