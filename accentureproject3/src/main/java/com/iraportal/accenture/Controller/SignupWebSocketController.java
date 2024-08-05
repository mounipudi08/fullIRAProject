package com.iraportal.accenture.Controller;

import com.iraportal.accenture.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class SignupWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SignupWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/signup-notification")
    public void notifyAdminOnSignup(User user) {
        // Logic to notify admin about the new signup
        String message = "New signup request from: " + user.getEmail();
        messagingTemplate.convertAndSend("/topic/admin", message);
    }
}