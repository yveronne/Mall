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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.User;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class Edit_employeeController extends Controller implements Initializable {

    @FXML
    private AnchorPane paneAnchor;
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
    private JFXTextField namefield;
    @FXML
    private JFXRadioButton caissiererbtn;
    @FXML
    private JFXRadioButton magasinierrbtn;
    
    private Stage currentStage;
    private TableView<User> userTable;
    private User userCurrent;
    
    private ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private JFXButton editbtn;
    
    private String name, username, email, tel, pwd, confpwd, typeString;
    
    private int type, isActive;
    
    private Pagination pagination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(){
        caissiererbtn.setToggleGroup(toggleGroup);
        magasinierrbtn.setToggleGroup(toggleGroup);
        
        namefield.setText(this.getUserCurrent().getName());
        usernamefield.setText(this.getUserCurrent().getUsername());
        emailfield.setText(this.getUserCurrent().getEmail());
        telephonefield.setText(this.getUserCurrent().getPhoneNumber());
        
        if(this.getUserCurrent().getTypeString().equals("Caissière")){
            caissiererbtn.setSelected(true);
        }
        else{
            magasinierrbtn.setSelected(true);
        }
    }


    @FXML
    private void caissiere(ActionEvent event) {
    }

    @FXML
    private void magasinier(ActionEvent event) {
    }

    
    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    
    public Stage getCurrentStage() {
        return currentStage;
    }
   


    public User getUserCurrent() {
        return userCurrent;
    }

    public void setUserCurrent(User userCurrent) {
        this.userCurrent = userCurrent;
    }

    @FXML
    private void edit(ActionEvent event) {
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
                if(caissiererbtn.isSelected()){
                    type = 0;
                    typeString = "Caissière";
                }
                else{
                    type = 1;
                    typeString = "Magasinier";
                }
                
                this.getUserCurrent().setName(name);
                this.getUserCurrent().setUsername(username);
                this.getUserCurrent().setType(type);
                this.getUserCurrent().setTypeString(typeString);
                this.getUserCurrent().setPhoneNumber(tel);
                this.getUserCurrent().setEmail(email);
                
                this.getUserCurrent().updateUser();
                
                Shop.users.remove(this.getUserCurrent());
            
                Shop.users.add(0, this.getUserCurrent());
                
                init(EmployeeController.list, pagination, userTable, Shop.users);
                
                this.currentStage.close();
                
                not.showNotifications(confirmation, "Gestionnaire modifié...", SUCCESS_NOT, time, bool);
                
            }catch (Exception ex) {
                not.showNotifications(error, "Veuillez remodifier un gestionnaire...", ECHEC_NOT, time, bool);
                return;
            }
        }
    }

    public TableView<User> getUserTable() {
        return userTable;
    }

    public void setUserTable(TableView<User> userTable) {
        this.userTable = userTable;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
    
}
