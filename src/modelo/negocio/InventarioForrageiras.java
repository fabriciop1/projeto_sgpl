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
 * @author Fabricio
 */
public class InventarioForrageiras extends DatabaseObject implements Serializable {
    private String forrageirasNaoAnuais;
    private int vidaUtil;
    private double custoFormacaoHectare;
    private Perfil perfil;
    private InventarioTerras inventarioTerras;
    
    public InventarioForrageiras() {
        super("inventario_forrageiras", "idInventarioForrageiras");
    }

    public InventarioForrageiras(String forrageirasNaoAnuais, int vidaUtil, double custoFormacaoHectare, Perfil perfil, InventarioTerras inventarioTerras) {
        super("inventario_forrageiras", "idInventarioForrageiras");
        
        this.forrageirasNaoAnuais = forrageirasNaoAnuais;
        this.vidaUtil = vidaUtil;
        this.custoFormacaoHectare = custoFormacaoHectare;
        this.perfil = perfil;
        this.inventarioTerras = inventarioTerras;
    }

    public String getForrageirasNaoAnuais() {
        return forrageirasNaoAnuais;
    }

    public void setForrageirasNaoAnuais(String forrageirasNaoAnuais) {
        this.forrageirasNaoAnuais = forrageirasNaoAnuais;
    }

    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public double getCustoFormacaoHectare() {
        return custoFormacaoHectare;
    }

    public void setCustoFormacaoHectare(double custoFormacaoHectare) {
        this.custoFormacaoHectare = custoFormacaoHectare;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public InventarioTerras getInventarioTerras() {
        return inventarioTerras;
    }

    public void setInventarioTerras(InventarioTerras inventarioTerras) {
        this.inventarioTerras = inventarioTerras;
    }

    @Override
    public Map<String, Object> getObjectTableData() {
         HashMap<String,Object> m = new HashMap<>();
         
         m.put("forrageirasNaoAnuais", forrageirasNaoAnuais);
         m.put("custoFormacaoHectare", custoFormacaoHectare);
         m.put("vidaUtil", vidaUtil);
         m.put("idPerfilFK", perfil.getId());
         m.put("idInventarioTerrasFK", inventarioTerras.getId());
         
         return m;
    }

    @Override
    public void setObjectData(Map<String, Object> data) {
        forrageirasNaoAnuais = Cast.toString(data.get("forrageirasNaoAnuais"));
        custoFormacaoHectare = Cast.toDouble(data.get("custoFormacaoHectare"));
        vidaUtil = Cast.toInt(data.get("vidaUtil"));
        perfil = new GenericDAO<>(Perfil.class).retrieve(Cast.toInt(data.get("idPerfilFK")));
        inventarioTerras = new GenericDAO<>(InventarioTerras.class).retrieve(Cast.toInt(data.get("idInventarioTerrasFK")));
    }
    
    
}
