/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manageflow1;
import controller.LoginController;
import dao.UserDAO;
import view.LoginView;
/**
 *
 * @author wafaj
 */
public class ManageFlow1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginView login = new LoginView();
        UserDAO userdao = new UserDAO();
        new LoginController(login, userdao);
    }
    
}
