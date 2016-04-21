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
import modelo.negocio.Perfil;

public class ControlePerfil implements Serializable {
     
    private List<Perfil> perfis;
    private Perfil perfilSelecionado;
 
    public ControlePerfil() {}
    
    private static class PerfilSelecionadoHolder { 
        private final static ControlePerfil instance = new ControlePerfil();
    }

    public static ControlePerfil getInstance() {
            return PerfilSelecionadoHolder.instance;
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
    
}
