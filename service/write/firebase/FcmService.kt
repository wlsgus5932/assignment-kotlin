package com.baum.baas.local.service.write.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FcmService {

    fun sendPushNotification(message: String) {
        val notification = Notification.builder()
            .setTitle("title test")
            .setBody("body test")
            .setImage("https://imgsv.imaging.nikon.com/lineup/dslr/df/img/sample/img_01.jpg")
            .build()

        FirebaseMessaging.getInstance().send(
            Message.builder()
                .putData("test", "putDataTest")
                .setNotification(notification)
                .setToken("ftcIdUIjRx6_jL_8FG37Wz:APA91bG3eRFnWpogqK2jeRGovsxgQyfHgf_-Z9MPYxUY07cpKNnOPI1V6-wBZcTmfJswXfCGss5KMSXYy5Klc7gP4lHP0u0UrfK_nisTie2J4jfLFuW-U62T_W1zrN-UK-tYT8RpYJ8C")
                .build()
        )
    }

    fun getAccessToken(): String {
        val googleCredentials =
            GoogleCredentials.fromStream(ClassPathResource("firebaseKey.json").inputStream)
                .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))

        googleCredentials.refreshIfExpired()
        return googleCredentials.accessToken.tokenValue
    }
}