/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import flex.db.GenericDAO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.dao.UsuarioDAO;
import modelo.negocio.Usuario;

/**
 *
 * @author Alexandre Jorge
 */
public class ControleLogin {
    
    private Usuario usuario;
    private GenericDAO<Usuario> usuarioDAO;
    
    private ControleLogin() {}
    
    private static class UsuarioLogadoHolder { 
        private final static ControleLogin instance = new ControleLogin();
    }

    public static ControleLogin getInstance() {
            return UsuarioLogadoHolder.instance;
    }
    
    public boolean fazerLogin(String login, char[] senha){
        
        Usuario atual = new Usuario();
        
        usuarioDAO = new GenericDAO<>(Usuario.class);
        
        atual = usuarioDAO.retrieveByColumn("login", login).get(0);
        
        if(atual != null){
            
            if(verificaSenha(atual.getSenha(), senha)){
                getInstance().setUsuario(atual);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Senha incorreta", null, JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuário incorreto", null, JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public boolean verificaSenha(String senhaStr, char[] senhaChar){
        
        char[] temp = senhaStr.toCharArray();
        
        if(senhaStr.length() == senhaChar.length){
            for(int i = 0; i < senhaChar.length; i++){
                if(temp[i] != senhaChar[i]){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean fazerLogout(){
        getInstance().setUsuario(null);
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }    
}
