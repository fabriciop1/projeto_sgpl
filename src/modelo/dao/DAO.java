/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;

/**
 *
 * @author Jefferson Sales
 */
public class DAO {

    private boolean connectionOwner;
    protected Connection connection;

    public DAO(Connection connection) {

        this.connection = connection;
        this.connectionOwner = false;
        
        if(connection == null){
            throw new NullPointerException("DAO(Connection): The connection is NULL");
        }
    }

    public DAO() {
        this.connectionOwner = true;
    }

    public boolean isConnectionOwner() {
        return connectionOwner;
    }

}
