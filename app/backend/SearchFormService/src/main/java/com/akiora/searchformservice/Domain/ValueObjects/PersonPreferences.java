package com.akiora.searchformservice.Domain.ValueObjects;

import com.akiora.searchformservice.Domain.Enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.naming.Name;

@Data
@AllArgsConstructor
@Builder
public class PersonPreferences{  
     
     @Schema(name="min_age")
     @Min(16)
     @Max(120)
     private Integer minAge;
     
     @Schema(name="max_age")
     @Min(16)
     @Max(120)
     private Integer maxAge;
     
     private Gender gender;
     
     @Builder.Default
     private Boolean voice=Boolean.TRUE;
     
     public static boolean isValid(PersonPreferences pp){
          if(pp.getMinAge() != null &&  pp.getMaxAge() != null ){
               return pp.getMaxAge() >= pp.getMinAge();
          }
          return true;
          
     }
     
}
