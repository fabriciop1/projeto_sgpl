/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.List;

/**
 *
 * @author Fabricio
 */
public class ControleIndicadoresAnuais {
    
    private List<Integer> anosSelecionados;
    private int tipoIndicador;
    
    public ControleIndicadoresAnuais() {}
    
    private static class IndicadoresAnuaisHolder { 
        private final static ControleIndicadoresAnuais INSTANCE = new ControleIndicadoresAnuais();
    }
    
    public static ControleIndicadoresAnuais getInstance() {
        return IndicadoresAnuaisHolder.INSTANCE;
    }
    
    public void gerarIndicadores(int tipoIndicador, List<Integer> anosSelecionados) {
        
        getInstance().setTipoIndicador(tipoIndicador);
        getInstance().setAnosSelecionados(anosSelecionados);
        
    }

    public void setTipoIndicador(int tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }
    
    public int getTipoIndicador() {
        return tipoIndicador;
    }

    public void setAnosSelecionados(List<Integer> anosSelecionados) {
        this.anosSelecionados = anosSelecionados;
    }

    public List<Integer> getAnosSelecionados() {
        return anosSelecionados;
    }

    
    
    
}
