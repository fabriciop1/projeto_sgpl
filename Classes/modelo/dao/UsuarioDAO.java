/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.negocio.Usuario;

/**
 *
 * @author usuario
 */
public class UsuarioDAO {
    Connection connection; 
    
    public UsuarioDAO(){
        this.connection = DBConexao.openConnection();
    }
            
    public boolean cadastrar(Usuario novoUsuario){
        
        String sql = "INSERT INTO usuario " + 
                "(idUsuario, login, senha, tipo) " +
                "VALUES (?,?,?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, novoUsuario.getCodigo());
            statement.setString(2, novoUsuario.getLogin());
            statement.setString(3, novoUsuario.getSenha());
            statement.setInt(4, novoUsuario.getTipo());
       
            statement.execute();
            statement.close();
            this.connection.close();
            return true;
        } catch(SQLException e){
            System.out.println("Falha ao adicionar novo usuario. " + e.getMessage());
            return false;
        }
    }
}
