/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Produit;
import javax.swing.*;
import java.util.List;
import dao.ProduitDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author wafaj
 */
public class ProduitView extends javax.swing.JFrame {

    private ProduitDAO produitdao;

    public ProduitView(ProduitDAO dao) {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // This makes it fullscreen
        setLocationRelativeTo(null);
        this.produitdao = dao;
        loadProduits();

        jButton1.addActionListener(this::jButtonAjouterActionPerformed); // Modifier
        jButton2.addActionListener(this::jButtonModifierActionPerformed); // Ajouter
        jButton3.addActionListener(this::jButtonSupprimerActionPerformed); // Supprimer
        jButton4.addActionListener(this::jButtonReinitialiserActionPerformed); // Réinitialiser
        jButton5.addActionListener(this::jButtonFermerActionPerformed); //fermer

        // Ajouter un MouseListener à la table pour la sélection d'un employé
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    jTextField1.setText(jTable1.getValueAt(selectedRow, 0).toString());
                    jTextField2.setText(jTable1.getValueAt(selectedRow, 1).toString());
                    jComboBox1.setSelectedItem(jTable1.getValueAt(selectedRow, 2).toString());
                    jTextField3.setText(jTable1.getValueAt(selectedRow, 3).toString());
                    jTextField4.setText(jTable1.getValueAt(selectedRow, 4).toString());
                    jTextArea1.setText(jTable1.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    private void jButtonReinitialiserActionPerformed(java.awt.event.ActionEvent evt) {
        // Clear all text fields
        jTextField1.setText("");
        jTextField2.setText("");
        jTextArea1.setText("");
        jTextField4.setText("");
        jTextField3.setText("");
        jComboBox1.setSelectedIndex(0);
    }

    private void jButtonAjouterActionPerformed(java.awt.event.ActionEvent evt) {
        // Get data from text fields
        String id = jTextField1.getText();
        String nom = jTextField2.getText();
        String categorie = (String) jComboBox1.getSelectedItem();
        String quantite = jTextField3.getText();
        String prixText = jTextField4.getText();
        String description = jTextArea1.getText();

        // Vérification des champs vides
        if (id.isEmpty() || nom.isEmpty() || categorie == null || quantite.isEmpty() || prixText.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !");
            return; // Ne pas continuer l'ajout si un champ est vide
        }

        // Vérifier si l'ID contient des lettres
        if (!id.matches("[0-9]+") || !quantite.matches("[0-9]+") || !prixText.matches("[0-9]+(\\.[0-9]+)?")) {
            JOptionPane.showMessageDialog(null, "L'ID, la quantité ou le prix ne doivent contenir que des chiffres.");
            return; // Ne pas continuer l'ajout si des lettres sont présentes
        }

        try {
            // Convert fields to appropriate types
            int id1 = Integer.parseInt(id); // Parse the ID to int
            int quantite1 = Integer.parseInt(quantite); // Parse the quantity to int
            double prix = Double.parseDouble(prixText); // Parse the price to double
            if (produitdao.isProduitExistByName(nom)) {
                JOptionPane.showMessageDialog(null, "Un produit avec ce nom existe déjà.");
                return; // Ne pas continuer l'ajout si le produit existe déjà
            }
            // Create new product object
            Produit p = new Produit(id1, nom, categorie, quantite1, prix, description);

            // Add product to the database
            boolean success = produitdao.addProduit(p);

            if (success) {
                JOptionPane.showMessageDialog(this, "Produit ajouté avec succès!");
                updateTable(); // Refresh the table

                // Clear fields after successful addition
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextArea1.setText("");
                jComboBox1.setSelectedIndex(0); // Reset combo box selection
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de produit.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour l'ID, la quantité et le prix.");
        }
    }

    private void jButtonModifierActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow(); // Get selected row
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à modifier.");
            return;
        }

        // Get updated values from text fields
        String idText = jTextField1.getText();
        String nom = jTextField2.getText();
        String categorie = (String) jComboBox1.getSelectedItem();
        String quantiteText = jTextField3.getText(); // Quantité field
        String prixText = jTextField4.getText(); // Prix field
        String description = jTextArea1.getText();

        // Vérification des champs vides
        if (idText.isEmpty() || nom.isEmpty() || categorie == null || quantiteText.isEmpty() || prixText.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !");
            return; // Ne pas continuer la modification si un champ est vide
        }

        try {
            // Convert fields to appropriate types
            int id = Integer.parseInt(idText); // Parse the ID to int
            int quantite = Integer.parseInt(quantiteText); // Parse the quantity to int
            double prix = Double.parseDouble(prixText); // Parse the price to double

            // Create updated product object
            Produit p = new Produit(id, nom, categorie, quantite, prix, description);

            boolean success = produitdao.updateProduit(p);

            if (success) {
                JOptionPane.showMessageDialog(this, "Produit modifié avec succès!");
                updateTable(); // Refresh the table

                // Clear fields after successful update
                jTextField1.setText("");
                jTextField2.setText("");
                jTextArea1.setText("");
                jTextField4.setText("");
                jTextField3.setText("");
                jComboBox1.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour l'ID, la quantité et le prix.");
        }
    }

    private void jButtonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow(); // Get selected row
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à supprimer.");
            return;
        }

        // Retrieve product ID from the selected row
        int productId = Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString());

