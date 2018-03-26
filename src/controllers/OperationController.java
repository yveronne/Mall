/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.GestionStock;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class OperationController extends Controller implements Initializable {

    private Stage currentStage;
    private TableView<Product> productTableView;
    
    
    @FXML
    private JFXTextField qtefield;
    @FXML
    private JFXButton okbtn;
    @FXML
    private JFXButton cancelbtn;
    @FXML
    private JFXRadioButton addrbtn;
    @FXML
    private JFXRadioButton retrieverbtn;
    
    private ToggleGroup toggle = new ToggleGroup();
    @FXML
    private Label succesLabel;
    @FXML
    private JFXTextField codeField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addrbtn.setSelected(true);
        addrbtn.setToggleGroup(toggle);
        retrieverbtn.setToggleGroup(toggle);
        codeField.setText(Shop.codeString);
    }    

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void ok(ActionEvent event){
        
        try {
            String qte = qtefield.getText(); int quantite;
            if(qte.trim().equals("")){
                not.showNotifications(error, "S\'il vous plait, completez tous les champs...", ECHEC_NOT, time, bool);
                return;
            }
            try {
                quantite = Integer.parseInt(qte);
            } catch (Exception e) {
                not.showNotifications(error, "La quantité doit être un entier naturel...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(quantite <= 0){
                not.showNotifications(error, "La quantité doit être positive...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(!addrbtn.isSelected() && !retrieverbtn.isSelected()){
                not.showNotifications(error, "Selectionner l\'opération à effectuer...", ECHEC_NOT, time, bool);
                return;
            }
            
            boolean type;
            if(addrbtn.isSelected()) {
                type = true;
            }
            else{
                type = false;
            }
            
            GestionStock s = new GestionStock(Shop.idGestionnaire, Shop.codeInt, quantite, type);
            s.createStock();
            Shop.stocks = GestionStock.listStocks();
            for(int i = 0; i < Shop.stocks.size(); i++){
                Shop.stocks.get(i).setIdCodeString(Shop.stocks.get(i).formatCode());
                //Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
                if(Shop.stocks.get(i).isTypegest()) Shop.stocks.get(i).setTypeString("Ajout");
                else Shop.stocks.get(i).setTypeString("Retrait");
            }
            this.getCurrentStage().close();
            not.showNotifications(confirmation, "Le stock a été ajouté avec succès...", SUCCESS_NOT, time, bool);
            Shop.showStock();
        } catch (Exception ex) {
            not.showNotifications(error, "La quantité du produit est encore nulle...", ECHEC_NOT, time, bool);
            return;
        }
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        this.getCurrentStage().close();
    }

    public TableView<Product> getProductTableView() {
        return productTableView;
    }
 
    public void setProductTableView(TableView<Product> productTableView) {
        this.productTableView = productTableView;
    }
}
