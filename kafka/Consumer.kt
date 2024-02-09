package com.baum.baas.local.kafka

import com.baum.baas.local.data.TestDto
import lombok.Data
import lombok.extern.slf4j.Slf4j
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
@Data
class KafkaConsumer {
    @KafkaListener(
        topics = ["topic1"],
        groupId = "topic1-group1",
        containerFactory = "filterListenerContainerFactory",
        concurrency = "3"
    )
    fun receive(
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offSet: Long,
        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) ts: Long,
        @Header(KafkaHeaders.GROUP_ID) groupId: String,
        consumerRecord: ConsumerRecord<String, TestDto>
    ) {
        println(
            "value:${consumerRecord.value()}, partition: ${partition}, offset: $offSet, ts: $ts, groupId: $groupId"
        )
        try {
            Thread.sleep(1000L)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}


