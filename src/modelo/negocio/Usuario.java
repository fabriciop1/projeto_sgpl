/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import flex.db.DatabaseObject;
import flex.db.GenericDAO;
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
    private Rota rota;
    
    private static GenericDAO<Rota> rotaDAO = new GenericDAO<>(Rota.class);
    
    public Usuario(){
       super("usuario", "idUsuario");
    }

    public Usuario(String login, String senha, Rota rota) {
        super("usuario", "idUsuario");
        
        this.login = login;
        this.senha = senha;
        this.rota = rota;
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

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    @Override
    public Map<String, Object> getObjectTableData() {
        
        HashMap<String,Object> map = new HashMap<>();
        
        map.put("login", login);
        map.put("senha", senha);
        map.put("idRotaFK", rota);
        
        return map;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
            login = Cast.toString(data.get("login"));
            senha = Cast.toString(data.get("senha"));
            rota = rotaDAO.retrieve(Cast.toInt(data.get("idRotaFK")));
    }  
}
