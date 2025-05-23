package com.credit.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.commons.event.Event;
import com.commons.event.EventType;
import com.credit.application.event.CreditCreatedEvent;
import com.credit.application.event.EventCredit;

@Component
public class CreditEventService {

    private final KafkaTemplate<String, Event<?>> producer;
    private final String topicCredit;

    public CreditEventService(
            KafkaTemplate<String, Event<?>> producer,
            @Value("${spring.kafka.topic.credit.name}") String topicCredit) {
        this.producer = producer;
        this.topicCredit = topicCredit;
    }

    public void publish(EventCredit credit) {

        CreditCreatedEvent created = new CreditCreatedEvent();
        created.setId(UUID.randomUUID().toString());
        created.setDate(LocalDateTime.now());
        created.setType(EventType.CREATED);
        created.setData(credit);

        this.producer.send(topicCredit, created);
    }

}
