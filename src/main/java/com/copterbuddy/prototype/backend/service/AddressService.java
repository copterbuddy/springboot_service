package com.copterbuddy.prototype.backend.service;

import com.copterbuddy.prototype.backend.entity.Address;
import com.copterbuddy.prototype.backend.entity.Social;
import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.repository.AddressRepository;
import com.copterbuddy.prototype.backend.repository.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public List<Address> findByUser(User user) {
        return repository.findByUser(user);
    }

    public Address create(User user,String line1,String line2,String zipcode){
        // TODO: validate

        // Create
        Address entity = new Address();

        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);

        return repository.save(entity);
    }

}
