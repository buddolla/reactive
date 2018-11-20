package com.reavtive.casendra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.reavtive.casendra.model.User;
import com.reavtive.casendra.repo.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@RestController
public class SpringReactiveCasendraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveCasendraApplication.class, args);
	}
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value="/getUsers" ,produces="text/event-stream" )
	public Flux<User> getUsers() {
		Scheduler scheduler = Schedulers.immediate();
		return userRepository.findAll().subscribeOn(scheduler);
	}

	@GetMapping("/getUsersByAge/{age}")
	public Flux<User> getUserByAge(@PathVariable int age) {
		return userRepository.findByAgeLessThan(age);
	}

	@GetMapping("/getUser/{address}")
	public Mono<User> getUser(@PathVariable String address) {
		return userRepository.findByAddress(address);
	}

}
