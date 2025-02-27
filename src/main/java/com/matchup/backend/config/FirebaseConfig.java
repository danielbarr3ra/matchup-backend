package com.matchup.backend.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");
        if (serviceAccount == null) {
            throw new IOException("Firebase service account file not found!");
        }

        //Getting around deprecated builder.
        FirebaseOptions.Builder optionsBuilder =  FirebaseOptions.builder();
        FirebaseOptions options = optionsBuilder.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

        return FirebaseApp.initializeApp(options);
    }
}
