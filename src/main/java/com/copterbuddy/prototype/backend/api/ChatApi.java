package com.copterbuddy.prototype.backend.api;

import com.copterbuddy.prototype.backend.business.ChatBusiness;
import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.model.ChatMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatApi {

    @Autowired
    private ChatBusiness business;

    @PostMapping("/message")
    public ResponseEntity<Void> post(@RequestBody ChatMessageRequest request) throws BaseException {
        business.post(request);
        return ResponseEntity.ok().build();
    }
}
