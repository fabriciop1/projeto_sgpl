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
import modelo.negocio.InventarioResumo;

/**
 *
 * @author Fabricio
 */
public class InventarioResumoDAO extends DAO implements InterfaceDAO<InventarioResumo> {

    public InventarioResumoDAO() {
        super();
    }

    public InventarioResumoDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(InventarioResumo inventario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "INSERT into inventario_resumo (custoOportunidade, atividadeLeiteira, salarioMinimo, mes, ano, vidaUtilReprodutores,"
                + "vidaUtilAnimaisServico, valorGastoCompraAnimais, idPerfilFK)"
                + " VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setDouble(1, inventario.getCustoOportunidade());
        st.setDouble(2, inventario.getAtividadeLeiteira());
        st.setDouble(3, inventario.getSalarioMinimo());
        st.setInt(4, inventario.getMes());
        st.setInt(5, inventario.getAno());
        st.setInt(6, inventario.getVidaUtilReprodutores());
        st.setInt(7, inventario.getVidaUtilAnimaisServico());
        st.setDouble(8, inventario.getValorGastoCompraAnimais());
        st.setInt(9, inventario.getPerfil().getId());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            inventario.setIdInventarioResumo(rs.getInt(1));
        }

        rs.close();
        st.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
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

        String sql = "DELETE FROM inventario_resumo WHERE idInventarioResumo=?";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
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
    public void atualizar(InventarioResumo inventario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "UPDATE inventario_resumo SET custoOportunidade=?, atividadeLeiteira=?, salarioMinimo=?, mes=?, ano=?,"
                + "vidaUtilReprodutores=?, vidaUtilAnimaisServico=?, valorGastoCompraAnimais=?"
                + "WHERE idInventarioResumo=?";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setDouble(1, inventario.getCustoOportunidade());
        ps.setDouble(2, inventario.getAtividadeLeiteira());
        ps.setDouble(3, inventario.getSalarioMinimo());
        ps.setInt(4, inventario.getMes());
        ps.setInt(5, inventario.getAno());
        ps.setInt(6, inventario.getVidaUtilReprodutores());
        ps.setInt(7, inventario.getVidaUtilAnimaisServico());
        ps.setDouble(8, inventario.getValorGastoCompraAnimais());
        ps.setInt(9, inventario.getIdInventarioResumo());

        ps.executeUpdate();

        ps.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public InventarioResumo recuperar(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        InventarioResumo inventario = null;

        String sql = "SELECT * FROM inventario_resumo WHERE idInventarioResumo=?";

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            inventario = new InventarioResumo();

            inventario.setCustoOportunidade(rs.getDouble("custoOportunidade"));
            inventario.setAtividadeLeiteira(rs.getDouble("atividadeLeiteira"));
            inventario.setSalarioMinimo(rs.getDouble("salarioMinimo"));
            inventario.setMes(rs.getInt("mes"));
            inventario.setAno(rs.getInt("ano"));
            inventario.setVidaUtilReprodutores(rs.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(rs.getInt("vidaUtilAnimaisServico"));
            inventario.setValorGastoCompraAnimais(rs.getDouble("valorGastoCompraAnimais"));
            inventario.setPerfil((new PerfilDAO()).recuperar(rs.getInt("idPerfilFK")));
            inventario.setIdInventarioResumo(rs.getInt("idInventarioResumo"));
        }

        rs.close();
        ps.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return inventario;
    }

    public InventarioResumo recuperarPorPerfil(int idPerfil) throws SQLException {
        String sql = "SELECT * FROM inventario_resumo WHERE idPerfilFK=?";
        InventarioResumo inventario = null;

        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idPerfil);

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            inventario = new InventarioResumo();

            inventario.setCustoOportunidade(rs.getDouble("custoOportunidade"));
            inventario.setAtividadeLeiteira(rs.getDouble("atividadeLeiteira"));
            inventario.setSalarioMinimo(rs.getDouble("salarioMinimo"));
            inventario.setMes(rs.getInt("mes"));
            inventario.setAno(rs.getInt("ano"));
            inventario.setVidaUtilReprodutores(rs.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(rs.getInt("vidaUtilAnimaisServico"));
            inventario.setValorGastoCompraAnimais(rs.getDouble("valorGastoCompraAnimais"));
            inventario.setPerfil((new PerfilDAO()).recuperar(rs.getInt("idPerfilFK")));
            inventario.setIdInventarioResumo(rs.getInt("idInventarioResumo"));
        }

        rs.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return inventario;
    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<InventarioResumo> recuperarTodos() throws SQLException {
        String sql = "SELECT * FROM inventario_resumo";

        ArrayList<InventarioResumo> inventarios = new ArrayList<>();

        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        PreparedStatement statement = this.connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            InventarioResumo inventario = new InventarioResumo();

            inventario.setCustoOportunidade(rs.getDouble("custoOportunidade"));
            inventario.setAtividadeLeiteira(rs.getDouble("atividadeLeiteira"));
            inventario.setSalarioMinimo(rs.getDouble("salarioMinimo"));
            inventario.setMes(rs.getInt("mes"));
            inventario.setAno(rs.getInt("ano"));
            inventario.setVidaUtilReprodutores(rs.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(rs.getInt("vidaUtilAnimaisServico"));
            inventario.setValorGastoCompraAnimais(rs.getDouble("valorGastoCompraAnimais"));
            inventario.setPerfil((new PerfilDAO()).recuperar(rs.getInt("idPerfilFK")));
            inventario.setIdInventarioResumo(rs.getInt("idInventarioResumo"));

            inventarios.add(inventario);
        }

        rs.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return inventarios;
    }

    public ArrayList<InventarioResumo> recuperarPorPeriodo(int anoInicio, int mesInicio, int anoFim, int mesFim) throws SQLException {
        String sql = "SELECT * FROM inventario_resumo WHERE (ano >= ? AND ano <= ?) AND (mes >= ? AND mes <= ?)";

        ArrayList<InventarioResumo> inventarios = new ArrayList<>();

        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, anoInicio);
        statement.setInt(2, anoFim);
        statement.setInt(3, mesInicio);
        statement.setInt(4, mesFim);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            InventarioResumo inventario = new InventarioResumo();

            inventario.setCustoOportunidade(rs.getDouble("custoOportunidade"));
            inventario.setAtividadeLeiteira(rs.getDouble("atividadeLeiteira"));
            inventario.setSalarioMinimo(rs.getDouble("salarioMinimo"));
            inventario.setMes(rs.getInt("mes"));
            inventario.setAno(rs.getInt("ano"));
            inventario.setVidaUtilReprodutores(rs.getInt("vidaUtilReprodutores"));
            inventario.setVidaUtilAnimaisServico(rs.getInt("vidaUtilAnimaisServico"));
            inventario.setValorGastoCompraAnimais(rs.getDouble("valorGastoCompraAnimais"));
            inventario.setPerfil((new PerfilDAO()).recuperar(rs.getInt("idPerfilFK")));
            inventario.setIdInventarioResumo(rs.getInt("idInventarioResumo"));

            inventarios.add(inventario);
        }

        rs.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return inventarios;
    }

}
