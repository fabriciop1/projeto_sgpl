/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.dao.UsuarioDAO;
import modelo.negocio.Usuario;

/**
 *
 * @author Jefferson Sales
 */
@ManagedBean(name="controleLogin")
@SessionScoped
public class ControleLogin {
    
    private Usuario usuario;
    private boolean logado;

    private static ControleLogin self = new ControleLogin();
    
    public static ControleLogin getInstance(){ 
        return self;
    }
    
    public ControleLogin() {
        
        logado = false;
        usuario = new Usuario();
    }    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
    
    public void fazerLogin(){
        
        UsuarioDAO dao = new UsuarioDAO();
        Usuario atual = dao.buscarPorLogin(usuario.getLogin());
        
        if(atual != null){
            
            if(usuario.getSenha().equals(atual.getSenha())){
                logado = true;
                usuario = atual;
                
                try { 
                    
                    FacesContext.getCurrentInstance().getExternalContext().redirect("VisualizarPerfil.xhtml");
                    
                } catch (IOException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Erro", "Ocorreu um erro ao mudar de pagina."));  
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Erro", "A senha está incorreta. Tente novamente."));  
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Erro", "Usuario não encontrado."));  
        }
    }
    
    public void fazerLogout(){
        logado = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();  
    }
}
