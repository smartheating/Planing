package de.smartheating.planing.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.smartheating.planing.rabbitmq.MessageConsumer;

@Configuration
public class RabbitMQConfig {

	public final static String RABBITMQ_QUEUE = "test-queue";
	public final static String RABBITMQ_EXCHANGE = "test-exchange";
	public final static String RABBITMQ_ROUTINGKEY = "test-key";
	
    @Bean
    Queue queue() {
        return new Queue(RABBITMQ_QUEUE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RABBITMQ_EXCHANGE, true, false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RABBITMQ_ROUTINGKEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RABBITMQ_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    
    @Bean
    MessageListenerAdapter listenerAdapter(MessageConsumer consumer) {
        return new MessageListenerAdapter(consumer, "consumeMessagesFromTestQueue");
    }
}
