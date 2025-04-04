/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Produit;

public class ProduitDAO {

    private Connection connection;

    // Constructor to initialize the connection
    public ProduitDAO() {
        this.connection = ConnectionProvider.getConnection();
    }

    // Method to add a new employee to the Responsable table
    public boolean addProduit(Produit p) {
        String sql = "INSERT INTO produit (Nom, Categorie, Quantite, Prix, Description) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, p.getNom());
            stmt.setString(2, p.getCategorie());
            stmt.setInt(3, p.getQuantite());
            stmt.setDouble(4, p.getPrix());
            stmt.setString(5, p.getDescription());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve generated ID
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));  // Set the new ID in the object
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing employee in the Responsable table
    public boolean updateProduit(Produit produit) {
        String query = "UPDATE produit SET Nom = ?, Categorie = ?, Quantite = ?, Prix = ?, Description = ? WHERE ID = ?";
        try ( PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, produit.getId());
            pst.setString(2, produit.getNom());
            pst.setString(3, produit.getCategorie());
            pst.setInt(4, produit.getQuantite());
            pst.setDouble(5, produit.getPrix());
            pst.setString(6, produit.getDescription());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete an employee from the Responsable table
   public boolean deleteProduit(String id) {
    String query = "DELETE FROM produit WHERE ID = ?";
    try (PreparedStatement pst = connection.prepareStatement(query)) {
        pst.setString(1, id);  // Change from setString to setInt
        return pst.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public List<Produit> getAllProduits() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Produit p = new Produit(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Categorie"),
                        rs.getInt("Quantite"),
                        rs.getDouble("Prix"),
                        rs.getString("Description")
                );
                produits.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }

    public boolean deleteProduit(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public boolean isProduitExistByName(String nom) {
    // SQL query to check if a product with the given name exists
    String query = "SELECT COUNT(*) FROM produit WHERE Nom = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, nom);  // Set the product name as a parameter
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1) > 0;  // If count > 0, it means the product exists
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false;  // Return false if no product with the same name was found
}

  

}
