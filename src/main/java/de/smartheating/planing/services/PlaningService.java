package de.smartheating.planing.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.smartheating.SmartHeatingCommons.persistedData.Event;
import de.smartheating.planing.rabbitmq.MessageProducer;

@Service
public class PlaningService {
	
	Logger logger = LoggerFactory.getLogger(PlaningService.class);
	
	@Autowired
	MessageProducer messageProducer;
	
	public void performPlaning(Event event) {
		logger.info("Started planing with event with the ID: " + event.getId());
		// TODO: Perform planing
		logger.info("Sending event to OutputAdapter");
		messageProducer.sendEvent(event);
	}

}
