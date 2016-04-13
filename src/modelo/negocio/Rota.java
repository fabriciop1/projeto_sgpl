/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import flex.db.DatabaseObject;
import java.util.HashMap;
import java.util.Map;
import util.Cast;

/**
 *
 * @author Fabricio
 */
public class Rota extends DatabaseObject{
    
    private String rota;

    public Rota() {
        super("rota", "idRota");
    }
    
    public Rota(String rota) {
        super("rota", "idRota");
        
        this.rota = rota;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    @Override
    public Map<String, Object> getObjectTableData() {
        
        HashMap<String,Object> data = new HashMap<>();
        data.put("rota", rota);
        
        return data;
    }

    @Override
    public void setObjectData(Map<String, Object> objectData) {
        rota = Cast.toString(objectData.get("rota"));
    }
    
    
    
}
