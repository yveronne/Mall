/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Category;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class New_productController extends Controller implements Initializable {
    
    private int itemPerPage = 2;
    
    private Stage currentStage;
    private ArrayList<String> photos = new ArrayList<>();
    private ArrayList<String> photosTargetFolder = new ArrayList<>();
    
    private TableView<Product> productTable;
    
    private Pagination pagination;

    @FXML
    private JFXTextField namefield;
    @FXML
    private JFXTextField pricefield;
    @FXML
    private JFXTextField vendorfield;
    @FXML
    private TextArea descriptionfield;
    @FXML
    private AnchorPane photofield;
    @FXML
    private JFXButton parcourirbtn;
    @FXML
    private JFXButton okbtn;
    @FXML
    private JFXComboBox<Category> categoryCbox;
    @FXML
    private Label succesLabel;
    @FXML
    private Label labelParcourir;
    
    @FXML
    private JFXListView<HBox> listphotoEdit;
    
    private ArrayList<File> sourceFiles = new ArrayList<>();
    @FXML
    private JFXButton cancelbtn;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Callback<ListView<Category>, ListCell<Category>> factory = new Callback<ListView<Category>, ListCell<Category>>() {

            public ListCell<Category> call(ListView<Category> lv) {
                return new ListCell<Category>() 
                {
                    @Override
                    protected void updateItem(Category item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.getNomCategory());
                    }
                };  
            }
        };
        categoryCbox.setCellFactory(factory);
        categoryCbox.setButtonCell(factory.call(null));
         
        categoryCbox.getItems().setAll(Shop.categorys);
      
    }    

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void ok(ActionEvent event) throws SQLException {
        
        if(namefield.getText().trim().equals("") || pricefield.getText().trim().equals("") || vendorfield.getText().trim().equals("")
                || ( categoryCbox.getValue() == null || categoryCbox.getValue().getNomCategory().equals("") ) ||
                descriptionfield.getText().trim().equals("") || photos.size() <= 0){
            not.showNotifications(error, "S\'il vous plait, entrer tous les champs...", ECHEC_NOT, time, bool);
            return;
        }
        else{
            String name = namefield.getText(), price = pricefield.getText(), vendor = vendorfield.getText(),
                    nomCategory = categoryCbox.getValue().getNomCategory(), desc = descriptionfield.getText();
            int idCategory = categoryCbox.getValue().getIdCategory();
            
            if(name.length() > 100){
                not.showNotifications(error, "Le nom d\'un produit ne doit pas dépasser 100 caractères...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(vendor.length() > 12){
                not.showNotifications(error, "Le code du fournisseur ne doit pas dépasser 12 caractères...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(nomCategory.length() > 50){
                not.showNotifications(error, "Le code d\'une catégorie ne doit pas dépasser 50 caractères...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(desc.length() > 100){
                not.showNotifications(error, "La description d\'un produit ne doit pas dépasser 100 caractères...", ECHEC_NOT, time, bool);
                return;
            }
            
            double prix;
            
            try
            {
                prix =  Double.parseDouble(price);
            }
            catch(Exception e)
            {
                not.showNotifications(error, "Entrez une somme au champ nommé prix...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(prix <= 0){
                not.showNotifications(error, "Entrez une valeur positive et différente de zéro...", ECHEC_NOT, time, bool);
                return;
            }
            
            Product p = new Product();
            p.getGenerateCode();
            
            for(File fSource:sourceFiles) {
                try {
                    String name_extension = fSource.getName();
                    File fileTemp = new File(fSource.getAbsolutePath().replace('\\', '/'));
                    sendInformation(fileTemp.getAbsolutePath().replace('\\', '/'), p.getCodeProduit()+"");
                    photosTargetFolder.add(name_extension);
                } catch (IOException ex) {
                    Logger.getLogger(New_productController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            p.setIdcategorie(idCategory);
            p.setNomcategorie(nomCategory);
            p.setPrix(prix);
            p.setDescriptions(desc);
            p.setNom(name);
            p.setPictures(photosTargetFolder);
            p.setCodefournisseur(vendor);
            
            p.createProduct();
            p.setCodeString(p.formatCode());
            
            Shop.products = Product.listProducts();
            int taille = Shop.products.size();
            
            for(int i = 0; i < taille; i++){
                Shop.products.get(i).setCodeString(Shop.products.get(i).formatCode());
                Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
            }
            
            init(ProductsController.list, this.pagination, this.productTable, Shop.products);
            this.getCurrentStage().close();
            not.showNotifications(confirmation, "Produit enregistré au code  "+p.getCodeString(), SUCCESS_NOT, time+err, bool);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        this.getCurrentStage().close();
    }
    
    @FXML
    private void parcourir(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterjpg, extFilterpng);
        
        List<File> listfile = fileChooser.showOpenMultipleDialog(getCurrentStage());
        
        if(listfile != null){
            for(File file : listfile){
                photos.add(file.getAbsolutePath().replace('\\', '/'));
                sourceFiles.add(file);
            }
            
            not.showNotifications(confirmation, "Images enregistrées avec succès...", SUCCESS_NOT, time, bool);
        }
        
        listphotoEdit.getItems().clear();
        
        if(photos.size() > 0){
            for(int i = 0; i < photos.size(); i++){
                try {
                    HBox hbox = new HBox(10);
                    Image imag = new Image(new FileInputStream(photos.get(i)));
                    ImageView im = new ImageView(imag);
                    double l = imag.getWidth(), L = imag.getHeight(), l_prim = 120, L_prim = (l_prim/l)*L;

                    im.setFitWidth(l_prim);
                    im.setFitHeight(L_prim);

                    JFXButton btn = new JFXButton("Delete");
                    btn.setStyle("-fx-background-color:  #46c0f3");
                    btn.setTextFill(WHITE);
                    btn.setVisible(false);
                    hbox.getChildren().addAll(im, btn);

                    listphotoEdit.getItems().add(hbox);
                    String temp = photos.get(i);
                    File filetemp=sourceFiles.get(i);

                    listphotoEdit.getSelectionModel().selectedItemProperty().addListener( (Observable, oldVal, newVal) -> {
                        btn.setVisible(true);
                        btn.setOnAction(e -> {
                            photos.remove(temp);
                            sourceFiles.remove(filetemp);
                            listphotoEdit.getItems().remove(newVal);
                        });
                    });
                } catch (FileNotFoundException ex) {
                    return;
                }
           }
        }
    }

    @FXML
    private void category(ActionEvent event) {
      
    }

    private void add(ActionEvent event) {
        
        //Shop.showCategory();
    }

    public TableView<Product> getProductTable() {
        return productTable;
    }

    public void setProductTable(TableView<Product> productTable) {
        this.productTable = productTable;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
