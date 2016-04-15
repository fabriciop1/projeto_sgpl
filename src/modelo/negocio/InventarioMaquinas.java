/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import flex.db.DatabaseObject;
import flex.db.GenericDAO;
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
public class InventarioMaquinas extends DatabaseObject {
    
    private String especificacao;
    private String unidade;
    private double quantidade;
    private double valorUnitario;
    private int vidaUtil;
    private Perfil perfil;
    
    public InventarioMaquinas() {
        super("inventario_maquinas", "idInventarioMaquinas");
    }

    public InventarioMaquinas(String especificacao, String unidade, double quantidade, double valorUnitario, int vidaUtil, Perfil perfil) {
        super("inventario_maquinas", "idInventarioMaquinas");
        
        this.especificacao = especificacao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.vidaUtil = vidaUtil;
        this.perfil = perfil;
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
        m.put("unidade", unidade);
        m.put("quantidade", quantidade);
        m.put("valorUnitario", valorUnitario);
        m.put("vidaUtil", vidaUtil);
        m.put("idPerfilFK", perfil.getId());
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        try {
            especificacao = Cast.toString(data.get("especificacao"));
            unidade = Cast.toString(data.get("unidade"));
            quantidade = Cast.toDouble(data.get("quantidade"));
            valorUnitario = Cast.toDouble(data.get("valorUnitario"));
            vidaUtil = Cast.toInt(data.get("vidaUtil"));
            perfil = new GenericDAO<>(Perfil.class).retrieve( Cast.toInt(data.get("idPerfilFK")) );
            
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(InventarioMaquinas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
