package com.copterbuddy.prototype.backend.mapper;

import com.copterbuddy.prototype.backend.entity.User;
import com.copterbuddy.prototype.backend.model.MRegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MRegisterResponse toRegisterResponse(User user);
}