/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import java.io.Serializable;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioTerras implements Serializable {
    
    private int id;   
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
    }

    public InventarioTerras(String especificacao, double areaArrendadaInicio, double areaPropriaInicio, double areaArrendadaFinal, 
            double areaPropriaFinal, double valorTerraNuaPropria, int vidaUtil, double custoFormacaoHectare, Perfil perfil) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
