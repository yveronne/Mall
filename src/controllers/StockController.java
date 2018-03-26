/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.GestionStock;
import models.Product;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class StockController extends Controller implements Initializable {

    private Stage currentStage;
    private Scene currentScene;
    private TableView<Product> producTableView;
    
    
    @FXML
    private JFXButton productbtn;
    @FXML
    private JFXButton stockbtn;
    @FXML
    private JFXTextField code;
    @FXML
    private Label succesLabel;
    @FXML
    private TableColumn<GestionStock, String> codeStColumn;
    @FXML
    private TableColumn<GestionStock, Integer> quantiteColumn;
    @FXML
    private TableColumn<GestionStock, Date> dateColumn;
    @FXML
    private TableColumn<GestionStock, String> operationColumn;
    @FXML
    private TableView<GestionStock> stockTable;
    
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private JFXButton signoutbtn;
    @FXML
    private TableColumn<GestionStock, String> namePrColumn;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pagination pagination;
    @FXML
    private TableColumn<GestionStock, String> gestionnaireColumn;
    @FXML
    private JFXTextField annee;
    @FXML
    private JFXTextField mois;
    
    private String anneeStock, moisStock, codePrStock;
    
    private ArrayList<GestionStock> printStock;
    @FXML
    private JFXButton printbtn;
    @FXML
    private Rectangle rec;
    @FXML
    private Label nameUser;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        nameUser.setText(Shop.nameUser);
        
        init(list, pagination, stockTable, Shop.stocks);
        
        codeStColumn.setCellValueFactory(new PropertyValueFactory<>("idCodeString"));
        namePrColumn.setCellValueFactory(new PropertyValueFactory<>("nomproduit"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datestock"));
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        gestionnaireColumn.setCellValueFactory(new PropertyValueFactory<>("nomGestString"));
        
        codeStColumn.prefWidthProperty().bind(stockTable.widthProperty().divide(5.0).divide(2));
        namePrColumn.prefWidthProperty().bind(stockTable.widthProperty().divide(5.0).
                add(stockTable.widthProperty().divide(5.0).divide(2.0)));
        quantiteColumn.prefWidthProperty().bind(stockTable.widthProperty().divide(5.0).divide(2));
        dateColumn.prefWidthProperty().bind(stockTable.widthProperty().divide(5.0));
        operationColumn.prefWidthProperty().bind(stockTable.widthProperty().divide(5.0).divide(2.0));
        gestionnaireColumn.prefWidthProperty().bind(stockTable.widthProperty().divide(5.0));
        
        code.textProperty().addListener((observable,oldValue,newValue) -> {
            
            filter.setPredicate((Predicate<? super GestionStock>) (GestionStock ges) -> {
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                if(ges.getIdCodeString().contains(newValue)){
                    return true;
                }
                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(stockTable.comparatorProperty());
            stockTable.setItems(sort);
        });
        
        annee.textProperty().addListener((observable,oldValue,newValue) -> {
            
            filter.setPredicate((Predicate<? super GestionStock>) (GestionStock ges) -> {
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String[] tab = ges.getDatestock().split("-");
                if(tab[0].trim().contains(newValue)){
                    return true;
                }
                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(stockTable.comparatorProperty());
            stockTable.setItems(sort);
        });
        
        mois.textProperty().addListener((observable,oldValue,newValue) -> {
            
            filter.setPredicate((Predicate<? super GestionStock>) (GestionStock ges) -> {
                
                if(newValue.isEmpty() || newValue == null){
                    return true;
                }
                String[] tab = ges.getDatestock().split("-");
                if(tab[1].trim().contains(newValue)){
                    return true;
                }
                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(stockTable.comparatorProperty());
            stockTable.setItems(sort);
        });
    }   

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void products(ActionEvent event) throws SQLException {
        Shop.products = Product.listProducts();
        for(int i = 0; i < Shop.products.size(); i++){
            Shop.products.get(i).setCodeString(Shop.products.get(i).formatCode());
            Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
        }
        Shop.showProducts();
    }

    @FXML
    private void stocks(ActionEvent event) throws SQLException {
        
        return;
        /*Shop.stocks = GestionStock.listStocks();
        for(int i = 0; i < Shop.stocks.size(); i++){
            Shop.stocks.get(i).setIdCodeString(Shop.stocks.get(i).formatCode());
            if(Shop.stocks.get(i).isTypegest()) Shop.stocks.get(i).setTypeString("Adding");
            else Shop.stocks.get(i).setTypeString("Withdrawal");
        }
        Shop.showStock();*/
    }

 
    FilteredList filter = new FilteredList(list, e -> true);


    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
    }

    public TableView<Product> getProducTableView() {
        return producTableView;
    }

    public void setProducTableView(TableView<Product> producTableView) {
        this.producTableView = producTableView;
    }

    @FXML
    private void print(ActionEvent event) {
        
        printStock = new ArrayList<>();
        
        for(int i = 0; i < stockTable.getItems().size(); i++){
            printStock.add(stockTable.getItems().get(i));
        }
        
        if(printStock.size() > 0){
            printStocks print = new printStocks();
            print.setPrint(printStock);
            
            anneeStock = annee.getText();
            moisStock = mois.getText();
            codePrStock = code.getText();
            
            if((codePrStock != null && !codePrStock.equals("")) && (anneeStock == null || anneeStock.equals("")) && (moisStock == null || moisStock.equals(""))){
                print.setStr("Stock du produit de code "+codePrStock);
            }
            
            else if((codePrStock != null && !codePrStock.equals("")) && (anneeStock != null && !anneeStock.equals("")) && (moisStock == null || moisStock.equals(""))){
                print.setStr("Stock du produit de code "+codePrStock+" en "+anneeStock);
            }
            
            else if((codePrStock != null && !codePrStock.equals("")) && (anneeStock == null || anneeStock.equals("")) && (moisStock != null && !moisStock.equals(""))){
                String m = formatMois(moisStock);
                print.setStr("Stock du produit de code "+codePrStock+" au mois de "+m);
            }
            
            else if((codePrStock != null && !codePrStock.equals("")) && (anneeStock != null && !anneeStock.equals("")) && (moisStock != null && !moisStock.equals(""))){
                String m = formatMois(moisStock);
                print.setStr("Stock du produit de code "+codePrStock+" en "+m+" "+anneeStock);
            }
            
            else if((anneeStock != null && !anneeStock.equals("")) && (moisStock == null || moisStock.equals(""))){
                print.setStr("Stock des produits de l\'année "+anneeStock);
            }
            
            else if((anneeStock == null || anneeStock.equals("")) && (moisStock != null && !moisStock.equals(""))){
                String m = formatMois(moisStock);
                print.setStr("Stock des produits du mois de "+m);
            }
            
            else if((anneeStock != null && !anneeStock.equals("")) && (moisStock != null && !moisStock.equals(""))){
                String m = formatMois(moisStock);
                print.setStr("Stock des produits de "+ m +" "+anneeStock);
            }
            
            else{
                if(printStock.size() == Shop.stocks.size()){
                    print.setStr("Stock de tous les produits");
                }
                else{
                    print.setStr("Stock de quelques produits");
                }
            }
            
            
            String pathPrint = pathSto+print.generateName();
            
            anneeStock = null;
            moisStock = null;
            codePrStock = null;
            
            print.setRESULT(pathPrint);
            print.print();
            File file = new File(pathPrint);
            Desktop dt = Desktop.getDesktop();
            try {
                dt.open(file);
            } catch (IOException ex) {
                Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            not.showNotifications(error, "La liste des produits à imprimer est vide...", ECHEC_NOT, time, bool);
        }
    }
    
    private String formatMois(String moisStock){
        String m = "";
        if(Integer.parseInt(moisStock) == 1){
            m = "Janvier";
        }
        else if(Integer.parseInt(moisStock) == 2){
            m = "Fevrier";
        }
        else if(Integer.parseInt(moisStock) == 3){
            m = "Mars";
        }
        else if(Integer.parseInt(moisStock) == 4){
            m = "Avril";
        }
        else if(Integer.parseInt(moisStock) == 5){
            m = "Mai";
        }
        else if(Integer.parseInt(moisStock) == 6){
            m = "Juin";
        }
        else if(Integer.parseInt(moisStock) == 7){
            m = "Juillet";
        }
        else if(Integer.parseInt(moisStock) == 8){
            m = "Août";
        }
        else if(Integer.parseInt(moisStock) == 9){
            m = "Septembre";
        }
        else if(Integer.parseInt(moisStock) == 10){
            m = "Octobre";
        }
        else if(Integer.parseInt(moisStock) == 11){
            m = "Novembre";
        }
        else{
            m = "Décembre";
        }
        return  m;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
    
    public void init(){
        rec.widthProperty().bind(getCurrentScene().widthProperty());
        rec.heightProperty().bind(getCurrentScene().heightProperty().subtract(getCurrentScene().heightProperty().divide(20)));
    }
}
