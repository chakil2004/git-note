package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Configuration de la base de données
 */
public class DatabaseConfig {
    
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Fichier db.properties non trouvé");
            }
            properties.load(input);
            
            // Charger le driver
            Class.forName(properties.getProperty("db.driver"));
            
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erreur de chargement de la configuration", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.username"),
            properties.getProperty("db.password")
        );
    }
}
