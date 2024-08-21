package com.back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dataBaseManager {

    private static final String URL = "jdbc:sqlite:caminhoes.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace(); // Melhor para debugging
            return null;
        }
    }

    public static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS caminhoes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tipo TEXT NOT NULL, " +
                "marca TEXT NOT NULL, " +
                "ano INTEGER NOT NULL, " +
                "valor INTEGER NOT NULL, " +
                "carroceria REAL, " +
                "carga INTEGER)";

        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace(); // Melhor para debugging
        }
    }
}
