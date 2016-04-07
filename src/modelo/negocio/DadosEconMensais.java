/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

/**
 *
 * @author Fabricio
 */
public class DadosEconMensais {
    
    private int id;
    private int mes;
    private int ano;
    private double quantidade;
    private double valorUnitario;
    private int tipoCampo; // A qual tabela o campo pertence? 1 - Entradas /2 - Saidas /3 - Despesas com Volumoso 
                           //4 - Concentrado /5 - Mineralização /6 - Medicamentos /7 - Ordenha /8 - Inseminação Artificial 
                           //9 - Despesas de Investimentos /10 - Despesas de Empréstimos /11 - COE da atividade leiteira
    private String especificacao; // id da especificacao associada à tabela DEM_especificacao
    private Perfil perfil;
    
    public DadosEconMensais() {
    }

    public DadosEconMensais(int mes, int ano, double quantidade, double valorUnitario, int tipoCampo, String especificacao, Perfil perfil) {
        this.mes = mes;
        this.ano = ano;
        this.especificacao = especificacao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.tipoCampo = tipoCampo;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
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

    public int getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(int tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
    
}
