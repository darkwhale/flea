package org.zxy.flea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.flea.util.KeyUtil;

@Component
public class KeyConfig {

    @Bean
    public KeyUtil keyUtil() {
        return new KeyUtil();
    }
}
