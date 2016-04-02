/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import modelo.negocio.Perfil;

/**
 *
 * @author Alexandre Jorge
 */
public class ControleInventario {
    
    private Perfil perfil;
    
    public ControleInventario() {}

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
}
