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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.negocio.Usuario;

/**
 *
 * @author usuario
 */
public class UsuarioDAO {
    Connection connection; 
    
    public UsuarioDAO(){
  
    }

    public boolean cadastrar(Usuario novoUsuario) {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO usuario " + 
                "(idUsuario, login, senha) " +
                "VALUES (?,?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, 0); // Substituido por outro ID quando for cadastrado no BD
            statement.setString(2, novoUsuario.getLogin());
            statement.setString(3, novoUsuario.getSenha());
       
            statement.execute();
            statement.close();
            
        } catch(SQLException e){
            System.out.println("Falha ao adicionar novo usuario. " + e.getMessage());
            return false;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return true;
    }

    public Usuario buscar(String login) {
        
        this.connection = DBConexao.openConnection();
        
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM usuario WHERE login = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, login);
            
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
            }
                
            statement.close();
            
            return usuario; 
            
        } catch(SQLException e) {
            System.out.println("Falha ao buscar usuário. " + e.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }

    public boolean atualizar(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void removerPorLogin(String login)
    {
        connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM usuario WHERE login = ?";
        
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, login);
                
                statement.executeUpdate();
                statement.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBConexao.closeConnection(connection);
    }

    public ArrayList<Usuario> recuperarTodos() {
        
        connection = DBConexao.openConnection();
        
        String sql = "SELECT * FROM usuario";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql); 
                 ResultSet result = statement.executeQuery()) {
                
                
                while(result.next()){
                    
                    Usuario u = new Usuario();
                    
                    u.setIdUsuario(result.getInt("idUsuario"));
                    u.setLogin(result.getString("login"));
                    u.setSenha(result.getString("senha"));
                    
                    usuarios.add(u);
                }
                
                statement.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBConexao.closeConnection(connection);
        
        return usuarios;
    }

    public void remover(int id) {
        connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                
                statement.executeUpdate();
                statement.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBConexao.closeConnection(connection);
    }
    
    public Usuario buscarPorId(int id) {
        
        this.connection = DBConexao.openConnection();
        
        Usuario usuario = new Usuario();
        
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            
            usuario.setIdUsuario(id);
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            
            statement.close();
            rs.close();
            
            return usuario;
            
        } catch (SQLException e) {
            System.out.println("Falha ao buscar Usuário por ID. " + e.getMessage());
        }
        return null;
    }
    
}
