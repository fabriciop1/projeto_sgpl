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
public class ControleRelatoriosMensais {
    private int tipo, anoIni, anoFim, mesIni, mesFim;
    
    private ControleRelatoriosMensais() {}
    
    private static class RelatorioHolder { 
        private final static ControleRelatoriosMensais instance = new ControleRelatoriosMensais();
    }

    public static ControleRelatoriosMensais getInstance() {
            return RelatorioHolder.instance;
    }
    
    public void gerarRelatorio(int tipo, int mesIni, int mesFim, int anoIni, int anoFim){
        
        getInstance().setTipo(tipo);
        getInstance().setAnoIni(anoIni);
        getInstance().setAnoFim(anoFim);
        getInstance().setMesIni(mesIni);
        getInstance().setMesFim(mesFim);        
        
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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
