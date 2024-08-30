package com.delivery.api.common.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;


    public void producer(String exchange, String routingKey, Object object){
        // 어떤 exchange에 어떤 routingKey를 통해서 어떤 object를 보낸다라고 설정.
        rabbitTemplate.convertAndSend(exchange, routingKey, object);

    }


}
