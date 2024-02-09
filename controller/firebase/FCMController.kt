package com.baum.baas.local.controller.firebase

import com.baum.baas.local.data.TestDto
import com.baum.baas.local.kafka.Producer
import com.baum.baas.local.service.write.firebase.FcmService
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/p")
class FCMController(
    private val fcmService: FcmService,
    private val producer: Producer,
) {
    @PostMapping("/send-notification")
    fun sendNotification(@RequestBody request: FcmRequest) {
        fcmService.sendPushNotification(request.message)
    }

    @PostMapping("/insert")
    fun insert(@RequestBody testDto: TestDto) {
        producer.send("topic1", testDto)
    }

    data class FcmRequest(
        val message: String,
    )
}
