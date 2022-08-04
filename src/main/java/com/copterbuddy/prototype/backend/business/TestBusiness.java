package com.copterbuddy.prototype.backend.business;

import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.exeption.UserException;
import org.springframework.stereotype.Service;

import com.copterbuddy.prototype.backend.model.MRegisterRequest;

import java.io.IOException;
import java.util.Objects;

@Service
public class TestBusiness {

    public String register(MRegisterRequest request) throws BaseException {
        if (request == null){
            throw UserException.requestNull();
        }

        if(Objects.isNull(request.getEmail())){
            throw  UserException.emailNull();
        }
        return "";
    }
    
}
