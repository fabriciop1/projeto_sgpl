/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Jefferson Sales
 */
public class GenericDBModifier {
    
    private Connection connection;
    
    public GenericDBModifier(Connection openedConnection){
        
        this.connection = openedConnection;
    }

    protected void retrieveDatabaseTables() throws SQLException{
        
        ResultSet rset = connection.getMetaData().getTables(null, null, null, null);
        ResultSetMetaData metaData = rset.getMetaData();
            
        while(rset.next()){
            
        }
    }
    
    public void insertTableRow(String tableName, Object[] rowData, boolean ignoreIDColumn) {
        
                
    }

    public void updateTableRow(String tableName, Object[] rowData, boolean ignoreIDColumn) {
        
    }

    public void deleteTableRow(String tableName, Object[] rowData, boolean ignoreIDColumn) {
        
    }
    
    
}
