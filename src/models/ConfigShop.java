/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import static controllers.Controller.ECHEC_NOT;
import static controllers.Controller.bool;
import static controllers.Controller.error;
import static controllers.Controller.not;
import static controllers.Controller.pathXml;
import static controllers.Controller.time;
import controllers.RootController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alain
 */
public class ConfigShop {
    private String admin;
    private String adminpwd;
    private String bd;
    private String login;
    private String loginpwd;
    private String server;
    private String port;
    private String dossierphotos;
    
    private String magasin;
    private String addresse;
    private String telephone;
    private String lieu;
    private String logo;
    
    private String upload;
    
    private String pathFac;
    private String pathPro;
    private String pathSto;
    
    
    private String status;
    
    public ConfigShop(){
        ArrayList<String> conf = readXmlFile();
        if(conf.get(8).equals("1")){
            admin = (conf.get(0).trim()==null)?"":conf.get(0);
            adminpwd = (conf.get(1).trim()==null)?"":conf.get(1);
            bd = (conf.get(2).trim()==null)?"":conf.get(2);
            login = (conf.get(3).trim()==null)?"":conf.get(3);
            loginpwd = (conf.get(4).trim()==null)?"":conf.get(4);
            server = (conf.get(5).trim()==null)?"":conf.get(5);
            port = (conf.get(6).trim()==null)?"":conf.get(6);
            dossierphotos = (conf.get(7).trim()==null)?"":conf.get(7);
            status = (conf.get(8).trim()==null)?"":conf.get(8);
            magasin = (conf.get(9).trim()==null)?"":conf.get(9);
            addresse = (conf.get(10).trim()==null)?"":conf.get(10);
            telephone = (conf.get(11).trim()==null)?"":conf.get(11); 
            lieu = (conf.get(12).trim()==null)?"":conf.get(12);
            logo= (conf.get(13).trim()==null)?"":conf.get(13);
            upload = (conf.get(14).trim()==null)?"":conf.get(14);
            pathFac = (conf.get(15).trim()==null)?"":conf.get(15);
            pathPro = (conf.get(16).trim()==null)?"":conf.get(16);
            pathSto = (conf.get(17).trim()==null)?"":conf.get(17);
        }
    }
    
    
    public boolean writeXML(String admin, String adminpwd, String bd, String user, 
            String password, String server, String port, String dossierphotos, String status, String path){
    boolean res = false;

    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder db;
    try {
        db = dbfactory.newDocumentBuilder();
        Document doc = db.newDocument();
        Element rootElement = doc.createElement("root");
        doc.appendChild(rootElement);
        
        Element adminNode = doc.createElement("admin");
        Element adminpwdNode = doc.createElement("adminpwd");
        Element bdNode = doc.createElement("bd");
        Element userNode = doc.createElement("user");
        Element passwordNode = doc.createElement("password");
        Element serverNode = doc.createElement("server");
        Element portNode = doc.createElement("port");
        Element dossierphotosNode = doc.createElement("dossierphotos");
        Element statusNode = doc.createElement("status");
        
        adminNode.appendChild(doc.createTextNode(admin));
        adminpwdNode.appendChild(doc.createTextNode(adminpwd));
        bdNode.appendChild(doc.createTextNode(bd));
        userNode.appendChild(doc.createTextNode(user));
        passwordNode.appendChild(doc.createTextNode(password));
        serverNode.appendChild(doc.createTextNode(server));
        portNode.appendChild(doc.createTextNode(port));
        dossierphotosNode.appendChild(doc.createTextNode(dossierphotos));
        statusNode.appendChild(doc.createTextNode(status));

        rootElement.appendChild(adminNode);
        rootElement.appendChild(adminpwdNode);
        rootElement.appendChild(bdNode);
        rootElement.appendChild(userNode);
        rootElement.appendChild(passwordNode);
        rootElement.appendChild(serverNode);
        rootElement.appendChild(portNode);
        rootElement.appendChild(dossierphotosNode);
        rootElement.appendChild(statusNode);

        Out(doc, path);
        res = true;
    } catch (Exception e) {
        return false;
    }
        return res;
    }
    
    private void Out(Document doc, String path){
        try {
            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            //for pretty print
            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            //StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(path));

            //write data
            //transformer.transform(source, console);
            transformer.transform(source, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public ArrayList<String> readXmlFile(){
        
        ArrayList<String> info = new ArrayList<String>();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathXml));
            NodeList racine = document.getElementsByTagName("root");
            Node r = racine.item(0);
            Element root = (Element)r;
            
            NodeList elts = root.getChildNodes();
            
            for(int i = 0; i < elts.getLength(); i++){
                Node n = elts.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    info.add(n.getTextContent());
                }
            }
            if(info.size() == 18) return info;
        } catch (ParserConfigurationException ex) {
        } catch (SAXException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            not.showNotifications(error, "Fichier de configuration manquant ...", ECHEC_NOT, time, bool);
            
        }
        
        return null;
    }

    public String getAdmin() {
        return admin;
    }
    
    public String getAdminpwd() {
        return adminpwd;
    }

    public String getBd() {
        return bd;
    }

    public String getLogin() {
        return login;
    }

    public String getLoginpwd() {
        return loginpwd;
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    
    public String getStatus() {
        return status;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDossierphotos() {
        return dossierphotos;
    }

    public void setDossierphotos(String dossierphotos) {
        this.dossierphotos = dossierphotos;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getPathFac() {
        return pathFac;
    }

    public void setPathFac(String pathFac) {
        this.pathFac = pathFac;
    }

    public String getPathPro() {
        return pathPro;
    }

    public void setPathPro(String pathPro) {
        this.pathPro = pathPro;
    }

    public String getPathSto() {
        return pathSto;
    }

    public void setPathSto(String pathSto) {
        this.pathSto = pathSto;
    }

}
