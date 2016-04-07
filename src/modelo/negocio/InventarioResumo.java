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
    private Perfil perfil;

    public InventarioResumo() {
        
    }
    
    public InventarioResumo(double custoOportunidade, double atividadeLeiteira, double salarioMinimo, int mes, int ano, Perfil perfil) {
        this.custoOportunidade = custoOportunidade;
        this.atividadeLeiteira = atividadeLeiteira;
        this.salarioMinimo = salarioMinimo;
        this.mes = mes;
        this.ano = ano;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
    
    
}
