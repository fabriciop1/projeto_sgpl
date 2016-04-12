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
    
    private int id;
    private String rota;

    public Rota() {
        super("rota",0);
    }
    
    public Rota(String rota) {
        super("rota",0);
        
        this.rota = rota;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    @Override
    public Map<String, String> getObjectTableData() {
        
        HashMap<String,String> data = new HashMap<>();
        data.put("rota", Cast.toSQLString(rota));
        
        return data;
    }

    @Override
    public void setObjectData(Map<String, Object> objectData) {
        rota = Cast.toString(objectData.get("rota"));
    }
    
    
    
}
