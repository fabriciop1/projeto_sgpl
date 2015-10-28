/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.negocio.InventarioTerras;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioTerrasDAO {
    
    private Connection connection;
    
    public void cadastrar(InventarioTerras inventario) throws SQLException{
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO inventario_terras" + 
                "(idInventarioTerras, especificacao, areaArrendadaInicio, areaPropriaInicio, areaArrendadaFinal, areaPropriaFinal,"
                + "valorTerraNuaPropria, valorHectare, vidaUtil) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
            // ID cadastrado pelo próprio banco
        statement.setInt(1, inventario.getId()); //Tanto faz o valor. Sera modificado pelo banco de dados depois.
        statement.setString(2, inventario.getEspecificacao());
        statement.setDouble(3, inventario.getAreaArrendadaInicio());
        statement.setDouble(4, inventario.getAreaPropriaInicio());
        statement.setDouble(5, inventario.getAreaArrendadaFinal());
        statement.setDouble(6, inventario.getAreaPropriaFinal());
        statement.setDouble(7, inventario.getValorTerraNuaPropria());
        statement.setDouble(8, inventario.getValorHectare());
        statement.setInt(9, inventario.getVidaUtil());
       
        statement.execute();
        statement.close();
           
        DBConexao.closeConnection(this.connection);
    }
    
    public void remover(int id) throws SQLException{
        
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM inventario_terras WHERE idInventarioTerras = ?";
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);
                
        statement.executeUpdate();
        statement.close();
            
        DBConexao.closeConnection(this.connection);        
    }
    
    public void alterar(InventarioTerras inventario){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    
    public InventarioTerras recuperar(int id) throws SQLException{
        
        String sql = "SELECT * FROM inventario_terras WHERE idInventarioTerras=?";
        
        InventarioTerras inventario = null;
        
        connection = DBConexao.openConnection();
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        
        ResultSet result = statement.executeQuery();
        
        if(result.next()){
            inventario = new InventarioTerras();
            
            inventario.setId(result.getInt("idInventarioTerras"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setAreaArrendadaInicio(result.getDouble("areaArrendadaInicio"));
            inventario.setAreaPropriaInicio(result.getDouble("areaPropriaInicio"));
            inventario.setAreaArrendadaFinal(result.getDouble("areaArrendadaFinal"));
            inventario.setAreaPropriaFinal(result.getDouble("areaPropriaFinal"));
            inventario.setValorTerraNuaPropria(result.getDouble("valorTerraNuaPropria"));
            inventario.setValorHectare(result.getDouble("valorHectare"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
        }
        
        result.close();
        statement.close();
        DBConexao.closeConnection(connection);
        
        return inventario;
    }
    
    public ArrayList<InventarioTerras> recuperarTodos() throws SQLException{
        
        String sql = "SELECT * FROM inventario_terras";
        
        ArrayList<InventarioTerras> inventarios = new ArrayList<>();
        
        connection = DBConexao.openConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        
        while(result.next()){
            
            InventarioTerras inventario = new InventarioTerras();
            
            inventario.setId(result.getInt("idInventarioTerras"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setAreaArrendadaInicio(result.getDouble("areaArrendadaInicio"));
            inventario.setAreaPropriaInicio(result.getDouble("areaPropriaInicio"));
            inventario.setAreaArrendadaFinal(result.getDouble("areaArrendadaFinal"));
            inventario.setAreaPropriaFinal(result.getDouble("areaPropriaFinal"));
            inventario.setValorTerraNuaPropria(result.getDouble("valorTerraNuaPropria"));
            inventario.setValorHectare(result.getDouble("valorHectare"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            
            inventarios.add(inventario);
        }
        
        result.close();
        statement.close();
        
        DBConexao.closeConnection(connection);
    
        return inventarios;
    }
    
}
