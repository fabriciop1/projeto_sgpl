/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fabricio
 */
public class DBConexao {

    private static String serverName;
    private static String portNumber;
    private static String database;
    private static String url;
    private static String username;
    private static String password;
    
    public DBConexao() {

    }

    public static Connection openConnection() {
        Connection connection;
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);

            serverName = "127.0.0.1";
            portNumber = "3306";
            database = "projeto_pesquisa";
            url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + database;
            username = "root";
            password = "root"; 

            connection = (Connection) DriverManager.getConnection(url, username, password);

            if (connection == null) {
                System.out.println("Falha na conexão com banco de dados.");
            }
            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado. " + e.getMessage());
            return null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problema durante conexão com servidor. " + ex.getMessage(), 
                    "Problema encontrado no servidor.", JOptionPane.ERROR_MESSAGE);
            System.out.println("Problema durante conexão com banco de dados. " + ex.getMessage());
            return null;
        }
    }

    public static void closeConnection(java.sql.Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Falha ao encerrar conexão com banco de dados." + ex.getMessage());
        }
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getPortNumber() {
        return portNumber;
    }

    public static String getDatabase() {
        return database;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }   
}
