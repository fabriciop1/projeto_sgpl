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
import modelo.negocio.InventarioMaquinas;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioMaquinasDAO {
    
    private Connection connection;
    
    public InventarioMaquinasDAO(){
        
    }
    
    public void cadastrar(InventarioMaquinas inventario) throws SQLException{
        
        try {
            connection = DBConexao.openConnection();
            
            String sql = "INSERT INTO inventario_maquinas"
                    + "(especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) VALUES "
                    + " (?,?,?,?,?,?)";
            
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, inventario.getEspecificacao());
            statement.setString(2, inventario.getUnidade());
            statement.setDouble(3, inventario.getQuantidade());
            statement.setDouble(4, inventario.getValorUnitario());
            statement.setInt(5, inventario.getVidaUtil());
            statement.setDouble(6, inventario.getPerfil().getIdPerfil());
            
            statement.executeUpdate();
            
            ResultSet keys = statement.getGeneratedKeys();
            
            if (keys.next()) {
                inventario.setId(keys.getInt(1));
            }
            
            keys.close();
            
            statement.close();
            
            DBConexao.closeConnection(connection);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void remover(int id) throws SQLException{
        
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM inventario_maquinas WHERE idInventarioMaquinas = ?";
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);
                
        statement.executeUpdate();
        statement.close();
            
        DBConexao.closeConnection(this.connection);   
    }

    public void atualizar(InventarioMaquinas inventario) throws SQLException{
        
        connection = DBConexao.openConnection();
        
        String sql = "UPDATE inventario_maquinas SET especificacao=?, unidade=?, quantidade=?, valorUnitario=?,"
                + "vidaUtil=? WHERE idInventarioMaquinas = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, inventario.getEspecificacao());
        statement.setString(2, inventario.getUnidade());
        statement.setDouble(3, inventario.getQuantidade());
        statement.setDouble(4, inventario.getValorUnitario());
        statement.setInt(5, inventario.getVidaUtil());
        statement.setInt(6, inventario.getId());
        
        statement.executeUpdate();
        statement.close();
        
        DBConexao.closeConnection(connection);
    }

    public InventarioMaquinas recuperar(int id) throws SQLException{
        
        String sql = "SELECT * FROM inventario_maquinas WHERE idInventarioMaquinas=?";
        
        InventarioMaquinas inventario = null;
        
        connection = DBConexao.openConnection();
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        
        ResultSet result = statement.executeQuery();
        
        if(result.next()){
            inventario = new InventarioMaquinas();
            
            inventario.setId(result.getInt("idInventarioMaquinas"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setUnidade(result.getString("unidade"));
            inventario.setQuantidade(result.getDouble("quantidade"));
            inventario.setValorUnitario(result.getDouble("valorUnitario"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
        }
        
        result.close();
        statement.close();
        DBConexao.closeConnection(connection);
        
        return inventario;
        
    }

    public ArrayList<InventarioMaquinas> recuperarTodos() throws SQLException{
        
        String sql = "SELECT * FROM inventario_maquinas";
        
        ArrayList<InventarioMaquinas> inventarios = new ArrayList<>();
        
        connection = DBConexao.openConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        
        while(result.next()){
            
            InventarioMaquinas inventario = new InventarioMaquinas();
            
            inventario.setId(result.getInt("idInventarioMaquinas"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setUnidade(result.getString("unidade"));
            inventario.setQuantidade(result.getDouble("quantidade"));
            inventario.setValorUnitario(result.getDouble("valorUnitario"));
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
