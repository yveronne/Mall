/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Main.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Category;
import models.ConfigShop;
import models.Product;
import models.User;

/**
 * FXML Controller class
 *
 * @author Alain
 */
public class RegisterController extends Controller implements Initializable {
    
    private Scene currentScene;
    private Stage currentStage;
    private boolean isAdmin = false;
    
    @FXML
    private JFXCheckBox checkUser;
    @FXML
    private JFXTextField usernamefield;
    @FXML
    private JFXPasswordField passwordfield;
    @FXML
    private JFXButton loginbtn;
    @FXML
    private Label succesLabel;
    @FXML
    private AnchorPane paneConnexion;
    @FXML
    private AnchorPane mainpane;
    @FXML
    private Rectangle rec;
    
    
    @FXML
    void connexion(ActionEvent event) throws SQLException{
        
        if(isAdmin){
            if(usernameAdmin.equals(usernamefield.getText()) && passwordAdmin.equals(passwordfield.getText())){
                Shop.showRoot();
            }
            else if(passwordfield.getText().trim().equals("")){
                passwordfield.setText("");
                not.showNotifications(error, "Le mot de passe entré est incorrect...", ECHEC_NOT, time, bool);
                return;
            }
            else if(usernamefield.getText().trim().equals("")){
                not.showNotifications(error, "Le nom d\'utilisateur entré est incorrect...", ECHEC_NOT, time, bool);
                return;
            }
            else{
                not.showNotifications(error, "Vous n\'êtes pas enregistré. Réessayez...", ECHEC_NOT, time, bool);
                return;
            }
        }
        else{
            if(usernamefield.getText().trim().equals("") || passwordfield.getText().trim().equals("")){
                usernamefield.setText("");
                passwordfield.setText("");
                not.showNotifications(error, "S\'il vous plait, complétez tous les champs...", ECHEC_NOT, time, bool);
                return;
            }
            else{
                int number = User.login(usernamefield.getText(), passwordfield.getText());
                User u;
                switch(number){
                    case 0:
                        usernamefield.setText("");
                        passwordfield.setText("");
                        not.showNotifications(error, "Vous êtes pas enregistré. Réessayez...", ECHEC_NOT, time, bool);
                        break;
                    case 1:
                        Shop.products = Product.listProducts();
                        
                        for(int i = 0; i < Shop.products.size(); i++){
                            Shop.products.get(i).setCodeString(Shop.products.get(i).formatCode());
                            Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
                        }
                        u = new User(usernamefield.getText());
                        Shop.idGestionnaire = u.getIdUser();
                        Shop.nameUser = usernamefield.getText();
                        Shop.categorys = Category.listCategorie();
                        
                        Category catVide = new Category();
                        catVide.setNomCategory(" ");
                        Shop.categorys.add(0, catVide);
                        
                        Shop.showCashier();
                        break;
                    case 2:
                        Shop.products = Product.listProducts();
                        
                        for(int i = 0; i < Shop.products.size(); i++){
                            Shop.products.get(i).setCodeString(Shop.products.get(i).formatCode());
                            Shop.products.get(i).setPictures(Shop.products.get(i).listPictures());
                        }
                        u = new User(usernamefield.getText());
                        Shop.idGestionnaire = u.getIdUser();
                        Shop.nameUser = usernamefield.getText();
                        Shop.categorys = Category.listCategorie();
                        
                        Shop.showProducts();
                        break;
                    default:
                        break;
                }
             }
        }
    }
    
    @FXML
    void check(ActionEvent event){
        
        if(checkUser.isSelected()){
            isAdmin = true;
        }
        else{
            isAdmin = false;
        }
    }
    
    private double WIDTH = 362, HEIGHT = 377;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConfigShop conf = new ConfigShop();
        
        usernameAdmin = conf.getAdmin();
        passwordAdmin = conf.getAdminpwd();
        db = conf.getBd();
        login = conf.getLogin();
        mdp = conf.getLoginpwd();
        server = conf.getServer();
        port = conf.getPort();
        targetFolder = conf.getDossierphotos();
        status = conf.getStatus();
        chaineConnexion="jdbc:mysql://"+server+":"+port+"/"+db+"?autoReconnect=true&useSSL=false";
        magasin = conf.getMagasin();
        addresse = conf.getAddresse();
        telephone = conf.getTelephone();
        lieu = conf.getLieu();
        logo = conf.getLogo();
        upload = conf.getUpload();
        pathFac = conf.getPathFac();
        pathPro = conf.getPathPro();
        pathSto = conf.getPathSto();
    }

    public void init(){
        paneConnexion.translateXProperty().bind(getCurrentScene().widthProperty().subtract(WIDTH).divide(2).subtract(WIDTH/4.0));
        paneConnexion.translateYProperty().bind(getCurrentScene().heightProperty().subtract(HEIGHT).divide(2).subtract(HEIGHT/4.0));
        rec.widthProperty().bind(getCurrentScene().widthProperty());
        rec.heightProperty().bind(getCurrentScene().heightProperty());
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

    public AnchorPane getPaneConnexion() {
        return paneConnexion;
    }

    public void setPaneConnexion(AnchorPane paneConnexion) {
        this.paneConnexion = paneConnexion;
    }

    public AnchorPane getMainpane() {
        return mainpane;
    }

    public void setMainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}