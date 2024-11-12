package org.cresplanex.nova.stomp.config;

import org.cresplanex.nova.stomp.template.KeyValueTemplate;
import org.cresplanex.nova.stomp.template.RedisKeyValueTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class KeyValueTemplateConfiguration {

    @Bean
    public KeyValueTemplate keyValueTemplate(
            RedisTemplate<String, Object> redisTemplate
    ) {
        return new RedisKeyValueTemplate(redisTemplate);
    }
}
