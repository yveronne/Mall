/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author veronne
 */
public class User{
    
    private String name;
    
    private int type;
    
    private String username;
    
    private String password;
    
    private int isActive;
    
    private String phoneNumber;
    
    private String email;

    public User(String name, int type, String username, String password, int isActive, String phoneNumber, String email) {
        this.name = name;
        this.type = type;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //Créer un utilisateur (caissière ou gestionnaire)
    public String createUser() throws SQLException{
        
        DBManager.seConnecter();
        String query = "insert into GESTIONNAIRE" + "(NOM, TYPEGEST, USERNAME, PASSWORD, ACTIF, TELEPHONE, EMAIL) values"
                + "(?,?,?,?,?,?,?)";
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setString(1, this.name);
        prepare.setInt(2, this.type);
        prepare.setString(3, this.username);
        prepare.setString(4, this.password);
        
        //L'utilisateur est par défaut désactivé
        prepare.setInt(5, 0);
        
        prepare.setString(6, this.phoneNumber);
        prepare.setString(7, this.email);
        prepare.executeUpdate();
        
        DBManager.fermerConnexion();
        return "User successfully added!";
    }
    
    //Activer l'utilisateur, car en le créant il est inactif
    public String activateUser() throws SQLException{
        DBManager.seConnecter();
        
        String query = "update GESTIONNAIRE set ACTIF = 1 where USERNAME = ?";
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setString(1, this.username);
        prepare.executeUpdate();
        
        DBManager.fermerConnexion();
        return "Utilisateur activé";
    }
    
    //Supprimer l'utilisateur
    public String deleteUser() throws SQLException{
        DBManager.seConnecter();
        
        String query = "delete from GESTIONNAIRE where USERNAME = ?";
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setString(1, this.username);
        prepare.executeUpdate();

        DBManager.fermerConnexion();
        return "Utilisateur supprimé!";
    }
    
    //Se connecter
    public static String login(String username, String password) throws SQLException{
        ArrayList<Integer> resultat = new ArrayList<>();
        String returnedString;
        DBManager.seConnecter();
        
        String query = "select TYPEGEST, USERNAME, PASSWORD from GESTIONNAIRE where USERNAME = ? AND PASSWORD = ?";
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setString(1, username);
        prepare.setString(2, password);
        ResultSet resultSet = prepare.executeQuery();
        
        if(!resultSet.isBeforeFirst())   returnedString = "Utilisateur inconnu";
        else{
            while(resultSet.next()){
            resultat.add(resultSet.getInt("TYPEGEST"));
            }
            if(resultat.get(0) == 0)    returnedString = "Loggée comme caissière";
            else    returnedString = "Loggé comme magasinier";
        } 
        
        DBManager.fermerConnexion();
        
        return returnedString;
    }
    
    //Récupérer la liste des utilisateurs
    public static ArrayList<User> getUsers() throws SQLException{
        
        ArrayList<User> users = new ArrayList<>();
        User user;
        DBManager.seConnecter();
        
        String query = "SELECT NOM, TYPEGEST, USERNAME, PASSWORD, ACTIF, TELEPHONE, EMAIL from GESTIONNAIRE";
        DBManager.executeQuery(query);
        ResultSet resultSet = DBManager.getResult();
        while(resultSet.next()){
            user = new User(resultSet.getString("NOM"),resultSet.getInt("TYPEGEST")
                    ,resultSet.getString("USERNAME"),resultSet.getString("PASSWORD")
                    ,resultSet.getInt("ACTIF"),resultSet.getString("TELEPHONE"), resultSet.getString("EMAIL"));
            users.add(user);
        }
        resultSet.close();
        
        DBManager.fermerConnexion();
        return users;
    
    }
    
    
}
