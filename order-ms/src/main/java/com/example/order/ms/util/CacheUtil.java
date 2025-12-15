package com.example.order.ms.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Component
@RequiredArgsConstructor
public class CacheUtil {

    private final RedissonClient redissonClient;

    public <T> T get(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket != null ? bucket.get() : null;
    }

    public <T> void put(String key, T value, long time, TemporalUnit unit) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value);
        bucket.expire(Duration.of(time, unit));
    }

    public void evict(String key) {
        redissonClient.getBucket(key).delete();
    }
}
