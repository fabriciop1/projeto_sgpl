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
 * @author Fabricio
 */
public class Indicador extends DatabaseObject implements Serializable {
    
    private String indicador;
    
    public Indicador() {
        super("dtm_indicador", "idDTM_indicador");
    }
    
    public Indicador(String indicador) {
         super("dtm_indicador", "idDTM_indicador");
         this.indicador = indicador;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    @Override
    public Map<String, Object> getObjectTableData() {
        HashMap<String,Object> m = new HashMap<>();
        
        m.put("indicador", indicador);
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        indicador = Cast.toString(data.get("indicador"));
    }
}
