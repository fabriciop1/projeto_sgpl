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
import modelo.negocio.InventarioBenfeitorias;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioBenfeitoriasDAO extends DAO implements InterfaceDAO<InventarioBenfeitorias> {

    public InventarioBenfeitoriasDAO() {
        super();
    }

    public InventarioBenfeitoriasDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(InventarioBenfeitorias inventario) throws SQLException {

        connection = DBConexao.openConnection();

        String sql = "INSERT INTO inventario_benfeitorias"
                + "(especificacao, unidade, quantidade, valorUnitario, vidaUtil, idPerfilFK) VALUES "
                + " (?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, inventario.getEspecificacao());
        statement.setString(2, inventario.getUnidade());
        statement.setDouble(3, inventario.getQuantidade());
        statement.setDouble(4, inventario.getValorUnitario());
        statement.setInt(5, inventario.getVidaUtil());
        statement.setInt(6, inventario.getPerfil().getId());

        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();

        if (keys.next()) {
            inventario.setId(keys.getInt(1));
        }

        keys.close();
        statement.close();
        DBConexao.closeConnection(connection);

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

        String sql = "DELETE FROM inventario_benfeitorias WHERE idInventarioBenfeitorias = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        statement.executeUpdate();
        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    /**
     *
     * @param inventario
     * @throws SQLException
     */
    @Override
    public void atualizar(InventarioBenfeitorias inventario) throws SQLException {

        connection = DBConexao.openConnection();

        String sql = "UPDATE inventario_benfeitorias SET especificacao=?, unidade=?, quantidade=?, valorUnitario=?,"
                + "vidaUtil=? WHERE idInventarioBenfeitorias = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, inventario.getEspecificacao());
        statement.setString(2, inventario.getUnidade());
        statement.setDouble(3, inventario.getQuantidade());
        statement.setDouble(4, inventario.getValorUnitario());
        statement.setInt(5, inventario.getVidaUtil());
        statement.setInt(6, inventario.getId());

        statement.executeUpdate();
        statement.close();

        DBConexao.closeConnection(connection);
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public InventarioBenfeitorias recuperar(int id) throws SQLException {

        String sql = "SELECT * FROM inventario_benfeitorias WHERE idInventarioBenfeitorias=?";

        InventarioBenfeitorias inventario = null;

        connection = DBConexao.openConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            inventario = new InventarioBenfeitorias();

            inventario.setId(result.getInt("idInventarioBenfeitorias"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setUnidade(result.getString("unidade"));
            inventario.setQuantidade(result.getDouble("quantidade"));
            inventario.setValorUnitario(result.getDouble("valorUnitario"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));
        }

        result.close();
        statement.close();
        DBConexao.closeConnection(connection);

        return inventario;

    }

    public ArrayList<InventarioBenfeitorias> recuperarPorPerfil(int idPerfil) throws SQLException {

        String sql = "SELECT * FROM inventario_benfeitorias WHERE idPerfilFK=?";

        ArrayList<InventarioBenfeitorias> inventarios = new ArrayList<>();

        connection = DBConexao.openConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, idPerfil);

        ResultSet result = statement.executeQuery();

        while (result.next()) {

            InventarioBenfeitorias inventario = new InventarioBenfeitorias();

            inventario.setId(result.getInt("idInventarioBenfeitorias"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setUnidade(result.getString("unidade"));
            inventario.setQuantidade(result.getDouble("quantidade"));
            inventario.setValorUnitario(result.getDouble("valorUnitario"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));

            inventarios.add(inventario);
        }

        result.close();
        statement.close();
        DBConexao.closeConnection(connection);

        return inventarios;

    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<InventarioBenfeitorias> recuperarTodos() throws SQLException {

        String sql = "SELECT * FROM inventario_benfeitorias";

        ArrayList<InventarioBenfeitorias> inventarios = new ArrayList<>();

        connection = DBConexao.openConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        while (result.next()) {

            InventarioBenfeitorias inventario = new InventarioBenfeitorias();

            inventario.setId(result.getInt("idInventarioBenfeitorias"));
            inventario.setEspecificacao(result.getString("especificacao"));
            inventario.setUnidade(result.getString("unidade"));
            inventario.setQuantidade(result.getDouble("quantidade"));
            inventario.setValorUnitario(result.getDouble("valorUnitario"));
            inventario.setVidaUtil(result.getInt("vidaUtil"));
            inventario.setPerfil((new PerfilDAO()).recuperar(result.getInt("idPerfilFK")));

            inventarios.add(inventario);
        }

        result.close();
        statement.close();
        DBConexao.closeConnection(connection);

        return inventarios;

    }
}
