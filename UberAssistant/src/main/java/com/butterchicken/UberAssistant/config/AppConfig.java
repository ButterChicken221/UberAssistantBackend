package com.butterchicken.UberAssistant.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"com.butterchicken.UberAssistant.controller", "com.butterchicken.UberAssistant.repo", "com.butterchicken.UberAssistant.entity", "com.butterchicken.UberAssistant.service", "com.butterchicken.UberAssistant.service.impl", "com.butterchicken.UberAssistant.config"})
public class AppConfig {


    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "UberAssistant");
        return FirebaseMessaging.getInstance(app);
    }

}
