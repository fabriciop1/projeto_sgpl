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
import modelo.negocio.InventarioAnimais;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioAnimaisDAO {
    
     private Connection connection;
    
    public void cadastrar(InventarioAnimais inventario) throws SQLException{
        try {
            this.connection = DBConexao.openConnection();
            
            String sql = "INSERT INTO inventario_animais "  
                    + "(categoria, inicio, nascimento, morte, venda, compra, final, valorCabeca, "
                    + "vidaUtilReprodutores, vidaUtilAnimaisServico, tipoAnimal, idPerfilFK) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, inventario.getCategoria());
            st.setInt(2, inventario.getValorInicio());
            st.setInt(3, inventario.getNascimento());
            st.setInt(4, inventario.getMorte());
            st.setInt(5, inventario.getVenda());
            st.setInt(6, inventario.getCompra());
            st.setInt(7, inventario.getValorFinal());
            st.setDouble(8, inventario.getValorCabeca());
            st.setInt(9, inventario.getVidaUtilReprodutores());
            st.setInt(10, inventario.getVidaUtilAnimaisServico());
            st.setInt(11, inventario.getTipoAnimal());
            st.setInt(12, inventario.getPerfil().getIdPerfil());
            
            st.executeUpdate();
            
            ResultSet keys = st.getGeneratedKeys();
            
            if (keys.next()) {
                inventario.setId(keys.getInt(1));
            }
            
            keys.close();
            
            st.close();
            
            DBConexao.closeConnection(this.connection);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
    
    public void atualizar(InventarioAnimais inventario){
        
        try{
            connection = DBConexao.openConnection();
            
            String sql = "UPDATE inventario_animais SET categoria=?, inicio=?, nascimento=?, morte=?, venda=?, compra=?, final=?, valorCabeca=?,"
                    + " vidaUtilReprodutores=?, vidaUtilAnimaisServico=?, tipoAnimal=?"
                    + " WHERE idInventarioTerras=?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setString(1, inventario.getCategoria());
            st.setInt(2, inventario.getValorInicio());
            st.setInt(3, inventario.getNascimento());
            st.setInt(4, inventario.getMorte());
            st.setInt(5, inventario.getVenda());
            st.setInt(6, inventario.getCompra());
            st.setInt(7, inventario.getValorFinal());
            st.setDouble(8, inventario.getValorCabeca());
            st.setInt(9, inventario.getVidaUtilReprodutores());
            st.setInt(10, inventario.getVidaUtilAnimaisServico());
            st.setInt(11, inventario.getTipoAnimal());
            st.setInt(12, inventario.getId());
            
            st.executeUpdate();
            st.close();
            
            DBConexao.closeConnection(connection);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

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
            inventario.setValorCabeca(res.getDouble("valorCabeca"));
            inventario.setVidaUtilReprodutores(res.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(res.getInt("vidaUtilAnimaisServico"));
            inventario.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
        }
        
        res.close();
        statement.close();
        DBConexao.closeConnection(connection);
        
        return inventario;
    }
    
    public ArrayList<InventarioAnimais> recuperarPorPerfil(int idPerfil) throws SQLException{
        
        String sql = "SELECT * FROM inventario_animais WHERE idPerfilFK=?";
        
        ArrayList<InventarioAnimais> inventarios = new ArrayList<>();
        
        connection = DBConexao.openConnection();
        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, idPerfil);
        
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
            inventario.setValorCabeca(res.getDouble("valorCabeca"));
            inventario.setVidaUtilReprodutores(res.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(res.getInt("vidaUtilAnimaisServico"));
            inventario.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
            
            inventarios.add(inventario);
        }
        
        res.close();
        statement.close();
        
        DBConexao.closeConnection(connection);
    
        return inventarios;
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
            inventario.setValorCabeca(res.getDouble("valorCabeca"));
            inventario.setVidaUtilReprodutores(res.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(res.getInt("vidaUtilAnimaisServico"));
            inventario.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
            
            inventarios.add(inventario);
        }
        
        res.close();
        statement.close();
        
        DBConexao.closeConnection(connection);
    
        return inventarios;
    }
    
}
