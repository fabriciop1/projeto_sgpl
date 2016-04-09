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
import modelo.negocio.Usuario;

/**
 *
 * @author usuario
 */
public class UsuarioDAO extends DAO implements InterfaceDAO<Usuario> {

    public UsuarioDAO() {
        super();
    }

    public UsuarioDAO(Connection connection) {
        super(connection);
    }

    /**
     *
     * @param novoUsuario
     * @throws SQLException
     */
    @Override
    public void cadastrar(Usuario novoUsuario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "INSERT INTO usuario (login, senha, idRotaFK) VALUES (?,?,?)";

        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // ID cadastrado pelo próprio banco
        statement.setString(1, novoUsuario.getLogin());
        statement.setString(2, novoUsuario.getSenha());
        statement.setInt(3, novoUsuario.getRota().getId());

        statement.executeUpdate();

        ResultSet key = statement.getGeneratedKeys();

        if (key.next()) {
            novoUsuario.setIdUsuario(key.getInt(1));
        }

        key.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public Usuario recuperarPorLogin(String login) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE login = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, login);

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setRota((new RotaDAO()).recuperar(rs.getInt("idRotaFK")));
        }

        rs.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return usuario;
    }

    /**
     *
     * @param usuario
     * @throws SQLException
     */
    @Override
    public void atualizar(Usuario usuario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "UPDATE usuario SET login=?, senha=? WHERE idUsuario = ?";

        PreparedStatement st = connection.prepareStatement(sql);

        st.setString(1, usuario.getLogin());
        st.setString(2, usuario.getSenha());
        st.setInt(3, usuario.getIdUsuario());

        st.executeUpdate();

        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<Usuario> recuperarTodos() throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "SELECT * FROM usuario";
        ArrayList<Usuario> usuarios = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()) {

            Usuario u = new Usuario();

            u.setIdUsuario(result.getInt("idUsuario"));
            u.setLogin(result.getString("login"));
            u.setSenha(result.getString("senha"));
            u.setRota((new RotaDAO()).recuperar(result.getInt("idRotaFK")));

            usuarios.add(u);
        }

        result.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return usuarios;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Usuario recuperar(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        Usuario usuario = new Usuario();

        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();

        usuario.setIdUsuario(id);
        usuario.setLogin(rs.getString("login"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setRota((new RotaDAO()).recuperar(rs.getInt("idRotaFK")));

        statement.close();
        rs.close();
        DBConexao.closeConnection(connection);

        return usuario;
    }

    /**
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void remover(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM usuario WHERE idUsuario = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public void removerPorLogin(String login) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM usuario WHERE login = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, login);

        statement.executeUpdate();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

}
