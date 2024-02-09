package com.baum.baas.local.kafka.config

import com.baum.baas.local.data.TestDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class ConsumerConfig {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, TestDto> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "topic1-group1"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        val deserializer: JsonDeserializer<TestDto> = JsonDeserializer(TestDto::class.java, false)

        return DefaultKafkaConsumerFactory(props, StringDeserializer(), deserializer)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TestDto> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, TestDto> =
            ConcurrentKafkaListenerContainerFactory<String, TestDto>()
        factory.consumerFactory = consumerFactory()
        
        return factory
    }

    @Bean
    fun filterListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TestDto> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, TestDto> =
            ConcurrentKafkaListenerContainerFactory<String, TestDto>()
        factory.consumerFactory = consumerFactory()

        return factory
    }


}