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
 * @author Fabricio
 */
public class DadosTecMensais extends DatabaseObject implements Serializable {

    private int mes;
    private int ano;
    private int dado;
    private Indicador indicador; // indicador associado a cada dado tecnico mensal
    private Perfil perfil;
    
    public DadosTecMensais() {
        super("dados_tecnicos_mensais", "idDTM");
    }

    public DadosTecMensais(int mes, int ano, int dado, Indicador indicador, Perfil perfil) {
        super("dados_tecnicos_mensais", "idDTM");
        
        this.mes = mes;
        this.ano = ano;
        this.dado = dado;
        this.indicador = indicador;
        this.perfil = perfil;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDado() {
        return dado;
    }

    public void setDado(int dado) {
        this.dado = dado;
    }

    public Indicador getIndicador() {
        return indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
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
        
        m.put("mes", mes);
        m.put("ano", ano);
        m.put("dado", dado);
        m.put("idDTM_indicadorFK", indicador.getId());
        m.put("idPerfilFK", perfil.getId());
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        mes = Cast.toInt(data.get("mes"));
        ano = Cast.toInt(data.get("ano"));
        dado = Cast.toInt(data.get("dado"));
        indicador = new GenericDAO<>(Indicador.class).retrieve(Cast.toInt(data.get("idDTM_indicadorFK")));
        perfil =  new GenericDAO<>(Perfil.class).retrieve( Cast.toInt(data.get("idPerfilFK")));
    }
    
}
