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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public boolean remover(){
        //Lembrar de modificar a InterfaceDAO pra o método abaixo depois vvvvvvvvvvvvv        
        return false;
    }
    
    public boolean remover(int idPerfil) {
        
        String sql = "DELETE FROM perfil WHERE idPerfil=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "" + idPerfil);
            statement.executeUpdate();
            statement.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(PerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public ArrayList<Perfil> recuperarTodos(){
        
        String sql = "SELECT * FROM perfil";
        ArrayList<Perfil> perfis = new ArrayList<>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            //Perfil perfil = new Perfil(666, "Adriana", "Garanhuns", 2.3f, 4.5f, 6.7f, 8.9f, 20, 3);
            
            while(result.next()){
                
                Perfil p = new Perfil(
                        result.getInt(1), //idPerfil
                        result.getString(2), //Nome
                        result.getString(3), //Cidade
                        result.getFloat(4), //Tam da Propriedade
                        result.getFloat(5), //Area Pec de Leite
                        result.getFloat(6), // Prod Diaria de Leite
                        result.getFloat(7), //Preço do Leite
                        result.getInt(8), //Num de Empregados Permanentes
                        result.getInt(9)); //Num de Familiares
                
                perfis.add(p);
            }
            
            statement.close();
            result.close();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(PerfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return perfis;
    }
}
