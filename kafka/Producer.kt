package com.baum.baas.local.kafka

import com.baum.baas.local.data.TestDto
import lombok.RequiredArgsConstructor
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random


@Component
@RequiredArgsConstructor
class Producer(val kafkaTemplate: KafkaTemplate<String, TestDto>) {
    fun send(topic: String, testDto: TestDto) {
        for (i in 0..29) {
            println("" + i)
            testDto.id = Random.nextLong()
            testDto.time = LocalDateTime.now()
            kafkaTemplate.send(topic, testDto)
        }
    }
}
