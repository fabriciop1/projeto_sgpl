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
import modelo.negocio.Perfil;

public class ControlePerfil implements Serializable {
     
    private int ano;
    private Perfil perfilSelecionado;
 
    public ControlePerfil() {}
    
    private static class PerfilSelecionadoHolder { 
        private static final ControlePerfil instance = new ControlePerfil();
    }

    public static ControlePerfil getInstance() {
            return PerfilSelecionadoHolder.instance;
    }

    public Perfil getPerfilSelecionado() {
        return perfilSelecionado;
    }

    public void setPerfilSelecionado(Perfil perfilSelecionado) {
        this.perfilSelecionado = perfilSelecionado;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
}
