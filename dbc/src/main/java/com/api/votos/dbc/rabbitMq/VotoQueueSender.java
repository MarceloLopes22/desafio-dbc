package com.api.votos.dbc.rabbitMq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Component
public class VotoQueueSender {
 
    @Autowired
    private RabbitTemplate rabbitTemplate;
 
    @Autowired
    private Queue queue;
 
    public void enviar(String resultado) {
        rabbitTemplate.convertAndSend(this.queue.getName(), resultado);
    }
}