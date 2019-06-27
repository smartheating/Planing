package de.smartheating.planing.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.smartheating.planing.rabbitmq.MessageConsumer;

@Configuration
public class RabbitMQConfig {

	public final static String RABBITMQ_RECEIVING_QUEUE = "planing";
	public final static String RABBITMQ_RECEIVING_EXCHANGE = "planing-exchange";
	public final static String RABBITMQ_RECEIVING_ROUTINGKEY = "to.planing";
	
	public final static String RABBITMQ_SENDING_QUEUE = "outputadapter";
	public final static String RABBITMQ_SENDING_EXCHANGE = "outputadapter-exchange";
	public final static String RABBITMQ_SENDING_ROUTINGKEY = "to.outputadapter";

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setConnectionFactory(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);

		return rabbitTemplate;
	}

	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

    @Bean(name = "receivingQueue")
    Queue receivingQueue() {
        return new Queue(RABBITMQ_RECEIVING_QUEUE, true);
    }
    
    @Bean(name = "sendingQueue")
    Queue sendingQueue() {
        return new Queue(RABBITMQ_SENDING_QUEUE, true);
    }

    @Bean(name = "receivingExchange")
    DirectExchange receivingExchange() {
        return new DirectExchange(RABBITMQ_RECEIVING_EXCHANGE, true, false);
    }
    
    @Bean(name = "sendingExchange")
    DirectExchange sendingExchange() {
        return new DirectExchange(RABBITMQ_SENDING_EXCHANGE, true, false);
    }

    @Bean(name = "receivingBinding")
    Binding receivingBinding(@Qualifier("receivingQueue") Queue queue, @Qualifier("receivingExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RABBITMQ_RECEIVING_ROUTINGKEY);
    }
    
    @Bean(name = "sendingBinding")
    Binding sendingBinding(@Qualifier("sendingQueue")Queue queue, @Qualifier("sendingExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RABBITMQ_SENDING_ROUTINGKEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RABBITMQ_RECEIVING_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    MessageListenerAdapter listenerAdapter(MessageConsumer consumer, MessageConverter messageConverter) {
    	MessageListenerAdapter adapter = new MessageListenerAdapter(consumer, messageConverter);
    	adapter.setDefaultListenerMethod("consumeEvent");
        return adapter;
    }
}
