package org.zxy.flea.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KeyUtil {

    private static final String KEY_PREFIX = "key_%s";

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    private Integer getIncr(String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key,
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisAtomicInteger.expire(12, TimeUnit.SECONDS);
        return redisAtomicInteger.getAndIncrement();
    }

    public String genUniqueKey() {
        Long current_second = System.currentTimeMillis() / 1000;
        String key = String.format(KEY_PREFIX, current_second);

        return current_second + MathUtil.fillDigest(getIncr(key));
    }
}
