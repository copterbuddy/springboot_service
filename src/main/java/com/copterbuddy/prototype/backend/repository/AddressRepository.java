package com.copterbuddy.prototype.backend.repository;

import com.copterbuddy.prototype.backend.entity.Address;
import com.copterbuddy.prototype.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,String> {

    List<Address> findByUser(User user);
}
