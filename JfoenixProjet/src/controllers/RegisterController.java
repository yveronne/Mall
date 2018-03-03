/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Admin;

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
    
    public void login(){
        if(adminCheckbox.isSelected()){
            Admin admin = new Admin(usernamefield.getText(), passwordfield.getText());
            System.out.println(admin.login());
        }
        else System.out.println("Connecte toi sur whatsapp");
    }
    
}
