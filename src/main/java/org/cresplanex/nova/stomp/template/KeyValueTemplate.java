package org.cresplanex.nova.stomp.template;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface KeyValueTemplate {
    void setValue(String key, Object value);
    void setValue(String key, Object value, long timeout, TimeUnit timeUnit);
    Long getExpire(String key, TimeUnit timeUnit);
    Optional<Object> getValue(String key);
    void delete(String key);
    boolean exists(String key);
}
