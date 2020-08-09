package org.zxy.flea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.flea.converter.AmoyConverter;

@Component
public class AmoyConfig {

    @Bean
    public AmoyConverter amoyConverter() {
        return new AmoyConverter();
    }
}
