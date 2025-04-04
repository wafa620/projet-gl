/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Responsable;
import dao.ResponsableDAO;
import view.EmployeView;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author wafaj
 */
public class ResponsableController {

    private Connection conn;
    private EmployeView employes;
    private ResponsableDAO responsabledao;

    public ResponsableController() {

        try {
            // Make sure to update the database connection details
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhairabeautyy?useSSL=false&serverTimezone=UTC", "root", "123789");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String username, String password) {
        try {
            String query = "SELECT * FROM responsable WHERE Nomutilisateur = ? AND Motpasse = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);  // In real-world, don't store plain text passwords!
            ResultSet rs = stmt.executeQuery();

            return rs.next();  // Returns true if the user exists with the provided credentials
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResponsableController(EmployeView employes, ResponsableDAO responsabledao) {
        this.employes = employes;
        this.responsabledao = responsabledao;
        this.employes.addAjouterListener(new AjouterListener());
        this.employes.addModifierListener(new ModifierListener());
        this.employes.addSupprimerListener(new SupprimerListener());
    }

    class AjouterListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String nom = employes.getNomField();
            String prenom = employes.getPrenomField();
            String tel = employes.getTelField();
            String cin = employes.getCinField();
            String adresse = employes.getAdresseField();
            String username = employes.getNomUtilisateurField();
            String password = employes.getMotpasseField();

            Responsable newResponsable = new Responsable(nom, prenom, tel, cin, adresse, username, password);


            // Add the new Responsable to the database
            if (responsabledao.addResponsable(newResponsable)) {
                JOptionPane.showMessageDialog(employes, "Responsable ajouté avec succès.");
                employes.loadResponsables(); // Reload the responsables table
            } else {
                JOptionPane.showMessageDialog(employes, "Erreur lors de l'ajout du responsable.");
            }

        }

    }

   class ModifierListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get selected row data from the table
            int selectedRow = employes.getResponsableTable().getSelectedRow();
            if (selectedRow != -1) { // Check if a row is selected
                // Get data from the selected row
                String nom = (String) employes.getResponsableTable().getValueAt(selectedRow, 0);
                String prenom = (String) employes.getResponsableTable().getValueAt(selectedRow, 1);
                String tel = (String) employes.getResponsableTable().getValueAt(selectedRow, 2);
                String cin = (String) employes.getResponsableTable().getValueAt(selectedRow, 3);
                String adresse = (String) employes.getResponsableTable().getValueAt(selectedRow, 4);
                String username = (String) employes.getResponsableTable().getValueAt(selectedRow, 5);
                String password = (String) employes.getResponsableTable().getValueAt(selectedRow, 6);

                // Create a Responsable object with updated values
                Responsable updatedResponsable = new Responsable(nom, prenom, tel, cin, adresse, username, password);

                // Modify the Responsable in the database
                if (responsabledao.updateResponsable(updatedResponsable)) {
                    JOptionPane.showMessageDialog(employes, "Responsable modifié avec succès.");
                    employes.loadResponsables(); // Reload the responsables table
                } else {
                    JOptionPane.showMessageDialog(employes, "Erreur lors de la modification du responsable.");
                }
            } else {
                JOptionPane.showMessageDialog(employes, "Veuillez sélectionner un responsable.");
            }
        }
    }

   // Listener for "Supprimer" (Delete) button
    class SupprimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get selected row data from the table
            int selectedRow = employes.getResponsableTable().getSelectedRow();
            if (selectedRow != -1) {
                // Get username or other unique identifier from the selected row
                String username = (String) employes.getResponsableTable().getValueAt(selectedRow, 5);

                // Delete the Responsable from the database
                if (responsabledao.deleteResponsable(username)) {
                    JOptionPane.showMessageDialog(employes, "Responsable supprimé avec succès.");
                    employes.loadResponsables(); // Reload the responsables table
                } else {
                    JOptionPane.showMessageDialog(employes, "Erreur lors de la suppression du responsable.");
                }
            } else {
                JOptionPane.showMessageDialog(employes, "Veuillez sélectionner un responsable.");
            }
        }
    }
}
