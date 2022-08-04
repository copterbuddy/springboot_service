package com.copterbuddy.prototype.backend.api;

import com.copterbuddy.prototype.backend.business.TestBusiness;
import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.model.MRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestApi {

    @Autowired
    private TestBusiness business;

    @GetMapping
    public String test() {
        return "Hello Test";
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody MRegisterRequest request) throws BaseException {
        String response = business.register(request);
        return ResponseEntity.ok(response);
    }
}
