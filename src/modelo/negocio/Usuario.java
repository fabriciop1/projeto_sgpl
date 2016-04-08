/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Usuario implements Serializable {
    
    private int idUsuario;
    private String login;
    private String senha;
    private Rota rota;
    
    public Usuario(){
       
    }

    public Usuario(String login, String senha, Rota rota) {
        this.login = login;
        this.senha = senha;
        this.rota = rota;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }
    
    
    
}
