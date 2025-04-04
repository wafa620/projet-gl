/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Responsable;
import javax.swing.*;
import java.util.List;
import dao.ResponsableDAO;
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
public class EmployeView extends javax.swing.JFrame {

    private ResponsableDAO responsabledao;

    public EmployeView(ResponsableDAO dao) {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // This makes it fullscreen
        setLocationRelativeTo(null);
        this.responsabledao = dao;
        loadResponsables();

        jButton1.addActionListener(this::jButtonModifierActionPerformed); // Modifier
        jButton2.addActionListener(this::jButtonAjouterActionPerformed); // Ajouter
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
                    jTextField3.setText(jTable1.getValueAt(selectedRow, 2).toString());
                    jTextField4.setText(jTable1.getValueAt(selectedRow, 3).toString());
                    jTextField5.setText(jTable1.getValueAt(selectedRow, 4).toString());
                    jTextField6.setText(jTable1.getValueAt(selectedRow, 5).toString());
                    jTextField7.setText(jTable1.getValueAt(selectedRow, 6).toString());
                }
            }
        });
    }

    private void jButtonReinitialiserActionPerformed(java.awt.event.ActionEvent evt) {
        // Clear all text fields
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
    }

    private void jButtonAjouterActionPerformed(java.awt.event.ActionEvent evt) {
        // Get data from text fields
        String nom = jTextField1.getText();
        String prenom = jTextField2.getText();
        String tel = jTextField3.getText();
        String cin = jTextField4.getText();
        String adresse = jTextField5.getText();
        String username = jTextField6.getText();
        String password = jTextField7.getText();

        if (isAnyFieldEmpty(nom, prenom, tel, cin, adresse, username, password)) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return; // Ne pas continuer l'ajout si un champ est vide
        }
        if (containsDigit(nom) || containsDigit(prenom)) {
            JOptionPane.showMessageDialog(this, "Le nom et le prénom ne doivent pas contenir de chiffres.");
            return;
        }

        if (!isValidPhoneNumber(tel)) {
            JOptionPane.showMessageDialog(this, "Numéro de téléphone invalide.");
            return;
        }

        if (!isValidCIN(cin)) {
            JOptionPane.showMessageDialog(this, "CIN invalide.");
            return;
        }

        // Create a new Responsable object
        Responsable r = new Responsable(nom, prenom, tel, cin, adresse, username, password);

        boolean success = responsabledao.addResponsable(r);

        if (success) {
            JOptionPane.showMessageDialog(this, "Employé ajouté avec succès!");
            updateTable(); // Refresh the table
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'employé.");
        }
    }

    private boolean containsDigit(String str) {
        return str.matches(".*\\d.*"); // Vérifie si la chaîne contient au moins un chiffre
    }

    private boolean isAnyFieldEmpty(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    // Helper method to validate phone number format (example: "123-456-7890")

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{8}");
    }

