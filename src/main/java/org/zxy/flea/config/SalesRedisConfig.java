package org.zxy.flea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.flea.util.SalesRedisUtil;

@Component
public class SalesRedisConfig {

    @Bean
    public SalesRedisUtil salesRedisUtil() {
        return new SalesRedisUtil();
    }
}
