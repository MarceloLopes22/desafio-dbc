package com.api.votos.dbc.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
 
import java.util.Map;
 
@Component
@Slf4j
public class VotoConsumer {
 
    @RabbitListener(queues = {"${queue.order.name}"})
    public void receber(@Payload String resultado) {
        log.info("Order: " + resultado);
    }
}