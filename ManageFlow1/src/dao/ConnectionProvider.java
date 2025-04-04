/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final String URL = "jdbc:mysql://localhost:3306/zhairabeautyy?useSSL=false&serverTimezone=UTC"; // Replace with your database URL
    private static final String USER = "root";  // Replace with your database username
    private static final String PASSWORD = "123789";  // Replace with your database password

    public static Connection getConnection() {
        try {
            // Create and return a new connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Database connection failed");
        }
    }
}
