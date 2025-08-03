package org.example.consumerservice.service;


import org.example.commonlibrary.entity.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    @KafkaListener(topics = "user_topic", groupId = "user_group")
    public void consumeUserMessage(User user) {
        System.out.println("Received user message: " + user);
    }
}
