package org.example.producerservice.controller;


import org.example.producerservice.service.MessageProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.commonlibrary.entity.User;

@RestController
@RequestMapping("api/v1/kafka/messages")
public class MessageController {

    @Autowired
    private MessageProducerService messageProducerService;

    //ping endpoint to check if the service is running
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Service is running");
    }

/*
    @GetMapping("/publish/{message}")
    public ResponseEntity<?> sendMessage(@PathVariable String message) {
        messageProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
*/

    @GetMapping("/publish")
    public ResponseEntity<?> sendMessages(@RequestParam String message) {
        messageProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }


    @GetMapping("/publishUser")
    public ResponseEntity<?> sendUser(@RequestBody User user) {
        messageProducerService.sendUser(user);
        return ResponseEntity.ok("user sent successfully");
    }


}
