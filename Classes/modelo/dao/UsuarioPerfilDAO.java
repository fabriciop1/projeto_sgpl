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
import modelo.negocio.Perfil;

/**
 *
 * @author Fabricio
 */
public class UsuarioPerfilDAO {
    
    Connection connection; 
    
    public UsuarioPerfilDAO() {
        
    }
    
    public void cadastrar(int idUsuario, int idPerfil) {
        
        connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO usuario_perfil(idUsuarioPerfil, idPerfilFK, idUsuarioFK) VALUES (?, ?, ?) ";
        
        try {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, 0);
                statement.setInt(2, idPerfil);
                statement.setInt(3, idUsuario);
                
                statement.execute();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBConexao.closeConnection(connection);
    }
    
    public ArrayList<Perfil> buscarPerfisPorUsuario(int idUsuario) {
        
        connection = DBConexao.openConnection();
        
        String sql = "SELECT * FROM usuario_perfil WHERE idUsuarioFK = ?"; 
        
        ArrayList<Perfil> perfis = new ArrayList<>();
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            
            ResultSet result = statement.executeQuery();
            PerfilDAO perfilDao = new PerfilDAO();
            
            while(result.next()){
                
                int idPerfil = result.getInt("idPerfilFK");
                
                Perfil perfil = perfilDao.buscarPorId(idPerfil);
                
                perfis.add(perfil);
            }
            
            statement.close();
            result.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return perfis;
    }
    
    
    
}
