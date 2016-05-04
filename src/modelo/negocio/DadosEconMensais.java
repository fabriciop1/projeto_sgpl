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
public class DadosEconMensais extends DatabaseObject implements Serializable {
    
    private int mes;
    private int ano;
    private double quantidade;
    private double valorUnitario;
    private Especificacao especificacao; // especificacao associada à tabela DEM_especificacao
    private Perfil perfil;
    
    public DadosEconMensais() {
        super("dados_economicos_mensais", "idDEM");
    }

    public DadosEconMensais(int mes, int ano, double quantidade, double valorUnitario, Especificacao especificacao, Perfil perfil) {
        super("dados_economicos_mensais", "idDEM");
        
        this.mes = mes;
        this.ano = ano;
        this.especificacao = especificacao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
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

    public Especificacao getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(Especificacao especificacao) {
        this.especificacao = especificacao;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
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
        m.put("quantidade", quantidade);
        m.put("valorUnitario", valorUnitario);
        m.put("idDEM_especificacaoFK", especificacao.getId());
        m.put("idPErfilFK", perfil.getId());
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        mes = Cast.toInt(data.get("mes"));
        ano = Cast.toInt(data.get("ano"));
        quantidade = Cast.toDouble(data.get("quantidade"));
        valorUnitario = Cast.toDouble(data.get("valorUnitario"));
        especificacao = new GenericDAO<>(Especificacao.class).retrieve(Cast.toInt(data.get("idDEM_especificacaoFK")));
        perfil =  new GenericDAO<>(Perfil.class).retrieve( Cast.toInt(data.get("idPerfilFK")) );
    }
    
    
    
}
