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
    private int tipo;
    
    public Usuario(){
    }

    public Usuario(String login, String senha, int tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
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

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
    
    public int getTipo() {
        return tipo;
    }
    
}
