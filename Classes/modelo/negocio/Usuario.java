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
    
    private int codigo;
    private String login;
    private String senha;
    private int tipo;
    
    public Usuario(){
    }

    public Usuario(int codigo, String login, String senha, int tipo) {
        this.codigo = codigo;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
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
