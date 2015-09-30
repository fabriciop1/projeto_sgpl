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
        this.connection = DBConexao.openConnection();
    }

    @Override
    public boolean cadastrar(Usuario novoUsuario) {
        String sql = "INSERT INTO usuario " + 
                "(login, senha, tipo) " +
                "VALUES (?,?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, novoUsuario.getLogin());
            statement.setString(2, novoUsuario.getSenha());
            statement.setInt(3, novoUsuario.getTipo());
       
            statement.execute();
            statement.close();
            
        } catch(SQLException e){
            System.out.println("Falha ao adicionar novo usuario. " + e.getMessage());
            return false;
        } finally {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                System.out.println("Conexão não encerrada corretamente. " + ex.getMessage());
            }
        }
        return true;
    }

    public Usuario buscar(String login) {
        Usuario usuario = new Usuario();
        String sql = "SELECT * FROM usuario WHERE login = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, login);
            
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()) {
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getInt("tipo"));
            }
                
            statement.close();
            return usuario; 
        } catch(SQLException e) {
            System.out.println("Falha ao buscar usuário. " + e.getMessage());
            return null;
        } finally {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                System.out.println("Conexão com o banco de dados não ancerrada corretamente. " + ex.getMessage());
            }
        }
    }

    @Override
    public boolean atualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remover(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> recuperarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
