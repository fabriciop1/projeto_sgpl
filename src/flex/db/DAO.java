/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.db;

import java.lang.reflect.Method;
import java.sql.Connection;
import modelo.dao.DBConexao;

/**
 *
 * @author Jefferson Sales
 */
public class DAO {

    private final boolean connectionOwner;
    private Connection connection;

    protected DAO(Connection connection) {

        this.connection = connection;
        this.connectionOwner = (connection == null);
    }

    public boolean isConnectionOwner() {
        return connectionOwner;
    }
    
    protected Connection openConnection(){
        
        if(isConnectionOwner()){
            return DBConexao.openConnection();
        } else {
            return connection;
        }
    }
    
    protected void closeConnection(Connection connection){
        
        if(isConnectionOwner()){
            DBConexao.closeConnection(connection);
        }
    }
}
