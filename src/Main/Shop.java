/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.BillingController;
import controllers.CashierController;
import controllers.CategoryController;
import controllers.Edit_employeeController;
import controllers.Edit_productController;
import controllers.EmployeeController;
import controllers.HistoryBillingForCashierController;
import controllers.New_productController;
import controllers.OperationController;
import controllers.ProductsController;
import controllers.RegisterController;
import controllers.RootController;
import controllers.StockController;
import controllers.View_productController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Category;
import models.GestionStock;
import models.Product;
import models.User;
import views.Res;

/**
 *
 * @author Alain
 */
public class Shop extends Application {
    
    private static Stage stage = new Stage();
    
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Product> products = new ArrayList<Product>();
    public static ArrayList<Category> categorys = new ArrayList<Category>();
    public static Product product = new Product();
    public static int codeProduitRecent = -1;
    public static ArrayList<GestionStock> stocks = new ArrayList<>();
    public static GestionStock stock = new GestionStock();
    public static int idGestionnaire = -1;
    public static int codeInt = -1;
    public static String codeString = "";
    public static String nameUser = "";
    
    private Scene scene;
    
    private static RegisterController rg;
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage=primaryStage;
        
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("register.fxml"));
            AnchorPane pane = (AnchorPane)loader.load();
            RegisterController controller = loader.getController();
            Scene scene =new Scene(pane);
            
            controller.setCurrentScene(scene);
            controller.init();

            stage.setScene(scene);
            
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            
            stage.setTitle("Boutique eShop");
            stage.centerOnScreen();
            stage.show();
            
        } catch (Exception e) {
            return;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void showEmployee()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("employee.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            EmployeeController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            controller.setCurrentScene(scene);
            controller.init();
            
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setTitle("Employé(e)s");
            //stage.setMaximized(true);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showNewProduct(TableView<Product> productTable, Pagination pagination)
    {
        Stage stage = new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("new_product.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            New_productController controller=loader.getController();
            controller.setProductTable(productTable);
            controller.setPagination(pagination);
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Nouveau produit");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showEditProduct(TableView<Product> produitTableView, Pagination pagination)
    {
        Stage stage = new Stage();
                
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("edit_product.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            Edit_productController controller=loader.getController();
            controller.setCurrentStage(stage);
            controller.setProductTable(produitTableView);
            controller.setPagination(pagination);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modification d\'un produit");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showOperation()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("operation.fxml"));
            AnchorPane pane=(AnchorPane)loader.load();

            OperationController controller=loader.getController();
            controller.setCurrentStage(stage);
            //controller.setProductTableView();

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Ajouter le stock");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showCashier()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("cashier.fxml"));
            BorderPane pane=(BorderPane)loader.load();
            
            CashierController controller=loader.getController();
            
            Scene scene=new Scene(pane);
            controller.setCurrentScene(scene);
            controller.init();
            stage.setScene(scene);
            
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            
            stage.setTitle("Caissière");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showHistoryBillingForCashier()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("HistoryBillingForCashier.fxml"));
            BorderPane pane=(BorderPane)loader.load();
            
            HistoryBillingForCashierController controller=loader.getController();
            
            Scene scene=new Scene(pane);
            controller.setCurrentScene(scene);
            controller.init();
            stage.setScene(scene);
            
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            
            stage.setTitle("Caissière");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showBilling()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("Billing.fxml"));
            BorderPane pane=(BorderPane)loader.load();
            
            BillingController controller=loader.getController();
            
            Scene scene=new Scene(pane);
            controller.setCurrentScene(scene);
            controller.init();
            stage.setScene(scene);
            
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            
            stage.setTitle("Les factures");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    
    public static void showProducts()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("products.fxml"));
            BorderPane pane=(BorderPane)loader.load();
            
            ProductsController controller=loader.getController();
            controller.setCurrentStage(stage);
            
            Scene scene=new Scene(pane);
            controller.setCurrentScene(scene);
            controller.init();
            stage.setScene(scene);
            
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            
            stage.setTitle("Mes produits");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showRegister()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("register.fxml"));
            AnchorPane pane=(AnchorPane)loader.load();

            RegisterController controller=loader.getController();
            Scene scene=new Scene(pane);
            
            controller.setCurrentScene(scene);
            controller.init();
            
            stage.setScene(scene);
            
            stage.setTitle("Accueil");
            stage.centerOnScreen();
            stage.show();
            
        } catch (Exception e) {
            return;
        }
    }

    public static void showRoot()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("root.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            RootController controller=loader.getController();

            Scene scene=new Scene(pane);
            
            controller.setCurrentScene(scene);
            controller.init();
            
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.setTitle("Administration");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showEditEmployee(TableView<User> employeeTable, User user, Pagination pagination)
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("edit_employee.fxml"));
            AnchorPane pane=(AnchorPane)loader.load();

            Edit_employeeController controller=loader.getController();
            controller.setCurrentStage(stage);
            controller.setUserCurrent(user);
            controller.setUserTable(employeeTable);
            controller.setPagination(pagination);
            controller.init();

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Modification d\'un gestionnaire");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    
    public static void showStock()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("stock.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            StockController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            controller.setCurrentScene(scene);
            controller.init();
            stage.setScene(scene);
            //stage.setResizable(true);
            
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            
            stage.setTitle("Stock");
            //stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showView_product()
    {
        Stage stage = new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("view_product.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            View_productController controller=loader.getController();
            controller.setCurrentStage(stage);
            //controller.setProductTable(productTableView);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Vue d\'un Produit");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }
    
    public static void showCategory(TableView<Product> producTableView)
    {
        Stage stage = new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("category.fxml"));
            AnchorPane pane=(AnchorPane)loader.load();

            CategoryController controller=loader.getController();
            controller.setCurrentStage(stage);
            controller.setProductTable(producTableView);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Categorie d\'un produit");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            return;
        }
    }


    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
}
