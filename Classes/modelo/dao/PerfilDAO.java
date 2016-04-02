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


public class PerfilDAO {
 
    private Connection connection; 
    
    public PerfilDAO(){
    }
    
    public void cadastrar(Perfil perfil, boolean cadastrarInventarios)  throws SQLException {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "INSERT INTO perfil " + 
                "(nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, empPermanentes, numFamiliares) " +
                "VALUES (?,?,?,?,?,?,?)";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, perfil.getNome());
            statement.setString(2, perfil.getCidade());
            statement.setDouble(3, perfil.getTamPropriedade());
            statement.setDouble(4, perfil.getAreaPecLeite());
            statement.setDouble(5, perfil.getProdLeiteDiario());
            statement.setInt(6, perfil.getEmpPermanentes());
            statement.setInt(7, perfil.getNumFamiliares());
            
            statement.executeUpdate();
            
            ResultSet keys = statement.getGeneratedKeys();
            
            if(keys.next()){
                perfil.setIdPerfil(keys.getInt(1));
            }
            
            keys.close();
            statement.close();
            
        } catch(Exception e){
            System.out.println("Falha ao adicionar novo perfil. " +  e.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    } 
    
    public void atualizar(Perfil perfil) throws SQLException {
        
        this.connection = DBConexao.openConnection();
        
        String sql = "UPDATE perfil SET nome=?, cidade=?, tamPropriedade=?, areaPecLeite=?, prodLeiteDiario=?, "
                + "empPermanentes=?, numFamiliares=? WHERE idPerfil=?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            
            statement.setString(1, perfil.getNome());
            statement.setString(2, perfil.getCidade());
            statement.setDouble(3, perfil.getTamPropriedade());
            statement.setDouble(4, perfil.getAreaPecLeite());
            statement.setDouble(5, perfil.getProdLeiteDiario());
            statement.setInt(6, perfil.getEmpPermanentes());
            statement.setInt(7, perfil.getNumFamiliares());
            
            statement.setInt(8, perfil.getIdPerfil());
            
            statement.close();
        } catch(Exception ex) {
            System.out.println("Falha ao atualizar Perfil. " + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
    }
    
    public ArrayList<Perfil> buscarPorNome(String nome)  throws SQLException {
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
                p.setTamPropriedade(result.getDouble(4)); //Tam da Propriedade
                p.setAreaPecLeite(result.getDouble(5)); //Area Pec de Leite
                p.setProdLeiteDiario(result.getDouble(6)); // Prod Diaria de Leite
                p.setEmpPermanentes(result.getInt(7)); //Num de Empregados Permanentes
                p.setNumFamiliares(result.getInt(8)); // Num Familiares
                    
                perfis.add(p);
            }
            result.close();
           
        } catch (Exception ex) {
            System.out.println("Falha ao recuperar perfis por nome." + ex.getMessage());
            return null;
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return perfis;
    }
    
    public Perfil recuperar(int id) throws SQLException {
        this.connection = DBConexao.openConnection();
        ResultSet rs;
        Perfil perfil = null;
        
        String sql = "SELECT * FROM perfil WHERE idPerfil = ?";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            rs = statement.executeQuery();
            if(rs.next()) {
                perfil = new Perfil();
                
                perfil.setIdPerfil(rs.getInt(1));
                perfil.setNome(rs.getString(2));
                perfil.setCidade(rs.getString(3));
                perfil.setTamPropriedade(rs.getDouble(4));
                perfil.setAreaPecLeite(rs.getDouble(5)); //Area Pec de Leite
                perfil.setProdLeiteDiario(rs.getDouble(6)); // Prod Diaria de Leite
                perfil.setEmpPermanentes(rs.getInt(7)); //Num de Empregados Permanentes
                perfil.setNumFamiliares(rs.getInt(8)); // Num Familiares
                
               }
            statement.close();
        } catch(Exception ex) {
            System.out.println("Falha ao buscar Perfil por ID." + ex.getMessage());
        } finally {
            DBConexao.closeConnection(this.connection);
        }
        return perfil;
    }
    
    public ArrayList<Perfil> buscarTodos() throws SQLException  {
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
                p.setTamPropriedade(result.getDouble(4)); //Tam da Propriedade
                p.setAreaPecLeite(result.getDouble(5)); //Area Pec de Leite
                p.setProdLeiteDiario(result.getDouble(6)); // Prod Diaria de Leite
                p.setEmpPermanentes(result.getInt(7)); //Num de Empregados Permanentes
                p.setNumFamiliares(result.getInt(8)); // Num Familiares
                    
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
    
     public boolean remover(int idPerfil) throws SQLException {
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
}
