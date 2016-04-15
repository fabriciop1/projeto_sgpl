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
import modelo.negocio.InventarioMaquinas;

import modelo.negocio.Perfil;
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
        
        System.out.println(sql);
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
        
        System.out.println(sql);
        return sql;
    }
    
    private String createSQLDeleteObject(T object){
        
        String sql = "DELETE FROM " + object.getTableName() + " WHERE ";
        
        sql += object.getIdTableColumn() + "=" + object.getId();
        
        System.out.println(sql);
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
    }
     
    public void update(T object) throws SQLException{
        
        Connection connection = openConnection();
        String sql = createSQLUpdateObject(object);

        PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();
        st.close();
        closeConnection(connection);
    }
    
    public void remove(T object) throws SQLException{
        
        Connection connection = openConnection();
        String sql = createSQLDeleteObject(object);

        PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();
        st.close();
        closeConnection(connection);
    }
    
    public T retrieve(int id) throws InstantiationException, IllegalAccessException, SQLException{
        
        T object = objectClass.newInstance();
        object.setId(id);
        
        List<T> objects = retrieveBySQL( createSQLSelectObject(object) );
        
        if(objects.size() > 0){
            return objects.get(0);
        } else {
            return null;
        }
    }
    
    
    public List<T> retrieveByColumn(String tableColumn, Object columnValue) throws InstantiationException,IllegalAccessException,SQLException{
       
        return retrieveByColumns(new String[]{tableColumn}, new Object[]{columnValue}, null, null);        
    }
    
    public List<T> retrieveByColumn(String tableColumn, Object columnValue, String groupBy, String orderBy) throws InstantiationException, IllegalAccessException, SQLException{
        
        return retrieveByColumns(new String[]{tableColumn}, new Object[]{columnValue}, groupBy, orderBy);
    }
    
    public List<T> retrieveByColumns(String[] tableColumns, Object[] columnsValues, String groupBy, String orderBy) throws InstantiationException,IllegalAccessException,SQLException{
       
        if(tableColumns == null || tableColumns.length == 0){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumns(String[],Object[]): O array de nomes das colunas da tabela passado é invalido.");
        }
        else if(columnsValues == null || columnsValues.length == 0){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumns(String[],Object[]): O array de valores das colunas da tabela passado é inválido.");
        }
        else if(columnsValues.length != tableColumns.length){
            throw new IllegalArgumentException("GenericDAO.retrieveByColumns(String[],Object[]): Os arrays de colunas e de valores das colunas tem tamanhos diferentes.");
        }
        
        T object = objectClass.newInstance();
        
        return retrieveBySQL(createSQLSelect(object.getTableName(),tableColumns,columnsValues,null,null));        
    }
    
    public List<T> retrieveAll() throws InstantiationException, IllegalAccessException, SQLException{
        
        T object = objectClass.newInstance();
        
        return retrieveBySQL( "SELECT * FROM " + object.getTableName() );
    }
    
    public List<T> retrieveAll(String groupBy, String orderBy) throws InstantiationException, IllegalAccessException, SQLException{
        
        T object = objectClass.newInstance();
        
        return retrieveBySQL(createSQLSelect(object.getTableName(), null, null, groupBy, orderBy));
    }
    
    
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
        
        GenericDAO<InventarioMaquinas> dao = new GenericDAO<>(InventarioMaquinas.class);
        
        List<InventarioMaquinas> inventarios1 = dao.retrieveAll(null, "especificacao"),
                                 inventarios2 = dao.retrieveAll("unidade", "quantidade");
        
        for(InventarioMaquinas i : inventarios2){
            System.out.println(i.getEspecificacao() + " | " + i.getUnidade() + " | " + i.getQuantidade() + " | " + i.getVidaUtil());
        }
    }
}



