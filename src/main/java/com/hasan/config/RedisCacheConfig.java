package com.hasan.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

	@Bean
	CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

		RedisCacheConfiguration defaults = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5))
				.disableCachingNullValues()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new JdkSerializationRedisSerializer()));

		Map<String, RedisCacheConfiguration> caches = new HashMap<>();

		caches.put("customers", defaults.entryTtl(Duration.ofMinutes(10)));

		caches.put("customerSearch", defaults.entryTtl(Duration.ofMinutes(2)));

		caches.put("customerStatusSearch", defaults.entryTtl(Duration.ofMinutes(2)));

		return RedisCacheManager.builder(connectionFactory).cacheDefaults(defaults)
				.withInitialCacheConfigurations(caches).build();
	}
}
