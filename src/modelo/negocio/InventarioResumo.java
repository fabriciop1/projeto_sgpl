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
public class InventarioResumo extends DatabaseObject implements Serializable {
    private int idInventarioResumo;
    private double custoOportunidade;
    private double atividadeLeiteira;
    private double salarioMinimo;
    private int mes;
    private int ano;
    private int vidaUtilReprodutores;
    private int vidaUtilAnimaisServico;
    private double valorGastoCompraAnimais;
    private int idPerfil;
    
    public InventarioResumo() {
        super("inventario_resumo", "idInventarioResumo");
    }

    public InventarioResumo(double custoOportunidade, double atividadeLeiteira, double salarioMinimo, int mes, int ano,
                int vidaUtilReprodutores, int vidaUtilAnimaisServico, double valorGastoCompraAnimais, int idPerfil) {
        super("inventario_resumo", "idInventarioResumo");
        this.custoOportunidade = custoOportunidade;
        this.atividadeLeiteira = atividadeLeiteira;
        this.salarioMinimo = salarioMinimo;
        this.mes = mes;
        this.ano = ano;
        this.vidaUtilReprodutores = vidaUtilReprodutores;
        this.vidaUtilAnimaisServico = vidaUtilAnimaisServico;
        this.valorGastoCompraAnimais = valorGastoCompraAnimais;
        this.idPerfil = idPerfil;
    }
    
    public int getIdInventarioResumo() {
        return idInventarioResumo;
    }

    public void setIdInventarioResumo(int idInventarioResumo) {
        this.idInventarioResumo = idInventarioResumo;
    }

    public double getCustoOportunidade() {
        return custoOportunidade;
    }

    public void setCustoOportunidade(double custoOportunidade) {
        this.custoOportunidade = custoOportunidade;
    }

    public double getAtividadeLeiteira() {
        return atividadeLeiteira;
    }

    public void setAtividadeLeiteira(double atividadeLeiteira) {
        this.atividadeLeiteira = atividadeLeiteira;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(double salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
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

    public int getVidaUtilReprodutores() {
        return vidaUtilReprodutores;
    }

    public void setVidaUtilReprodutores(int vidaUtilReprodutores) {
        this.vidaUtilReprodutores = vidaUtilReprodutores;
    }

    public int getVidaUtilAnimaisServico() {
        return vidaUtilAnimaisServico;
    }

    public void setVidaUtilAnimaisServico(int vidaUtilAnimaisServico) {
        this.vidaUtilAnimaisServico = vidaUtilAnimaisServico;
    }

    public double getValorGastoCompraAnimais() {
        return valorGastoCompraAnimais;
    }

    public void setValorGastoCompraAnimais(double valorGastoCompraAnimais) {
        this.valorGastoCompraAnimais = valorGastoCompraAnimais;
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
        
        m.put("custoOportunidade", custoOportunidade);
        m.put("atividadeLeiteira", atividadeLeiteira);
        m.put("salarioMinimo", salarioMinimo);
        m.put("mes", mes);
        m.put("ano", ano);
        m.put("vidaUtilReprodutores", vidaUtilReprodutores);
        m.put("vidaUtilAnimaisServico", vidaUtilAnimaisServico);
        m.put("valorGastoCompraAnimais", valorGastoCompraAnimais);
        m.put("idPerfilFK", idPerfil);
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        custoOportunidade = Cast.toDouble(data.get("custoOportunidade"));
        atividadeLeiteira = Cast.toDouble(data.get("atividadeLeiteira"));
        salarioMinimo = Cast.toDouble(data.get("salarioMinimo"));
        mes = Cast.toInt(data.get("mes"));
        ano = Cast.toInt(data.get("ano"));
        vidaUtilReprodutores = Cast.toInt(data.get("vidaUtilReprodutores"));
        vidaUtilAnimaisServico = Cast.toInt(data.get("vidaUtilAnimaisServico"));
        valorGastoCompraAnimais = Cast.toDouble(data.get("valorGastoCompraAnimais"));
        idPerfil = Cast.toInt(data.get("idPerfilFK"));
    }
    
}
