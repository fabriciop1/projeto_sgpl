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
public class InventarioResumo {
    private int idInventarioResumo;
    private double custoOportunidade;
    private double atividadeLeiteira;
    private double salarioMinimo;
    private int mes;
    private int ano;
    private int vidaUtilReprodutores;
    private int vidaUtilAnimaisServico;
    private double valorGastoCompraAnimais;
    private Perfil perfil;

    public InventarioResumo() {
        
    }

    public InventarioResumo(double custoOportunidade, double atividadeLeiteira, double salarioMinimo, int mes, int ano, int vidaUtilReprodutores, int vidaUtilAnimaisServico, double valorGastoCompraAnimais, Perfil perfil) {
        this.custoOportunidade = custoOportunidade;
        this.atividadeLeiteira = atividadeLeiteira;
        this.salarioMinimo = salarioMinimo;
        this.mes = mes;
        this.ano = ano;
        this.vidaUtilReprodutores = vidaUtilReprodutores;
        this.vidaUtilAnimaisServico = vidaUtilAnimaisServico;
        this.valorGastoCompraAnimais = valorGastoCompraAnimais;
        this.perfil = perfil;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
    
    
}
