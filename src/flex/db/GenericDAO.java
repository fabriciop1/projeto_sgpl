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

import modelo.negocio.Perfil;
import modelo.negocio.Rota;
import modelo.negocio.Usuario;
import util.Cast;

/**
 *
 * @author Jefferson Sales
 * @param <T>
 */
public class GenericDAO<T extends DatabaseObject> extends DAO {
    
    
    private Class<T> objectClass;
    
    
    public GenericDAO(Class<T> objectClass, Connection connection) throws SQLException{
        super(connection);
        
        this.objectClass = objectClass;
        
        if(connection != null && connection.isClosed())
            throw new IllegalArgumentException("GenericDAO(Connection): A conexao passada esta fechada.");
    }
    
    public GenericDAO(Class<T> objectClass) throws SQLException{
        this(objectClass, null);
    }
    
    private String createSQLInsert(T object){
                
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
    
    private String createSQLUpdate(T object){
        
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
    
    private String createSQLDelete(T object){
        
        String sql = "DELETE FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLSelectById(T object){
        
        String sql = "SELECT * FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLSelectByColumn(T object, String tableColumn, Object value){
        
        String sql = "SELECT * FROM " + object.getTableName() + " WHERE "; 
        
        sql += tableColumn + "=" + Cast.toSQLValue(value);
        
        System.out.println(sql);
        return sql;
    }
    
    private List<T> retrieveBySQL(String sql) throws InstantiationException, IllegalAccessException, SQLException{
        
        Connection connection = openConnection();
        
        List<T> objects = new ArrayList<>();
        
        PreparedStatement st = connection.prepareStatement(sql);
        
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
            
            T object = objectClass.newInstance();
            
            object.setId(rset.getInt(1));
            object.setObjectData(dataMap);
            
            objects.add(object);
            
        }while(rset.next());
        
        rset.close();
        st.close();
        
        closeConnection(connection);
        
        return objects;
    }    
    
    
    public int insert(T object) throws SQLException{
        
        Connection connection = openConnection();
        String sql = createSQLInsert(object);

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
    }
     
    public void update(T object) throws SQLException{
        
        Connection connection = openConnection();
        String sql = createSQLUpdate(object);

        PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();
        st.close();
        closeConnection(connection);
    }
    
    public void remove(T object) throws SQLException{
        
        Connection connection = openConnection();
        String sql = createSQLDelete(object);

        PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();
        st.close();
        closeConnection(connection);
    }
    
    public T retrieve(int id) throws InstantiationException, IllegalAccessException, SQLException{
        
        Connection connection = openConnection();
        T object = objectClass.newInstance();
        object.setId(id);
        
        String sql = createSQLSelectById(object);
        
        PreparedStatement st = connection.prepareStatement(sql);
        
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
        
        closeConnection(connection);
        
        return object;
    }
    
    public List<T> retrieveByColumn(String tableColumn, Object value) throws InstantiationException, IllegalAccessException, SQLException{
       
        if(tableColumn == null || tableColumn.isEmpty()){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumn(Class,String,Object): O nome da coluna da tabela passado é invalido.");
        }
        else if(value == null){
            throw new NullPointerException("GenericDAO.retrieveByColumn(Class,String,Object): O valor da coluna passado é igual a NULL");
        }
        
        T object = objectClass.newInstance();
        
        return retrieveBySQL(createSQLSelectByColumn(object, tableColumn, value));        
    }
    
    public List<T> retrieveAll() throws InstantiationException, IllegalAccessException, SQLException{
        
        T object = objectClass.newInstance();
        
        return retrieveBySQL("SELECT * FROM " + object.getTableName());
    }
    
    
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
        
        GenericDAO<Rota> dao1 = new GenericDAO<>(Rota.class);
        GenericDAO<Perfil> dao2 = new GenericDAO<>(Perfil.class);
        GenericDAO<Usuario> dao3 = new GenericDAO<>(Usuario.class);
        
        dao1.insert(new Rota("As pessoas boas devem amar seus inimigos"));
        
        List<Rota> rotas = dao1.retrieveAll();
        List<Perfil> perfis = dao2.retrieveAll();
        
        for(Rota r : rotas){
            System.out.println(r.getRota());
        }
        
        for(Perfil p : perfis){
            System.out.println(p.getNome() + " - Tam. Propr. :" + p.getTamPropriedade());
        }
    }
}



