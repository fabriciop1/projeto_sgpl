/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

/**
 *
 * @author Fabricio
 */
public class Rota {
    private int id;
    private String rota;

    public Rota() {
        
    }
    
    public Rota(String rota) {
        this.rota = rota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }
    
    
    
}
