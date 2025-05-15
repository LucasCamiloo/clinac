package com.senac.projetointegrador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MongoConfig {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    public MongoClient mongoClient() {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb+srv://lucascamilo430:qf58LzXnV94H5OIr@cluster0.9olvadd.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .applyToSocketSettings(builder -> builder
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .readTimeout(2, TimeUnit.SECONDS)
                    )
                    .applyToClusterSettings(builder -> builder
                        .serverSelectionTimeout(2, TimeUnit.SECONDS)
                    )
                    .build();

            // Testa conexão antes de retornar
            MongoClient client = MongoClients.create(mongoClientSettings);
            try {
                client.getDatabase("admin").runCommand(new org.bson.Document("ping", 1));
                logger.info("Conexão com MongoDB estabelecida.");
                return client;
            } catch (Exception ex) {
                logger.error("MongoDB está offline ou inacessível: " + ex.getMessage());
                client.close();
                return null;
            }
        } catch (MongoException e) {
            logger.error("Não foi possível conectar ao MongoDB: " + e.getMessage());
            return null;
        }
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        if (mongoClient == null) {
            logger.warn("MongoDatabaseFactory não pode ser criado porque o MongoClient é nulo.");
            return null;
        }
        return new SimpleMongoClientDatabaseFactory(mongoClient, "clinacdb");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        if (mongoDatabaseFactory == null) {
            logger.warn("MongoTemplate não pode ser criado porque o MongoDatabaseFactory é nulo.");
            return null;
        }
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
