/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

/**
 *
 * @author Alexandre
 */
public class ControleIndicadoresMensais {
    private int tipoIndicador, anoIni, anoFim, mesIni, mesFim;
    
    public ControleIndicadoresMensais() {}
    
    private static class RelatorioHolder { 
        private final static ControleIndicadoresMensais INSTANCE = new ControleIndicadoresMensais();
    }

    public static ControleIndicadoresMensais getInstance() {
            return RelatorioHolder.INSTANCE;
    }
    
    public void gerarIndicadores(int tipoIndicador, int mesIni, int mesFim, int anoIni, int anoFim){
        
        getInstance().setTipoIndicador(tipoIndicador);
        getInstance().setAnoIni(anoIni);
        getInstance().setAnoFim(anoFim);
        getInstance().setMesIni(mesIni);
        getInstance().setMesFim(mesFim);        
        
    }

    public void setTipoIndicador(int tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }

    public int getTipoIndicador() {
        return tipoIndicador;
    }

    public int getAnoIni() {
        return anoIni;
    }

    public void setAnoIni(int anoIni) {
        this.anoIni = anoIni;
    }

    public int getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(int anoFim) {
        this.anoFim = anoFim;
    }

    public int getMesIni() {
        return mesIni;
    }

    public void setMesIni(int mesIni) {
        this.mesIni = mesIni;
    }

    public int getMesFim() {
        return mesFim;
    }

    public void setMesFim(int mesFim) {
        this.mesFim = mesFim;
    }
    
    
}
