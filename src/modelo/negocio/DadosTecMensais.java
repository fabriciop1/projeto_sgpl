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
    private double dado;
    private Indicador indicador; // indicador associado a cada dado tecnico mensal
    private int idPerfil;
    
    private static GenericDAO<Indicador> indDAO = new GenericDAO<>(Indicador.class);
    
    public DadosTecMensais() {
        super("dados_tecnicos_mensais", "idDTM");
    }

    public DadosTecMensais(int mes, int ano, double dado, Indicador indicador, int idPerfil) {
        super("dados_tecnicos_mensais", "idDTM");
        
        this.mes = mes;
        this.ano = ano;
        this.dado = dado;
        this.indicador = indicador;
        this.idPerfil = idPerfil;
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

    public double getDado() {
        return dado;
    }

    public void setDado(double dado) {
        this.dado = dado;
    }

    public Indicador getIndicador() {
        return indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
        
    @Override
    public Map<String, Object> getObjectTableData() {
        HashMap<String,Object> m = new HashMap<>();
        
        m.put("mes", mes);
        m.put("ano", ano);
        m.put("dado", dado);
        m.put("idDTM_indicadorFK", indicador.getId());
        m.put("idPerfilFK", idPerfil);
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        mes = Cast.toInt(data.get("mes"));
        ano = Cast.toInt(data.get("ano"));
        dado = Cast.toDouble(data.get("dado"));
        indicador = indDAO.retrieve(Cast.toInt(data.get("idDTM_indicadorFK")));
        idPerfil =  Cast.toInt(data.get("idPerfilFK"));
    }
    
}
