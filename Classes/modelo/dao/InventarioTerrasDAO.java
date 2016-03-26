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
import java.sql.Statement;
import java.util.ArrayList;
import modelo.negocio.InventarioTerras;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioTerrasDAO {
    
    private Connection connection;
    
    public void cadastrar(InventarioTerras inventario) throws SQLException{
        try {
            this.connection = DBConexao.openConnection();
            
            String sql = "INSERT INTO inventario_terras "
                    + "(especificacao, areaArrendadaInicio, areaPropriaInicio, areaArrendadaFinal, areaPropriaFinal, "
                    + "valorTerraNuaPropria, vidaUtil, custoFormacaoHectares, idPerfilFK) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, inventario.getEspecificacao());
            statement.setDouble(2, inventario.getAreaArrendadaInicio());
            statement.setDouble(3, inventario.getAreaPropriaInicio());
            statement.setDouble(4, inventario.getAreaArrendadaFinal());
            statement.setDouble(5, inventario.getAreaPropriaFinal());
            statement.setDouble(6, inventario.getValorTerraNuaPropria());
            statement.setInt(7, inventario.getVidaUtil());
            statement.setDouble(8, inventario.getCustoFormacaoHectare());
            statement.setInt(9, inventario.getPerfil().getIdPerfil());
            
            statement.executeUpdate();
            
            ResultSet keys = statement.getGeneratedKeys();
            
            if (keys.next()) {
                inventario.setId(keys.getInt(1));
            }
            
            keys.close();
            
            statement.close();
            
            DBConexao.closeConnection(this.connection);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
   
    public void atualizar(InventarioTerras inventario){
        
        try{
            connection = DBConexao.openConnection();
            
            String sql = "UPDATE inventario_terras SET especificacao=?, areaArrendadaInicio=?, areaPropriaInicio=?, "
                    + "areaArrendadaFinal=?, areaPropriaFinal=?, valorTerraNuaPropria=?, vidaUtil=?, custoFormacaoHectare=?"
                    + " WHERE idInventarioTerras=?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setString(1, inventario.getEspecificacao());
            st.setDouble(2, inventario.getAreaArrendadaInicio());
            st.setDouble(3, inventario.getAreaPropriaInicio());
            st.setDouble(4, inventario.getAreaArrendadaFinal());
            st.setDouble(5, inventario.getAreaPropriaFinal());
            st.setDouble(6, inventario.getValorTerraNuaPropria());
            st.setInt(7, inventario.getVidaUtil());
            st.setDouble(8, inventario.getCustoFormacaoHectare());
            st.setInt(9, inventario.getId()); 
            
            st.executeUpdate();
            st.close();
            
            DBConexao.closeConnection(connection);
        }
        
        catch(Exception e){
            System.out.println(e.getMessage());
        }
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
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setCustoFormacaoHectare(result.getDouble("custoFormacaoHectare"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
        }
        
        result.close();
        statement.close();
        DBConexao.closeConnection(connection);
        
        return inventario;
    }
    
    public ArrayList<InventarioTerras> recuperarPorPerfil(int idPerfil) throws SQLException{
        
        String sql = "SELECT * FROM inventario_terras WHERE idPerfilFK=?";
        
        ArrayList<InventarioTerras> inventarios = new ArrayList<>();
        
        connection = DBConexao.openConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, idPerfil);
        
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
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
            
            inventarios.add(inventario);
        }
        
        result.close();
        statement.close();
        
        DBConexao.closeConnection(connection);
    
        return inventarios;
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
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
            
            inventarios.add(inventario);
        }
        
        result.close();
        statement.close();
        
        DBConexao.closeConnection(connection);
    
        return inventarios;
    }
}
