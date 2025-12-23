package com.akiora.searchformservice.App.Services;

import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.UpdateHotP2PForm;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Domain.ValueObjects.AccountInfo;
import com.akiora.searchformservice.Domain.ValueObjects.LeagueAccount;
import com.akiora.searchformservice.Domain.ValueObjects.UserData;
import com.akiora.searchformservice.Events.AccountParseEvent;
import com.akiora.searchformservice.Events.UserDataFetchEvent;
import com.akiora.searchformservice.Infra.HotP2PFormRepo;
import com.akiora.searchformservice.Events.FormMatchedEvent;
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
        
        // Send event to parse account
        AccountParseEvent accountParseEvent = AccountParseEvent.builder()
                .formId(newForm.getId().toString())
                .accountName(newForm.getAccount().getNickname())
                .accountTag(newForm.getAccount().getTag())
                .accountServer(newForm.getAccount().getServer())
                .build();
        formEventProducer.sendAccountParseEvent(accountParseEvent);
        
        // Send event to fetch user data if not provided
      
        UserDataFetchEvent userDataFetchEvent = UserDataFetchEvent.builder()
                .formId(newForm.getId().toString())
                .creatorId(newForm.getCreatorId().toString())
                .build();
        formEventProducer.sendUserDataFetchEvent(userDataFetchEvent);
       
        
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
    
    public List<HotP2PForm> FindAvailableForUser(String userId) {
        return repo.findAvailableFormsForUser(userId);
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
        boolean accountChanged = false;
        
        if (updateData.getAccount() != null) {
            // Validate account data
            if (updateData.getAccount().getNickname() == null || 
                updateData.getAccount().getServer() == null || 
                updateData.getAccount().getTag() == null) {
                throw new DomainException("Account nickname, server, and tag are required");
            }
            
            // Check if account actually changed
            LeagueAccount oldAccount = form.getAccount();
            LeagueAccount newAccount = updateData.getAccount();
            
            if (oldAccount == null || 
                !oldAccount.getNickname().equals(newAccount.getNickname()) ||
                !oldAccount.getServer().equals(newAccount.getServer()) ||
                !oldAccount.getTag().equals(newAccount.getTag())) {
                
                form.setAccount(newAccount);
                form.setAccountInfo(null); // Reset account info when account changes
                accountChanged = true;
            }
        }
        
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
        
        // If account changed, send parse event
        if (accountChanged) {
            AccountParseEvent accountParseEvent = AccountParseEvent.builder()
                    .formId(form.getId().toString())
                    .accountName(form.getAccount().getNickname())
                    .accountTag(form.getAccount().getTag())
                    .accountServer(form.getAccount().getServer())
                    .build();
            formEventProducer.sendAccountParseEvent(accountParseEvent);
            log.info("Sent account parse event for updated form {}", formId);
        }
        
        return Optional.of(form);
    }
    
    public List<HotP2PForm> FindLikedByUser(String userId) {
        return repo.findAllByLikedByContaining(userId);
    }
    
    public List<HotP2PForm> FindDislikedByUser(String userId) {
        return repo.findAllByDislikedByContaining(userId);
    }
    
    public Optional<HotP2PForm> UpdateAccountInfo(String formId, AccountInfo accountInfo) throws DomainException {
        var formOpt = FindById(formId);
        if (formOpt.isEmpty()) {
            throw new DomainException("Form not found");
        }
        
        HotP2PForm form = formOpt.get();
        System.out.println(accountInfo.toString());
        form.setAccountInfo(accountInfo);
        repo.save(form);
        
        log.info("Updated account info for form {}", formId);
        return Optional.of(form);
    }
    
    public Optional<HotP2PForm> UpdateUserData(String formId, UserData userData) throws DomainException {
        var formOpt = FindById(formId);
        if (formOpt.isEmpty()) {
            throw new DomainException("Form not found");
        }
        
        HotP2PForm form = formOpt.get();
        form.setUserData(userData);
        repo.save(form);
        
        log.info("Updated user data for form {}", formId);
        return Optional.of(form);
    }
}
