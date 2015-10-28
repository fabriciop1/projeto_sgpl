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
import java.util.List;
import modelo.dao.UsuarioPerfilDAO;
import modelo.negocio.Perfil;
import modelo.negocio.Usuario;

public class ControlePerfil implements Serializable {
     
    private List<Perfil> perfis;
    private Perfil perfilSelecionado;
    private Usuario usuario;
    
    private UsuarioPerfilDAO usuarioPerfilDao;
 
    public ControlePerfil() {

    }
    
    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public Perfil getPerfilSelecionado() {
        return perfilSelecionado;
    }

    public void setPerfilSelecionado(Perfil perfilSelecionado) {
        this.perfilSelecionado = perfilSelecionado;
    }
    
    public UsuarioPerfilDAO getUsuarioPerfilDao() {
        return usuarioPerfilDao;
    }

    public void setUsuarioPerfilDao(UsuarioPerfilDAO usuarioPerfilDao) {
        this.usuarioPerfilDao = usuarioPerfilDao;
    }
    
}
