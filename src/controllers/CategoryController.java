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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Category;
import models.Product;


/**
 * FXML Controller class
 *
 * @author Alain
 */
public class CategoryController extends Controller implements Initializable {
    
    private Stage currentStage;
    private TableView<Product> productTable;
    
    
    @FXML
    private JFXTextField categoryfield;
    @FXML
    private JFXButton okbtn;
    @FXML
    private JFXButton cancelbtn;
    @FXML
    private Label succesLabel;
    @FXML
    private JFXListView<Category> listcategories;
   
    boolean ok_edit = true;
    
    private int indexSelected = -1, numberItem = 0;
    
    private int indexCategorie;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        okbtn.setGraphic(null);
        
        for(int i = 0; i < Shop.categorys.size(); i++){
            Category category = Shop.categorys.get(i);
            listcategories.getItems().add(category);
        }
       
        listcategories.setCellFactory(new Callback<ListView<Category>, ListCell<Category>>(){
            @Override
            public ListCell<Category> call(ListView<Category> lv) {
                
                ListCell<Category> cell = new ListCell<Category>(){
                    @Override
                    public void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.getNomCategory());
                        }
                    }
                };
                
                SelectionModel<Category> selectionModel = listcategories.getSelectionModel();
                cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    listcategories.requestFocus();
                    if (! cell.isEmpty()) {
                        int index = cell.getIndex();
                        if(selectionModel.getSelectedIndex() == index) {
                            categoryfield.clear();
                            selectionModel.clearSelection(index);
                            ok_edit = true;
                            okbtn.setText("Ajouter");
                        } else {
                            selectionModel.select(index);
                            categoryfield.setText(cell.getItem().getNomCategory());
                            indexSelected = index;
                            indexCategorie = cell.getItem().getIdCategory();
                            ok_edit = false;
                            okbtn.setText("Editer");
                        }
                        event.consume();
                    }
                });
                return cell;
                
            } 
        });
        
    }

    public void mouseClicked(MouseEvent event, Category category)
    {
        if (event.getClickCount() == 2) {
            //textField.setDisable(false);
            categoryfield.setText(category.getNomCategory());
        }
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void ok(ActionEvent event) throws SQLException {
        
        if(ok_edit){
            if(categoryfield.getText().trim().equals("")){
                not.showNotifications(error, "Saisissez une catégorie...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(categoryfield.getText().length() > 50){
                not.showNotifications(error, "La catégorie ne doit pas avoir plus de 50 caractères...", ECHEC_NOT, time, bool);
                return;
            }

            Category category = new Category(categoryfield.getText());
            category.createCategory();

            Shop.categorys.add(0, category);

            categoryfield.clear();
            
            listcategories.getItems().add(0, category);
            not.showNotifications(confirmation, "Catégorie enregistrée avec succès...", SUCCESS_NOT, time, bool);
        }
        else{
            if(categoryfield.getText().trim().equals("")){
                not.showNotifications(error, "Saisissez une catégorie à modifier...", ECHEC_NOT, time, bool);
                return;
            }
            
            if(categoryfield.getText().length() > 50){
                not.showNotifications(error, "La catégorie ne doit pas avoir plus de 50 caractères...", ECHEC_NOT, time, bool);
                return;
            }
            
            Category category = new Category(indexCategorie, categoryfield.getText());
            
            category.updateCategory(indexCategorie);
            
            categoryfield.clear();
            
            listcategories.getItems().set(indexSelected, category);
            
            Shop.products = Product.listProducts();
            
            for(int i = 0; i < Shop.products.size(); i++){
                Shop.products.get(i).setCodeString(Shop.products.get(i).formatCode());
                Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
            }
            
            ProductsController.list.setAll(Shop.products);
            this.productTable.getItems().setAll(Shop.products);
            this.productTable.setItems(ProductsController.list);
            
            not.showNotifications(confirmation, "Catégorie modifiée...", SUCCESS_NOT, time, bool);
        }
        
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        this.getCurrentStage().close();
    }

    public TableView<Product> getProductTable() {
        return productTable;
    }

    public void setProductTable(TableView<Product> productTable) {
        this.productTable = productTable;
    }
    
}
