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
import modelo.negocio.InventarioForrageiras;

/**
 *
 * @author Fabricio
 */
public class InventarioForrageirasDAO extends DAO implements InterfaceDAO<InventarioForrageiras>{
    
    public InventarioForrageirasDAO() {
        super();
    }
    
    public InventarioForrageirasDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(InventarioForrageiras inventario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        
        String sql = "INSERT INTO inventario_forrageiras "
                + "(forrageirasNaoAnuais, custoFormacaoHectare, vidaUtil, idPerfilFK, idInventarioTerrasFK) "
                + "VALUES (?,?,?,?,?)";
        
        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        statement.setString(1, inventario.getForrageirasNaoAnuais());
        statement.setDouble(2, inventario.getCustoFormacaoHectare());
        statement.setInt(3, inventario.getVidaUtil());
        statement.setInt(4, inventario.getPerfil().getId());
        statement.setInt(5, inventario.getInventarioTerras().getId());
        
        statement.executeUpdate();
        
        ResultSet keys = statement.getGeneratedKeys();

        if (keys.next()) {
            inventario.setId(keys.getInt(1));
        }

        keys.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }    
    }

    @Override
    public void atualizar(InventarioForrageiras inventario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "UPDATE inventario_forrageiras SET forrageirasNaoAnuais=?, custoFormacaoHectare=?, vidaUtil=?, "
                + "idPerfilFK=?, idInventarioTerrasFK=?"
                + " WHERE idInventarioForrageiras=?";

        PreparedStatement st = this.connection.prepareStatement(sql);
        
        st.setString(1, inventario.getForrageirasNaoAnuais());
        st.setDouble(2, inventario.getCustoFormacaoHectare());
        st.setInt(3, inventario.getVidaUtil());
        st.setInt(4, inventario.getPerfil().getId());
        st.setInt(5, inventario.getInventarioTerras().getId());
        st.setInt(6, inventario.getId());
        
        st.executeUpdate();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    @Override
    public void remover(int id) throws SQLException {
       if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM inventario_forrageiras WHERE idInventarioForrageiras = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();
        
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    @Override
    public InventarioForrageiras recuperar(int id) throws SQLException {
        String sql = "SELECT * FROM inventario_forrageiras WHERE idInventarioForrageiras = ?";
        
        InventarioForrageiras inventario = null;
        
         if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            inventario = new InventarioForrageiras();
            
            inventario.setId(result.getInt("idInventarioForrageiras"));
            inventario.setForrageirasNaoAnuais(result.getString("forrageirasNaoAnuais"));
            inventario.setCustoFormacaoHectare(result.getDouble("custoFormacaoHectare"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
            inventario.setInventarioTerras((new InventarioTerrasDAO()).recuperar(result.getInt("idInventarioTerrasFK")));
        }
        result.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
        return inventario;
    }
    
    public ArrayList<InventarioForrageiras> recuperarPorPerfil(int idPerfil) throws SQLException {
        String sql = "SELECT * FROM inventario_forrageiras WHERE idPerfilFK = ?";
        
        ArrayList<InventarioForrageiras> inventarios = new ArrayList<>();
        
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idPerfil);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            InventarioForrageiras inventario = new InventarioForrageiras();
            
            inventario.setId(result.getInt("idInventarioForrageiras"));
            inventario.setForrageirasNaoAnuais(result.getString("forrageirasNaoAnuais"));
            inventario.setCustoFormacaoHectare(result.getDouble("custoFormacaoHectare"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
            inventario.setInventarioTerras((new InventarioTerrasDAO()).recuperar(result.getInt("idInventarioTerrasFK")));
            
            inventarios.add(inventario);
        }

        result.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return inventarios;
    }

    @Override
    public ArrayList<InventarioForrageiras> recuperarTodos() throws SQLException {
        String sql = "SELECT * FROM inventario_forrageiras";
        
        ArrayList<InventarioForrageiras> inventarios = new ArrayList<>();
        
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        PreparedStatement statement = this.connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            InventarioForrageiras inventario = new InventarioForrageiras();
            
            inventario.setId(result.getInt("idInventarioForrageiras"));
            inventario.setForrageirasNaoAnuais(result.getString("forrageirasNaoAnuais"));
            inventario.setCustoFormacaoHectare(result.getDouble("custoFormacaoHectare"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
            inventario.setInventarioTerras((new InventarioTerrasDAO()).recuperar(result.getInt("idInventarioTerrasFK")));
            
            inventarios.add(inventario);
        }

        result.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return inventarios;
    }
    
}
