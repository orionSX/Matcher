package com.akiora.searchformservice.API.Contollers;


import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.DislikeFormRequest;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.LikeFormRequest;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.UpdateHotP2PForm;
import com.akiora.searchformservice.App.Services.HotP2PFormService;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Infra.HotP2PFormRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forms")
public class HotP2PFormController {
    
    @Autowired
    HotP2PFormService service;
    
    @PostMapping
    public ResponseEntity<?> addForm(@Valid @RequestBody CreateHotP2PForm form) {
       
        try {
            var createdForm = service.Create(form);
            return new ResponseEntity<>(createdForm, HttpStatus.CREATED);
        }
        catch (DomainException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/by-creator/{creator_id}")
    public ResponseEntity<?> getFormsByCreator(@PathVariable("creator_id") String creatorId) {
        try {
            var forms = service.FindByCreatorId(creatorId);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (DomainException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{form_id}")
    public ResponseEntity<?> getFormById(@PathVariable("form_id") String formId) {
        var form = service.FindById(formId);
        if (form.isPresent()) {
            return new ResponseEntity<>(form.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping
    public ResponseEntity<List<HotP2PForm>> getAllForms() {
        var forms = service.FindAll();
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }
    
    @PostMapping("/like")
    public ResponseEntity<?> likeForm(@Valid @RequestBody LikeFormRequest request) {
        try {
            var form = service.LikeForm(request.getFormId(), request.getUserId());
            if (form.isPresent()) {
                return new ResponseEntity<>(form.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
        } catch (DomainException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/dislike")
    public ResponseEntity<?> dislikeForm(@Valid @RequestBody DislikeFormRequest request) {
        try {
            var form = service.DislikeForm(request.getFormId(), request.getUserId());
            if (form.isPresent()) {
                return new ResponseEntity<>(form.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
        } catch (DomainException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{form_id}")
    public ResponseEntity<?> updateForm(
            @PathVariable("form_id") String formId,
            @Valid @RequestBody UpdateHotP2PForm updateData) {
        try {
            var form = service.UpdateForm(formId, updateData);
            if (form.isPresent()) {
                return new ResponseEntity<>(form.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
        } catch (DomainException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/liked-by/{user_id}")
    public ResponseEntity<List<HotP2PForm>> getFormsLikedByUser(@PathVariable("user_id") String userId) {
        var forms = service.FindLikedByUser(userId);
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }
    
    @GetMapping("/disliked-by/{user_id}")
    public ResponseEntity<List<HotP2PForm>> getFormsDislikedByUser(@PathVariable("user_id") String userId) {
        var forms = service.FindDislikedByUser(userId);
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }
}
