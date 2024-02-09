package com.baum.baas.local.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream


@Configuration
class FirebaseConfig {
    @PostConstruct
    fun init() {
        try {
            val serviceAccount = FileInputStream("src/main/resources/firebaseKey.json")
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            FirebaseApp.initializeApp(options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}