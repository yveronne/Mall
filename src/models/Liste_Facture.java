/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Alain
 */
public class Liste_Facture {
    private int codeProduit;
    private int idFact;
    private int idLfact;
    private int quantité;
    private double prix;
    
    private Product produit;
    
    private double sousTotal;
    
    private String codeString;
    
    public Liste_Facture(int codeProduit,int idLfact, int quantité, double prix) {
        this.codeProduit = codeProduit;
        this.idLfact = idLfact;
        this.quantité = quantité;
        this.prix = prix;
    }
    
    public void createListeFacture() throws SQLException{
        DBManager.seConnecter();
        String query = "INSERT INTO LISTE_FACTURE"
                    + "(CODEPRODUIT, IDFACTURE, QUANTITE, PRIX) VALUES (?,?,?,?)";
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setInt(1, this.getCodeProduit());
        prepare.setInt(2, this.getIdFact());
        prepare.setInt(3, this.getQuantité());
        prepare.setDouble(4, this.getPrix());
        
        prepare.executeUpdate();
        DBManager.fermerConnexion();
    }
    
    public Liste_Facture(){}
    
    public String formatCode(){
        String temp=String.valueOf(this.getCodeProduit());
        return temp.substring(0,3)+"-"+temp.substring(3,6);
    }

    public int getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(int codeProduit) {
        this.codeProduit = codeProduit;
    }

    public int getIdFact() {
        return idFact;
    }

    public void setIdFact(int idFact) {
        this.idFact = idFact;
    }

    public int getIdLfact() {
        return idLfact;
    }

    public void setIdLfact(int idLfact) {
        this.idLfact = idLfact;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Product getProduit() {
        return produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public double getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(double sousTotal) {
        this.sousTotal = sousTotal;
    }

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }
    
}
