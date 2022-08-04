package com.copterbuddy.prototype.backend.business;

import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.exeption.FileException;
import com.copterbuddy.prototype.backend.exeption.ProductException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ProductBusiness {

    public String getProductById(String id) throws BaseException {
        //TODO: get data from database
        if (Objects.equals("1234",id)){
            throw ProductException.notFound();
        }
        return id;
    }
}
