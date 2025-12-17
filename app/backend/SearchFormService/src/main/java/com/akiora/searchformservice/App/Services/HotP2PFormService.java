package com.akiora.searchformservice.App.Services;

import com.akiora.searchformservice.App.DTO.HotP2PForm.Request.CreateHotP2PForm;
import com.akiora.searchformservice.Domain.Entities.HotP2PForm;
import com.akiora.searchformservice.Domain.Exceptions.DomainException;
import com.akiora.searchformservice.Infra.HotP2PFormRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.UUID;


@Service
@RequestScope
public class HotP2PFormService {

    @Autowired
    HotP2PFormRepo repo;
    public HotP2PForm Create(CreateHotP2PForm form) throws DomainException {
        
        var newForm = HotP2PForm.Create(form);
        repo.save(newForm);   
        return newForm;
    }
    public List<HotP2PForm> FindByCreatorId(String creatorId) throws DomainException {
        
        var forz=repo.findAllByCreatorIdOrderByCreatedAtDesc(creatorId);
        
        return forz;
    }   
    
}
