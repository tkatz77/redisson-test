package com.example.redissontest;

import jakarta.servlet.http.HttpServletRequest;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
public class SampleController {
	private RedissonClient redissonClient;

	public SampleController(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	@GetMapping("/test")
	@ResponseBody
	public String showTest(HttpServletRequest request) {
		RAtomicLong counter = redissonClient.getAtomicLong("test:counter");
		long curr = counter.incrementAndGet();
		counter.expire(Duration.ofMinutes(5));

		return Long.toString(curr);
	}
}
