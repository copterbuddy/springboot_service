package com.copterbuddy.prototype.backend.repository;

import com.copterbuddy.prototype.backend.entity.Social;
import com.copterbuddy.prototype.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social,String> {

    Optional<Social> findByUser(User user);
}
