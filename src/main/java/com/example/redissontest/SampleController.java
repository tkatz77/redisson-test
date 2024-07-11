package com.example.redissontest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
public class SampleController {

	@GetMapping("/test")
	@ResponseBody
	public String showTest(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setAttribute("testVar", "testVal");

		return "Test!";
	}
}
