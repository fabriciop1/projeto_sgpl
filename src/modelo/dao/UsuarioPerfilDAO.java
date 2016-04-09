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
import modelo.negocio.Perfil;
import modelo.negocio.Usuario;

/**
 *
 * @author Fabricio
 */
public class UsuarioPerfilDAO extends DAO {

    public UsuarioPerfilDAO() {
        super();
    }

    public UsuarioPerfilDAO(Connection connection) {
        super(connection);
    }

    public void cadastrar(Usuario usuario, Perfil perfil) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "INSERT INTO usuario_perfil(idPerfilFK, idUsuarioFK) VALUES (?, ?) ";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        // ID cadastrado automaticamente pelo banco de dados
        statement.setInt(1, perfil.getIdPerfil());
        statement.setInt(2, usuario.getIdUsuario());

        statement.executeUpdate();

        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public ArrayList<Perfil> recuperarPerfisPorUsuario(int idUsuario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "SELECT * FROM usuario_perfil WHERE idUsuarioFK = ?";

        ArrayList<Perfil> perfis = new ArrayList<>();

        ResultSet result;
        PreparedStatement statement = this.connection.prepareStatement(sql);

        statement.setInt(1, idUsuario);
        result = statement.executeQuery();

        PerfilDAO perfilDao = new PerfilDAO();

        while (result.next()) {
            int idPerfil = result.getInt("idPerfilFK");
            Perfil perfil = perfilDao.recuperar(idPerfil);
            perfis.add(perfil);
        }

        result.close();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return perfis;
    }

    public void remover(int idUsuario, int idPerfil) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM usuario_perfil WHERE idPerfilFK = ? AND idUsuarioFK = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idPerfil);
        statement.setInt(2, idUsuario);

        statement.executeUpdate();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public void removerPerfil(int idPerfil) throws SQLException {

        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM usuario_perfil WHERE idPerfilFK = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idPerfil);

        statement.executeUpdate();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

    }

    /**
     *
     * @param idUsuario
     * @throws SQLException
     */
    public void removerPorUsuario(int idUsuario) throws SQLException {

        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM usuario_perfil WHERE idUsuarioFK = ?";

        PreparedStatement statement;
        statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idUsuario);

        statement.executeUpdate();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

    }
}
