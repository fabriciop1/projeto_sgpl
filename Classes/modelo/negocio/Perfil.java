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
    private float tamPropriedade;
    private float areaPecLeite;
    private float prodLeiteDiario;
    private float precoLeite;
    private int empPermanentes;
    private int numFamiliares;
  //private Usuario usuario;

    public Perfil() {
    }

    public Perfil(int idPerfil, String nome, String cidade, float tamPropriedade, float areaPecLeite, float prodLeiteDiario, float precoLeite, int empPermanentes, int numFamiliares /*Usuario usuario*/) {
     // this.usuario = usuario;
        this.nome = nome;
        this.idPerfil = idPerfil;
        this.cidade = cidade;
        this.tamPropriedade = tamPropriedade;
        this.areaPecLeite = areaPecLeite;
        this.prodLeiteDiario = prodLeiteDiario;
        this.precoLeite = precoLeite;
        this.empPermanentes = empPermanentes;
        this.numFamiliares = numFamiliares;
    }

    //public void setUsuario(Usuario usuario) {
    //    this.usuario = usuario;
    //}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setTamPropriedade(float tamPropriedade) {
        this.tamPropriedade = tamPropriedade;
    }

    public void setAreaPecLeite(float areaPecLeite) {
        this.areaPecLeite = areaPecLeite;
    }

    public void setProdLeiteDiario(float prodLeiteDiario) {
        this.prodLeiteDiario = prodLeiteDiario;
    }

    public void setPrecoLeite(float precoLeite) {
        this.precoLeite = precoLeite;
    }

    public void setEmpPermanentes(int empPermanentes) {
        this.empPermanentes = empPermanentes;
    }

    public void setNumFamiliares(int numFamiliares) {
        this.numFamiliares = numFamiliares;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

  //  public Usuario getUsuario() {
  //      return usuario;
  //  }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public float getTamPropriedade() {
        return tamPropriedade;
    }

    public float getAreaPecLeite() {
        return areaPecLeite;
    }

    public float getProdLeiteDiario() {
        return prodLeiteDiario;
    }

    public float getPrecoLeite() {
        return precoLeite;
    }

    public int getEmpPermanentes() {
        return empPermanentes;
    }

    public int getNumFamiliares() {
        return numFamiliares;
    }
}
