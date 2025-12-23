package com.akiora.searchformservice.API.Contollers;


import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.DislikeFormRequest;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.LikeFormRequest;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.UpdateHotP2PForm;
import com.akiora.searchformservice.App.Services.HotP2PFormService;
import com.akiora.searchformservice.App.Services.FormEventProducer;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Domain.ValueObjects.AccountInfo;
import com.akiora.searchformservice.Domain.ValueObjects.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/forms")
@Slf4j
public class HotP2PFormController {
    
    @Autowired
    HotP2PFormService service;
    @Autowired
    FormEventProducer rabbitMQProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
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

    @GetMapping("/mesage")
    public ResponseEntity<String> postMessageINtoRabbit(@RequestParam String message) {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent");
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
    
    @PutMapping("/{form_id}/account-info")
    public ResponseEntity<?> updateAccountInfo(
            @PathVariable("form_id") String formId,
            @RequestBody Map<String , Object> account) {
        try {
            var accountInfo =  objectMapper.convertValue(account, AccountInfo.class);
            log.info("Received account info update for form {}", formId);
            log.info("AccountInfo: soloQueue={}, flexQueue={}, championStats size={}", 
                    accountInfo.getSoloQueue(), 
                    accountInfo.getFlexQueue(), 
                    accountInfo.getChampionStats() != null ? accountInfo.getChampionStats().size() : 0);
            
            if (accountInfo.getSoloQueue() != null) {
                log.info("SoloQueue details: rank={}, lp={}, winRate={}", 
                        accountInfo.getSoloQueue().getCurrentRank(),
                        accountInfo.getSoloQueue().getCurrentLP(),
                        accountInfo.getSoloQueue().getWinRate());
            }
            
            var form = service.UpdateAccountInfo(formId, accountInfo);
            if (form.isPresent()) {
                return new ResponseEntity<>(form.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
        } catch (DomainException e) {
            log.error("Error updating account info: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error updating account info: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{form_id}/user-data")
    public ResponseEntity<?> updateUserData(
            @PathVariable("form_id") String formId,
            @RequestBody Map<String, Object> userData) {
        try {
            log.info(userData.toString());
            var userDataObj = objectMapper.convertValue(userData, UserData.class);
            log.info("Received user data update for form {}", formId);
            log.info("UserData: nickname={}, email={}, age={}, gender={}", 
                    userDataObj.getNickname(), 
                    userDataObj.getEmail(), 
                    userDataObj.getAge(),
                    userDataObj.getGender());
            
            var form = service.UpdateUserData(formId, userDataObj);
            if (form.isPresent()) {
                return new ResponseEntity<>(form.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Form not found", HttpStatus.NOT_FOUND);
        } catch (DomainException e) {
            log.error("Error updating user data: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error updating user data: {}", e.getMessage(), e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
