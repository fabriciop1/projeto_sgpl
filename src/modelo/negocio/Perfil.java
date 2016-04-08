/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Perfil implements Serializable {
    
    private int idPerfil;
    private String nome;
    private String cidade;
    private double tamPropriedade;
    private double areaPecLeite;
    private double prodLeiteDiario;
    private int empPermanentes;
    private int numFamiliares;
    private Rota rota;
    
    public Perfil() {
        
    }
   
    public Perfil(String nome, String cidade, double tamPropriedade, double areaPecLeite, double prodLeiteDiario, int empPermanentes, int numFamiliares, Rota rota) {
        this.nome = nome;
        this.cidade = cidade;
        this.tamPropriedade = tamPropriedade;
        this.areaPecLeite = areaPecLeite;
        this.prodLeiteDiario = prodLeiteDiario;
        this.empPermanentes = empPermanentes;
        this.numFamiliares = numFamiliares;
        this.rota = rota;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getTamPropriedade() {
        return tamPropriedade;
    }

    public void setTamPropriedade(double tamPropriedade) {
        this.tamPropriedade = tamPropriedade;
    }

    public double getAreaPecLeite() {
        return areaPecLeite;
    }

    public void setAreaPecLeite(double areaPecLeite) {
        this.areaPecLeite = areaPecLeite;
    }

    public double getProdLeiteDiario() {
        return prodLeiteDiario;
    }

    public void setProdLeiteDiario(double prodLeiteDiario) {
        this.prodLeiteDiario = prodLeiteDiario;
    }

    public int getEmpPermanentes() {
        return empPermanentes;
    }

    public void setEmpPermanentes(int empPermanentes) {
        this.empPermanentes = empPermanentes;
    }

    public int getNumFamiliares() {
        return numFamiliares;
    }

    public void setNumFamiliares(int numFamiliares) {
        this.numFamiliares = numFamiliares;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }
    
    

}
