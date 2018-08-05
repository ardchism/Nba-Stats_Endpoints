package com.nbastat.player.endpoints;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testHelloWorld {

	@RequestMapping("/helloWorld")
	public String helloWorld() {
		return "Hello world!!!";
	}
	
}
