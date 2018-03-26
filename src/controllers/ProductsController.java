/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static controllers.Controller.targetFolder;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Category;
import models.GestionStock;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class ProductsController extends Controller implements Initializable {
    
    @FXML
    private JFXButton productbtn;
    @FXML
    private JFXButton stockbtn;
    @FXML
    private JFXTextField code;
    @FXML
    private JFXButton addbtn;
    @FXML
    private JFXButton editbtn;
    
    private Stage currentStage;
    
    private Scene currentScene;
    
    @FXML
    private JFXButton viewtbn;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> codeColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> quantiteColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private AnchorPane anchor;
    
    @FXML
    private Pagination pagination;
    
    public static ObservableList list = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton signoutbtn;
    @FXML
    private JFXButton cat;
    @FXML
    private TableColumn<Product, String> codeFournisseurColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private JFXTextField codef;
    @FXML
    private JFXButton st;
    
    @FXML
    private ImageView afficheUnePhoto;
    @FXML
    private JFXTextField qte;
    
    FilteredList filter = new FilteredList(list, p->true);
    
    private ArrayList<Product> printProducts;
    
    private String quantity, vendor, codePr, categ;
    @FXML
    private Rectangle rec;
    @FXML
    private AnchorPane anchorRectangle;
    @FXML
    private Label nameUserMagasin;
    @FXML
    private JFXComboBox<Category> catComboBox;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    
   
    public void init(){
        rec.widthProperty().bind(getCurrentScene().widthProperty());
        rec.heightProperty().bind(getCurrentScene().heightProperty());

    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Category catVide = new Category();
        catVide.setNomCategory(" ");
        
        if(Shop.categorys.size() != 0 && !Shop.categorys.get(0).getNomCategory().trim().equals(""))
        {
            Shop.categorys.add(0, catVide);
        }
        
        nameUserMagasin.setText(Shop.nameUser);
        
        init(list, pagination, productTable, Shop.products);
        
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("codeString"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("nomcategorie"));
        codeFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("codefournisseur"));
        
        codeColumn.prefWidthProperty().bind(productTable.widthProperty().divide(15.0));
        nameColumn.prefWidthProperty().bind(productTable.widthProperty().divide(25.0).multiply(6.0));
        descriptionColumn.prefWidthProperty().bind(productTable.widthProperty().divide(25.0).multiply(4.0));
        quantiteColumn.prefWidthProperty().bind(productTable.widthProperty().divide(10.0));
        priceColumn.prefWidthProperty().bind(productTable.widthProperty().divide(15.0).multiply(2.0));
        categoryColumn.prefWidthProperty().bind(productTable.widthProperty().divide(5.0));
        codeFournisseurColumn.prefWidthProperty().bind(productTable.widthProperty().divide(10.0));
        
        qte.textProperty().addListener((observable,oldValue,newValue) -> {
            
            filter.setPredicate((Predicate<? super Product>) (Product pr) -> {
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                if(String.valueOf(pr.getQuantite()).contains(newValue)){
                    return true;
                }
                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sort);
        });
        
        code.textProperty().addListener((observable,oldValue,newValue) -> {
            
            filter.setPredicate((Predicate<? super Product>) (Product pr) -> {
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(pr.getCodeString().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sort);
        });
        
        codef.textProperty().addListener((observable,oldValue,newValue) -> {
            
            filter.setPredicate((Predicate<? super Product>) (Product pr) -> {
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(pr.getCodefournisseur().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sort);
        });
        
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
        catComboBox.setCellFactory(factory);
        catComboBox.setButtonCell(factory.call(null));
        catComboBox.getItems().setAll(Shop.categorys);
        
        catComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>(){
            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                
                filter.setPredicate((Predicate<? super Product>) (Product pr) -> {
                
                    if(newValue.getNomCategory().trim().isEmpty() || newValue == null){
                        return true;
                    }
                    
                    if(pr.getNomcategorie().equals(newValue.getNomCategory())){
                        return true;
                    }
                    return false;
                });
                SortedList sort = new SortedList(filter);
                sort.comparatorProperty().bind(productTable.comparatorProperty());
                productTable.setItems(sort);
            }
            
        });
        
        
        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        productTable.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
                            try {
                                if(newVal == null){
                                    afficheUnePhoto.setImage(null);
                                }
                                else{
                                    if(newVal.getPictures().size() > 0 ){
                                        String pathImageInServer = targetFolder+newVal.getCodeProduit()+"/"+newVal.getPictures().get(0);
                                        URL urlImage = new URL(pathImageInServer);
                                        InputStream fileStream = urlImage.openStream();
                                        Image imag = new Image(fileStream);
                                        double l = imag.getWidth(), L = imag.getHeight(), l_prim = 185, L_prim = (l_prim/l)*L;

                                        afficheUnePhoto.setFitWidth(l_prim);
                                        afficheUnePhoto.setFitHeight(L_prim);
                                        afficheUnePhoto.setImage(imag);
                                    }
                                    else{
                                        afficheUnePhoto.setImage(null);
                                    }
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                });
             }
        }).start();
        
        //productTable.setItems(list);
    }
    

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void add(ActionEvent event) throws SQLException {
        Shop.categorys = Category.listCategorie();
        Shop.showNewProduct(productTable, pagination);
    }

    @FXML
    private void edit(ActionEvent event) throws SQLException {
        ObservableList<Product> productsAll, productSelected;
        productsAll = productTable.getItems();
        productSelected = productTable.getSelectionModel().getSelectedItems();
        Product p = productSelected.get(0);
        
        if(p == null){
            not.showNotifications(error, "Sélectionnez le produit avant de l\'éditer...", ECHEC_NOT, time, bool);
            return;
        }
        p.listPictures();
        Shop.product = p;
        Shop.codeProduitRecent = p.getCodeProduit();
        Shop.categorys = Category.listCategorie();
        
        Shop.showEditProduct(productTable, pagination);
    }

    @FXML
    private void view(ActionEvent event) throws SQLException {
        ObservableList<Product> productSelected;
        productSelected = productTable.getSelectionModel().getSelectedItems();
        Product p = productSelected.get(0);
        if(p == null){
            not.showNotifications(error, "Sélectionnez le produit avant de le voir...", ECHEC_NOT, time, bool);
            return;
        }
        p.listPictures();
        Shop.product = p;
        Shop.codeProduitRecent = p.getCodeProduit();
        Shop.showView_product();
    }

    @FXML
    private void products(ActionEvent event) {
        return;
        //Shop.showProducts();
    }

    @FXML
    private void stocks(ActionEvent event) throws SQLException {
        Shop.stocks = GestionStock.listStocks();
        for(int i = 0; i < Shop.stocks.size(); i++){
            Shop.stocks.get(i).setIdCodeString(Shop.stocks.get(i).formatCode());
            //Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
            if(Shop.stocks.get(i).isTypegest()) Shop.stocks.get(i).setTypeString("Ajout");
            else Shop.stocks.get(i).setTypeString("Retrait");
        }
        Shop.showStock();
    }

    
    
    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
    }

    @FXML
    private void categories(ActionEvent event) throws SQLException {
        Shop.categorys = Category.listCategorie();
        Shop.showCategory(productTable);
    }

    @FXML
    private void addStock(ActionEvent event) {
        ObservableList<Product> productSelected;
        productSelected = productTable.getSelectionModel().getSelectedItems();
        Product p = productSelected.get(0);
        if(p == null){
            not.showNotifications(error, "Sélectionnez le produit avec d\'ajouter un stock...", ECHEC_NOT, time, bool);
            return;
        }
        Shop.codeInt = p.getCodeProduit();
        Shop.codeString = p.getCodeString();
        Shop.showOperation();
    }

    @FXML
    private void print(ActionEvent event){
        
        printProducts = new ArrayList<>();
        
        for(int i = 0; i < productTable.getItems().size(); i++){
            printProducts.add(productTable.getItems().get(i));
        }
        
        if(printProducts.size() > 0){
            printProduits print = new printProduits();
            print.setPrint(printProducts);
            
            quantity = qte.getText();
            vendor = codef.getText();
            codePr = code.getText();
            //categ = catt.getText();
            if(catComboBox != null && catComboBox.getValue()!= null
                    && !catComboBox.getValue().getNomCategory().trim().equals("")){
                categ = catComboBox.getValue().getNomCategory();
            }
            
            if((codePr != null && !codePr.equals(""))){
                print.setStr("Produit de code "+codePr);
            }
            
            else if(categ != null && !categ.equals("")){
                print.setStr("Liste des produits avec pour catégorie "+categ);
            }
            
            else if((quantity != null && !quantity.equals("")) && (vendor == null || vendor.equals(""))){
                print.setStr("Liste des produits de quantité égale à "+quantity);
            }
            
            else if((quantity == null || quantity.equals("")) && (vendor != null && !vendor.equals(""))){
                print.setStr("Liste des produits livré par le fournisseur de code "+vendor);
            }
            
            else if((quantity != null && !quantity.equals("")) && (vendor != null && !vendor.equals(""))){
                print.setStr("Liste des produits de quantité égale à "+quantity+" et livré par le fournisseur de code "+vendor);
            }
            
            else{
                if(printProducts.size() == Shop.products.size()){
                    print.setStr("Liste de tous les produits");
                }
                else{
                    print.setStr("Liste de quelques produits");
                }
            }
            
            quantity = null;
            vendor = null;
            codePr = null;
            categ = null;

            String pathPrint = pathPro+print.generateName();
            
            print.setRESULT(pathPrint);
            print.print();
            File file = new File(pathPrint);
            Desktop dt = Desktop.getDesktop();
            try {
                dt.open(file);
            } catch (IOException ex) {
                Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else{
            not.showNotifications(error, "La liste des produits à imprimer est vide...", ECHEC_NOT, time, bool);
        }
    } 

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}