        // Ask for confirmation before deletion
        int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce produit ?",
                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

        // If user clicked "Yes", proceed with deletion
        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = produitdao.deleteProduit(productId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Produit supprimé avec succès!");
                updateTable(); // Refresh the table to reflect changes
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Suppression annulée.");
        }
    }

    private void jButtonFermerActionPerformed(ActionEvent evt) {
        this.dispose();  // Close the current window

        // Create HomeView using the no-argument constructor
        HomeView homeview = new HomeView();  // Will initialize with default isSuperAdmin = false
        homeview.setVisible(true);

    }

    public void loadProduits() {
        List<Produit> produits = produitdao.getAllProduits();
        String[] columnNames = {"ID", "Nom", "Categorie", "Quantite", "Prix", "Description"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Produit p : produits) {
            model.addRow(new Object[]{
                p.getId(), p.getNom(), p.getCategorie(), p.getQuantite(), p.getPrix(), p.getDescription()
            });
        }
        jTable1.setModel(model);
    }

    private void updateTable() {

        List<Produit> produits = this.produitdao.getAllProduits();

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nom", "Categorie", "Quantite", "Prix", "Description"}, 0
        );

        for (Produit p : produits) {
            model.addRow(new Object[]{
                p.getId(), p.getNom(), p.getCategorie(), p.getQuantite(), p.getPrix(), p.getDescription()
            });
        }
        jTable1.setModel(model);
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = jTable1.getSelectedRow(); // Get selected row

        // Fill text fields with the selected row data
        jTextField1.setText(jTable1.getValueAt(selectedRow, 0).toString());
        jTextField2.setText(jTable1.getValueAt(selectedRow, 1).toString());
        jComboBox1.setSelectedItem(jTable1.getValueAt(selectedRow, 2).toString());
        jTextField3.setText(jTable1.getValueAt(selectedRow, 3).toString());
        jTextField4.setText(jTable1.getValueAt(selectedRow, 4).toString());
        jTextArea1.setText(jTable1.getValueAt(selectedRow, 5).toString());

    }

    public int getId() {
        return Integer.parseInt(jTextField1.getText());
    }

    public String getNom() {
        return jTextField2.getText();
    }

    public String getDescription() {
        return jTextArea1.getText();
    }

    public double getPrix() {
        return Double.parseDouble(jTextField4.getText());
    }

    public int getQuantite() {
        return Integer.parseInt(jTextField3.getText());
    }

    public String getCategorie() {
        return jComboBox1.getSelectedItem().toString();
    }

    public JTable getProduitTable() {
        return jTable1;  // Use jTable1 instead of responsableTable
    }

// Add listeners to the buttons
    public void addAjouterListener(ActionListener listener) {
        jButton1.addActionListener(listener); // Replace with actual button name
    }

    public void addModifierListener(ActionListener listener) {
        jButton2.addActionListener(listener); // Replace with actual button name
    }

    public void addSupprimerListener(ActionListener listener) {
        jButton3.addActionListener(listener); // Replace with actual button name
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nom", "Categorie", "Quantite", "Prix", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cheveux", "Corps", "Visage" }));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jButton1.setText("Ajouter");

        jButton2.setText("Modifier");

        jButton3.setText("Supprimer");

        jButton4.setText("Reintialiser");

        jButton5.setText("Retour");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox1, 0, 157, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(336, 336, 336))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(101, 101, 101))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
