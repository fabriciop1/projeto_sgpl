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

/**
 *
 * @author Fabricio
 */
public class DEMEspecificacaoDAO extends DAO {

    public DEMEspecificacaoDAO() {
        super();
    }

    public DEMEspecificacaoDAO(Connection connection) {
        super(connection);
    }

    public void cadastrar(String especificacao) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "INSERT INTO dem_especificacao (especificacao) VALUES (?)";

        PreparedStatement st = this.connection.prepareStatement(sql);
        st.setString(1, especificacao);

        st.executeUpdate();

        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public void remover(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM dem_especificacao WHERE idDEM_especificacao = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public void atualizar(int id, String especificacao) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "UPDATE dem_especificacao SET especificacao=? WHERE idDEM_especificacao=?";

        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, especificacao);
        st.setInt(2, id);

        st.executeUpdate();

        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public String recuperar(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        String especificacao = null;

        String sql = "SELECT * FROM dem_especificacao WHERE idDEM_especificacao=?";

        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            especificacao = rs.getString("especificacao");
        }

        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return especificacao;
    }

    public int recuperar(String especificacao) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        int id = 0;

        String sql = "SELECT * FROM dem_especificacao WHERE especificacao = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, especificacao);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getInt("idDEM_especificacao");
        }
        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return id;
    }

    public ArrayList<String> recuperarTodos() throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        ArrayList<String> especificacoes = new ArrayList<>();

        String sql = "SELECT * FROM dem_especificacao";

        PreparedStatement st = this.connection.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String especificacao = rs.getString("especificacao");
            especificacoes.add(especificacao);
        }
        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
        return especificacoes;
    }

}
