package org.cresplanex.nova.stomp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class ConnectionConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${app.front.origins}")
    private String frontOrigins;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        List<String> origins = List.of(frontOrigins.split(","));

        // WebSocketのエンドポイントを設定
        registry.addEndpoint("/ws")
                // クロスオリジンリクエストを許可
//                .setAllowedOrigins(origins.toArray(new String[0]))
                .setAllowedOriginPatterns("*")
                // ClientがWebSocketをサポートしていない場合にフォールバックするためにSockJSを使用
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // clientからサーバーへのメッセージの宛先のプレフィックス
        config.setApplicationDestinationPrefixes("/app");
        // サーバーからclientへのメッセージの宛先のプレフィックス
        // これをサブスクライブするclientにメッセージが送信される
        // インメモリベースのメッセージブローカーを有効化する場合
        config.enableSimpleBroker("/topic");
        // RabbitMQを使用する場合
//        config.enableStompBrokerRelay("/topic")
//                .setRelayHost("localhost")
//                .setRelayPort(61613)
//                .setClientLogin("guest")
//                .setClientPasscode("guest");
        // TODO: 外部のメッセージブローカーを使用する
    }
}
