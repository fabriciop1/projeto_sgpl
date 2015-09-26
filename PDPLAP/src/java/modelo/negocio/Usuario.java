/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

/**
 *
 * @author usuario
 */
public class Usuario {
    
    private int id;
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

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
    
    public int getId() {
        return id;
    }
    
    
}
