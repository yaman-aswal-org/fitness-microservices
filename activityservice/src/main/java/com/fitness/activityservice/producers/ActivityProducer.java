package com.fitness.activityservice.producers;

import com.fitness.activityservice.models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    public void sendActivity(Activity savedActivity) {

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        } catch (Exception e) {
            log.error("Failed to publish activity to RabbitMQ : ", e);
        }

    }
}
