/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

/**
 *
 * @author usuario
 */
public class Perfil {
    
    private Usuario usuario;
    private String nome;
    private String cidade;
    private float tamPropriedade;
    private float areaPecLeite;
    private float prodLeiteDiario;
    private float precoLeite;
    private int empregadosPerm;
    private int numFamiliares;

    public Perfil() {
    }

    public Perfil(Usuario usuario, String nome, String cidade, float tamPropriedade, float areaPecLeite, float prodLeiteDiario, float precoLeite, int empregadosPerm, int numFamiliares) {
        this.usuario = usuario;
        this.nome = nome;
        this.cidade = cidade;
        this.tamPropriedade = tamPropriedade;
        this.areaPecLeite = areaPecLeite;
        this.prodLeiteDiario = prodLeiteDiario;
        this.precoLeite = precoLeite;
        this.empregadosPerm = empregadosPerm;
        this.numFamiliares = numFamiliares;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

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

    public void setEmpregadosPerm(int empregadosPerm) {
        this.empregadosPerm = empregadosPerm;
    }

    public void setNumFamiliares(int numFamiliares) {
        this.numFamiliares = numFamiliares;
    }

    public Usuario getUsuario() {
        return usuario;
    }

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

    public int getEmpregadosPerm() {
        return empregadosPerm;
    }

    public int getNumFamiliares() {
        return numFamiliares;
    }
    
    
    
}
