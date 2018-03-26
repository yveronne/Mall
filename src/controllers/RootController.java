/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.User;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class RootController extends Controller implements Initializable {

    
    @FXML
    private JFXButton administratorbtn;
    @FXML
    private JFXTextField usernamefield;
    @FXML
    private JFXTextField emailfield;
    @FXML
    private JFXTextField telephonefield;
    @FXML
    private JFXPasswordField passwordfield;
    @FXML
    private JFXPasswordField confirmpasswordfield;
    @FXML
    private JFXButton addbtn;
    @FXML
    private JFXButton employeesbtn;
    @FXML
    private JFXTextField namefield;
    @FXML
    private JFXRadioButton caissiererbtn;
    @FXML
    private JFXRadioButton magasinierrbtn;
    
    
    private Stage currentStage;
    private Scene currentScene;
    private boolean caissiere = false;
    private boolean magasinier = false;
    private boolean actif = false;
    private String name, username, email, tel, pwd, confpwd;
    private String nameXml = "", pwdXml = "";
    private ToggleGroup type = new ToggleGroup();
    @FXML
    private JFXButton signoutbtn;
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane paneAnchor;
    
    private double WIDTH = 638, HEIGHT = 320;
    @FXML
    private Rectangle rec;
    @FXML
    private AnchorPane anchorRectangle;
    
    public void init(){
        rec.widthProperty().bind(getCurrentScene().widthProperty());
        rec.heightProperty().bind(getCurrentScene().heightProperty().subtract(getCurrentScene().heightProperty().divide(20)));
        
        getCurrentScene().widthProperty().addListener(new ChangeListener<Number>(){
          
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double width_val = (newValue.doubleValue()-WIDTH)/2;
               
               AnchorPane.setLeftAnchor(paneAnchor, width_val);
               AnchorPane.setRightAnchor(paneAnchor, width_val);
            }
           
        });
        
        getCurrentScene().heightProperty().addListener(new ChangeListener<Number>(){
          
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double height_val = (newValue.doubleValue()-HEIGHT)/2;
               
               AnchorPane.setTopAnchor(paneAnchor, height_val);
               AnchorPane.setBottomAnchor(paneAnchor, height_val);
            }
           
        });
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        caissiererbtn.setToggleGroup(type);
        magasinierrbtn.setToggleGroup(type);
    }
    
   
    
    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void add(ActionEvent event) {
        
        if(namefield.getText().trim().equals("") || usernamefield.getText().trim().equals("") || emailfield.getText().trim().equals("")
                || telephonefield.getText().trim().equals("") || passwordfield.getText().trim().equals("") || confirmpasswordfield.getText().trim().equals("")
                || (!caissiererbtn.isSelected() && !magasinierrbtn.isSelected()) ){
            not.showNotifications(error, "S'il vous plait, completez tous les champs...", ECHEC_NOT, time, bool);
            return;
        }
        else{
            try {
                name = namefield.getText();
                username = usernamefield.getText();
                email = emailfield.getText();
                tel = telephonefield.getText();
                pwd = passwordfield.getText();
                confpwd = confirmpasswordfield.getText();
                int type;
                
                if(name.length() > 30){
                    not.showNotifications(error, "Le nom ne doit pas dépasser 30 caractères...", ECHEC_NOT, time, bool);
                    return;
                }
                if(username.length() > 30){
                    not.showNotifications(error, "Le nom d\'utilisateur ne doit pas dépasser 30 caractères...", ECHEC_NOT, time, bool);
                    return;
                }
                if(email.length() > 100){
                    not.showNotifications(error, "L\'email ne doit pas dépasser 100 caractères...", ECHEC_NOT, time, bool);
                    return;
                }
                
                if(tel.length() > 12){
                    not.showNotifications(error, "Le numéro de téléphone ne doit pas dépasser 12 caractères...", ECHEC_NOT, time, bool);
                    return;
                }
                
                if(pwd.length() > 30){
                    not.showNotifications(error, "Le mot de passe ne doit pas dépasser 30 caractères...", ECHEC_NOT, time, bool);
                    return;
                }
                
                try{
                    int telNumber = (int) Double.parseDouble(tel);
                    if(telNumber <= 0){
                        not.showNotifications(error, "Entrez un numéro de téléphone valide...", ECHEC_NOT, time, bool);
                        return;
                    }
                }catch(Exception e){
                    not.showNotifications(error, "Entrez un numéro de téléphone valide...", ECHEC_NOT, time, bool);
                    return;
                }
                
                if(!pwd.equals(confpwd)){
                    not.showNotifications(error, "Erreur de confirmation de mot de passe...", ECHEC_NOT, time, bool);
                    confirmpasswordfield.setText("");
                    return;
                }
                
                if(!caissiererbtn.isSelected() && !magasinierrbtn.isSelected()){
                    not.showNotifications(error, "Choisissez un type de gestionnaire...", ECHEC_NOT, time, bool);
                    return;
                }
                
                if(caissiererbtn.isSelected()) type = 0;
                else type = 1;
                
                User user = new User(name, type, username, pwd, 0, tel, email);
                
                user.createUser();
                
                Shop.users = User.getUsers();
                for(int i = 0; i < Shop.users.size(); i++){
                    if(Shop.users.get(i).getType()  == 0) {
                        Shop.users.get(i).setTypeString("Caissière");
                    }
                    else{
                        Shop.users.get(i).setTypeString("Magasinier");
                    }

                    if(Shop.users.get(i).getIsActive() == 1){
                        Shop.users.get(i).setStatus("Activé");
                    }
                    else{
                        Shop.users.get(i).setStatus("Désactivé");
                    }
                }
                Shop.showEmployee();
                not.showNotifications(confirmation, "Gestionnaire enregistré avec succès...", SUCCESS_NOT, time, bool);
            } catch (SQLException ex) {
                not.showNotifications(error, "Veuillez recréer un gestionnaire...", ECHEC_NOT, time, bool);
                return;
            }
        }
    }

    @FXML
    private void employees(ActionEvent event) throws SQLException {
        Shop.users = User.getUsers();
        for(int i = 0; i < Shop.users.size(); i++){
            if(Shop.users.get(i).getType()  == 0) {
                Shop.users.get(i).setTypeString("Caissière");
            }
            else{
                Shop.users.get(i).setTypeString("Magasinier");
            }
            
            if(Shop.users.get(i).getIsActive() == 1){
                Shop.users.get(i).setStatus("Activé");
            }
            else{
                Shop.users.get(i).setStatus("Désactivé");
            }
        }
        
        Shop.showEmployee();
    }

    private void actif(ActionEvent event) {
        //caissiererbtn.setSelected(false);
        
        if(caissiererbtn.isSelected()){
            actif = true;
        }
        else{
            actif= false;
        }
    }

    @FXML
    private void caissiere(ActionEvent event) {
        
        if(caissiererbtn.isSelected()){
            caissiere = true;
        }
        else{
            caissiere = false;
        }
    }

    @FXML
    private void magasinier(ActionEvent event) {
        
        if(magasinierrbtn.isSelected()){
            magasinier = true;
        }
        else{
            magasinier = false;
        }
    }

    @FXML
    private void home(ActionEvent event) {
        Shop.showRoot();
    }

    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}
