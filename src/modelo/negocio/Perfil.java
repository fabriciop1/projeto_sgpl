/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import flex.db.DatabaseObject;
import flex.db.GenericDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Cast;

/**
 *
 * @author usuario
 */
public class Perfil extends DatabaseObject implements Serializable {
    
    private String nome;
    private String cidade;
    private double tamPropriedade;
    private double areaPecLeite;
    private double prodLeiteDiario;
    private int empPermanentes;
    private int numFamiliares;
    private Rota rota;
    
    public Perfil() {
        super("perfil", "idPerfil");
    }
   
    public Perfil(String nome, String cidade, double tamPropriedade, double areaPecLeite, double prodLeiteDiario, int empPermanentes, int numFamiliares, Rota rota) {
        super("perfil","idPerfil");
        
        this.nome = nome;
        this.cidade = cidade;
        this.tamPropriedade = tamPropriedade;
        this.areaPecLeite = areaPecLeite;
        this.prodLeiteDiario = prodLeiteDiario;
        this.empPermanentes = empPermanentes;
        this.numFamiliares = numFamiliares;
        this.rota = rota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getTamPropriedade() {
        return tamPropriedade;
    }

    public void setTamPropriedade(double tamPropriedade) {
        this.tamPropriedade = tamPropriedade;
    }

    public double getAreaPecLeite() {
        return areaPecLeite;
    }

    public void setAreaPecLeite(double areaPecLeite) {
        this.areaPecLeite = areaPecLeite;
    }

    public double getProdLeiteDiario() {
        return prodLeiteDiario;
    }

    public void setProdLeiteDiario(double prodLeiteDiario) {
        this.prodLeiteDiario = prodLeiteDiario;
    }

    public int getEmpPermanentes() {
        return empPermanentes;
    }

    public void setEmpPermanentes(int empPermanentes) {
        this.empPermanentes = empPermanentes;
    }

    public int getNumFamiliares() {
        return numFamiliares;
    }

    public void setNumFamiliares(int numFamiliares) {
        this.numFamiliares = numFamiliares;
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
        
        map.put("nome", nome);
        map.put("cidade", cidade);
        map.put("tamPropriedade", tamPropriedade);
        map.put("areaPecLeite", areaPecLeite);
        map.put("prodLeiteDiario", prodLeiteDiario);
        map.put("empPermanentes", empPermanentes);
        map.put("numFamiliares", numFamiliares);
        map.put("idRotaFK", rota.getId());
        
        return map;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        
            nome = Cast.toString(data.get("nome"));
            cidade = Cast.toString(data.get("cidade"));
            tamPropriedade = Cast.toDouble(data.get("tamPropriedade"));
            areaPecLeite = Cast.toDouble(data.get("areaPecLeite"));
            prodLeiteDiario = Cast.toDouble(data.get("prodLeiteDiario"));
            empPermanentes = Cast.toInt(data.get("empPermanentes"));
            numFamiliares = Cast.toInt(data.get("numFamiliares"));
            rota = new GenericDAO<>(Rota.class).retrieve( Cast.toInt(data.get("idRotaFK")) );
    }
    
    

}
