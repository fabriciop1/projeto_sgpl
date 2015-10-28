/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import java.sql.Date;

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
    
    private double valorPorCabeca;

    public InventarioAnimais() {
    }

    public InventarioAnimais(int id, String categoria, int valorInicio, int nascimento, int morte, int venda, int compra, int valorFinal, double valorPorCabeca) {
        this.id = id;
        this.categoria = categoria;
        this.valorInicio = valorInicio;
        this.nascimento = nascimento;
        this.morte = morte;
        this.venda = venda;
        this.compra = compra;
        this.valorFinal = valorFinal;
        this.valorPorCabeca = valorPorCabeca;
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

    public double getValorPorCabeca() {
        return valorPorCabeca;
    }

    public void setValorPorCabeca(double valorPorCabeca) {
        this.valorPorCabeca = valorPorCabeca;
    }

    
}
