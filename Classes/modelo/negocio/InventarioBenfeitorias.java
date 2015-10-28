/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioBenfeitorias {
    
    private int id;
    
    private String especificacao;
    
    private String unidade;
    
    private double quantidade;
    
    private double valorUnitario;
    
    private int vidaUtil;

    public InventarioBenfeitorias() {
    }

    public InventarioBenfeitorias(int id, String especificacao, String unidade, double quantidade, double valorUnitario, int vidaUtil) {
        this.id = id;
        this.especificacao = especificacao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.vidaUtil = vidaUtil;
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

    public double getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }
}
