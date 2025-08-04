package org.example.consumerservice.service;


import org.example.commonlibrary.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    Logger logger = LoggerFactory.getLogger(MessageConsumerService.class);

/*    @KafkaListener(topics = "user_topic", groupId = "user_group")
    public void consumeUserMessage(User user) {
        System.out.println("Received user message: " + user);
    }*/

    //implementing retry logic with DLT (Dead Letter Topic)
    @KafkaListener(topics = "user_topic", groupId = "user-group")
    public void consume(User user) {
        if (user.getName().contains("fail")) {
            throw new RuntimeException("Forced failure for retry demo");
        }
        System.out.println("Processed: " + user);
    }

    @KafkaListener(topics = "user-topic-dlt", groupId = "dlt-logger")
    public void listenDLT(User user) {
        logger.info("DLT - Failed message: " + user);
    }


}
