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
import modelo.dao.DBConexao;
import modelo.negocio.Rota;
import util.Cast;

/**
 *
 * @author Jefferson Sales
 */
public class GenericDAO {
    
    private Connection connection;
    
    public GenericDAO(Connection connection) throws SQLException{
        this.connection = connection;
        
        if(connection == null)
            throw new NullPointerException("GenericDAO(Connection): A conexao passada é igual a NULL");
        
        if(connection.isClosed())
            throw new IllegalArgumentException("GenericDAO(Connection): A conexao passada esta fechada.");
    }
    
    
    
    private String createSQLInsert(DatabaseObject object){
                
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
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLUpdate(DatabaseObject object){
        
        Map<String,Object> dataMap = object.getObjectTableData();
        
        String sql = "UPDATE " + object.getTableName() + " SET ";
        
        for(Map.Entry<String,Object> entry : dataMap.entrySet()){
            
            sql += entry.getKey() + "=" + Cast.toSQLValue(entry.getValue()) + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        
        sql += " WHERE " + object.getIdTableColumn() + "=" + object.getId();
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLDelete(DatabaseObject object){
        
        String sql = "DELETE FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLSelectById(DatabaseObject object){
        
        String sql = "SELECT * FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLSelectByColumn(DatabaseObject object, String tableColumn, Object value){
        
        String sql = "SELECT * FROM " + object.getTableName() + " WHERE "; 
        
        sql += tableColumn + "=" + Cast.toSQLValue(value);
        
        System.out.println(sql);
        return sql;
    }
    
    private List<DatabaseObject> retrieveBySQL(Class databaseObjectSubclass, String sql) throws InstantiationException, 
            IllegalAccessException, SQLException{
        
        List<DatabaseObject> objects = new ArrayList<>();
        
        PreparedStatement st = this.connection.prepareStatement(sql);
        
        ResultSet rset = st.executeQuery();
        ResultSetMetaData rsetMeta = rset.getMetaData();
        
        if(!rset.first()){
            
            rset.close();
            st.close();
            return null;
        }
        
        final int columnCount = rsetMeta.getColumnCount();
        
        do{
            Map<String,Object> dataMap = new HashMap<>();
            
            for(int i=1; i<=columnCount; i++){
                dataMap.put(rsetMeta.getColumnName(i), rset.getObject(i));
            }
            
            DatabaseObject object = (DatabaseObject)databaseObjectSubclass.newInstance();
            
            object.setId(rset.getInt(1));
            object.setObjectData(dataMap);
            
            objects.add(object);
            
        }while(rset.next());
        
        rset.close();
        st.close();
        
        return objects;
    }    
    
    
    public int insert(DatabaseObject object) throws SQLException{
        
        String sql = createSQLInsert(object);

        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();

        ResultSet keys = st.getGeneratedKeys();

        if (keys.next()) {
            object.setId(keys.getInt(1));
        }

        keys.close();
        st.close();
        
        return object.getId();
    }
     
    public void update(DatabaseObject object) throws SQLException{
        
        String sql = createSQLUpdate(object);

        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();
        st.close();
    }
    
    public void remove(DatabaseObject object) throws SQLException{
        
        String sql = createSQLDelete(object);

        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();
        st.close();
    }
    
    public DatabaseObject retrieve(Class databaseObjectSubclass, int id) throws InstantiationException, IllegalAccessException, SQLException{
        
        if(DatabaseObject.class.isAssignableFrom(databaseObjectSubclass) == false){
            throw new IllegalArgumentException("GenericDAO.retrieve(int,Class): A classe passada não é subclasse de DatabaseObject");
        }
        
        DatabaseObject object = (DatabaseObject)databaseObjectSubclass.newInstance();
        object.setId(id);
        
        String sql = createSQLSelectById(object);
        
        PreparedStatement st = this.connection.prepareStatement(sql);
        
        ResultSet rset = st.executeQuery();
        ResultSetMetaData rsetMeta = rset.getMetaData();
        Map<String,Object> dataMap = new HashMap<>();
        
        if(!rset.first()){
            
            rset.close();
            st.close();
            return null;
        }
        
        final int columnCount = rsetMeta.getColumnCount();
        for(int i=1; i<=columnCount; i++){
            
            dataMap.put(rsetMeta.getColumnName(i), rset.getObject(i));
        }
        
        object.setObjectData(dataMap);
        
        rset.close();
        st.close();
        
        return object;
    }
    
    public List<DatabaseObject> retrieveByColumn(Class databaseObjectSubclass, String tableColumn, Object value) throws InstantiationException, IllegalAccessException, SQLException{
        
        if(DatabaseObject.class.isAssignableFrom(databaseObjectSubclass) == false){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumn(Class,String,Object): A classe passada não é subclasse de DatabaseObject");
        }
        else if(tableColumn == null || tableColumn.isEmpty()){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumn(Class,String,Object): O nome da coluna da tabela passado é invalido.");
        }
        else if(value == null){
            throw new NullPointerException("GenericDAO.retrieveByColumn(Class,String,Object): O valor da coluna passado é igual a NULL");
        }
        
        DatabaseObject object = (DatabaseObject)databaseObjectSubclass.newInstance();
        
        return retrieveBySQL(databaseObjectSubclass, createSQLSelectByColumn(object, tableColumn, value));        
    }
    
    public List<DatabaseObject> retrieveAll(Class databaseObjectSubclass) throws InstantiationException, IllegalAccessException, SQLException{
        
        if(DatabaseObject.class.isAssignableFrom(databaseObjectSubclass) == false){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumn(Class,String,Object): A classe passada não é subclasse de DatabaseObject");
        }
        
        DatabaseObject object = (DatabaseObject)databaseObjectSubclass.newInstance();
        
        return retrieveBySQL(databaseObjectSubclass, "SELECT * FROM " + object.getTableName());
    }
    
    public static void main(String[] args) throws SQLException {
        
        try {
            Connection c = DBConexao.openConnection();
            
            GenericDAO dao = new GenericDAO(c);
            
            List<DatabaseObject> rotas = dao.retrieveAll(Rota.class);

            for(DatabaseObject _rota : rotas){
                Rota rota = (Rota)_rota;
                System.out.println("ID: " + rota.getId() + " - Rota: " + rota.getRota());
            }
            
            DBConexao.closeConnection(c);
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



