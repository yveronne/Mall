/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alain
 */
public class Category {
    
    private int idCategory;
    
    private String nomCategory;
    
    public Category(String nomCategory){
        this.nomCategory = nomCategory;
    }
    
    public Category(int idCategory, String nomCategory){
        this.idCategory = idCategory;
        this.nomCategory = nomCategory;
    }
    
    public Category(){}

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getNomCategory() {
        return nomCategory;
    }

    public void setNomCategory(String nomCategory) {
        this.nomCategory = nomCategory;
    }
    
    public void createCategory() throws SQLException{
        DBManager.seConnecter();
        DBManager.update("INSERT INTO CATEGORIE (NOMCATEGORIE) VALUES('"+this.nomCategory+"')");
        DBManager.fermerConnexion();
    }
    
    public static ArrayList<Category> listCategorie() throws SQLException{
        
        ArrayList<Category> ajout = new ArrayList<>();
        DBManager.seConnecter();
        Category temp = new Category();
        DBManager.executeQuery("SELECT * FROM CATEGORIE ORDER BY IDCATEGORIE DESC");
        while(DBManager.getResult().next()){
            
            temp = new Category(DBManager.getResult().getInt(1),DBManager.getResult().getString(2));
            ajout.add(temp);
        }
        DBManager.fermerConnexion();
        return ajout;
    }
    
    public void updateCategory(int id) throws SQLException{
        DBManager.seConnecter();
        DBManager.update("UPDATE CATEGORIE SET NOMCATEGORIE='"+nomCategory+"' WHERE IDCATEGORIE="+id+" ");
        DBManager.fermerConnexion();
    }

    @Override
    public String toString() {
        return nomCategory;
    }
    
}
