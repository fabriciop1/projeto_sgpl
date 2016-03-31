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
    private String campo; // Nome do campo do tabela
    private int quantidade;
    private double valorUnitario;
    private int tipoCampo; // A qual tabela o campo pertence? 1 - Entradas /2 - Saidas /3 - Despesas com Volumoso /4 - Concentrado /5 - Mineralização /6 - Medicamentos /7 - Ordenha /8 - Inseminação Artificial /9 - Despesas de Investimentos /10 - Despesas de Empréstimos /11 - COE da atividade leiteira
    private Perfil perfil;
    
    public DadosEconMensais() {
    }

    public DadosEconMensais(int mes, int ano, String campo, int quantidade, double valorUnitario, int tipoCampo, Perfil perfil) {
        this.mes = mes;
        this.ano = ano;
        this.campo = campo;
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

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
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
