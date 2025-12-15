package com.example.order.ms.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${redis.server.url}")
    private String redisServer;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {

        Config config = new Config();
        config.setCodec(new SerializationCodec());

        config.useSingleServer()
                .setAddress(redisServer)
                .setConnectionMinimumIdleSize(2)
                .setConnectionPoolSize(10)
                .setTimeout(3000);

        return Redisson.create(config);
    }
}
