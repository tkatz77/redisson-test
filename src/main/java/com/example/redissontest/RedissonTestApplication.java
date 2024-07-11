package com.example.redissontest;

import io.micrometer.common.util.StringUtils;
import org.redisson.tomcat.RedissonSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedissonTestApplication {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${redissonConfFile:#{null}}")
	private String redissonConfFile;

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizeTomcat() {
		if (StringUtils.isBlank(redissonConfFile)) {
			throw new RuntimeException("redissonConfFile not specified, or blank.");
		}

		logger.info("Configuring Redisson Session Manager");
		return (factory) -> {
			factory.addContextCustomizers(context -> {
				RedissonSessionManager tc10 = new RedissonSessionManager();
				tc10.setConfigPath(redissonConfFile);
				tc10.setReadMode("MEMORY");
				tc10.setUpdateMode("AFTER_REQUEST");
				tc10.setBroadcastSessionEvents(false);
				tc10.setBroadcastSessionUpdates(false);
				tc10.setKeyPrefix("testing");

				context.setSessionTimeout(30);
				context.setManager(tc10);
			});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(RedissonTestApplication.class, args);
	}
}
