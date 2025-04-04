/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import view.LoginView;
import view.HomeView;
import dao.UserDAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView login;
    private UserDAO userdao;

    public LoginController(LoginView login, UserDAO userDAO) {
        this.login = login;
        this.userdao = userDAO;
        this.login.addLoginListener(new LoginButtonListener());
    }

    class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = login.getUsername(); // Get username from the LoginView screen
            String password = login.getPassword(); // Get password from the LoginView screen

            User user = new User(username, password);
            boolean isSuperAdmin = false;

            if (userdao.validateUser(user)) { // Check if User exists in either admin or responsable table
                isSuperAdmin = userdao.isSuperAdmin(user);

                HomeView home = new HomeView(isSuperAdmin); // Pass the boolean to the HomeView constructor
                home.setVisible(true);
                login.dispose(); // Close the LoginView screen
            } else {
                JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect.");
            }
        }
    }
}
