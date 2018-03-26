/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import com.mysql.jdbc.Connection;
import controllers.Controller;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Salomon Donald N.B
 */
public class DBManager extends Controller{
    private static Connection connexion;
    private static Statement statement;
    private static ResultSet result;
    private static PreparedStatement prepareStatement;
    
    public static Connection getConnexion() {
        return connexion;
    }

    public void setConnexion(Connection connexion) {
        this.connexion = connexion;
    }

    public Statement getStatement() {
        return statement;
    }

    public PreparedStatement getPrepareStatement() {
        return prepareStatement;
    }

    public void setPrepareStatement(PreparedStatement prepareStatement) {
        this.prepareStatement = prepareStatement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public static ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }
    
    public static void seConnecter() throws SQLException{
        try{
            if(DBManager.connexion==null || DBManager.connexion.isClosed()){
                Class.forName(driver);
                if(login != null){
                    DBManager.connexion=(Connection) DriverManager.getConnection(DBManager.chaineConnexion, DBManager.login,DBManager.mdp);
                }
                else
                    DBManager.connexion=(Connection) DriverManager.getConnection(DBManager.chaineConnexion);
            }
        }
        catch(ClassNotFoundException ex){
            throw new SQLException("Classe introuvable "+ex.getMessage());
        }
    }
    
    public static void fermerConnexion() throws SQLException
    {
        if(DBManager.connexion!=null && !DBManager.connexion.isClosed())
            DBManager.connexion.close();
    }
    
    public static void update(String query) throws SQLException{
        DBManager.prepareStatement = connexion.prepareStatement(query);
        prepareStatement.executeUpdate();

        DBManager.fermerConnexion();
    }
    
    public static void executeQuery(String query) throws SQLException{
        DBManager.statement = (Statement) connexion.createStatement();
        if (query.toLowerCase().contains("insert")||query.toLowerCase().contains("delete")||query.toLowerCase().contains("update")){
            statement.execute(query);
        }
        else{
                DBManager.result = statement.executeQuery(query);
        }

    }
}
