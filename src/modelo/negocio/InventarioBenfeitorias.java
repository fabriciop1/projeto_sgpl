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
 * @author Jefferson Sales
 */
public class InventarioBenfeitorias extends DatabaseObject implements Serializable {
    
    private String especificacao;
    private String unidade;
    private double quantidade;
    private double valorUnitario;
    private int vidaUtil;
    private int idPerfil;
    
    private static GenericDAO<Perfil> perfilDAO = new GenericDAO<>(Perfil.class);
    
    public InventarioBenfeitorias() {
        super("inventario_benfeitorias", "idInventarioBenfeitorias");
    }

    public InventarioBenfeitorias(String especificacao, String unidade, double quantidade, double valorUnitario, int vidaUtil, int idPerfil) {
        super("inventario_benfeitorias", "idInventarioBenfeitorias");
        
        this.especificacao = especificacao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.vidaUtil = vidaUtil;
        this.idPerfil = idPerfil;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
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

    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    @Override
    public Map<String, Object> getObjectTableData() {
        
        HashMap<String,Object> m = new HashMap<>();
        
        m.put("especificacao", especificacao);
        m.put("unidade", unidade);
        m.put("quantidade", quantidade);
        m.put("valorUnitario", valorUnitario);
        m.put("vidaUtil", vidaUtil);
        m.put("idPerfilFK", idPerfil);
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
            especificacao = Cast.toString(data.get("especificacao"));
            unidade = Cast.toString(data.get("unidade"));
            quantidade = Cast.toDouble(data.get("quantidade"));
            valorUnitario = Cast.toDouble(data.get("valorUnitario"));
            vidaUtil = Cast.toInt(data.get("vidaUtil"));
            idPerfil = Cast.toInt(data.get("idPerfilFK"));
    }
}
