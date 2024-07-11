package com.example.redissontest;

import io.micrometer.common.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class RedissonTestApplication {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${redissonConfFile:#{null}}")
	private String redissonConfFile;

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() {
		if (StringUtils.isBlank(redissonConfFile)) {
			throw new RuntimeException("redissonConfFile not specified, or blank.");
		}
		logger.info("Initializing Redis Cluster Connection...");
		try {
			return Redisson.create(Config.fromYAML(new File(redissonConfFile)));
		} catch (Exception e) {
			logger.error("Unable to create Redis client", e);
		}
		return null;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedissonTestApplication.class, args);
	}
}
