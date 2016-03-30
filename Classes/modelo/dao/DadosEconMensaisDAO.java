/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.negocio.DadosEconMensais;

/**
 *
 * @author Fabricio
 */
public class DadosEconMensaisDAO {
    
    private Connection connection;
    
    public void cadastrar(DadosEconMensais dados) throws SQLException {
        
    }
    
    public void remover(int id) throws SQLException {
        
    }
    
    public void atualizar(DadosEconMensais dados) throws SQLException {
        
    }
    
    public DadosEconMensais recuperar(int id) throws SQLException {
        return null;
    }
    
    public ArrayList<DadosEconMensais> recuperarPorPerfil(int idPerfil) throws SQLException {
        return null;
    }
    
    public ArrayList<DadosEconMensais> recuperarTodos() throws SQLException {
        return null;
    }
            
    
}
