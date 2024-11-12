package org.cresplanex.nova.stomp.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.nova.stomp.ws.ChatMessage;
import org.cresplanex.nova.stomp.ws.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectionEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = sha.getSessionId();
        log.info("Session Connected: " + sessionId);
    }

    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = sha.getSessionId();
        Map<String, Object> sessionAttributes = sha.getSessionAttributes();
        if (sessionAttributes != null) {
            String username = (String) sessionAttributes.get("username");
            if (username != null) {
                log.info("User Disconnected: " + username);
                ChatMessage chatMessage = ChatMessage.builder()
                                .type(MessageType.LEAVE)
                                .sender(username)
                                .build();
                messagingTemplate.convertAndSend("/topic/public", chatMessage);
            }
        }
        log.info("Session Disconnected: " + sessionId);
    }
}
