/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import java.sql.SQLException;
import modelo.dao.UsuarioDAO;

/**
 *
 * @author Alexandre
 */
public class main {
    
    public static void main(String[] args)throws ClassNotFoundException, SQLException {
	
        UsuarioDAO u = new UsuarioDAO();
	Usuario usuario = new Usuario("teste", "12345", 1);
	
        u.inserir(usuario);
        

    }
    
}
