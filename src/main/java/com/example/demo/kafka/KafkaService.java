package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.example}")
    private String topicName;

    @Autowired
    public KafkaService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, Object payload) {
        System.out.println("Отправка сообщения в топик: " + topic);
        kafkaTemplate.send(topic, payload);
    }

    public void send(String topic, String key, Object payload) {
        System.out.println("Отправка сообщения с ключом в топик: " + topic);
        kafkaTemplate.send(topic, key, payload);
    }

    @KafkaListener(topics = "${kafka.topic.example}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(Object payload) {
        System.out.println("Получено сообщение из топика " + topicName + ": " + payload);
    }
}