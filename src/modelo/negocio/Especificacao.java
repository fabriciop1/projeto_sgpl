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
 * @author Usuário
 */
public class Especificacao extends DatabaseObject implements Serializable {
    
    private String especificacao;
    
    public Especificacao() {
        super("dem_especificacao", "idDEM_especificacao");
    }
    
    public Especificacao(String especificacao) {
        super("dem_especificacao", "idDEM_especificacao");
        
        this.especificacao = especificacao;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }
    
    @Override
    public Map<String, Object> getObjectTableData() {
        HashMap<String,Object> m = new HashMap<>();
        
        m.put("especificacao", especificacao);
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        especificacao = Cast.toString(data.get("especificacao"));
    }
    
    
}
