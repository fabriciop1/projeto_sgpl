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
    private int id; 
    
    protected DatabaseObject(String tableName, int id){
        this.tableName = tableName;
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }
    
    public abstract Map<String,String> getObjectTableData();
    
    public abstract void setObjectData(Map<String,Object> objectData);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}