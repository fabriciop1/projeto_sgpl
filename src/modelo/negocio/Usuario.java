/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import flex.db.DatabaseObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import util.Cast;

/**
 *
 * @author usuario
 */
public class Usuario extends DatabaseObject implements Serializable {
    
    private String login;
    private String senha;
    private int tipoUsuario; // 1 - Adm    2 - Usuario comum    3 - Usuario com restrição
    
    public Usuario(){
       super("usuario", "idUsuario");
    }

    public Usuario(String login, String senha, int tipoUsuario) {
        super("usuario", "idUsuario");
        
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }


    @Override
    public Map<String, Object> getObjectTableData() {
        
        HashMap<String,Object> map = new HashMap<>();
        
        map.put("login", login);
        map.put("senha", senha);
        map.put("tipoUsuario", tipoUsuario);
        
        return map;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
            login = Cast.toString(data.get("login"));
            senha = Cast.toString(data.get("senha"));
            tipoUsuario = Cast.toInt(data.get("tipoUsuario"));
    }  
}
