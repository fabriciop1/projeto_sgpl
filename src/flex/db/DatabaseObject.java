/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.db;

import java.util.Map;

/**
 *
 * @author Jefferson Sales
 */
public abstract class DatabaseObject {
    
    private final String tableName;
    private final String idTableColumn;
    private int id; 
    
    protected DatabaseObject(String tableName, String idTableColumn, int id){
        this.tableName = tableName;
        this.idTableColumn = idTableColumn;
        this.id = id;
    }

    protected DatabaseObject(String tableName, String idTableColumn){
        this(tableName, idTableColumn, 0);
    }
    
    
    public abstract Map<String,Object> getObjectTableData();
    
    public abstract void setObjectData(Map<String,Object> data);

    
    public String getTableName() {
        return tableName;
    }

    public String getIdTableColumn() {
        return idTableColumn;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
