/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import modelo.dao.DBConexao;
import modelo.negocio.Rota;

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
                
        Map<String,String> dataMap = object.getObjectTableData();
        
        String sql = "INSERT INTO " + object.getTableName() + " (";
        
        for(final String entry : dataMap.keySet()){
            sql += entry + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        sql += ") VALUES (";
        
        for(final String value : dataMap.values()){
            sql += value + ",";
        }
        sql = sql.substring(0, sql.length()-1);
        sql += ")";
        
        System.out.println(sql);
        return sql;
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
     
    public void update(DatabaseObject object){
        throw new UnsupportedOperationException("GenericDAO.update(DatabaseObject): Metodo não implementado ainda :P");
    }
    
    public void remove(int id){
        throw new UnsupportedOperationException("GenericDAO.remove(int): Metodo não implementado ainda :P");
    }
    
    public List<DatabaseObject> retrieveAll(){
        throw new UnsupportedOperationException("GenericDAO.retrieveAll: Metodo não implementado ainda :P");
    }
    
    public DatabaseObject retrieve(int id){
        throw new UnsupportedOperationException("GenericDAO.retrieve(int): Metodo não implementado ainda :P");
    }
    
    
    public static void main(String[] args) throws SQLException {
        Connection c = DBConexao.openConnection();
        GenericDAO dao = new GenericDAO(c);
        dao.insert(new Rota("Casa do Caraleo"));
    }
}



