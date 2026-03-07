package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple database configuration loader.
 * <p>
 * Reads JDBC settings from <code>db.properties</code> on the classpath.
 */
public class DatabaseConfig {

    private static final String PROPERTIES_FILE = "/db.properties";
    private static final Properties PROPS = loadProperties();

    private DatabaseConfig() {
        // utility class
    }

    private static Properties loadProperties() {
        Properties props = new Properties();

        // 1) Try load from classpath (packaged in JAR / build output)
        try (InputStream in = DatabaseConfig.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (in != null) {
                props.load(in);
                return props;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load database configuration from classpath", e);
        }

        // 2) Fallback: try loading from working directory (e.g., when running directly from IDE)
        try (InputStream in = DatabaseConfig.class.getResourceAsStream("db.properties")) {
            // try without leading slash (some classloaders treat paths differently)
            if (in != null) {
                props.load(in);
                return props;
            }
        } catch (IOException ignored) {
            // ignore - will try next option
        }

        // 3) Another fallback: plain file from current working directory
        try (InputStream in = java.nio.file.Files.newInputStream(java.nio.file.Paths.get("db.properties"))) {
            props.load(in);
            return props;
        } catch (IOException ignored) {
            // ignore - will throw below
        }

        throw new IllegalStateException("Could not find " + PROPERTIES_FILE + " on classpath or db.properties in working directory");
    }

    public static Connection getConnection() throws SQLException {
        String driver = PROPS.getProperty("jdbc.driver");
        if (driver != null && !driver.isBlank()) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("JDBC driver not found: " + driver, e);
            }
        }

        String url = PROPS.getProperty("jdbc.url");
        String user = PROPS.getProperty("jdbc.user");
        String password = PROPS.getProperty("jdbc.password");

        return DriverManager.getConnection(url, user, password);
    }

    public static String get(String key) {
        return PROPS.getProperty(key);
    }
}
