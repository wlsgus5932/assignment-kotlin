package com.baum.baas.local.kafka.config

import com.baum.baas.local.data.TestDto
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.RoundRobinPartitioner
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class ProducerConfig {
    @Bean
    fun factory(): ProducerFactory<String, TestDto> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        props[ProducerConfig.PARTITIONER_CLASS_CONFIG] = RoundRobinPartitioner::class.java.getName()

        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, TestDto> {
        return KafkaTemplate(factory())
    }
}