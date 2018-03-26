/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Facture;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class HistoryBillingForCashierController implements Initializable {

    @FXML
    private JFXButton signoutbtn;
    @FXML
    private Label nameUserMagasin;
    @FXML
    private AnchorPane anchorRectangle;
    @FXML
    private Rectangle rec;
    @FXML
    private TextField noFactField;
    @FXML
    private TextField moisField;
    @FXML
    private TextField anneeField;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Facture> factureTable;
    @FXML
    private TableColumn<Facture, Integer> noFactColumn;
    @FXML
    private TableColumn<Facture,  Double> mpColumn;
    @FXML
    private TableColumn<Facture, String> dateColumn;
    @FXML
    private TableColumn<Facture, String> caissierColumn;
    
    private Stage currentStage;
    private Scene currentScene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameUserMagasin.setText(Shop.nameUser);
        
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
        Shop.showBilling();
    }

    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
    }

    @FXML
    private void Historique(ActionEvent event) {
        Shop.showHistoryBillingForCashier();
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
    
}
