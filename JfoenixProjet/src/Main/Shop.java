/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import controllers.BillingController;
import controllers.New_productController;
import controllers.OperationController;
import controllers.ProductsController;
import controllers.RegisterController;
import controllers.RootController;
import controllers.StockController;
import controllers.View_productController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.Res;

/**
 *
 * @author Alain
 */
public class Shop extends Application {
    
    private Stage stage;
    private Scene scene;
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage=primaryStage;
        try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(Res.class.getResource("register.fxml"));
                AnchorPane pane=(AnchorPane)loader.load();

                RegisterController controller =loader.getController();

                Scene scene=new Scene(pane);

                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Shop");
                stage.show();

        } catch (Exception e) {
                e.printStackTrace();
        }        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void showBilling()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("billing.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            BillingController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Billing");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showEmployee()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("employee.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            BillingController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Employees");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showNewProduct()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("new_product.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            New_productController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Product");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showOperation()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("operation.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            OperationController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Operation");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showProducts()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("products.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            ProductsController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Products");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showRegister()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("register.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            RegisterController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registration");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    
    public void showRoot()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("root.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            RootController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Administration");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showStock()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("stock.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            StockController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Stocks");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public void showView_production()
    {
        Stage stage=new Stage();
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Res.class.getResource("view_production.fxml"));
            BorderPane pane=(BorderPane)loader.load();

            View_productController controller=loader.getController();
            controller.setCurrentStage(stage);

            Scene scene=new Scene(pane);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Billing");
            stage.show();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public Stage getStage() {
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
