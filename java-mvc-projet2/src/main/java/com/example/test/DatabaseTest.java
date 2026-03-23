package com.example.test;

import com.example.config.DatabaseConfig;
import java.sql.Connection;

/**
 * Test simple de connexion à la base de données
 */
public class DatabaseTest {
    public static void main(String[] args) {
        try {
            System.out.println("Test de connexion à la base de données...");
            Connection conn = DatabaseConfig.getConnection();
            
            if (conn != null) {
                System.out.println("✅ Connexion réussie !");
                System.out.println("URL: " + conn.getMetaData().getURL());
                conn.close();
            } else {
                System.out.println("❌ Connexion échouée !");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
