package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Product {
    private int codeProduit;
    private int idcategorie;
    private double prix;
    private int quantite;
    private String descriptions;
    private String nom;
    private String codefournisseur;
    private boolean actif;
    private ArrayList<String> pictures = new ArrayList<>();
    private String codeString;
    private String nomcategorie;

    public String getNomcategorie() {
        return nomcategorie;
    }

    public void setNomcategorie(String nomcategorie) {
        this.nomcategorie = nomcategorie;
    }

    public int getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(int codeProduit) {
        this.codeProduit = codeProduit;
    }

    public int getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodefournisseur() {
        return codefournisseur;
    }

    public void setCodefournisseur(String codefournisseur) {
        this.codefournisseur = codefournisseur;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public Product() {
    }

    public Product(int codeProduit, int idcategorie, double prix, int quantite, String descriptions, String nom, String codefournisseur, boolean actif, String nomcategorie) {
        this.codeProduit = codeProduit;
        this.idcategorie = idcategorie;
        this.prix = prix;
        this.quantite = quantite;
        this.descriptions = descriptions;
        this.nom = nom;
        this.codefournisseur = codefournisseur;
        this.actif = actif;
        this.nomcategorie=nomcategorie;
    }
    
    /*public Product(int idcategorie, double prix, String descriptions, String nom, String codefournisseur, ArrayList<String> pictures) {
        this.idcategorie = idcategorie;
        this.prix = prix;
        this.descriptions = descriptions;
        this.nom = nom;
        this.codefournisseur = codefournisseur;
        this.pictures = pictures;
    }*/
    
    
    public String nomCat() throws SQLException{
        String name = "";
        DBManager.seConnecter();
        DBManager.executeQuery("SELECT NOMCATEGORIE FROM PRODUIT INNER JOIN CATEGORIE USING(IDCATEGORIE) "
                + "WHERE PRODUIT.IDCATEGORIE="+this.idcategorie);
        while (DBManager.getResult().next()) {
            name = DBManager.getResult().getString(1);
        }
        DBManager.fermerConnexion();
        return name;
    }

    public static ArrayList<Product> listProducts() throws SQLException{
        ArrayList<Product> listProducts=new ArrayList<>();
        DBManager.seConnecter();
        try {
            DBManager.executeQuery("SELECT * FROM PRODUIT INNER JOIN CATEGORIE USING(IDCATEGORIE)");
            Product temp=new Product();
            while(DBManager.getResult().next()){
                temp=new Product(DBManager.getResult().getInt(2),DBManager.getResult().getInt(1),
                        DBManager.getResult().getDouble(3),DBManager.getResult().getInt(4),DBManager.getResult().getString(5),
                        DBManager.getResult().getString(6),DBManager.getResult().getString(7),DBManager.getResult().getBoolean(8),DBManager.getResult().getString(9));
                listProducts.add(temp);
            }
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
        DBManager.fermerConnexion();
        return listProducts;
    }
    
    public void getGenerateCode(){
        try {
            DBManager.seConnecter();
            this.setCodeProduit(generateCode());
            DBManager.fermerConnexion();
        } catch (SQLException ex) {
            try {
                DBManager.fermerConnexion();
            } catch (SQLException ex1) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public void createProduct() throws SQLException{
        DBManager.seConnecter();
        String query = "INSERT INTO PRODUIT"
                    + "(CODEPRODUIT, IDCATEGORIE, PRIX, QUANTITE, DESCRIPTIONS, NOM, CODEFOURNISSEUR, ACTIF) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setInt(1, this.getCodeProduit());
        prepare.setInt(2, this.getIdcategorie());
        prepare.setDouble(3, this.getPrix());
        prepare.setInt(4, 0);
        
        //L'utilisateur est par défaut désactivé
        prepare.setString(5, this.getDescriptions());
        
        prepare.setString(6, this.getNom());
        prepare.setString(7, this.getCodefournisseur());
        prepare.setBoolean(8, true);
        prepare.executeUpdate();
        for (String temp:this.getPictures()) {
            addPicture(temp);
        }
        DBManager.fermerConnexion();
    }

    public void updateProduct() throws SQLException{
        DBManager.seConnecter();
        String query = "UPDATE PRODUIT SET IDCATEGORIE=?, PRIX=?, DESCRIPTIONS=?, NOM=?, CODEFOURNISSEUR=? WHERE CODEPRODUIT=?" ;
        PreparedStatement prepare = DBManager.getConnexion().prepareStatement(query);
        prepare.setInt(1, this.getIdcategorie());
        prepare.setDouble(2, this.getPrix());
        prepare.setString(3, this.getDescriptions());
        prepare.setString(4, this.getNom());
        
        //L'utilisateur est par défaut désactivé
        prepare.setString(5, this.getCodefournisseur());
        
        prepare.setInt(6, this.getCodeProduit());
        /*prepare.setString(7, this.getCodefournisseur());
        prepare.setBoolean(8, true);*/
        prepare.executeUpdate();
        for (String temp:this.getPictures()) {
            addPicture(temp);
        }
        DBManager.fermerConnexion();
    }
    
     public void deletePicture(String lienpicture) throws SQLException{
        try {
            DBManager.seConnecter();
            DBManager.executeQuery("DELETE FROM PHOTO WHERE CODEPRODUIT="+this.getCodeProduit()+" AND LIEN='"+lienpicture+"';");
            DBManager.fermerConnexion();
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
    }

    public static Product getProductById(int codeProduit) throws SQLException{
        Product product=new Product();
        try {
            DBManager.seConnecter();
            DBManager.executeQuery("SELECT * FROM PRODUIT INNER JOIN CATEGORIE USING(IDCATEGORIE) WHERE CODEPRODUIT="+codeProduit);
            if (DBManager.getResult().next()){
                product=new Product(DBManager.getResult().getInt(2),DBManager.getResult().getInt(1),
                        DBManager.getResult().getDouble(3),DBManager.getResult().getInt(4),DBManager.getResult().getString(5),
                        DBManager.getResult().getString(6),DBManager.getResult().getString(7),DBManager.getResult().getBoolean(8),DBManager.getResult().getString(9));

                product.setPictures(getPhotosByIdProduct(codeProduit));
                DBManager.fermerConnexion();
                return product;
            }
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
        DBManager.fermerConnexion();
        return null;
    }

    public static ArrayList<String> getPhotosByIdProduct(int codeProduit){

        ArrayList<String> listPhotos=new ArrayList<>();
        try {
            DBManager.executeQuery("SELECT LIEN FROM PHOTO WHERE CODEPRODUIT="+codeProduit);
            String temp=new String();
            while(DBManager.getResult().next()){
                temp=DBManager.getResult().getString(1);
                listPhotos.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPhotos;
    }

    public void addPicture(String picture){
        try {
            DBManager.executeQuery("INSERT INTO PHOTO (CODEPRODUIT,LIEN) VALUES("+this.getCodeProduit()+",'"+picture+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int generateCode(){
        boolean flag=true;
        int codegenerated;

        do{
            codegenerated= (int)((899999)*Math.random()+100000);
            try {
                DBManager.executeQuery("SELECT PRIX FROM PRODUIT WHERE CODEPRODUIT="+codegenerated);
                //on entre dans ce if si il y'a un résultat
                if(DBManager.getResult().next()){
                    continue;
                }
                flag=false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }while(flag);

        return codegenerated;
    }

    public String formatCode(){
        String temp=String.valueOf(this.getCodeProduit());
        return temp.substring(0,3)+"-"+temp.substring(3,6);
    }

    public String toString(){
        return this.getCodeProduit()+", "+this.getNom();
    }

    public String getCodeString() {
        codeString = formatCode();
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }
    
    public ArrayList<String> listPictures() throws SQLException{
        ArrayList<String> photos = new ArrayList<>();
        DBManager.seConnecter();
        try {
            DBManager.executeQuery("SELECT * FROM PHOTO WHERE CODEPRODUIT="+this.codeProduit);
            String lien ="";
            while(DBManager.getResult().next()){
                lien = DBManager.getResult().getString(3);
                photos.add(lien);
            }
            this.setPictures(photos);
        } catch (SQLException e) {
            DBManager.fermerConnexion();
            e.printStackTrace();
        }
        DBManager.fermerConnexion();
        return photos;
    }
}
