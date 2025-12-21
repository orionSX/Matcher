package com.akiora.searchformservice.App.Services;

import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.UpdateHotP2PForm;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Infra.HotP2PFormRepo;
import com.akiora.searchformservice.Shared.FormMatchedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequestScope
@Slf4j
public class HotP2PFormService {

    @Autowired
    HotP2PFormRepo repo;
    
   
    
    @Autowired
    FormEventProducer formEventProducer;
    
    public HotP2PForm Create(CreateHotP2PForm form) throws DomainException {
        
        var newForm = HotP2PForm.Create(form);
        repo.save(newForm);   
        return newForm;
    }
    
    public List<HotP2PForm> FindByCreatorId(String creatorId) throws DomainException {
        
        var forms = repo.findAllByCreatorIdOrderByCreatedAtDesc(creatorId);
        
        return forms;
    }
    
    public Optional<HotP2PForm> FindById(String formId) {
        try {
            UUID uuid = UUID.fromString(formId);
            return repo.findById(uuid);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format: {}", formId);
            return Optional.empty();
        }
    }
    
    public List<HotP2PForm> FindAll() {
        return repo.findAllByOrderByCreatedAtDesc();
    }
    
    public Optional<HotP2PForm> LikeForm(String formId, String userId) throws DomainException {
        var formOpt = FindById(formId);
        if (formOpt.isEmpty()) {
            throw new DomainException("Form not found");
        }
        
        HotP2PForm form = formOpt.get();
        
  
        if (form.getLikedBy().contains(userId)) {
            return Optional.of(form);
        }
        
 
        form.getDislikedBy().remove(userId);
        
     
        form.getLikedBy().add(userId);
        
    
        String creatorId = form.getCreatorId().toString();
        
        
        HotP2PForm updatedForm = repo.save(form);
        FormMatchedEvent formMatchedEvent= FormMatchedEvent.builder()
                .formId(formId)
                .formCreator(creatorId)
                .likeSender(userId)                
                .build();
        formEventProducer.sendFormMatchedEvent(formMatchedEvent);
        return Optional.of(updatedForm);
    }
    
    public Optional<HotP2PForm> DislikeForm(String formId, String userId) throws DomainException {
        var formOpt = FindById(formId);
        if (formOpt.isEmpty()) {
            throw new DomainException("Form not found");
        }
        
        HotP2PForm form = formOpt.get();
        

        if (form.getDislikedBy().contains(userId)) {
            return Optional.of(form);
        }
        
      
        form.getLikedBy().remove(userId);

        form.getDislikedBy().add(userId);
        
        repo.save(form);
        return Optional.of(form);
    }
    
    public Optional<HotP2PForm> UpdateForm(String formId, UpdateHotP2PForm updateData) throws DomainException {
        var formOpt = FindById(formId);
        if (formOpt.isEmpty()) {
            throw new DomainException("Form not found");
        }
        
        HotP2PForm form = formOpt.get();
        
        if (updateData.getDescription() != null) {
            form.setDescription(updateData.getDescription());
        }
        
        if (updateData.getLeaguePreferences() != null) {
            form.setLeaguePreferences(updateData.getLeaguePreferences());
        }
        
        if (updateData.getPersonPreferences() != null) {
            form.setPersonPreferences(updateData.getPersonPreferences());
        }
        
        repo.save(form);
        return Optional.of(form);
    }
    
    public List<HotP2PForm> FindLikedByUser(String userId) {
        return repo.findAllByLikedByContaining(userId);
    }
    
    public List<HotP2PForm> FindDislikedByUser(String userId) {
        return repo.findAllByDislikedByContaining(userId);
    }
}
