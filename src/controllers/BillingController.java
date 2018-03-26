/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Liste_Facture;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class BillingController extends Controller implements Initializable {

    @FXML
    private JFXButton signoutbtn;
    @FXML
    private Label nameUserMagasin;
    @FXML
    private AnchorPane anchorRectangle;
    @FXML
    private Rectangle rec;
    @FXML
    private JFXTextField telField;
    @FXML
    private JFXTextField remiseField;
    @FXML
    private JFXCheckBox TypeFact;
    @FXML
    private Label mgLabel;
    @FXML
    private Label mpLabel;
    @FXML
    private TableColumn<Liste_Facture, String> codeColumn;
    @FXML
    private TableColumn<Liste_Facture, Double> priceColumn;
    @FXML
    private TableColumn<Liste_Facture, Integer> quantiteColumn;
    @FXML
    private TableColumn<Liste_Facture, Double> sousTotalColumn;
    @FXML
    private JFXButton retrieveBtn;
    @FXML
    private JFXTextField codePrField;
    @FXML
    private JFXTextField quantiteField;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXListView<Label> photoListView;
    
    
    private Scene currentScene;
    private Stage currentStage;
    @FXML
    private TableView<Liste_Facture> productPrintTable;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameUserMagasin.setText(Shop.nameUser);
        quantiteField.setText("1");
        remiseField.setText("0");
        
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("codeString"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantité"));
        sousTotalColumn.setCellValueFactory(new PropertyValueFactory<>("sousTotal"));
    }    
    
    public void init(){
        rec.widthProperty().bind(getCurrentScene().widthProperty());
        rec.heightProperty().bind(getCurrentScene().heightProperty());
    }

    @FXML
    private void products(ActionEvent event) {
        Shop.showCashier();
    }

    @FXML
    private void facture(ActionEvent event) {
    }

    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
    }

    @FXML
    private void historique(ActionEvent event) {
        Shop.showHistoryBillingForCashier();
    }

    @FXML
    private void retirer(ActionEvent event) {
    }

    @FXML
    private void annuler(ActionEvent event) {
    }

    @FXML
    private void valider(ActionEvent event) {
    }

    @FXML
    private void preview(ActionEvent event) {
    }

    @FXML
    private void add(ActionEvent event) {
        String code = codePrField.getText();
        String qte = quantiteField.getText();
       
        if(code.contains("-")){
            code = code.replace("-", "");
        }
        else{
            not.showNotifications(error, "Code de produit invalide...", ECHEC_NOT, time, bool);
            return;
        }

        int codeInteger, qteInteger;
        
        try {
            codeInteger = Integer.parseInt(code);
        } catch (Exception e) {
            not.showNotifications(error, "Code de produit invalide...", ECHEC_NOT, time, bool);
            return;
        }
        
        try {
            qteInteger = Integer.parseInt(qte);
        } catch (Exception e) {
            not.showNotifications(error, "La quantité invalide...", ECHEC_NOT, time, bool);
            return;
        }
        
        if(qteInteger <= 0){
            not.showNotifications(error, "La quantité doit être supérieure ou égale  1...", ECHEC_NOT, time, bool);
            return;
        }
        
        Product pr = null;
        try {
            pr = Product.getProductById(codeInteger);
        } catch (SQLException ex) {
            Logger.getLogger(BillingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(pr == null){
            not.showNotifications(error, "Le produit n\'existe pas encore...", ECHEC_NOT, time, bool);
            return;
        }
        
        if(pr.getQuantite() < qteInteger){
            not.showNotifications(error, "La quantité disponible est de "+pr.getQuantite(), ECHEC_NOT, time, true);
            return;
        }
        
        if(!isExist(codeInteger, qteInteger)){
            Liste_Facture liste = new Liste_Facture();
            //liste.setProduit(pr);
            liste.setCodeProduit(codeInteger);
            liste.setCodeString(pr.formatCode());
            liste.setPrix(pr.getPrix());
            liste.setQuantité(liste.getQuantité()+qteInteger);
            liste.setSousTotal(pr.getPrix()*pr.getQuantite());
            
            productPrintTable.getItems().add(0, liste);
        }
        
        System.out.println(" --------------------------- PUTA");
        
    }
    
    private boolean isExist(int codeProduit, int quantite){
        for(Liste_Facture liste : productPrintTable.getItems()){
            if(liste.getCodeProduit() == codeProduit){
                liste.setQuantité(liste.getQuantité()+quantite);
                liste.setSousTotal(liste.getQuantité()*liste.getPrix());
                return true;
            }
        }
        return false;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    
}
