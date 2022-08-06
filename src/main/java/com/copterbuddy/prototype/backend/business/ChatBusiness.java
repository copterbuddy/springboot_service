package com.copterbuddy.prototype.backend.business;

import com.copterbuddy.prototype.backend.exeption.BaseException;
import com.copterbuddy.prototype.backend.exeption.ChatException;
import com.copterbuddy.prototype.backend.model.ChatMessage;
import com.copterbuddy.prototype.backend.model.ChatMessageRequest;
import com.copterbuddy.prototype.backend.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatBusiness {

    @Autowired
    private SimpMessagingTemplate template;

    public void post(ChatMessageRequest request) throws BaseException {

        Optional<String> opt = SecurityUtil.getCurrentUserId();

        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        // TODO: validate message


        final String destination = "/topic/chat";

        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination, payload);
    }
}
