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
import modelo.negocio.Usuario;

/**
 *
 * @author usuario
 */
public class UsuarioDAO implements InterfaceDAO<Usuario> {
    Connection connection; 
    
    public UsuarioDAO(){
  
    }

    @Override
    public boolean cadastrar(Usuario novoUsuario) {
        this.connection = DBConexao.openConnection();
        String sql = "INSERT INTO usuario " + 
                "(login, senha, tipo) " +
                "VALUES (?,?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, novoUsuario.getLogin());
            statement.setString(2, novoUsuario.getSenha());
       
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

    @Override
    public ArrayList<Usuario> buscar(String login) {
        this.connection = DBConexao.openConnection();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM usuario WHERE login = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, login);
            
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
            }
                
            statement.close();
            usuarios.add(usuario);
            return usuarios; 
        } catch(SQLException e) {
            System.out.println("Falha ao buscar usuário. " + e.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }

    @Override
    public boolean atualizar(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean removerPorLogin(String login)
    {
        return true;
    }

    @Override
    public ArrayList<Usuario> recuperarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remover(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Usuario buscarPorId(int id) {
        return null;
    }

}
