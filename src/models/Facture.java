/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.SQLException;

/**
 *
 * @author Alain
 */
public class Facture {
    
    private int idFact;
    private int idGest;
    private String dateFac;
    private double remise;
    private double mp;
    private boolean typeFact;
    private String telClient;
    
    private String nomGest;
    private String typeFactString;
    
    private Liste_Facture liste_Facture;

    public Facture(int idGest, double remise, boolean typeFact, String telClient) {
        this.idGest = idGest;
        this.remise = remise;
        this.typeFact = typeFact;
        this.telClient = telClient;
    }
    
    
    public void createFacture() throws SQLException, Exception {
        DBManager.seConnecter();
        try {
            if(this.typeFact){
                DBManager.executeQuery("INSERT INTO FACTURE (IDGEST, DATEFACTURE, MONTANT, TYPEFACT, TELCLIENT) VALUES("
                +this.getIdGest()+",NOW(),"+this.getMp()+",1,"+this.getTelClient()+")");
            }
            else {
                DBManager.executeQuery("INSERT INTO FACTURE (IDGEST, DATEFACTURE, MONTANT, TYPEFACT, TELCLIENT) VALUES("
                +this.getIdGest()+",NOW(),"+this.getMp()+",0,"+this.getTelClient()+")");
            }
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
        DBManager.fermerConnexion();
    }

    public int getIdFact() {
        return idFact;
    }

    public void setIdFact(int idFact) {
        this.idFact = idFact;
    }

    public int getIdGest() {
        return idGest;
    }

    public void setIdGest(int idGest) {
        this.idGest = idGest;
    }

    public String getDateFac() {
        return dateFac;
    }

    public void setDateFac(String dateFac) {
        this.dateFac = dateFac;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public boolean isTypeFact() {
        return typeFact;
    }

    public void setTypeFact(boolean typeFact) {
        this.typeFact = typeFact;
    }

    public String getNomGest() {
        return nomGest;
    }

    public void setNomGest(String nomGest) {
        this.nomGest = nomGest;
    }

    public String getTypeFactString() {
        return typeFactString;
    }

    public void setTypeFactString(String typeFactString) {
        this.typeFactString = typeFactString;
    }

    public String getTelClient() {
        return telClient;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

    public Liste_Facture getListe_Facture() {
        return liste_Facture;
    }

    public void setListe_Facture(Liste_Facture liste_Facture) {
        this.liste_Facture = liste_Facture;
    }
    
    
    
}
