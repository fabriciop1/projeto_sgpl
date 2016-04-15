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
 * @author Jefferson Sales
 */
public class InventarioTerras extends DatabaseObject implements Serializable {
    
    private String especificacao;
    private double areaArrendadaInicio;
    private double areaPropriaInicio;
    private double areaArrendadaFinal;
    private double areaPropriaFinal;
    private double valorTerraNuaPropria;
    private int vidaUtil;
    private double custoFormacaoHectare;
    private Perfil perfil;
    
    public InventarioTerras() {
        super("inventario_terras","idInventarioTerras");
    }

    public InventarioTerras(String especificacao, double areaArrendadaInicio, double areaPropriaInicio, double areaArrendadaFinal, 
            double areaPropriaFinal, double valorTerraNuaPropria, int vidaUtil, double custoFormacaoHectare, Perfil perfil) {
        super("inventario_terras","idInventarioTerras");
        
        this.especificacao = especificacao;
        this.areaArrendadaInicio = areaArrendadaInicio;
        this.areaPropriaInicio = areaPropriaInicio;
        this.areaArrendadaFinal = areaArrendadaFinal;
        this.areaPropriaFinal = areaPropriaFinal;
        this.valorTerraNuaPropria = valorTerraNuaPropria;
        this.vidaUtil = vidaUtil;
        this.custoFormacaoHectare = custoFormacaoHectare;
        this.perfil = perfil;
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

    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public double getCustoFormacaoHectare() {
        return custoFormacaoHectare;
    }

    public void setCustoFormacaoHectare(double custoFormacaoHectare) {
        this.custoFormacaoHectare = custoFormacaoHectare;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
        m.put("vidaUtil", vidaUtil);
        m.put("custoFormacaoHectare", custoFormacaoHectare);
        m.put("idPerfilFK", perfil.getId());
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        try {
            especificacao = Cast.toString(data.get("especificacao"));
            areaArrendadaInicio = Cast.toDouble(data.get("areaArrendadaInicio"));
            areaPropriaInicio = Cast.toDouble(data.get("areaPropriaInicio"));
            areaArrendadaFinal = Cast.toDouble(data.get("areaArrendadaFinal"));
            areaPropriaFinal = Cast.toDouble(data.get("areaPropriaFinal"));
            valorTerraNuaPropria = Cast.toDouble(data.get("valorTerraNuaPropria"));
            vidaUtil = Cast.toInt(data.get("vidaUtil"));
            custoFormacaoHectare = Cast.toDouble(data.get("custoFormacaoHectare"));
            perfil = new GenericDAO<>(Perfil.class).retrieve( Cast.toInt(data.get("idPerfilFK")) );
            
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(InventarioTerras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
