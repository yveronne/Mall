/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopn.models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author MBOGNI RUVIC
 */
public class GlobalNotifications {
    
    public static final String SUCCESS_NOT="SUCCESS";
    public static final String ECHEC_NOT="ECHEC";
    private ImageView success_img = null;
    private ImageView fail_img = null;
    
    
    public GlobalNotifications() {
        try {
            success_img = new ImageView(new Image(new FileInputStream("src/statics/correct.png")));
            fail_img =new ImageView(new Image(new FileInputStream("src/statics/incorrect.png")));
            
            success_img.setFitWidth(50);
            success_img.setFitHeight(50);
            fail_img.setFitWidth(50);
            fail_img.setFitHeight(50);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GlobalNotifications.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showNotifications(String title, String msg, String type_not, int time, boolean dark_style){
        
        ImageView img=((type_not.equals(SUCCESS_NOT))?success_img:fail_img);
        
        Notifications notBuilder=Notifications.create()
                .title(title)
                .text(msg)
                .graphic(img)
                .hideAfter(Duration.seconds(time))
                .position(Pos.TOP_RIGHT);
        if(dark_style) notBuilder.darkStyle();
        
        notBuilder.show();
    }
    
    public void showDialog(StackPane pane,String title, String msg, BooleanProperty prop){
        
        Text heading=new Text(title);
        heading.setId("header");
        
        Text body=new Text(msg);
        body.setId("body");
        
        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(heading);
        content.setBody(body);

        JFXDialog dialog=new JFXDialog(pane, content, JFXDialog.DialogTransition.CENTER, false);
        
        JFXButton okBtn=new JFXButton("Valider");
        JFXButton emptyBtn=new JFXButton();
        JFXButton cancelBtn=new JFXButton("Annuler");
        
        cancelBtn.setId("btn");
        okBtn.setId("btn");
        cancelBtn.setButtonType(JFXButton.ButtonType.RAISED);
        okBtn.setButtonType(JFXButton.ButtonType.RAISED);
        emptyBtn.setDisable(true);
        
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               prop.setValue(Boolean.TRUE);
               dialog.close();
            }
        });
        
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        
        content.setActions(cancelBtn, emptyBtn,okBtn);
        
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>(){
            @Override
            public void handle(JFXDialogEvent event) {
                pane.setVisible(false);
            }
            
        });
        
        dialog.show();
        
        
    }
    
}
