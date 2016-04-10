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
import modelo.negocio.Rota;

/**
 *
 * @author Fabricio
 */
public class RotaDAO extends DAO implements InterfaceDAO<Rota> {

    public RotaDAO() {
        super();
    }

    public RotaDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(Rota rota) throws SQLException {

        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "INSERT INTO rota (rota) VALUES (?)";

        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, rota.getRota());

        st.executeUpdate();

        ResultSet keys = st.getGeneratedKeys();

        if (keys.next()) {
            rota.setId(keys.getInt(1));
        }

        keys.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

    }

    @Override
    public void atualizar(Rota rota) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        String sql = "UPDATE rota SET rota=? WHERE idRota = ?";

        PreparedStatement st = this.connection.prepareStatement(sql);
        st.setString(1, rota.getRota());
        st.setInt(2, rota.getId());

        st.executeUpdate();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    @Override
    public void remover(int id) throws SQLException {
        if (isConnectionOwner()) {
            if (isConnectionOwner()) {
                this.connection = DBConexao.openConnection();
            }
        }
        String sql = "DELETE FROM rota WHERE idRota=?";

        PreparedStatement st = this.connection.prepareStatement(sql);
        st.setInt(1, id);

        st.executeUpdate();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    @Override
    public ArrayList<Rota> recuperarTodos() throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        ArrayList<Rota> rotas = null;

        String sql = "SELECT * FROM rota";

        PreparedStatement st = this.connection.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Rota rota = new Rota();

            rota.setId(rs.getInt("idRota"));
            rota.setRota(rs.getString("rota"));

            rotas.add(rota);
        }
        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
        return rotas;
    }

    @Override
    public Rota recuperar(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        Rota rota = null;

        String sql = "SELECT * FROM rota WHERE idRota = ?";

        PreparedStatement st = this.connection.prepareStatement(sql);
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            rota = new Rota();

            rota.setRota(rs.getString("rota"));
            rota.setId(rs.getInt("idRota"));
        }

        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
        return rota;
    }
    
    public Rota recuperar(String rota) throws SQLException {
         if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
         
        Rota rotaObj = null;

        String sql = "SELECT * FROM rota WHERE rota = ?";
        
        PreparedStatement st = this.connection.prepareStatement(sql);
        st.setString(1, rota);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            rotaObj = new Rota();

            rotaObj.setRota(rs.getString("rota"));
            rotaObj.setId(rs.getInt("idRota"));
        }
        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
        
        return rotaObj;
        
    }
}
