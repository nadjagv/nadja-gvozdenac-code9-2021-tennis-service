package com.nadjagv.nadjagvozdenaccode92021tennisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NadjaGvozdenacCode92021TennisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NadjaGvozdenacCode92021TennisServiceApplication.class, args);
	}

	@GetMapping("/test")
	public String test(@RequestParam(value="name", defaultValue = "World") String name){
		return String.format("Hello %s!", name);
	}

}
