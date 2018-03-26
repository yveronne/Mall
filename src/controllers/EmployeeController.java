/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class EmployeeController extends Controller implements Initializable {
    
    private Stage currentStage;
    private Scene currentScene;
    
    @FXML
    private JFXButton administratorbtn;
    @FXML
    private Text deleteEmployeeText;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> telColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableView<User> employeeTable;
    @FXML
    private TableColumn<User, String> statusColumn;
    @FXML
    private Label succesLabel;
    @FXML
    private JFXButton activedbtn;
    @FXML
    private JFXButton signoutbtn;
    @FXML
    private JFXButton disabletn;
    @FXML
    private JFXButton editbtn;
    @FXML
    private Pagination pagination;
    
    public static ObservableList list = FXCollections.observableArrayList();
    @FXML
    private Rectangle rec;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        nameColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        usernameColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        telColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        emailColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        roleColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        statusColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        
        
        nameColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        usernameColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        emailColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        telColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        roleColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        statusColumn.prefWidthProperty().bind(employeeTable.widthProperty().divide(6.0));
        
        init(list, pagination, employeeTable, Shop.users);
       
        /*employeeTable.setItems(list);*/
        
        //employeeTable.getItems().setAll(Shop.users);
    }
    
    public void init(){
        rec.widthProperty().bind(getCurrentScene().widthProperty());
        rec.heightProperty().bind(getCurrentScene().heightProperty().subtract(getCurrentScene().heightProperty().divide(20)));
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private void home(ActionEvent event) {
        Shop.showRoot();
    }

    @FXML
    private void actived(ActionEvent event) throws SQLException {
        ObservableList<User> employeeAll, employeeSelected;
        employeeAll = employeeTable.getItems();
        employeeSelected = employeeTable.getSelectionModel().getSelectedItems();
        User user = employeeSelected.get(0);
        if(user == null){
            not.showNotifications(error, "Sélectionnez un gestionnaire avec de l\'activer...", ECHEC_NOT, time, bool);
            return;
        }
        
        user.activateUser();
        user.setStatus("Activé");
        not.showNotifications(confirmation, "Activation réussie...", SUCCESS_NOT, time, bool);
        employeeTable.getItems().setAll(Shop.users);
        init(list, pagination, employeeTable, Shop.users);
    }


    @FXML
    private void signout(ActionEvent event) {
        Shop.showRegister();
    }

    @FXML
    private void disable(ActionEvent event) throws SQLException {
        ObservableList<User> employeeAll, employeeSelected;
        employeeAll = employeeTable.getItems();
        employeeSelected = employeeTable.getSelectionModel().getSelectedItems();
        User user = employeeSelected.get(0);
        if(user == null){
            not.showNotifications(error, "Sélectionnez un gestionnaire avant de le désactiver...", ECHEC_NOT, time, bool);
            return;
        }
        user.disableUser();
        user.setStatus("Désactivé");
        not.showNotifications(confirmation, "Désactivation réussie...", SUCCESS_NOT, time, bool);
        employeeTable.getItems().setAll(Shop.users);
        init(list, pagination, employeeTable, Shop.users);
    }

    @FXML
    private void edit(ActionEvent event) {
        ObservableList<User> employeeAll, employeeSelected;
        employeeAll = employeeTable.getItems();
        employeeSelected = employeeTable.getSelectionModel().getSelectedItems();
        User user = employeeSelected.get(0);
        if(user == null){
            not.showNotifications(error, "Sélectionnez un gestionnaire avec de l\'editer...", ECHEC_NOT, time, bool);
            return;
        }
        Shop.showEditEmployee(employeeTable, user, pagination);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
    
}
