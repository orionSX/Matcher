package com.akiora.searchformservice.API.Contollers;


import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.App.Services.HotP2PFormService;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Infra.HotP2PFormRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotP2PFormController {
    
    @Autowired
    HotP2PFormService service;
    @PostMapping("/form")
    public ResponseEntity<String> addForm(@Valid  @RequestBody CreateHotP2PForm form)  {
        try {
            var createdForm = service.Create(form);
            return new ResponseEntity<>(createdForm.toString(), HttpStatus.OK);
        }
        catch (DomainException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/form")
    public ResponseEntity<List<HotP2PForm>> getForm(@RequestParam("creator_id") String id) throws DomainException {
        
        var forms = service.FindByCreatorId(id);
        return new ResponseEntity<>(forms, HttpStatus.OK);
        
        
    }
}
