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
import modelo.negocio.Usuario;

/**
 *
 * @author usuario
 */
public class UsuarioDAO {
    
    private Connection connection; 
    
    public UsuarioDAO(){
  
    }

    public boolean cadastrar(Usuario novoUsuario) {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO usuario" + 
                "(login, senha) " +
                "VALUES (?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // ID cadastrado pelo próprio banco
            statement.setString(1, novoUsuario.getLogin());
            statement.setString(2, novoUsuario.getSenha());
       
            statement.executeUpdate();
            
            ResultSet key = statement.getGeneratedKeys();
            
            if(key.next()){
                novoUsuario.setIdUsuario(key.getInt(1));
            }
            
            key.close();
            statement.close();
            
        } catch(SQLException e){
            System.out.println("Falha ao adicionar novo usuario. " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return true;
    }

    public Usuario buscarPorLogin(String login) {
        
        this.connection = DBConexao.openConnection();
        
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE login = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, login);
            
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                usuario = new Usuario();
                
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
            }
                
            statement.close();
            
        } catch(SQLException e) {
            System.out.println("Falha ao buscar usuário. " + e.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return usuario;
    }

    public boolean atualizar(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Usuario> buscarTodos() {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "SELECT * FROM usuario";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        try {
            try (PreparedStatement statement = this.connection.prepareStatement(sql); 
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
            System.out.println("Falha ao recuperar todos os usuários. " + ex.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        
        return usuarios;
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
            
        } catch (SQLException e) {
            System.out.println("Falha ao buscar Usuário por ID. " + e.getMessage());
            return null;
        }
        return usuario;
    }
    
    public void remover(int id) {
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        
        try {
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                
                statement.executeUpdate();
                statement.close();
            }
            
        } catch (SQLException ex) {
            System.out.println("Falha ao remover usuário por id. " + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
    
    public void removerPorLogin(String login)
    {
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM usuario WHERE login = ?";
        
        try {
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, login);
                
                statement.executeUpdate();
                statement.close();
            }
            
        } catch (SQLException ex) {
            System.out.println("Falha ao remover Usuario por login. " + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
    
}
