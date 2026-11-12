
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConnection {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    // Static block loads database configuration
    
static {

    Properties props = new Properties();

    try (InputStream input = DBConnection.class
            .getClassLoader()
            .getResourceAsStream("db.properties")) {

        if (input == null) {
            throw new RuntimeException(
                "db.properties NOT FOUND in classpath!"
            );
        }

        System.out.println("db.properties found!");

        props.load(input);

        URL = props.getProperty("db.url");
        USERNAME = props.getProperty("db.user");
        PASSWORD = props.getProperty("db.password");

        if (URL == null || USERNAME == null || PASSWORD == null) {
            throw new RuntimeException(
                "One or more database properties are missing!"
            );
        }

        System.out.println("Database configuration loaded!");

    } catch (Exception e) {

        System.out.println("DATABASE CONFIGURATION ERROR:");
        e.printStackTrace();

        throw new ExceptionInInitializerError(e);
    }
}


    // Establish database connection
    public static Connection getConnection() {

        try {

            Connection con = DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
            );

            System.out.println("Database connected successfully!");

            return con;

        } catch (SQLException e) {

            System.out.println("Database connection failed!");

            e.printStackTrace();

            return null;
        }
    }
}
