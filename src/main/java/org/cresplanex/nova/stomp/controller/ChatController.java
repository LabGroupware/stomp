package org.cresplanex.nova.stomp.controller;

import org.cresplanex.nova.stomp.ws.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage message
    ) {
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage message,
            StompHeaderAccessor sha
    ){
        Map<String, Object> sessionAttributes = sha.getSessionAttributes();
        if (sessionAttributes != null) {
            sessionAttributes.put("username", message.getSender());
        }
        return message;
    }
}
