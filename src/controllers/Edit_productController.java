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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class Edit_productController extends Controller implements Initializable {
    private Stage currentStage;
    private TableView<Product> productTable;
    

    @FXML
    private JFXTextField namefield;
    @FXML
    private JFXTextField pricefield;
    @FXML
    private JFXTextField vendorfield;
    @FXML
    private TextArea descriptionfield;
    @FXML
    private JFXComboBox<Category> categoryCbox;
    @FXML
    private Label succesLabel;
    @FXML
    private AnchorPane photofield;
    @FXML
    private JFXButton parcourirbtn;
    @FXML
    private JFXButton okbtn;
    @FXML
    private Label labelParcourir;

    
    private ArrayList<String> photos = new ArrayList<>();
    private ArrayList<File> photosFile = new ArrayList<>();
    
    @FXML
    private JFXListView<HBox> listphotoEdit;

    private ArrayList<File> sourceFiles = new ArrayList<>();
    
    private ArrayList<String> sourceDeletes = new ArrayList<>();
    
    private ArrayList<String> photosTargetFolder = new ArrayList<>();
    
    private Product pr = new Product();

    private int nbreactuelimage;
    
    private Pagination pagination;
    
 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        
        try {
            pr = Product.getProductById(Shop.codeProduitRecent);
            pr.setCodeProduit(Shop.codeProduitRecent);
        } catch (SQLException ex) {
            Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        namefield.setText(pr.getNom());
        pricefield.setText(pr.getPrix()+"");
        vendorfield.setText(pr.getCodefournisseur());
        descriptionfield.setText(pr.getDescriptions());
        Category currentCategory = new Category(pr.getIdcategorie(), pr.getNomcategorie());
        categoryCbox.setValue(currentCategory);
        
        photos = pr.getPictures();
        
        for(int i = 0; i < photos.size(); i++){
            photosFile.add(new File(targetFolder+Shop.product+"/"+photos.get(i)));
            sourceFiles.add(new File(targetFolder+Shop.product+"/"+photos.get(i)));
        }
        nbreactuelimage= photos.size();

        for(int i = 0; i < photos.size(); i++){
            try {
                HBox hbox = new HBox(10);
                
                String pathImageInServer = targetFolder+Shop.product.getCodeProduit()+"/"+photos.get(i);
                URL urlImage = new URL(pathImageInServer);
                InputStream fileStream = urlImage.openStream();
                Image imag = new Image(fileStream);
                
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
                
                File filetemp = sourceFiles.get(i);
                String temp = photos.get(i);

                listphotoEdit.getSelectionModel().selectedItemProperty().addListener( (Observable, oldVal, newVal) -> {
                    btn.setVisible(true);
                    btn.setOnAction(e -> {
                        try {
                            photos.remove(temp);
                            sourceFiles.remove(filetemp);
                            deleteOneImage(Shop.product.getCodeProduit()+"", filetemp.getName());
                            try {
                                pr.deletePicture(temp);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            nbreactuelimage--;
                            listphotoEdit.getItems().remove(newVal);
                        } catch (IOException ex) {
                            Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                });
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View_productController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
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

    @FXML
    private void category(ActionEvent event) {
    }


    @FXML
    private void parcourir(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterjpg, extFilterpng);
        
        List<File> listfile = fileChooser.showOpenMultipleDialog(getCurrentStage());
        
        int nbre= photos.size();
        
        if(listfile != null){
            for(File file : listfile){
                photos.add(file.getAbsolutePath().replace('\\', '/'));
                sourceFiles.add(file);
            }
            not.showNotifications(confirmation, "Images enregistrées avec succès...", SUCCESS_NOT, time, bool);
        }
        
        listphotoEdit.getItems().clear();
        
        String tar = targetFolder.replace("//", "/");
        
        if(photos.size() > 0){
            for(int i = 0; i < photos.size(); i++){
                try {
                    HBox hbox = new HBox(10);
                    
                    String repPath = sourceFiles.get(i).getParent().replace('\\', '/')+"/";
                    
                    Image imag;
                    if(repPath.contains(tar)){
                        String pathImageInServer = targetFolder+Shop.product.getCodeProduit()+"/"+photos.get(i);
                        URL urlImage = new URL(pathImageInServer);
                        InputStream fileStream = urlImage.openStream();
                        imag = new Image(fileStream);
                    }
                    else{
                        imag = new Image(new FileInputStream(photos.get(i)));
                    }
                    
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
                            
                            if(repPath.contains(tar)){
                                try {
                                    photos.remove(temp);
                                    sourceFiles.remove(filetemp);
                                    deleteOneImage(Shop.product.getCodeProduit()+"", filetemp.getName());
                                    try {
                                        pr.deletePicture(temp);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                    nbreactuelimage--;
                                    listphotoEdit.getItems().remove(newVal);
                                } catch (IOException ex) {
                                    Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else{
                                photos.remove(temp);
                                sourceFiles.remove(filetemp);
                                listphotoEdit.getItems().remove(newVal);
                            }
                            
                        });
                    });
                } catch (FileNotFoundException ex) {
                    return;
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Edit_productController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        }
    }

    @FXML
    private void ok(ActionEvent event) throws SQLException, IOException {

        if(namefield.getText().trim().equals("") || pricefield.getText().trim().equals("") || vendorfield.getText().trim().equals("")
                || ( categoryCbox.getValue() == null || categoryCbox.getValue().getNomCategory().equals("") ) ||
                descriptionfield.getText().trim().equals("") || photos.size() <= 0){
            not.showNotifications(error, "S\'il vous plait, entrer tous les champs...", ECHEC_NOT, time, bool);
            return;
        }
        else{
            String name = namefield.getText(), price = pricefield.getText(), vendor = vendorfield.getText(),
                    nomCategory = categoryCbox.getValue().getNomCategory(), desc = descriptionfield.getText();
            int idCategory = pr.getIdcategorie();
            
            double prix;
            
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

            //supréssion des images dans la bd
            for (String temp : pr.getPictures()) {
                pr.deletePicture(temp);
            }

            File fTarget;
            String name_extension;
            
            //on copie ses images dans le dossier tempon de eShop
            for (int i=0; i<nbreactuelimage; i++) {
                name_extension = sourceFiles.get(i).getName();
                fTarget = new File(new File(targetFolder+pr.getCodeProduit()+"/"), name_extension);
                sourceFiles.set(i,fTarget);
                photos.set(i,fTarget.getAbsolutePath());
                photosTargetFolder.add(name_extension);
            }
            
            for(int i = nbreactuelimage; i < sourceFiles.size(); i++){
                name_extension = sourceFiles.get(i).getName();
                File fileTemp = new File(sourceFiles.get(i).getAbsolutePath().replace('\\', '/'));
                sendInformation(fileTemp.getAbsolutePath().replace('\\', '/'), targetFolder+pr.getCodeProduit()+"");
                photosTargetFolder.add(name_extension);
            }

            pr.setIdcategorie(idCategory);
            pr.setNomcategorie(nomCategory);
            pr.setPrix(prix);
            pr.setDescriptions(desc);
            pr.setNom(name);
            pr.setPictures(photosTargetFolder);
            pr.setCodefournisseur(vendor);
            pr.updateProduct();
            pr.setCodeString(pr.formatCode());
            pr.setPictures(pr.listPictures());
            
            Shop.products.remove(Shop.product);
            
            Shop.products.add(0, pr);
            
            init(ProductsController.list, pagination, productTable, Shop.products);
            
            this.currentStage.close();
            not.showNotifications(confirmation, "Produit modifié au code  "+pr.getCodeString(), SUCCESS_NOT, time+err, bool);
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Shop.product = new Product();
        this.getCurrentStage().close();
    }


    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
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
