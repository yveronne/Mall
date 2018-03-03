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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author veronne
 */
public class Admin{
    
    private String username;
    
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String login(){
        
        String stringReturned="";
        
        //Chemin du fichier XML contenant les credentials de l'admin
        File xmlFile = new File("/home/veronne/Bureau/root.xml");
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =  dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("user");
            
                Node node = nodeList.item(0);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    if(element.getElementsByTagName("username").item(0).getTextContent().equals(this.getUsername())){
                        if (element.getElementsByTagName("password").item(0).getTextContent().equals(this.getPassword())) {
                            stringReturned = "Success";
                        }
                        else stringReturned = "Wrong Password!";
                    }
                    else stringReturned = "Wrong Username";
                }
                else stringReturned = "What the fuck";
            
           
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            stringReturned = "An error occured while reading the xml file";
        }
        
        return stringReturned;
    }

}
