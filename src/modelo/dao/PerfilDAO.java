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
import modelo.negocio.Rota;
import modelo.negocio.Usuario;

public class PerfilDAO extends DAO implements InterfaceDAO<Perfil> {

    public PerfilDAO() {
        super();
    }

    public PerfilDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(Perfil perfil) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "INSERT INTO perfil "
                + "(nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, empPermanentes, numFamiliares, idRotaFK) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, perfil.getNome());
        statement.setString(2, perfil.getCidade());
        statement.setDouble(3, perfil.getTamPropriedade());
        statement.setDouble(4, perfil.getAreaPecLeite());
        statement.setDouble(5, perfil.getProdLeiteDiario());
        statement.setInt(6, perfil.getEmpPermanentes());
        statement.setInt(7, perfil.getNumFamiliares());
        statement.setInt(8, perfil.getRota().getId());

        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();

        if (keys.next()) {
            perfil.setIdPerfil(keys.getInt(1));
        }

        keys.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    /**
     *
     * @param perfil
     * @throws SQLException
     */
    @Override
    public void atualizar(Perfil perfil) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "UPDATE perfil SET nome=?, cidade=?, tamPropriedade=?, areaPecLeite=?, prodLeiteDiario=?, "
                + "empPermanentes=?, numFamiliares=? WHERE idPerfil=?";

        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, perfil.getNome());
        statement.setString(2, perfil.getCidade());
        statement.setDouble(3, perfil.getTamPropriedade());
        statement.setDouble(4, perfil.getAreaPecLeite());
        statement.setDouble(5, perfil.getProdLeiteDiario());
        statement.setInt(6, perfil.getEmpPermanentes());
        statement.setInt(7, perfil.getNumFamiliares());

        statement.setInt(8, perfil.getIdPerfil());

        statement.executeUpdate();

        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public ArrayList<Perfil> recuperarPorNome(String nome) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "SELECT * FROM perfil WHERE nome = ?";
        ArrayList<Perfil> perfis = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement(sql);
        ResultSet result;

        statement.setString(1, nome);

        result = statement.executeQuery();

        while (result.next()) {
            Perfil p = new Perfil();
            p.setIdPerfil(result.getInt(1)); //idPerfil
            p.setNome(result.getString(2)); //Nome
            p.setCidade(result.getString(3)); //Cidade
            p.setTamPropriedade(result.getDouble(4)); //Tam da Propriedade
            p.setAreaPecLeite(result.getDouble(5)); //Area Pec de Leite
            p.setProdLeiteDiario(result.getDouble(6)); // Prod Diaria de Leite
            p.setEmpPermanentes(result.getInt(7)); //Num de Empregados Permanentes
            p.setNumFamiliares(result.getInt(8)); // Num Familiares
            p.setRota((new RotaDAO()).recuperar(result.getInt(9))); // idRotaFK

            perfis.add(p);
        }

        result.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return perfis;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Perfil recuperar(int id) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        Perfil perfil = null;

        String sql = "SELECT * FROM perfil WHERE idPerfil = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            perfil = new Perfil();

            perfil.setIdPerfil(rs.getInt(1));
            perfil.setNome(rs.getString(2));
            perfil.setCidade(rs.getString(3));
            perfil.setTamPropriedade(rs.getDouble(4));
            perfil.setAreaPecLeite(rs.getDouble(5)); //Area Pec de Leite
            perfil.setProdLeiteDiario(rs.getDouble(6)); // Prod Diaria de Leite
            perfil.setEmpPermanentes(rs.getInt(7)); //Num de Empregados Permanentes
            perfil.setNumFamiliares(rs.getInt(8)); // Num Familiares
            perfil.setRota((new RotaDAO()).recuperar(rs.getInt(9))); //idRotaFK

        }
        statement.close();
        rs.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return perfil;
    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<Perfil> recuperarTodos() throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "SELECT * FROM perfil";
        ArrayList<Perfil> perfis = new ArrayList<>();

        Statement statement = this.connection.createStatement();
        ResultSet result;
        result = statement.executeQuery(sql);

        while (result.next()) {
            Perfil p = new Perfil();
            p.setIdPerfil(result.getInt(1)); //idPerfil
            p.setNome(result.getString(2)); //Nome
            p.setCidade(result.getString(3)); //Cidade
            p.setTamPropriedade(result.getDouble(4)); //Tam da Propriedade
            p.setAreaPecLeite(result.getDouble(5)); //Area Pec de Leite
            p.setProdLeiteDiario(result.getDouble(6)); // Prod Diaria de Leite
            p.setEmpPermanentes(result.getInt(7)); //Num de Empregados Permanentes
            p.setNumFamiliares(result.getInt(8)); // Num Familiares
            p.setRota((new RotaDAO()).recuperar(result.getInt(9))); // idRotaFK

            perfis.add(p);
        }

        result.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }

        return perfis;
    }

    /**
     *
     * @param idPerfil
     * @throws SQLException
     */
    @Override
    public void remover(int idPerfil) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }

        String sql = "DELETE FROM perfil WHERE idPerfil=?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idPerfil);
        statement.executeUpdate();

        statement.close();

        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
    }

    public ArrayList<Perfil> recuperarPorRota(Usuario usuario) throws SQLException {
        if (isConnectionOwner()) {
            this.connection = DBConexao.openConnection();
        }
        ArrayList<Perfil> perfis = null;

        String sql = "SELECT * FROM perfil WHERE rota = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, usuario.getRota().getId());

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Perfil perfil = new Perfil();

            perfil.setIdPerfil(rs.getInt("idPerfil")); //idPerfil
            perfil.setNome(rs.getString("nome")); //Nome
            perfil.setCidade(rs.getString("cidade")); //Cidade
            perfil.setTamPropriedade(rs.getDouble("tamPropriedade")); //Tam da Propriedade
            perfil.setAreaPecLeite(rs.getDouble("areaPecLeite")); //Area Pec de Leite
            perfil.setProdLeiteDiario(rs.getDouble("prodLeiteDiario")); // Prod Diaria de Leite
            perfil.setEmpPermanentes(rs.getInt("empPermanentes")); //Num de Empregados Permanentes
            perfil.setNumFamiliares(rs.getInt("numFamiliares")); // Num Familiares
            perfil.setRota((new RotaDAO()).recuperar(rs.getInt("idRotaFK"))); // rota

            perfis.add(perfil);
        }
        rs.close();
        statement.close();
        if (isConnectionOwner()) {
            DBConexao.closeConnection(this.connection);
        }
        return perfis;
    }
}
