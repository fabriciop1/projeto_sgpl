/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Fabricio
 */
public class DBConexao {
    
    public DBConexao(){
        
    }
    
    public static Connection openConnection() 
    {
        Connection connection;
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
            
            String serverName = "localhost";
            String portNumber = "3306"; 
            String database = "projeto_pesquisa";
            String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + database;
            String username = "root";
            String password = "1234"; 
            
            connection = (Connection) DriverManager.getConnection(url, username, password);
            
            if(connection == null) {
                System.out.println("Falha na conexão com banco de dados.");
            } else {
                System.out.println("Conexão com banco de dados realizada com sucesso.");
            }
            return connection;
            
        } catch(ClassNotFoundException e) {
            System.out.println("Driver não encontrado. " + e.getMessage());
            return null;
        } catch (SQLException ex) {
            System.out.println("Problema durante conexão com banco de dados. " + ex.getMessage());
            return null;
        }
    }
    public static void closeConnection(java.sql.Connection connection) {
        try {
            connection.close();
            System.out.println("Conexão com banco de dados encerrada.");
        } catch (SQLException ex) {
            System.out.println("Falha ao encerrar conexão com banco de dados." + ex.getMessage());
        }
    }
}
