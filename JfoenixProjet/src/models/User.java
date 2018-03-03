/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author veronne
 */
public abstract class User {
    
    protected String username;
    
    protected String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
            
    public String adminLogin(){
        
        File xmlFile = new File("/home/veronne/Bureau/root.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =  dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName(username)
           
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    abstract String login();
    
    abstract String register();
    
    abstract String delete();
}
