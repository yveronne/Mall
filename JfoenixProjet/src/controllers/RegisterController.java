/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Admin;
import models.User;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class RegisterController extends Controller implements Initializable {
    
    @FXML
    private Button loginButton;
    
    @FXML
    private CheckBox adminCheckbox;
    
    @FXML
    private TextField usernamefield;
            
    @FXML
    private PasswordField passwordfield;
    
    private Stage currentStage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    
    public void login() throws SQLException{
        //JE TESTE LA CONNEXION DE L'ADMINISTRATEUR
        /*if(adminCheckbox.isSelected()){
            Admin admin = new Admin(usernamefield.getText(), passwordfield.getText());
            System.out.println(admin.login());
        }
        else System.out.println("Connecte toi sur whatsapp");*/
        
        
        //JE TESTE LA CREATION D'UN UTILISATEUR
        /*User user = new User("YEPMO", 0, "bambi", "123", 1, "697814563", "yver@gmail.com" );
        try {
            System.out.println(user.createUser());
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        //JE TESTE LA LISTE DES UTILISATEURS
        //System.out.println(User.getUsers().size());
        
        
        //JE TESTE LA CONNEXION
        //System.out.println(User.login(usernamefield.getText(), passwordfield.getText()));
        
        
        //JE TESTE L'ACTIVATION
        /*User user = new User("YEPMO", 0, "bambi", "123", 1, "697814563", "yver@gmail.com" );
        System.out.println(user.activateUser());*/
        
        //JE TESTE LA SUPPRESSION
        User user = new User("BIYONG", 1, "lepere", "123", 1, "697814563", "yver@gmail.com" );
        System.out.println(user.deleteUser());
    }
    
}
