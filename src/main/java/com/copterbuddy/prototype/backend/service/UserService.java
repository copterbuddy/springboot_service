package com.copterbuddy.prototype.backend.service;

import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.exeption.UserException;
import com.copterbuddy.prototype.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    public Optional<User> findByEmail(String email){
        return  repository.findByEmail(email);
    }

    public Optional<User> findById(String userId){
        return  repository.findById(userId);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

    public boolean matchPassword(String rawPassword,String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }

    public User update(User user){
        return repository.save(user);
    }

    public User updateName(String id,String name) throws UserException {
        Optional<User> opt = repository.findById(id);
        if (opt.isEmpty()){
            throw  UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return repository.save(user);
    }

    public User create(String email,String password,String name) throws BaseException {
        //validate
        if (Objects.isNull(email)){
            //throw error email null
            throw UserException.createEmailNull();
        }

        if (Objects.isNull(password)){
            //throw error email null
            throw UserException.createPasswordNull();
        }

        if (Objects.isNull(name)){
            //throw error email null
            throw UserException.createNameNull();
        }

        //verify
        if(repository.existsByEmail(email)){
            throw UserException.createEmailDuplicated();
        }

        //save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);

        return repository.save(entity);
    }

}
