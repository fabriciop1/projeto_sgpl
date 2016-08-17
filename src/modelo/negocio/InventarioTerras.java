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
 * @author Jefferson Sales
 */
public class InventarioTerras extends DatabaseObject implements Serializable {
    
    private String especificacao;
    private double areaArrendadaInicio;
    private double areaPropriaInicio;
    private double areaArrendadaFinal;
    private double areaPropriaFinal;
    private double valorTerraNuaPropria;
    private int ano;
    private int idPerfil;
    
    public InventarioTerras() {
        super("inventario_terras","idInventarioTerras");
    }

    public InventarioTerras(String especificacao, double areaArrendadaInicio, double areaPropriaInicio, double areaArrendadaFinal, 
            double areaPropriaFinal, double valorTerraNuaPropria, int ano, int idPerfil) {
        super("inventario_terras","idInventarioTerras");
        
        this.especificacao = especificacao;
        this.areaArrendadaInicio = areaArrendadaInicio;
        this.areaPropriaInicio = areaPropriaInicio;
        this.areaArrendadaFinal = areaArrendadaFinal;
        this.areaPropriaFinal = areaPropriaFinal;
        this.valorTerraNuaPropria = valorTerraNuaPropria;
        this.ano = ano;
        this.idPerfil = idPerfil;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public double getAreaArrendadaInicio() {
        return areaArrendadaInicio;
    }

    public void setAreaArrendadaInicio(double areaArrendadaInicio) {
        this.areaArrendadaInicio = areaArrendadaInicio;
    }

    public double getAreaPropriaInicio() {
        return areaPropriaInicio;
    }

    public void setAreaPropriaInicio(double areaPropriaInicio) {
        this.areaPropriaInicio = areaPropriaInicio;
    }

    public double getAreaArrendadaFinal() {
        return areaArrendadaFinal;
    }

    public void setAreaArrendadaFinal(double areaArrendadaFinal) {
        this.areaArrendadaFinal = areaArrendadaFinal;
    }

    public double getAreaPropriaFinal() {
        return areaPropriaFinal;
    }

    public void setAreaPropriaFinal(double areaPropriaFinal) {
        this.areaPropriaFinal = areaPropriaFinal;
    }

    public double getValorTerraNuaPropria() {
        return valorTerraNuaPropria;
    }

    public void setValorTerraNuaPropria(double valorTerraNuaPropria) {
        this.valorTerraNuaPropria = valorTerraNuaPropria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    } 

    @Override
    public Map<String, Object> getObjectTableData() {
        
        HashMap<String,Object> m = new HashMap<>();
        
        m.put("especificacao", especificacao);
        m.put("areaArrendadaInicio", areaArrendadaInicio);
        m.put("areaPropriaInicio", areaPropriaInicio);
        m.put("areaArrendadaFinal", areaArrendadaFinal);
        m.put("areaPropriaFinal", areaPropriaFinal);
        m.put("valorTerraNuaPropria", valorTerraNuaPropria);
        m.put("ano", ano);
        m.put("idPerfilFK", idPerfil);
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
            especificacao = Cast.toString(data.get("especificacao"));
            areaArrendadaInicio = Cast.toDouble(data.get("areaArrendadaInicio"));
            areaPropriaInicio = Cast.toDouble(data.get("areaPropriaInicio"));
            areaArrendadaFinal = Cast.toDouble(data.get("areaArrendadaFinal"));
            areaPropriaFinal = Cast.toDouble(data.get("areaPropriaFinal"));
            valorTerraNuaPropria = Cast.toDouble(data.get("valorTerraNuaPropria"));
            ano = Cast.toInt(data.get("ano"));
            idPerfil = Cast.toInt(data.get("idPerfilFK"));
    }
}
