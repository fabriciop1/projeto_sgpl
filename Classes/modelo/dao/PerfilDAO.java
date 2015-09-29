/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.negocio.Perfil;

/**
 *
 * @author usuario
 */
public class PerfilDAO implements InterfaceDAO<Perfil>{
 
    Connection connection; 
    
    public PerfilDAO(){
        this.connection = DBConexao.openConnection();
    }
    
    @Override
    public void cadastrar(Perfil novoPerfil){
        
        String sql = "INSERT INTO perfil " + 
                "(nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, precoLeite, empPermanentes, numFamiliares) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, novoPerfil.getNome());
            statement.setString(2, novoPerfil.getCidade());
            statement.setFloat(3, novoPerfil.getTamPropriedade());
            statement.setFloat(4, novoPerfil.getAreaPecLeite());
            statement.setFloat(5, novoPerfil.getProdLeiteDiario());
            statement.setFloat(6, novoPerfil.getPrecoLeite());
            statement.setInt(7, novoPerfil.getEmpPermanentes());
            statement.setInt(8, novoPerfil.getNumFamiliares());
            //statement.setInt(9, novoPerfil.getUsuario().getIdUsuario());
       
            statement.execute();
            statement.close();
        } catch(SQLException e){
            System.out.println("Falha ao adicionar novo perfil. " +  e.getMessage());
        } finally {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                System.out.println("Falha ao encerrar conexão com banco de dados." + ex.getMessage());
            }
        }
    } 
    
    @Override
    public boolean atualizar(){
        return true;
    }
    
    @Override
    public Perfil buscar(String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remover() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
