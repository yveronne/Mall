/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import eshopn.models.GlobalNotifications;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import models.Product;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Alain
 */
public abstract class Controller {
    
    public static String targetFolder;
    public static String pathXml="C:/Program Files/eShop/configShop.xml";
    
    public static String driver = "com.mysql.jdbc.Driver";
    public static String chaineConnexion;
    
    public static String usernameAdmin;
    public static String passwordAdmin;
    public static String login ;
    public static String mdp ;
    public static String db ;
    public static String server;
    public static String port ;
    public static String status;
    
    public static String magasin;
    public static String addresse;
    public static String telephone;
    public static String lieu;
    public static String dossierLogo;
    public static String logo;
    public static String upload;
    
    public static String pathFac;
    public static String pathPro;
    public static String pathSto;
    
    public int itemPerPage = 20;
    
    public static GlobalNotifications not = new GlobalNotifications();
    public static String SUCCESS_NOT="SUCCESS", ECHEC_NOT="ECHEC", 
            error = "Erreur", confirmation = "Confirmation";
    public static Boolean bool = false;
    public static int time = 3, err = 3;
    
    
    public void init(ObservableList list, Pagination pagination, TableView tableView, ArrayList table){
        //list.addAll(table);
        list.setAll(table);
        
        int pages , elts =  table.size();
        
        if(elts % itemPerPage == 0){
            pages = elts / itemPerPage;
        }
        else{
            pages = (elts / itemPerPage) + 1;
        }
        
        pagination.setPageCount(pages);
        
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if (pageIndex > table.size() / itemPerPage + 1) {
                    return null;
                } else {
                    return createPage(pageIndex, tableView, table);
                }
            }
        });
    }
    
    /*public void copyFileUsingStream(File source, File dest)  {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }catch(Exception ex) {
            not.showNotifications(error, "Veuillez recréer le produit...", ECHEC_NOT, time, bool);
            return;
        }	
        finally {
            try {
                    is.close();
                    os.close();
            }catch(Exception ex) {}
        }
    }*/
    
    /*public static void deleteImage(String idProduit) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        
        HttpPost httppost = new HttpPost("http://localhost/eShop/upload.php");
        
        String idProduct = idProduit;
        
        StringBody cbDelete = new StringBody("delete", org.apache.http.entity.ContentType.TEXT_PLAIN);
        StringBody cbPath = new StringBody(idProduct+"/", org.apache.http.entity.ContentType.TEXT_PLAIN);

        
        HttpEntity mpEntity = MultipartEntityBuilder.create()
                    .addPart("delete", cbDelete)
                    .addPart("pathProduct", cbPath)
                    .build();
        
        httppost.setEntity(mpEntity);

        System.out.println("1.executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println("2."+response.getStatusLine());
        if (resEntity != null) {
          System.out.println("3."+EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
          resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
    }*/
    
    public void deleteOneImage(String idProduit, String nameImage) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        
        HttpPost httppost = new HttpPost(upload);
        
        String idProduct = idProduit;
        
        StringBody cbDelete = new StringBody("deleteOneImage", org.apache.http.entity.ContentType.TEXT_PLAIN);
        StringBody cbPath = new StringBody(idProduct+"/", org.apache.http.entity.ContentType.TEXT_PLAIN);
        StringBody cbOneImage = new StringBody(nameImage, org.apache.http.entity.ContentType.TEXT_PLAIN);
        
        HttpEntity mpEntity = MultipartEntityBuilder.create()
                    .addPart("delete", cbDelete)
                    .addPart("name", cbOneImage)
                    .addPart("pathProduct", cbPath)
                    .build();
        
        httppost.setEntity(mpEntity);

        System.out.println("1.executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println("2."+response.getStatusLine());
        if (resEntity != null) {
          System.out.println("3."+EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
          resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
    }
    
    public void copyImage(String idProduit, String nameImage, String type) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        
        HttpPost httppost = new HttpPost(upload);
        
        String idProduct = idProduit;
        
        StringBody cbCopy = new StringBody("copy", org.apache.http.entity.ContentType.TEXT_PLAIN);
        StringBody cbImage = new StringBody(nameImage, org.apache.http.entity.ContentType.TEXT_PLAIN);
        StringBody cbPath = null;
        
        cbPath = new StringBody(idProduct+"/", org.apache.http.entity.ContentType.TEXT_PLAIN);
        
        StringBody cbType = new StringBody(type, org.apache.http.entity.ContentType.TEXT_PLAIN);
        
        HttpEntity mpEntity = MultipartEntityBuilder.create()
                    .addPart("copy", cbCopy)
                    .addPart("typeCopy", cbType)
                    .addPart("pathProduct", cbPath)
                    .addPart("image", cbImage)
                    .build();
        
        httppost.setEntity(mpEntity);

        System.out.println("1.executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println("2."+response.getStatusLine());
        if (resEntity != null) {
          System.out.println("3."+EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
          resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
    }
    
   
    public void sendInformation(String pathImageProduit, String idProduit) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        
        HttpPost httppost = new HttpPost(upload);
        
        String idProduct = idProduit;
        File file = new File(pathImageProduit);
        
        ContentBody cbFile = new FileBody(file);   
        StringBody cbPath = new StringBody(idProduct+"/", org.apache.http.entity.ContentType.TEXT_PLAIN);

        
        HttpEntity mpEntity = MultipartEntityBuilder.create()
                    .addPart("userfile", cbFile)
                    .addPart("pathProduct", cbPath)
                    .build();
        
        httppost.setEntity(mpEntity);

        System.out.println("1.executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println("2."+response.getStatusLine());
        if (resEntity != null) {
          System.out.println("3."+EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
          resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
    }
    
    
    public Node createPage(int pageIndex, TableView tableView, ArrayList table){
        tableView.setItems(FXCollections.observableArrayList(getData(pageIndex, table)));
        return tableView;
    }
    
    public ArrayList<Product> getData(int pageIndex, ArrayList table){
        int lastIndex = 0;
        int displace = table.size() % itemPerPage;
        if (displace > 0) {
            lastIndex = table.size() / itemPerPage + 1;
        } else {
            lastIndex = (table.size() / itemPerPage) ;
        }

        if (lastIndex == pageIndex) {
            ArrayList tab = new ArrayList<>();
            for(int i = pageIndex * itemPerPage; i < pageIndex * itemPerPage + displace; i++){
                if( i < table.size()) tab.add(table.get(i));
            }
            
            return tab;
        } else {
            ArrayList tab = new ArrayList<>();
            for(int i = pageIndex * itemPerPage; i < pageIndex * itemPerPage + itemPerPage; i++){
                if( i < table.size()) tab.add(table.get(i));
            }
            
            return tab;
        }
    }
}
