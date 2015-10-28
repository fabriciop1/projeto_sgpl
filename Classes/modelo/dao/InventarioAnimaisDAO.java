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
import modelo.negocio.InventarioAnimais;
import modelo.negocio.InventarioTerras;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioAnimaisDAO {
    
     private Connection connection;
    
    public void cadastrar(InventarioAnimais inventario) throws SQLException{
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO inventario_animais" + 
                "(idInventarioAnimais, categoria, inicio, nascimento, morte, venda,"
                + "compra, final, valorCabeca) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement st = this.connection.prepareStatement(sql);
            // ID cadastrado pelo próprio banco
        st.setInt(1, inventario.getId()); //Tanto faz o valor. Sera modificado pelo banco de dados depois.
        st.setString(2, inventario.getCategoria());
        st.setInt(3, inventario.getValorInicio());
        st.setInt(4, inventario.getNascimento());
        st.setInt(5, inventario.getMorte());
        st.setInt(6, inventario.getVenda());
        st.setInt(7, inventario.getCompra());
        st.setInt(8, inventario.getValorFinal());
        st.setDouble(9, inventario.getValorPorCabeca());
       
        st.execute();
        st.close();
           
        DBConexao.closeConnection(this.connection);
    }
    
    public void remover(int id) throws SQLException{
        
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM inventario_animais WHERE idInventarioAnimais = ?";
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);
                
        statement.executeUpdate();
        statement.close();
            
        DBConexao.closeConnection(this.connection);        
    }
    
    public void alterar(InventarioTerras inventario){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    
    public InventarioAnimais recuperar(int id) throws SQLException{
        
        String sql = "SELECT * FROM inventario_animais WHERE idInventarioAnimais=?";
        
        InventarioAnimais inventario = null;
        
        connection = DBConexao.openConnection();
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        
        ResultSet res = statement.executeQuery();
        
        if(res.next()){
            inventario = new InventarioAnimais();
            
            inventario.setId(res.getInt("idInventarioAnimais"));
            inventario.setCategoria(res.getString("categoria"));
            inventario.setValorInicio(res.getInt("inicio"));
            inventario.setNascimento(res.getInt("nascimento"));
            inventario.setMorte(res.getInt("morte"));
            inventario.setVenda(res.getInt("venda"));
            inventario.setCompra(res.getInt("compra"));
            inventario.setValorFinal(res.getInt("final"));
            inventario.setValorPorCabeca(res.getDouble("valorCabeca"));
        }
        
        res.close();
        statement.close();
        DBConexao.closeConnection(connection);
        
        return inventario;
    }
    
    public ArrayList<InventarioAnimais> recuperarTodos() throws SQLException{
        
        String sql = "SELECT * FROM inventario_animais";
        
        ArrayList<InventarioAnimais> inventarios = new ArrayList<>();
        
        connection = DBConexao.openConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        
        while(res.next()){
            
            InventarioAnimais inventario = new InventarioAnimais();
            
            inventario.setId(res.getInt("idInventarioAnimais"));
            inventario.setCategoria(res.getString("categoria"));
            inventario.setValorInicio(res.getInt("inicio"));
            inventario.setNascimento(res.getInt("nascimento"));
            inventario.setMorte(res.getInt("morte"));
            inventario.setVenda(res.getInt("venda"));
            inventario.setCompra(res.getInt("compra"));
            inventario.setValorFinal(res.getInt("final"));
            inventario.setValorPorCabeca(res.getDouble("valorCabeca"));
            
            inventarios.add(inventario);
        }
        
        res.close();
        statement.close();
        
        DBConexao.closeConnection(connection);
    
        return inventarios;
    }
    
}
