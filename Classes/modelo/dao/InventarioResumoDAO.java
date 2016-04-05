/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.negocio.InventarioResumo;

/**
 *
 * @author Fabricio
 */
public class InventarioResumoDAO {
    private Connection connection;
    
    public InventarioResumoDAO() {
        
    }
    
    public void cadastrar(InventarioResumo inventario) throws SQLException {
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT into inventario_resumo (custoOportunidade, atividadeLeiteira, salarioMinimo, mes, ano, idPerfilFK)"
                + " VALUES (?,?,?,?,?,?)";
        
        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setDouble(1, inventario.getCustoOportunidade());
        st.setDouble(2, inventario.getAtividadeLeiteira());
        st.setDouble(3, inventario.getSalarioMinimo());
        st.setInt(4, inventario.getMes());
        st.setInt(5, inventario.getAno());
        st.setInt(6, inventario.getPerfil().getIdPerfil());
    }
    
    public void remover(int id) throws SQLException {
        
    }
    
    public void atualizar(InventarioResumo inventario) throws SQLException {
        
    }
    
    public InventarioResumo recuperar(int id) throws SQLException {
        return null;
    }
    
    public ArrayList<InventarioResumo> recuperarPorPerfil(int idPerfil) throws SQLException {
        return null;
    }
    
    public ArrayList<InventarioResumo> recuperarTodos() throws SQLException {
        return null;
    }
    
    public ArrayList<InventarioResumo> recuperarPorPeriodo(int anoInicio, int mesInicio, int anoFim, int mesFim) throws SQLException {
        return null;
    }
            
}
