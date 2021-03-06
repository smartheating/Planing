package de.smartheating.planing.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.smartheating.planing.rabbitmq.MessageProducer;
import io.swagger.annotations.ApiOperation;

@RestController
public class PlaningController {
	
	@Autowired
	MessageProducer rabbitMQProducer;
	
	/**
	 * This method returns a greeting.
	 * 
	 * @return greeting
	 */
	@GetMapping(value = "/testMessaging", produces = "application/json")
	@ApiOperation(value = "Tests messaging with a message broker")
	public ResponseEntity<?> testMessaging() {
		return new ResponseEntity<>("Test", HttpStatus.OK);
	}
}