// Helper method to validate CIN format (just an example)
    private boolean isValidCIN(String cin) {
        return cin.matches("[A-Za-z0-9]{8}"); // Adjust the regex as per your CIN format
    }

    private void jButtonModifierActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow(); // Get selected row
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un employé à modifier.");
            return;
        }

        // Retrieve username from the selected row
        String username = jTable1.getValueAt(selectedRow, 5).toString();

        // Get updated values from text fields
        String nom = jTextField1.getText();
        String prenom = jTextField2.getText();
        String tel = jTextField3.getText();
        String cin = jTextField4.getText();
        String adresse = jTextField5.getText();
        String password = jTextField7.getText();
        // Validate inputs
        if (isAnyFieldEmpty(nom, prenom, tel, cin, adresse, username, password)) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return;
        }

        if (!isValidPhoneNumber(tel)) {
            JOptionPane.showMessageDialog(this, "Numéro de téléphone invalide.");
            return;
        }
        if (containsDigit(nom) || containsDigit(prenom)) {
            JOptionPane.showMessageDialog(this, "Le nom et le prénom ne doivent pas contenir de chiffres.");
            return;
        }

        if (!isValidCIN(cin)) {
            JOptionPane.showMessageDialog(this, "CIN invalide.");
            return;
        }
        // Create updated Responsable object
        Responsable r = new Responsable(nom, prenom, tel, cin, adresse, username, password);

        boolean success = responsabledao.updateResponsable(r);

        if (success) {
            JOptionPane.showMessageDialog(this, "Employé modifié avec succès!");
            updateTable(); // Refresh table
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.");
        }
    }

    private void jButtonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow(); // Get selected row
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un employé à supprimer.");
            return;
        }

        // Retrieve username from the selected row
        String username = jTable1.getValueAt(selectedRow, 5).toString();

        // Ask for confirmation before deletion
        int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet employé ?",
                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

        // If user clicked "Yes", proceed with deletion
        if (confirmation == JOptionPane.YES_OPTION) {
            if (username != null && !username.isEmpty()) {
                boolean success = responsabledao.deleteResponsable(username);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Employé supprimé avec succès!");
                    updateTable(); // Refresh the table to reflect changes
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur invalide.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Suppression annulée.");
        }
    }

    private void jButtonFermerActionPerformed(ActionEvent evt) {
        this.dispose(); // Close the current window

        boolean isSuperAdmin = true; // Set this based on user type
        HomeView home = new HomeView(isSuperAdmin);
        home.setVisible(true);
    }

    public void loadResponsables() {
        List<Responsable> responsables = responsabledao.getAllResponsables();
        String[] columnNames = {"Nom", "Prenom", "Tel", "Cin", "Adresse", "NomUtilisateur", "Motpasse"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Responsable r : responsables) {
            model.addRow(new Object[]{
                r.getNom(), r.getPrenom(), r.getTel(), r.getCin(), r.getAdresse(), r.getUsername(), r.getPassword()
            });
        }
        jTable1.setModel(model);
    }

    private void updateTable() {

        List<Responsable> responsables = this.responsabledao.getAllResponsables();

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Nom", "Prenom", "Tel", "CIN", "Adresse", "Nomutilisateur", "Motpasse"}, 0
        );

        for (Responsable r : responsables) {
            model.addRow(new Object[]{
                r.getNom(), r.getPrenom(), r.getTel(), r.getCin(), r.getAdresse(), r.getUsername(), r.getPassword()
            });
        }
        jTable1.setModel(model);
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = jTable1.getSelectedRow(); // Get selected row

        // Fill text fields with the selected row data
        jTextField1.setText(jTable1.getValueAt(selectedRow, 0).toString()); // Nom
        jTextField2.setText(jTable1.getValueAt(selectedRow, 1).toString()); // Prenom
        jTextField3.setText(jTable1.getValueAt(selectedRow, 2).toString()); // Tel
        jTextField4.setText(jTable1.getValueAt(selectedRow, 3).toString()); // CIN
        jTextField5.setText(jTable1.getValueAt(selectedRow, 4).toString()); // Adresse
        jTextField6.setText(jTable1.getValueAt(selectedRow, 5).toString()); // Nomutilisateur
        jTextField7.setText(jTable1.getValueAt(selectedRow, 6).toString()); // Motpasse
    }

    public String getNomField() {
        return jTextField1.getText(); // Replace with actual text field name
    }

    public String getPrenomField() {
        return jTextField2.getText(); // Replace with actual text field name
    }

    public String getTelField() {
        return jTextField3.getText(); // Replace with actual text field name
    }

    public String getCinField() {
        return jTextField4.getText(); // Replace with actual text field name
    }

    public String getAdresseField() {
        return jTextField5.getText(); // Replace with actual text field name
    }

    public String getNomUtilisateurField() {
        return jTextField6.getText(); // Replace with actual text field name
    }

    public String getMotpasseField() {
        return jTextField7.getText(); // Replace with actual text field name
    }

    public JTable getResponsableTable() {
        return jTable1;  // Use jTable1 instead of responsableTable
    }

// Add listeners to the buttons
    public void addAjouterListener(ActionListener listener) {
        jButton2.addActionListener(listener); // Replace with actual button name
    }

    public void addModifierListener(ActionListener listener) {
        jButton1.addActionListener(listener); // Replace with actual button name
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

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nom", "Prenom", "Tel", "CIN", "Adresse", "Nom d'utilisateur", "Mot de passe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Modifier");

        jButton2.setText("Ajouter");

        jButton3.setText("Supprimer");

        jButton4.setText("Rénitialiser");

        jButton5.setText("Retour");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                        .addComponent(jTextField3)
                                        .addComponent(jTextField2)
                                        .addComponent(jTextField1))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField6)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))))
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jButton2)
                        .addGap(53, 53, 53)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(72, 72, 72)
                        .addComponent(jButton3)
                        .addGap(63, 63, 63)
                        .addComponent(jButton4)
                        .addGap(59, 59, 59)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(366, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton2))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            ResponsableDAO dao = new ResponsableDAO();  // Make sure you have this object
            new EmployeView(dao).setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

}
