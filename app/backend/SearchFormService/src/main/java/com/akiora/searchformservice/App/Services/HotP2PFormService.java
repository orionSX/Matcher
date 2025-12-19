package com.akiora.searchformservice.App.Services;

import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.UpdateHotP2PForm;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Infra.HotP2PFormRepo;
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
    NotificationService notificationService;
    
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
        
        // Проверяем, не лайкал ли уже пользователь
        if (form.getLikedBy().contains(userId)) {
            return Optional.of(form);
        }
        
        // Убираем из дизлайков если был там
        form.getDislikedBy().remove(userId);
        
        // Добавляем в лайки
        form.getLikedBy().add(userId);
        
        // Проверяем взаимный лайк (match)
        String creatorId = form.getCreatorId().toString();
        if (form.getLikedBy().contains(creatorId) && !userId.equals(creatorId)) {
            // Проверяем, лайкнул ли создатель формы этого пользователя
            List<HotP2PForm> userForms = repo.findAllByCreatorId(userId);
            for (HotP2PForm userForm : userForms) {
                if (userForm.getLikedBy().contains(creatorId)) {
                    // Есть взаимный лайк - отправляем уведомление
                    log.info("Match detected between {} and {}", userId, creatorId);
                    notificationService.sendMatchNotification(userId, creatorId, formId);
                    break;
                }
            }
        }
        
        repo.save(form);
        return Optional.of(form);
    }
    
    public Optional<HotP2PForm> DislikeForm(String formId, String userId) throws DomainException {
        var formOpt = FindById(formId);
        if (formOpt.isEmpty()) {
            throw new DomainException("Form not found");
        }
        
        HotP2PForm form = formOpt.get();
        
        // Проверяем, не дизлайкал ли уже пользователь
        if (form.getDislikedBy().contains(userId)) {
            return Optional.of(form);
        }
        
        // Убираем из лайков если был там
        form.getLikedBy().remove(userId);
        
        // Добавляем в дизлайки
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
