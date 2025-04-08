package com.ecms.employee.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Bean
	public DirectExchange dlxExchange() {
		return new DirectExchange("dlx-exchange");
	}
	
	@Bean
	public Queue deadLetterQueue() {
	    return new Queue("employee.dlq");
	}
	
	@Bean
	public Binding dlqBinding() {
		return BindingBuilder
				.bind(deadLetterQueue())
				.to(dlxExchange())
				.with("employee.dlq");
	}
	
	@Bean
	public Queue employeeQueue() {
		return QueueBuilder.durable("employeeQueue")
				.deadLetterExchange("dlx-exchange")
				.deadLetterExchange("employee.dlq")
				.build();
	}
}
