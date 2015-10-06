/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

/**
 *
 * @author Alexandre
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import modelo.dao.UsuarioPerfilDAO;
import modelo.negocio.Perfil;
import modelo.negocio.Usuario;
 
@ManagedBean(name="controlePerfil")
@ViewScoped
public class ControlePerfil implements Serializable {
     
    public List<Perfil> perfis;
    public Usuario usuario;
    
    @ManagedProperty("#{usuarioPerfilDAO}")
    private UsuarioPerfilDAO usuarioPerfilDao;
 
    public ControlePerfil() {
        
        perfis = new ArrayList<>();
        
        usuario = new Usuario();
        usuarioPerfilDao = new UsuarioPerfilDAO();
        
        usuario = (Usuario) ControleLogin.getInstance().getSession().getAttribute("usuario");
        
        //ControleLogin controleLogin = ControleLogin.getInstance();
        //usuario = controleLogin.getUsuario();
        System.out.println(usuario.getLogin());
        
        perfis = usuarioPerfilDao.buscarPerfisPorUsuario(usuario.getIdUsuario());
        System.out.println(perfis.get(0).getNome());
        
    }
     
    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public UsuarioPerfilDAO getUsuarioPerfilDao() {
        return usuarioPerfilDao;
    }

    public void setUsuarioPerfilDao(UsuarioPerfilDAO usuarioPerfilDao) {
        this.usuarioPerfilDao = usuarioPerfilDao;
    }
    
    
    
}
