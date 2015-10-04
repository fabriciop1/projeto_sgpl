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

/**
 *
 * @author usuario
 */
public class PerfilDAO {
 
    Connection connection; 
    
    public PerfilDAO(){
       
    }
    
    public boolean cadastrar(Perfil novoPerfil){
        
        this.connection = DBConexao.openConnection();
        
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
       
            statement.execute();
            statement.close();
        } catch(SQLException e){
            System.out.println("Falha ao adicionar novo perfil. " +  e.getMessage());
            return false;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return true;
    } 
    
    public boolean atualizar(Perfil perfil){
        this.connection = DBConexao.openConnection();
        
        String sql = "UPDATE perfil SET nome=?, cidade=?, tamPropriedade=?, areaPecLeite=?, prodLeiteDiario=?, precoLeite=?, "
                + "empPermanentes=?, numFamiliares=? WHERE idPerfil=?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, perfil.getNome());
            statement.setString(2, perfil.getCidade());
            statement.setFloat(3, perfil.getTamPropriedade());
            statement.setFloat(4, perfil.getAreaPecLeite());
            statement.setFloat(5, perfil.getProdLeiteDiario());
            statement.setFloat(6, perfil.getPrecoLeite());
            statement.setInt(7, perfil.getEmpPermanentes());
            statement.setInt(8, perfil.getNumFamiliares());
            statement.setInt(9, perfil.getIdPerfil());
            
            statement.close();
        } catch(SQLException ex) {
            System.out.println("Falha ao atualizar Perfil. " + ex.getMessage());
            return false;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return true;
    }
    
    public ArrayList<Perfil> buscar(String nome) {
        this.connection = DBConexao.openConnection();
        
        String sql = "SELECT * FROM perfil WHERE nome = ?";
        ArrayList<Perfil> perfis = new ArrayList<>();
            
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            ResultSet result;
            
            statement.setString(1, nome);
            
            result = statement.executeQuery();
                
            while(result.next()){
                Perfil p = new Perfil();
                p.setIdPerfil(result.getInt(1)); //idPerfil
                p.setNome(result.getString(2)); //Nome
                p.setCidade(result.getString(3)); //Cidade
                p.setTamPropriedade(result.getFloat(4)); //Tam da Propriedade
                p.setAreaPecLeite(result.getFloat(5)); //Area Pec de Leite
                p.setProdLeiteDiario(result.getFloat(6)); // Prod Diaria de Leite
                p.setPrecoLeite(result.getFloat(7)); //Preço do Leite
                p.setEmpPermanentes(result.getInt(8)); //Num de Empregados Permanentes
                p.setNumFamiliares(result.getInt(9)); // Num Familiares
                    
                perfis.add(p);
            }
            result.close();
           
        } catch (SQLException ex) {
            System.out.println("Falha ao recuperar perfis por nome." + ex.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return perfis;
    }
    
    public Perfil buscarPorId(int id) {
        this.connection = DBConexao.openConnection();
        ResultSet rs;
        Perfil perfil = new Perfil();
        
        String sql = "SELECT * FROM perfil WHERE idPerfil = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            rs = statement.executeQuery();
            if(rs.next()) {
                perfil.setIdPerfil(1);
                perfil.setNome(rs.getString(2));
                perfil.setCidade(rs.getString(3));
                perfil.setTamPropriedade(rs.getInt(4));
                perfil.setAreaPecLeite(rs.getFloat(5)); //Area Pec de Leite
                perfil.setProdLeiteDiario(rs.getFloat(6)); // Prod Diaria de Leite
                perfil.setPrecoLeite(rs.getFloat(7)); //Preço do Leite
                perfil.setEmpPermanentes(rs.getInt(8)); //Num de Empregados Permanentes
                perfil.setNumFamiliares(rs.getInt(9)); // Num Familiares
            }
            statement.close();
        } catch(SQLException ex) {
            System.out.println("Falha ao buscar Perfil por ID." + ex.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return perfil;
    }
    
    public boolean remover(int idPerfil) {
        this.connection = DBConexao.openConnection();
        
        String sql = "DELETE FROM perfil WHERE idPerfil=?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, idPerfil);
            statement.executeUpdate();
            
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Falha ao remover perfil." + ex.getMessage());
            return false;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return true;
    }
    
    public ArrayList<Perfil> buscarTodos() {
        this.connection = DBConexao.openConnection();
        
        String sql = "SELECT * FROM perfil";
        ArrayList<Perfil> perfis = new ArrayList<>();
            
        try (Statement statement = this.connection.createStatement()) {
            ResultSet result;
            result = statement.executeQuery(sql);
                
            while(result.next()){
                Perfil p = new Perfil();
                p.setIdPerfil(result.getInt(1)); //idPerfil
                p.setNome(result.getString(2)); //Nome
                p.setCidade(result.getString(3)); //Cidade
                p.setTamPropriedade(result.getFloat(4)); //Tam da Propriedade
                p.setAreaPecLeite(result.getFloat(5)); //Area Pec de Leite
                p.setProdLeiteDiario(result.getFloat(6)); // Prod Diaria de Leite
                p.setPrecoLeite(result.getFloat(7)); //Preço do Leite
                p.setEmpPermanentes(result.getInt(8)); //Num de Empregados Permanentes
                p.setNumFamiliares(result.getInt(9)); // Num Familiares
                    
                perfis.add(p);
            }
            result.close();
           
        } catch (SQLException ex) {
            System.out.println("Falha ao recuperar todos os perfis." + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        
        return perfis;
    }
}
