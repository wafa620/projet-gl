/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Responsable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsableDAO {

    private Connection connection;

    // Constructor to initialize the connection
    public ResponsableDAO() {
        this.connection = ConnectionProvider.getConnection();
    }

    // Method to add a new employee to the Responsable table
    public boolean addResponsable(Responsable r) {

        String sql = "INSERT INTO responsable (Nom, Prenom, Tel, CIN, Adresse, Nomutilisateur, Motpasse) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, r.getNom());
            stmt.setString(2, r.getPrenom());
            stmt.setString(3, r.getTel());
            stmt.setString(4, r.getCin());
            stmt.setString(5, r.getAdresse());
            stmt.setString(6, r.getUsername());
            stmt.setString(7, r.getPassword());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing employee in the Responsable table
    public boolean updateResponsable(Responsable responsable) {
        String query = "UPDATE responsable SET Nom = ?, Prenom = ?, Tel = ?, CIN = ?, Adresse = ?, Motpasse = ? WHERE Nomutilisateur = ?";
        try ( PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, responsable.getNom());
            pst.setString(2, responsable.getPrenom());
            pst.setString(3, responsable.getTel());
            pst.setString(4, responsable.getCin());
            pst.setString(5, responsable.getAdresse());
            pst.setString(6, responsable.getPassword()); // Fix: Password should be set before the username
            pst.setString(7, responsable.getUsername()); // Fix: Username should be at the last index
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete an employee from the Responsable table
    public boolean deleteResponsable(String nomUtilisateur) {
        String query = "DELETE FROM responsable WHERE Nomutilisateur = ?";
        try ( PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, nomUtilisateur);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Responsable> getAllResponsables() {
        List<Responsable> responsables = new ArrayList<>();
        String sql = "SELECT * FROM responsable";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Responsable r = new Responsable(
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Tel"),
                        rs.getString("CIN"),
                        rs.getString("Adresse"),
                        rs.getString("Nomutilisateur"),
                        rs.getString("Motpasse")
                );
                responsables.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return responsables;
    }

}
