package com.hasan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTest implements CommandLineRunner {

	private final RedisConnectionFactory connectionFactory;

	public RedisConnectionTest(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public void run(String... args) {
		try {
			String response = connectionFactory.getConnection().ping();

			System.out.println("Redis connection successful: " + response);
		} catch (Exception e) {
			System.err.println("Redis connection failed:");
			e.printStackTrace();
		}
	}
}