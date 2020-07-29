package org.zxy.flea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FleaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleaApplication.class, args);
    }

}
