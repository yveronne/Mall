/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class View_productController extends Controller implements Initializable {

    
    
    private Stage currentStage;

    private TableView<Product> productTable;

    @FXML
    private JFXListView<Label> photoslist;
    @FXML
    private JFXButton okbtn;
    @FXML
    private JFXTextField namefield;
    @FXML
    private JFXTextField pricefield;
    @FXML
    private JFXTextField vendorfield;
    @FXML
    private TextArea descriptionfield;
    @FXML
    private JFXTextField categoriefield;
    @FXML
    private JFXTextField qtefield;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        namefield.setEditable(false);
        pricefield.setEditable(false);
        vendorfield.setEditable(false);
        descriptionfield.setEditable(false);
        categoriefield.setEditable(false);
        qtefield.setEditable(false);

        namefield.setText(Shop.product.getNom());
        pricefield.setText(Shop.product.getPrix()+"");
        vendorfield.setText(Shop.product.getCodefournisseur());
        descriptionfield.setText(Shop.product.getDescriptions());
        categoriefield.setText(Shop.product.getNomcategorie());
        qtefield.setText(Shop.product.getQuantite()+"");
        
        for(int i = 0; i < Shop.product.getPictures().size(); i++){
            
            try {
                Label lbl = new Label("");
                String pathImageInServer = targetFolder+Shop.product.getCodeProduit()+"/"+Shop.product.getPictures().get(i);
                URL urlImage = new URL(pathImageInServer);
                InputStream fileStream = urlImage.openStream();
                Image imag = new Image(fileStream);
                ImageView im = new ImageView(imag);
                double l = imag.getWidth(), L = imag.getHeight(), l_prim = 210, L_prim = (l_prim/l)*L;
                im.setFitWidth(l_prim);
                im.setFitHeight(L_prim);
                lbl.setGraphic(im);
                photoslist.getItems().add(lbl);
            } catch (MalformedURLException ex) {
                Logger.getLogger(View_productController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(View_productController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void ok(ActionEvent event) {
        Shop.product = new Product();
        this.getCurrentStage().close();
    }

    public TableView<Product> getProductTable() {
        return productTable;
    }

    public void setProductTable(TableView<Product> productTable) {
        this.productTable = productTable;
    }
    
}
