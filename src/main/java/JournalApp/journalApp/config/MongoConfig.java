//package com.example.myproject.config;
package JournalApp.journalApp.config;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        // Replace the connection string and database name with your MongoDB details
        String connectionString = "mongodb+srv://jokhiowajidali:Ewuoz1YATJc2wmO4@cluster0.59w2jkf.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        String databaseName = "journaldb";

        // Create a MongoTemplate instance
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(connectionString), databaseName);

        return mongoTemplate;
    }
}
