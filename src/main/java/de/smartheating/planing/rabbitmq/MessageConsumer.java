package de.smartheating.planing.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.smartheating.SmartHeatingCommons.persistedData.Event;
import de.smartheating.planing.rabbitmq.MessageConsumer;
import de.smartheating.planing.services.PlaningService;

@Component
public class MessageConsumer {
	
	Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
	
	@Autowired
	PlaningService planingService;

	public void consumeEvent(Event event) {
		logger.info("Consumed event with id: " + event.getId());
		planingService.performPlaning(event);
	}
}
