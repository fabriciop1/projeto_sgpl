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
import modelo.negocio.DadosEconMensais;

/**
 *
 * @author Fabricio
 */
public class DadosEconMensaisDAO {
    
    private Connection connection;
    
    public void cadastrar(DadosEconMensais dados) throws SQLException {
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO dados_economicos_mensais "
                + "(mes, ano, campo, quantidde, valorUnitario, tipoCampo, idPerfilFK) "
                + "VALUES (?,?,?,?,?,?,?)";
        
        PreparedStatement st = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, dados.getMes());
        st.setInt(2, dados.getAno());
        st.setString(3, dados.getCampo());
        st.setInt(4, dados.getQuantidade());
        st.setDouble(5, dados.getValorUnitario());
        st.setInt(6, dados.getTipoCampo());
        st.setInt(7, dados.getPerfil().getIdPerfil());
        
        st.executeUpdate();
            
        ResultSet keys = st.getGeneratedKeys();
            
        if (keys.next()) {
            dados.setId(keys.getInt(1));
        }
            
        keys.close();
        st.close();
            
        DBConexao.closeConnection(this.connection);
    }
    
    public void remover(int id) throws SQLException {
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM dados_economicos_mensais WHERE idDEM = ?";
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);
        
        statement.executeUpdate();
        statement.close();
            
        DBConexao.closeConnection(this.connection);   
    }
    
    public void atualizar(DadosEconMensais dados) throws SQLException {
        this.connection = DBConexao.openConnection();
        
        String sql = "UPDATE dados_economicos_mensais SET mes=?, ano=?, campo=?, quantidade=?, valorUnitario=?, tipoCampo=? "
                + "WHERE idDEM=?";
        
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, dados.getMes());
        st.setInt(2, dados.getAno());
        st.setString(3, dados.getCampo());
        st.setInt(4, dados.getQuantidade());
        st.setDouble(5, dados.getValorUnitario());
        st.setInt(6, dados.getTipoCampo());
        st.setInt(7, dados.getId());
        
        st.executeUpdate();
        st.close();
            
        DBConexao.closeConnection(this.connection);
    }
    
    public DadosEconMensais recuperar(int id) throws SQLException {
        String sql = "SELECT * FROM dados_economicos_mensais WHERE idDEM=?";
        
        DadosEconMensais dados = null;
        
        this.connection = DBConexao.openConnection();
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        
        if(res.next()){
            dados = new DadosEconMensais();
            
            dados.setAno(res.getInt("ano"));
            dados.setMes(res.getInt("mes"));
            dados.setCampo(res.getString("campo"));
            dados.setId(res.getInt("idDEM"));
            dados.setQuantidade(res.getInt("quantidade"));
            dados.setTipoCampo(res.getInt("tipoCampo"));
            dados.setValorUnitario(res.getDouble("valorUnitario"));
            dados.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
        }
        res.close();
        statement.close();
        DBConexao.closeConnection(connection);
        return dados;
    }
    
    public ArrayList<DadosEconMensais> recuperarPorPerfil(int idPerfil) throws SQLException {
        String sql = "SELECT * FROM dados_economicos_mensais WHERE idDEM = ?";
        
        ArrayList<DadosEconMensais> dados = new ArrayList<>();
        
        this.connection = DBConexao.openConnection();
        
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setInt(1, idPerfil);
        ResultSet res = statement.executeQuery();
        
        while(res.next()){
            DadosEconMensais dado = new DadosEconMensais();
            
            dado.setAno(res.getInt("ano"));
            dado.setCampo(res.getString("campo"));
            dado.setId(res.getInt("idDEM"));
            dado.setMes(res.getInt("mes"));
            dado.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
            dado.setQuantidade(res.getInt("quantidade"));
            dado.setTipoCampo(res.getInt("tipoCampo"));
            dado.setValorUnitario(res.getDouble("valorUnitario"));
            
            dados.add(dado);
        }
        res.close();
        statement.close();
        DBConexao.closeConnection(this.connection);
        
        return dados;
        
    }
    
    public ArrayList<DadosEconMensais> recuperarTodos() throws SQLException {
        String sql = "SELECT * FROM dados_economicos_mensais";
        
        ArrayList<DadosEconMensais> dados = new ArrayList<>();
        
        DadosEconMensais dado;
        
        this.connection = DBConexao.openConnection();
        PreparedStatement statement = this.connection.prepareStatement(sql);
        
        ResultSet res = statement.executeQuery();
        
        while(res.next()) {
            dado = new DadosEconMensais();
           
            dado.setAno(res.getInt("ano"));
            dado.setCampo(res.getString("campo"));
            dado.setId(res.getInt("idDEM"));
            dado.setMes(res.getInt("mes"));
            dado.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
            dado.setQuantidade(res.getInt("quantidade"));
            dado.setTipoCampo(res.getInt("tipoCampo"));
            dado.setValorUnitario(res.getDouble("valorUnitario"));
            
            dados.add(dado);
            
        }
        
        res.close();
        statement.close();
        
        DBConexao.closeConnection(this.connection);
        return dados;
    }
            
    public ArrayList<DadosEconMensais> recuperarPorPeriodo(int anoInicio, int mesInicio, int anoFim, int mesFim) throws SQLException {
        String sql = "SELECT * FROM dados_economicos_mensais WHERE (ano >= ? AND ano <= ?) AND (mes >= ? AND mes <= ?)";
         ArrayList<DadosEconMensais> dados = new ArrayList<>();
        
        DadosEconMensais dado;
        
        this.connection = DBConexao.openConnection();
        PreparedStatement statement = this.connection.prepareStatement(sql);
         statement.setInt(1, anoInicio);
         statement.setInt(2, anoFim);
         statement.setInt(3, mesInicio);
         statement.setInt(4, mesFim);
        
        ResultSet res = statement.executeQuery();
        
        while(res.next()) {
            dado = new DadosEconMensais();
           
            dado.setAno(res.getInt("ano"));
            dado.setCampo(res.getString("campo"));
            dado.setId(res.getInt("idDEM"));
            dado.setMes(res.getInt("mes"));
            dado.setPerfil((new PerfilDAO()).recuperar(res.getInt("idPerfilFK")));
            dado.setQuantidade(res.getInt("quantidade"));
            dado.setTipoCampo(res.getInt("tipoCampo"));
            dado.setValorUnitario(res.getDouble("valorUnitario"));
            
            dados.add(dado);
        }
        res.close();
        statement.close();
        
        DBConexao.closeConnection(this.connection);
        return dados;    
    }
    
}
