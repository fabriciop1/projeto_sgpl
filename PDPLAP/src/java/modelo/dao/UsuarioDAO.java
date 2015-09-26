/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package modelo.dao;

import java.sql.SQLException;
import modelo.negocio.Usuario;

/**
 *
 * @author usuario
 */
public class UsuarioDAO extends JDBconexao{
    
    public void inserir(Usuario usuario) {
	try{
		setConfirmacao(getConexao().createStatement());

		String sql = "INSERT INTO Usuario(login, senha, tipo) " +
					 "VALUES("+ usuario.getLogin()    +","
                                                  + usuario.getSenha()    +","
                                                  + usuario.getTipo()     +");";

		getConfirmacao().executeUpdate(sql);
		getConexao().close();
		System.out.println("Inserido com sucesso!");
	}catch(SQLException e) {
		System.out.println("Erro na conexão.");
	}
    }
    
    public void atualizar(Usuario usuario) {
        try{
		setConfirmacao(getConexao().createStatement());

		String sql = "UPDATE INTO Usuario " +
                             "SET login = " + usuario.getLogin() + ", "
                            +  "senha = " + usuario.getSenha() + ", "
                            +  "tipo = " + usuario.getTipo() + ";";

		getConfirmacao().executeUpdate(sql);
		getConexao().close();
		System.out.println("Atualizado com sucesso!");
	}catch(SQLException e) {
		System.out.println("Erro na conexão.");
	}
    }

    public void deletar(Usuario usuario) {
	try {
		setConfirmacao(getConexao().createStatement());
                        
		String sql = "DELETE FROM Usuario WHERE idUsuario = " + usuario.getId() + ";";

		getConfirmacao().execute(sql);

		System.out.println("Deletado com sucesso!");
	}catch(SQLException e) {
		System.out.println("Erro na conexão.");
	}
    }
    
}
