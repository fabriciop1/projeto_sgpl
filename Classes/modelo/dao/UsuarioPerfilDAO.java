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
import modelo.negocio.Perfil;

/**
 *
 * @author Fabricio
 */
public class UsuarioPerfilDAO {
    
    private Connection connection; 
    
    public UsuarioPerfilDAO() {
        
    }
    
    public void cadastrar(int idUsuario, int idPerfil) {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO usuario_perfil(idPerfilFK, idUsuarioFK) VALUES (?, ?) ";
        
        try {
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                // ID cadastrado automaticamente pelo banco de dados
                statement.setInt(1, idPerfil);
                statement.setInt(2, idUsuario);
                
                statement.execute();
                statement.close();
            }
            
        } catch (SQLException ex) {
            System.out.println("Falha ao cadastrar na tabela usuario_perfil. " + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
    
    public ArrayList<Perfil> buscarPerfisPorUsuario(int idUsuario) {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "SELECT * FROM usuario_perfil WHERE idUsuarioFK = ?"; 
        
        ArrayList<Perfil> perfis = new ArrayList<>();
   
        ResultSet result;
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            result = statement.executeQuery();
            PerfilDAO perfilDao = new PerfilDAO();
            while(result.next()){
                   
                int idPerfil = result.getInt("idPerfilFK");
                   
                Perfil perfil = perfilDao.recuperar(idPerfil);
                   
                perfis.add(perfil);
            }
            result.close();
            statement.close();
            
        } catch (SQLException ex) {
           System.out.println("Falha ao buscar perfis por usuario." + ex.getMessage());
           return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        
        return perfis;
    }
    
    public void remover(int idUsuario, int idPerfil){
        
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM usuario_perfil WHERE idPerfilFK = ? AND idUsuarioFK = ?";
        
        try {
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setInt(1, idPerfil);
                statement.setInt(2, idUsuario);
                
                statement.executeUpdate();
                statement.close();
            }
            
        } catch (SQLException ex) {
            System.out.println("Falha ao remover em tabela usuario_perfil." + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
    
    public void removerPerfil(int idPerfil){
        
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM usuario_perfil WHERE idPerfilFK = ?";
        
        try {
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setInt(1, idPerfil);
                
                statement.executeUpdate();
                statement.close();
            }
            
        } catch (SQLException ex) {
            System.out.println("Falha ao remover Perfil através do id do usuário. " + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
    
    public void removerUsuario(int idUsuario){
        
        this.connection = DBConexao.openConnection();

        String sql = "DELETE FROM usuario_perfil WHERE idUsuarioFK = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
                
            statement.executeUpdate();
            statement.close();
            
        } catch (SQLException ex) {
            System.out.println("Falha ao remover Usuario por id." + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
}
