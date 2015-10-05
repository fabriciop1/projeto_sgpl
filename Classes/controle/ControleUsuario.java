/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.dao.UsuarioDAO;
import modelo.negocio.Usuario;

/**
 *
 * @author Alexandre
 */
@ManagedBean(name="controleUsuario")
@SessionScoped
public class ControleUsuario {
    
    private String login;
    private String senha;
    private Usuario usuario;
    private UsuarioDAO usuarioDao;
    
    public ControleUsuario(){
        usuarioDao = new UsuarioDAO();
    }
    
    public String entrar(){
        usuario = new Usuario();
        usuario = usuarioDao.buscarPorLogin(login);
        
        System.out.println(usuario.getLogin());
        addMessage("Entrando...");
        
        if(usuario == null){
            addMessage("Usuário inválido");
            return "";
        } else {
            if( !(usuario.getSenha().equals(senha)) ){
                addMessage("Senha inválida");
                return "";
            }
            
            
        }
        
        return "/VisualizarPerfil.xhtml";
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    //Método para adicionar uma mensagem ao primefaces e mostrá-la na tela
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
