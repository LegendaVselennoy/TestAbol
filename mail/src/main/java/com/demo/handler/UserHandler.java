package com.demo.handler;

import com.demo.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "event-topic")
public class UserHandler {

    @KafkaHandler
    public void handler(UserEvent event) {
        log.info("Received event, creating a user named: {}", event.getEventName());
    }
}
