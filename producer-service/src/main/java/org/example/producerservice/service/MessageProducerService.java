package org.example.producerservice.service;

import org.example.commonlibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplateUser;

    public void sendMessage(String message) {
        kafkaTemplateString.send("demo-topic", message);
    }

    public void sendUser(User user) {
        kafkaTemplateUser.send("user_topic", user);
        // Optionally, you can specify a key for the message ,key will decide which partition the message will go to
        // kafkaTemplateUser.send("user_topic", "key_123",user);

    }

}
