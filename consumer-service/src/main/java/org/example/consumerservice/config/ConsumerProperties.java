package org.example.consumerservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.commonlibrary.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerProperties {


    @Bean
    public ConsumerFactory<String, User> consumerFactory() {
        JsonDeserializer<User> deserializer = new JsonDeserializer<>(User.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("org.example.commonlibrary"); // no dash!
        deserializer.setUseTypeMapperForKey(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user_group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public RetryTopicConfiguration retryTopicConfiguration(KafkaTemplate<Object, Object> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(3)
                .fixedBackOff(2000) // 2 seconds between retries
                .dltSuffix("-dlt") // e.g., "user-topic-dlt"
                .create(template);
    }

        @Bean
        public ProducerFactory<Object, Object> retryProducerFactory() {
            Map<String, Object> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

            return new DefaultKafkaProducerFactory<>(config);
        }

        @Bean
        public KafkaTemplate<Object, Object> kafkaTemplate() {
            return new KafkaTemplate<>(retryProducerFactory());
        }

}
