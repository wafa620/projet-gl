/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    // Constructor: when the UserDAO class is created, the connection is also established
    public UserDAO() {
        // Get the database connection using the DatabaseConnection class
        this.connection = ConnectionProvider.getConnection();
    }

    // Method to validate User login by matching username and password
    public boolean validateUser(User user) {
        String query = "SELECT * FROM admin WHERE Nomutilisateur = ? AND Motpasse = ?";
        boolean isSuperAdmin = false;

        // Using try-with-resources to automatically close the resources
        try ( Connection connection = ConnectionProvider.getConnection();  PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            ResultSet rs = pst.executeQuery(); // ResultSet will also be closed automatically

            if (rs.next()) {
                isSuperAdmin = true; // Found in admin table, User is a superadmin
                return true; // Valid admin User
            }

            // If not found in the admin table, check the responsable table
            query = "SELECT * FROM responsable WHERE Nomutilisateur = ? AND Motpasse = ?";
            try ( PreparedStatement pst2 = connection.prepareStatement(query)) {
                pst2.setString(1, user.getUsername());
                pst2.setString(2, user.getPassword());
                rs = pst2.executeQuery();

                if (rs.next()) {
                    return true; // Valid responsable User
                }
            }

            return false; // Invalid User, not found in both tables
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public boolean isSuperAdmin(User user) {
        String query = "SELECT * FROM admin WHERE Nomutilisateur = ? AND Motpasse = ?";

        try (Connection connection = ConnectionProvider.getConnection(); 
             PreparedStatement pst = connection.prepareStatement(query)) {
            
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            ResultSet rs = pst.executeQuery();

            return rs.next();  // Returns true if User found in admin table (SuperAdmin)
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Default to false if not found
    }
}

