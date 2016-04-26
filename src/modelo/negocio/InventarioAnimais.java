/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocio;

import flex.db.DatabaseObject;
import flex.db.GenericDAO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import util.Cast;

/**
 *
 * @author Jefferson Sales
 */
public class InventarioAnimais extends DatabaseObject implements Serializable {
    
    private String categoria;
    private int valorInicio;
    private int nascimento;
    private int morte;
    private int venda;
    private int compra;
    private int valorFinal;
    private double valorCabeca;
    private int tipoAnimal; // 1 - Produção ; 2 - Em Serviço
    private Perfil perfil;

    public InventarioAnimais() {
        super("inventario_animais", "idInventarioAnimais");
    }
        
    public InventarioAnimais(String categoria, int valorInicio, int nascimento, int morte, int venda, int compra, int valorFinal, 
            double valorCabeca, int tipoAnimal, Perfil perfil) {
        super("inventario_animais", "idInventarioAnimais");
        
        this.categoria = categoria;
        this.valorInicio = valorInicio;
        this.nascimento = nascimento;
        this.morte = morte;
        this.venda = venda;
        this.compra = compra;
        this.valorFinal = valorFinal;
        this.valorCabeca = valorCabeca;
        this.tipoAnimal = tipoAnimal;
        this.perfil = perfil;
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


    public int getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(int tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public Map<String, Object> getObjectTableData() {

        HashMap<String,Object> m = new HashMap<>();
        
        m.put("categoria", categoria);
        m.put("inicio", valorInicio);
        m.put("nascimento", nascimento);
        m.put("morte", morte);
        m.put("venda", venda);
        m.put("compra", compra);
        m.put("final", valorFinal);
        m.put("valorCabeca", valorCabeca);
        m.put("tipoAnimal", tipoAnimal);
        m.put("idPerfilFK", perfil.getId());
        
        return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        
            categoria = Cast.toString(data.get("categoria"));
            valorInicio = Cast.toInt(data.get("inicio"));
            nascimento = Cast.toInt(data.get("nascimento"));
            morte = Cast.toInt(data.get("morte"));
            venda = Cast.toInt(data.get("venda"));
            compra = Cast.toInt(data.get("compra"));
            valorFinal = Cast.toInt(data.get("final"));
            valorCabeca = Cast.toDouble(data.get("valorCabeca"));
            tipoAnimal = Cast.toInt(data.get("tipoAnimal"));
            perfil = new GenericDAO<>(Perfil.class).retrieve( Cast.toInt(data.get("idPerfilFK")) );
    }

    
    
}
