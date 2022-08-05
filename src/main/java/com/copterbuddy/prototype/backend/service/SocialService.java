package com.copterbuddy.prototype.backend.service;

import com.copterbuddy.prototype.backend.entity.Social;
import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.repository.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SocialService {

    @Autowired
    private SocialRepository repository;

    public Optional<Social> findByUser(User user) {
        return repository.findByUser(user);
    }

    public Social create(User user,String facebook,String line,String instagram,String tiktok){
        // TODO: validate

        // Create
        Social entity = new Social();

        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setInstagram(instagram);
        entity.setLine(line);
        entity.setTiktok(tiktok);

        return repository.save(entity);
    }

}
