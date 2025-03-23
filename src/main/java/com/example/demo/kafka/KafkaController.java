package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.repository.User;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaService kafkaService;
    
    @Value("${kafka.topic.example}")
    private String topicName;

    @Autowired
    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Object payload) {
        System.out.println("Отправка сообщения в топик: " + topicName);
        kafkaService.send(topicName, payload);
        return ResponseEntity.ok("Сообщение успешно отправлено в топик: " + topicName);
    }
} 