//package com.programmingtechie.order_service.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
//
//    @Value("${app.rabbitmq.exchange:shipping_exchange}")
//    private String exchange;
//
//    @Value("${app.rabbitmq.queue:shipping}")
//    private String queue;
//
//    @Value("${app.rabbitmq.routingkey:shipping_routingkey}")
//    private String routingKey;
//
//    @Bean
//    public TopicExchange exchange() {
//        logger.info("Creating RabbitMQ exchange: {}", exchange);
//        return new TopicExchange(exchange);
//    }
//
//    @Bean
//    public Queue queue() {
//        logger.info("Creating RabbitMQ queue: {}", queue);
//        return new Queue(queue, true);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        logger.info("Creating RabbitMQ binding: queue={} to exchange={} with routingKey={}", queue.getName(), exchange.getName(), routingKey);
//        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
//    }
//
//    @Bean
//    public MessageConverter converter() {
//        logger.info("Configuring Jackson2JsonMessageConverter with JavaTimeModule");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        // إزالة enableDefaultTyping واستبدالها بـ activateDefaultTyping
//        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(),
//                ObjectMapper.DefaultTyping.NON_FINAL);
//        return new Jackson2JsonMessageConverter(mapper);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
//        logger.info("Configuring RabbitTemplate with Jackson2JsonMessageConverter");
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter);
//        return rabbitTemplate;
//    }
//}