package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, Object payload) {
        kafkaTemplate.send(topic, payload);
    }

    public void send(String topic, String key, Object payload) {
        kafkaTemplate.send(topic, key, payload);
    }

    @KafkaListener(topics = "${kafka.topic.example:example-topic}", groupId = "${spring.kafka.consumer.group-id:demo-group}")
    public void listen(Object payload) {
        System.out.println("Получено сообщение: " + payload);
    }
}