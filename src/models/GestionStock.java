package models;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionStock {
    private int idstock;
    private int idgest;
    private int codeproduit;
    private String nomproduit;
    private int quantite;
    private String datestock;
    private boolean typegest;
    
    private String idStockString;
    private String idGestString;
    private String idCodeString;
    private String typeString;
    private String nomGestString;
    
    public GestionStock(){}

    public int getIdstock() {
        return idstock;
    }

    public void setIdstock(int idstock) {
        this.idstock = idstock;
    }

    public int getIdgest() {
        return idgest;
    }

    public void setIdgest(int idgest) {
        this.idgest = idgest;
    }

    public int getCodeproduit() {
        return codeproduit;
    }

    public void setCodeproduit(int codeproduit) {
        this.codeproduit = codeproduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDatestock() {
        return datestock;
    }

    public void setDatestock(String datestock) {
        this.datestock = datestock;
    }

    public boolean isTypegest() {
        return typegest;
    }

    public void setTypegest(boolean typegest) {
        this.typegest = typegest;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public GestionStock(int idstock, int idgest, int codeproduit, int quantite, String datestock, boolean typegest, String nomproduit, String nomGesString) {
        this.idstock = idstock;
        this.idgest = idgest;
        this.codeproduit = codeproduit;
        this.quantite = quantite;
        this.datestock = datestock;
        this.typegest = typegest;
        this.nomproduit=nomproduit;
        this.nomGestString = nomGesString;
    }

    public GestionStock(int idgest, int codeproduit, int quantite, String datestock, boolean typegest, String nomproduit) {
        this.idgest = idgest;
        this.codeproduit = codeproduit;
        this.quantite = quantite;
        this.datestock = datestock;
        this.typegest = typegest;
        this.nomproduit=nomproduit;
    }
    
    public GestionStock(int idgest, int codeproduit, int quantite, boolean typegest, String nomproduit) {
        this.idgest = idgest;
        this.codeproduit = codeproduit;
        this.quantite = quantite;
        this.typegest = typegest;
        this.nomproduit=nomproduit;
    }

    public GestionStock(int idgest, int codeproduit, int quantite, boolean typegest) {
        this.idgest = idgest;
        this.codeproduit = codeproduit;
        this.quantite = quantite;
        this.typegest = typegest;
    }

    public String getIdStockString() {
        return idStockString;
    }

    public void setIdStockString(String idStockString) {
        this.idStockString = idStockString;
    }

    public String getIdGestString() {
        return idGestString;
    }

    public void setIdGestString(String idGestString) {
        this.idGestString = idGestString;
    }

    public String getIdCodeString() {
        return idCodeString;
    }

    public void setIdCodeString(String idCodeString) {
        this.idCodeString = idCodeString;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String formatCode(){
        String temp=String.valueOf(this.getCodeproduit());
        return temp.substring(0,3)+"-"+temp.substring(3,6);
    }
    
    public static String getGestionnaire(int gestionnaire) throws SQLException{
        DBManager.seConnecter();
        String name = "";
        DBManager.executeQuery("SELECT * FROM GESTIONNAIRE WHERE IDGEST="+gestionnaire);
        while (DBManager.getResult().next()) {
            name = DBManager.getResult().getString(2);
            break;
        }
        DBManager.fermerConnexion();
        return name;
    }

    public static ArrayList<GestionStock> listStocks() throws SQLException{
        DBManager.seConnecter();
        ArrayList<GestionStock> liststocks=new ArrayList<>();
        try {
            DBManager.executeQuery("SELECT * FROM GESTIONSTOCK INNER JOIN PRODUIT USING(CODEPRODUIT) INNER JOIN gestionnaire USING(IDGEST) ORDER BY DATESTOCK DESC");
            GestionStock temp;
            while(DBManager.getResult().next()){
                temp=new GestionStock(DBManager.getResult().getInt(3),DBManager.getResult().getInt(1),DBManager.getResult().getInt(2),
                        DBManager.getResult().getInt(4),DBManager.getResult().getString(5),DBManager.getResult().getBoolean(6),
                        DBManager.getResult().getString(11), DBManager.getResult().getString(14));
                liststocks.add(temp);
            }
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
        DBManager.fermerConnexion();
        return  liststocks;
    }

    public void createStock() throws SQLException, Exception {
        DBManager.seConnecter();
        try {
            if(!this.isTypegest()){
                DBManager.executeQuery("SELECT QUANTITE FROM PRODUIT WHERE CODEPRODUIT="+this.getCodeproduit());
                int quantiteact=0;
                while(DBManager.getResult().next())
                    quantiteact=DBManager.getResult().getInt(1);

                if(this.getQuantite()>quantiteact){
                    throw new Exception("!!!!!!!!La quantité actuelle est plus petite que celle que vous voulez retirer!!!!!!!!");
                }
            }
            DBManager.executeQuery("INSERT INTO GESTIONSTOCK (IDGEST, CODEPRODUIT, QUANTITE, DATESTOCK, TYPEGEST) VALUES("
            +this.getIdgest()+","+this.getCodeproduit()+","+this.getQuantite()+",NOW(),"+this.isTypegest()+")");
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
        DBManager.fermerConnexion();
    }

    public String getNomGestString() {
        return nomGestString;
    }

    public void setNomGestString(String nomGestString) {
        this.nomGestString = nomGestString;
    }

}
