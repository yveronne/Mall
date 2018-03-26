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
import static controllers.Controller.ECHEC_NOT;
import static controllers.Controller.bool;
import static controllers.Controller.error;
import static controllers.Controller.not;
import static controllers.Controller.targetFolder;
import static controllers.Controller.time;
import static controllers.ProductsController.list;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import models.Category;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class CashierController extends Controller implements Initializable {

    @FXML
    private JFXButton signoutbtn;
    @FXML
    private Label nameUserMagasin;
    @FXML
    private AnchorPane anchorRectangle;
    @FXML
    private Rectangle rec;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pagination pagination;
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
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private JFXTextField code;
    @FXML
    private JFXButton viewtbn;
    @FXML
    private ImageView afficheUnePhoto;
    @FXML
    private JFXTextField qte;
    
    private Scene currentScene;
    public static ObservableList list = FXCollections.observableArrayList();
    FilteredList filter = new FilteredList(list, p->true);
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private JFXComboBox<Category> catComboBox;
    
    
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
        nameUserMagasin.setText(Shop.nameUser);
        
        init(list, pagination, productTable, Shop.products);
        
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("codeString"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        //codeFournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("codefournisseur"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptions"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("nomcategorie"));
        
        codeColumn.prefWidthProperty().bind(productTable.widthProperty().divide(15.0));
        nameColumn.prefWidthProperty().bind(productTable.widthProperty().divide(10.0).multiply(3.0));
        descriptionColumn.prefWidthProperty().bind(productTable.widthProperty().divide(10.0).multiply(2.0));
        quantiteColumn.prefWidthProperty().bind(productTable.widthProperty().divide(10.0));
        priceColumn.prefWidthProperty().bind(productTable.widthProperty().divide(15.0).multiply(2.0));
        categoryColumn.prefWidthProperty().bind(productTable.widthProperty().divide(5.0));
        
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
            System.out.println("fournisseur "+productTable.getItems().size());
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
            System.out.println("fournisseur "+productTable.getItems().size());
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
                System.out.println("fournisseur "+productTable.getItems().size());
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
    }    

    @FXML
    private void products(ActionEvent event) {
        return;
    }


    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
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
    
    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    @FXML
    private void facture(ActionEvent event) {
        Shop.showBilling();
    }

    @FXML
    private void historique(ActionEvent event) {
        Shop.showHistoryBillingForCashier();
    }
    
}
