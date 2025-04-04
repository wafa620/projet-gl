/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// GestionProduitsController.java (Contrôleur)
package controller;

import model.Produit;
import dao.ProduitDAO;
import view.ProduitView;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author wafaj
 */
public class ProduitController {

    private Connection conn;
    private ProduitView produit;
    private ProduitDAO produitdao;

    public ProduitController() {

        try {
            // Make sure to update the database connection details
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhairabeautyy?useSSL=false&serverTimezone=UTC", "root", "123789");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProduitController(ProduitView produit, ProduitDAO produitdao) {
        this.produit = produit;
        this.produitdao = produitdao;
        this.produit.addAjouterListener(new AjouterListener());
        this.produit.addModifierListener(new ModifierListener());
        this.produit.addSupprimerListener(new SupprimerListener());
    }

    class AjouterListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int id = produit.getId();  // Fetching data from the view
        String nom = produit.getNom();
        String categorie = produit.getCategorie();
        int quantite = produit.getQuantite();
        double prix = produit.getPrix();
        String description = produit.getDescription();

        // Create a new product
        Produit newProduit = new Produit(id, nom, categorie, quantite, prix, description);

        // Add the product to the database using the DAO
        if (produitdao.addProduit(newProduit)) {
            JOptionPane.showMessageDialog(produit, "Produit ajouté avec succès.");
            produit.loadProduits();  // Refresh the product table view
        } else {
            JOptionPane.showMessageDialog(produit, "Erreur lors de l'ajout du produit.");
        }
    }
}


    class ModifierListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get selected row data from the table
            int selectedRow = produit.getProduitTable().getSelectedRow();
            if (selectedRow != -1) { // Check if a row is selected
                // Get data from the selected row
                int id = Integer.parseInt(produit.getProduitTable().getValueAt(selectedRow, 0).toString());
                String nom = produit.getProduitTable().getValueAt(selectedRow, 1).toString();
                String categorie = produit.getProduitTable().getValueAt(selectedRow, 2).toString();
                int quantite = Integer.parseInt(produit.getProduitTable().getValueAt(selectedRow, 3).toString());
                double prix = Double.parseDouble(produit.getProduitTable().getValueAt(selectedRow, 4).toString());
                String description = produit.getProduitTable().getValueAt(selectedRow, 5).toString();

                // Create a Responsable object with updated values
                Produit updatedproduit = new Produit(id, nom, categorie, quantite, prix, description);

                // Modify the Responsable in the database
                if (produitdao.updateProduit(updatedproduit)) {
                    JOptionPane.showMessageDialog(produit, "Produit modifié avec succès.");
                    produit.loadProduits(); // Reload the responsables table
                } else {
                    JOptionPane.showMessageDialog(produit, "Erreur lors de la modification du produit.");
                }
            } else {
                JOptionPane.showMessageDialog(produit, "Veuillez sélectionner un produit.");
            }
        }
    }

    // Listener for "Supprimer" (Delete) button
    class SupprimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get selected row data from the table
            int selectedRow = produit.getProduitTable().getSelectedRow();
            if (selectedRow != -1) {
                // Get username or other unique identifier from the selected row
                String id =  (String) produit.getProduitTable().getValueAt(selectedRow, 0);  // Casting it directly to an int

                // Delete the Responsable from the database
                if (produitdao.deleteProduit(id)) {
                    JOptionPane.showMessageDialog(produit, "Produit supprimé avec succès.");
                    produit.loadProduits();
                } else {
                    JOptionPane.showMessageDialog(produit, "Erreur lors de la suppression du produit.");
                }
            } else {
                JOptionPane.showMessageDialog(produit, "Veuillez sélectionner un produit.");
            }
        }
    }
}
