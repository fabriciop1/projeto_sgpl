/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.DAO;
import util.Cast;

/**
 * @version 1.7.13
 * @author Jefferson Sales
 * @param <T> 
 */
public class GenericDAO<T extends DatabaseObject> extends DAO {
        
    private Class<T> objectClass;
    
    public GenericDAO(Class<T> objectClass, Connection connection){
        super(connection);
        
        try {
            this.objectClass = objectClass;
            
            if(connection != null && connection.isClosed())
                throw new IllegalArgumentException("GenericDAO(Connection): A conexao passada esta fechada.");
            
        } catch (SQLException | IllegalArgumentException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GenericDAO(Class<T> objectClass){
        this(objectClass, null);
    }
    
    
    private String createSQLInsertObject(T object){
                
        Map<String,Object> dataMap = object.getObjectTableData();
        
        String sql = "INSERT INTO " + object.getTableName() + " (";
        
        for(final String entry : dataMap.keySet()){
            sql += entry + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        sql += ") VALUES (";
        
        for(final Object value : dataMap.values()){
            
            sql += Cast.toSQLValue(value) + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        sql += ")";
        
        return sql;
    }
    
    private String createSQLUpdateObject(T object){
        
        Map<String,Object> dataMap = object.getObjectTableData();
        
        String sql = "UPDATE " + object.getTableName() + " SET ";
        
        for(Map.Entry<String,Object> entry : dataMap.entrySet()){
            
            sql += entry.getKey() + "=" + Cast.toSQLValue(entry.getValue()) + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        
        sql += " WHERE " + object.getIdTableColumn() + "=" + object.getId();
        
        return sql;
    }
    
    private String createSQLUpdateObjectColumns(T object, String[] tableColumns){
        
        Map<String,Object> dataMap = object.getObjectTableData();
        
        String sql = "UPDATE " + object.getTableName() + " SET ";
        
        for(String column : tableColumns){
            
            sql += column + "=" + Cast.toSQLValue(dataMap.get(column)) + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        
        sql += " WHERE " + object.getIdTableColumn() + "=" + object.getId();
        
        return sql;
    }
    
    private String createSQLDeleteObject(T object){
        
        String sql = "DELETE FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        return sql;
    }
    
    private String createSQLSelectObject(T object){
        
        String sql = "SELECT * FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        return sql;
    }
    
    
    private static String createSQLSelect(String tableName, String[] tableColumns, Object[] columnsValues, String groupBy, String orderBy){
        
        String sql = "SELECT * FROM " + tableName;
        
        if(tableColumns != null && tableColumns.length > 0){
            
            sql += " WHERE ";
            for(int i=0; i<tableColumns.length && i<columnsValues.length; i++){
                sql += tableColumns[i] + "=" + Cast.toSQLValue(columnsValues[i]) + " AND ";
            }
            sql = sql.substring(0, sql.length()-5);
        }
        
        if(groupBy != null && groupBy.length() > 0){
            sql += " GROUP BY " + groupBy;
        }
        
        if(orderBy != null && orderBy.length() > 0){
            sql += " ORDER BY " + orderBy;
        }
        
        return sql;
    }

//    private static String createSQLUpdate(String tableName, String[] tableColumns, Object[] columnsValues, String idColumn, Object idValue){
//        
//        String sql = "UPDATE " + tableName + " SET ";
//        
//        for(int i=0; i<tableColumns.length && i<columnsValues.length; i++){
//            
//            sql += tableColumns[i] + "=" + Cast.toSQLValue(columnsValues[i]) + ",";
//        }
//        sql = sql.substring(0, sql.length()-1);
//        
//        sql += " WHERE " + idColumn + "=" + Cast.toSQLValue(idValue);
//        
//        return sql;
//    }
//    
    protected static int counter = 0;
    
    public List<T> executeSQL(String sql){
        
        try {
            Connection connection = openConnection();
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.execute();
            
            ResultSet rset = st.getResultSet();
            
            List<T> objects = new ArrayList<>();
            
            
            if(rset == null || !rset.first()){
                
                st.close();
                closeConnection(connection);
                
                return objects;
            }
            
            ResultSetMetaData rsetMeta = rset.getMetaData();
            
            final int columnCount = rsetMeta.getColumnCount();
            
            do{
                Map<String,Object> dataMap = new HashMap<>();
                
                for(int i=1; i<=columnCount; i++){
                    dataMap.put(rsetMeta.getColumnName(i), rset.getObject(i));
                }
                
                T object = objectClass.newInstance();
                
                object.setId(rset.getInt(1));
                object.setObjectData(dataMap);
                
                objects.add(object);
                
            }while(rset.next());
            
            rset.close();
            st.close();
            
            closeConnection(connection);
            
            return objects;
            
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }    
    
    
    public int insert(T object){
        
        try {
            Connection connection = openConnection();
            String sql = createSQLInsertObject(object);
            
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.executeUpdate();
            
            ResultSet keys = st.getGeneratedKeys();
            
            if (keys.next()) {
                object.setId(keys.getInt(1));
            }
            
            keys.close();
            st.close();
            
            closeConnection(connection);
            
            return object.getId();
            
        } catch (SQLException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
     
    public void remove(int id){
        
        try {
            T object = objectClass.newInstance();
            object.setId(id);
            
            executeSQL( createSQLDeleteObject(object) );
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void update(T object){
        
        executeSQL( createSQLUpdateObject(object) );
    }
    
    public void updateByColumns(T object, String[] tableColumns){
        
        executeSQL( createSQLUpdateObjectColumns(object, tableColumns) );
    }
    
    public void updateByColumns(int id, String[] tableColumns, Object[] columnsValues){
        throw new UnsupportedOperationException("Essa porcaria ta dando erro ainda. Use outro método.");
        
//        try {
//            T object = objectClass.newInstance();
//            object.setId(id);
//            
//            executeSQL( createSQLUpdate(object.getTableName(), tableColumns, columnsValues, object.getIdTableColumn(), id) );
//            
//        } catch (InstantiationException | IllegalAccessException ex) {
//            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    
    public T retrieve(int id){
        
        try {
            T object = objectClass.newInstance();
            object.setId(id);
            
            List<T> objects = executeSQL( createSQLSelectObject(object) );
            
            if(objects.size() > 0){
                return objects.get(0);
            } else {
                return null;
            }
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<T> retrieveByColumn(String tableColumn, Object columnValue){
       
        return retrieveByColumns(new String[]{tableColumn}, new Object[]{columnValue}, null, null);        
    }
    
    public List<T> retrieveByColumn(String tableColumn, Object columnValue, String groupBy, String orderBy){
        
        return retrieveByColumns(new String[]{tableColumn}, new Object[]{columnValue}, groupBy, orderBy);
    }
    
    public List<T> retrieveByColumns(String[] tableColumns, Object[] columnsValues){
        
        return retrieveByColumns(tableColumns, columnsValues, null, null);
    }
    
    public List<T> retrieveByColumns(String[] tableColumns, Object[] columnsValues, String groupBy, String orderBy){
       
        try {
            if(tableColumns == null || tableColumns.length == 0){
                throw new IllegalArgumentException("GenericDAO.retrieveByColumns(String[],Object[],String,String): O array de nomes das colunas da tabela passado é invalido.");
            }
            else if(columnsValues == null || columnsValues.length == 0){
                throw new IllegalArgumentException("GenericDAO.retrieveByColumns(String[],Object[],String,String): O array de valores das colunas da tabela passado é inválido.");
            }
            else if(columnsValues.length != tableColumns.length){
                throw new IllegalArgumentException("GenericDAO.retrieveByColumns(String[],Object[],String,String): Os arrays de colunas e de valores das colunas tem tamanhos diferentes.");
            } 
            
            T object = objectClass.newInstance();
            
            return executeSQL(createSQLSelect(object.getTableName(),tableColumns,columnsValues,groupBy,orderBy));
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<T> retrieveAll(){
        
        try {
            T object = objectClass.newInstance();
            
            return executeSQL( "SELECT * FROM " + object.getTableName() );
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<T> retrieveAll(String groupBy, String orderBy){
        
        try {
            T object = objectClass.newInstance();
            
            return executeSQL(createSQLSelect(object.getTableName(), null, null, groupBy, orderBy));
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}



