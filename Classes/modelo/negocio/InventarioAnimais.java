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
public class InventarioAnimais {
    
    private int id;
    private String categoria;
    private int valorInicio;
    private int nascimento;
    private int morte;
    private int venda;
    private int compra;
    private int valorFinal;
    private double valorCabeca;
    private int vidaUtilReprodutores;
    private int vidaUtilAnimaisServico;
    private int tipoAnimal; // 1 - Produção ; 2 - Em Serviço
    private double valorGastoCompraAnimais;
    private Perfil perfil;

    public InventarioAnimais() {
    }
        
    public InventarioAnimais(String categoria, int valorInicio, int nascimento, int morte, int venda, int compra, int valorFinal, 
            double valorCabeca, int vidaUtilReprodutores, int vidaUtilAnimaisServico, int tipoAnimal, double valorGastoCompraAnimais, Perfil perfil) {
        this.categoria = categoria;
        this.valorInicio = valorInicio;
        this.nascimento = nascimento;
        this.morte = morte;
        this.venda = venda;
        this.compra = compra;
        this.valorFinal = valorFinal;
        this.valorCabeca = valorCabeca;
        this.vidaUtilReprodutores = vidaUtilReprodutores;
        this.vidaUtilAnimaisServico = vidaUtilAnimaisServico;
        this.tipoAnimal = tipoAnimal;
        this.valorGastoCompraAnimais = valorGastoCompraAnimais;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getValorInicio() {
        return valorInicio;
    }

    public void setValorInicio(int valorInicio) {
        this.valorInicio = valorInicio;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public int getMorte() {
        return morte;
    }

    public void setMorte(int morte) {
        this.morte = morte;
    }

    public int getVenda() {
        return venda;
    }

    public void setVenda(int venda) {
        this.venda = venda;
    }

    public int getCompra() {
        return compra;
    }

    public void setCompra(int compra) {
        this.compra = compra;
    }

    public int getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(int valorFinal) {
        this.valorFinal = valorFinal;
    }

    public double getValorCabeca() {
        return valorCabeca;
    }

    public void setValorCabeca(double valorCabeca) {
        this.valorCabeca = valorCabeca;
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

    public int getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(int tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
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
