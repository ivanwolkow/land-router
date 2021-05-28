package com.example.landrouter.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CacheConfiguration {

    @Value("${route.cache.max-size}")
    private Long routeCacheMaxSize;

    @Bean
    public Caffeine routeCache() {
        log.info("Initializing Caffeine with max size = {}", routeCacheMaxSize);
        return Caffeine.newBuilder()
                .maximumSize(routeCacheMaxSize);
    }

    @Bean
    public CacheManager routeCacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
