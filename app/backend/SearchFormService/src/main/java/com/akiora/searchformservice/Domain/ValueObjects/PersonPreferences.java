package com.akiora.searchformservice.Domain.ValueObjects;

import com.akiora.searchformservice.Domain.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonPreferences{
    
     private Integer minAge;
   
     private Integer maxAge;
     private Gender gender;
     private Boolean voice;
     
     public static boolean isValid(PersonPreferences pp){
          if(pp.getMinAge() != null &&  pp.getMaxAge() != null ){
               return pp.getMaxAge() >= pp.getMinAge();
          }
          return true;
          
     }
     
}
