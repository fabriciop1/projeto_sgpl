/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import flex.db.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.InventarioAnimais;
import modelo.negocio.InventarioBenfeitorias;
import modelo.negocio.InventarioForrageiras;
import modelo.negocio.InventarioMaquinas;
import modelo.negocio.InventarioResumo;
import modelo.negocio.InventarioTerras;
import modelo.negocio.Perfil;
import util.Calc;

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
    
    public Object[] getIndEconomMensais(){
        return new Object[] {
            "Renda bruta do leite",
            "Preço médio mensal do leite",
            "COE do leite",
            "COT do leite",
            "Custo total do leite",
            "COE unitário do leite",
            "COT unitário do leite",
            "CT unitário do leite",
            "COE do leite/preço do leite",
            "COT do leite/preço do leite",
            "CT do leite/preço do leite",
            "Gasto com MDO contratada permanente do leite/renda bruta do leite",
            "Gasto com MDO total do leite/ renda bruta do leite",
            "Gasto com concentrado do leite/renda bruta do leite",
            "Margem bruta do leite",
            "Margem bruta unitária",
            "Margem bruta em equivalente litros de leite",
            "Margem bruta/Área",
            "Margem bruta/vaca em lactação",
            "Margem bruta/total de vacas",
            "Margem líquida do leite",
            "Margem líquida unitária",
            "Margem líquida em equivalente litros de leite", 
            "Margem líquida/Área",
            "Lucro total do leite",
            "Lucro unitário",
            "Lucro em equivalente litros de leite",
            "Custo da mão-de-obra familiar",
            "Lucratividade",
            "Ponto de Resíduo ( RB = COT )",
            "Ponto de Nivelamento ( RB = CT )",
            "Capital investido por litro de leite"
        };
        
    }
    
    public Object[] getUniEconomMensais(){
        return new Object[] {
            "R$/Mês",
            "R$/L",
            "R$/Mês",
            "R$/Mês",
            "R$/Mês",
            "R$/L",
            "R$/L",
            "R$/L",
            "%",
            "%",
            "%",
            "%",
            "%",
            "%",
            "R$/Mês",
            "R$/L",
            "L/Mês",
            "R$/ha/Mês",
            "R$/Cab",
            "R$/Cab",
            "R$/Mês",
            "R$/L",
            "L/Mês", 
            "R$/ha/Mês",
            "R$/Mês",
            "R$/L",
            "L/Mês",
            "R$/Mês",
            "% a.m.",
            "L/dia",
            "L/dia",
            "R$/L"
        };
        
    }
    
    public Object[] getIndTecnMensais(){
        return new Object[] {
            "PRODUTIVOS",
            "Produção mensal de leite",
            "Área usada para pecuária",
            "Rebanho total",
            "Total de vacas",
            "Vacas em lactação",
            "Vaca Seca",
            "Novilhas",
            "Bezerras",
            "Bezerros",
            "Touro",
            "Outros",
            "Vacas em lactação / total de vacas",
            "Vacas em lactação / rebanho",
            "Vacas em lactação / área para pecuária",
            "Vacas em lactação / funcionário",
            "Produção / vaca em lactação",
            "Produção / mão-de-obra permanente (contratada)",
            "Produção / área para pecuária (1/2) x 365",
            "",
            "SANITÁRIOS",
            "Nº Abortos",
            "Nº Natimortos",
            "Nº Retenção de placenta",
            "Nº Morte de bezerros",
            "Nº Bezerros doentes",
            "Nº Morte de novilhas",
            "Nº Morte de vacas",
            "Nº Vacas com mastite clínica",
        };    
    }
    
    public Object[] getUniTecnMensais(){
        return new Object[] {
            "",
            "L/mês",
            "ha",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "%",
            "%",
            "Cab./ha",
            "Cab./dh",
            "L/dia",
            "L/dh",
            "L/ha/mês",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        };    
    }
    
    public Object[] getConteudoEconomico(List<DadosEconMensais> dems, List<DadosTecMensais> dtms, int mes, int ano){
        
        double rendaBruta = 0.0, litros = 0.0, coeLeite = 0.0, lactacao = 0.0;
        double somaConcentrado = 0.0, somaCOE = 0.0, somaVacas = 0.0, capitalEmpatado = 0.0, salarioMensal = 0.0;
        double maoObraFam = 0.0, maoObraPer = 0.0, depreciacaoDoLeite = 0.0, ativLeiteira = 0.0, custoOportunidade = 0.0;
        
        Perfil perfil = ControlePerfil.getInstance().getPerfilSelecionado();
        InventarioResumo resumo = null;
        
        GenericDAO<InventarioResumo> irdao = new GenericDAO<>(InventarioResumo.class);
        List<InventarioResumo> resumos = irdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioTerras> itdao = new GenericDAO<>(InventarioTerras.class);
        List<InventarioTerras> terras = itdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioForrageiras> ifdao = new GenericDAO<>(InventarioForrageiras.class);
        List<InventarioForrageiras> forrageiras = ifdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioAnimais> iadao = new GenericDAO<>(InventarioAnimais.class);
        List<InventarioAnimais> animais = iadao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioMaquinas> imdao = new GenericDAO<>(InventarioMaquinas.class);
        List<InventarioMaquinas> maquinas = imdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioBenfeitorias> ibdao = new GenericDAO<>(InventarioBenfeitorias.class);
        List<InventarioBenfeitorias> benfeitorias = ibdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        if(resumos != null && !resumos.isEmpty()) {
            resumo = resumos.get(0);
            ativLeiteira = resumo.getAtividadeLeiteira() / 100.0;
            custoOportunidade = resumo.getCustoOportunidade() / 100.0;
            salarioMensal = (resumo.getSalarioMinimo() * 13 + resumo.getSalarioMinimo() * 0.3) / 12.0;
        }
        
        for(int i = 0; i < dems.size(); i++) {   
            if (dems.get(i).getAno() == ano && dems.get(i).getMes() == mes) {    
                switch(dems.get(i).getEspecificacao().getId()) { 
                    case 2: // Venda de Leite (L)
                        rendaBruta = dems.get(i).getQuantidade() * dems.get(i).getValorUnitario();
                        break;
                    case 7: //Mão-de-obra permanente
                        maoObraPer = dems.get(i).getQuantidade() * dems.get(i).getValorUnitario();
                        break;
                    case 70: //Mão-de-obra familiar (não paga)
                        maoObraFam = dems.get(i).getQuantidade() * salarioMensal;
                        break;
                }
                
                if(dems.get(i).getEspecificacao().getId() >= 7 &&  dems.get(i).getEspecificacao().getId() < 70){
                    somaCOE += dems.get(i).getQuantidade() * dems.get(i).getValorUnitario();
                }
                
                if(dems.get(i).getEspecificacao().getId() >= 29 &&
                        dems.get(i).getEspecificacao().getId() <= 38){
                    somaConcentrado += dems.get(i).getQuantidade() * dems.get(i).getValorUnitario();
                }
            }
        }
        
        for(int i = 0; i < dtms.size(); i++){
            if (dtms.get(i).getAno() == ano && dtms.get(i).getMes() == mes) {    
                switch(dtms.get(i).getIndicador().getId()) { 
                    case 1: // Total Litros/Mês (L)
                        litros = dtms.get(i).getDado();
                        break;
                    case 2: //Nº V. Lactação
                        lactacao = dtms.get(i).getDado();
                        somaVacas += dtms.get(i).getDado();
                        break;
                    case 3: //Nº V. Secas
                        somaVacas += dtms.get(i).getDado();
                        break;
                }
            }
        }
        
        depreciacaoDoLeite = ativLeiteira * getResumoDepreciacao(resumo, terras, forrageiras, animais, benfeitorias, maquinas);
        capitalEmpatado = ativLeiteira * getResumoInventario(terras, forrageiras, animais, benfeitorias, maquinas);
        coeLeite = somaCOE * ativLeiteira;
        
        double custoTotalLeite = (coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )) + (capitalEmpatado * ((custoOportunidade)/12.0));
        
        return new Object[] {
            rendaBruta,
            Calc.dividir(rendaBruta, litros),
            coeLeite,
            coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 ),
            custoTotalLeite,
            Calc.dividir(coeLeite, litros), 
            Calc.dividir((coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )),litros),
            Calc.dividir(custoTotalLeite,litros),
            Calc.dividir(coeLeite,rendaBruta) * 100.0,
            Calc.dividir((coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )),rendaBruta) * 100.0,
            Calc.dividir(custoTotalLeite,rendaBruta) * 100.0,
            Calc.dividir((maoObraPer * ativLeiteira), rendaBruta) * 100.0,
            Calc.dividir((maoObraPer + maoObraFam) * ativLeiteira, rendaBruta) * 100.0,
            Calc.dividir(somaConcentrado * ativLeiteira,rendaBruta) * 100.0,
            rendaBruta - coeLeite,
            Calc.dividir((rendaBruta - coeLeite), litros),
            Calc.dividir((rendaBruta - coeLeite), (Calc.dividir(rendaBruta,litros))),
            Calc.dividir((rendaBruta - coeLeite), perfil.getAreaPecLeite()),
            Calc.dividir((rendaBruta - coeLeite), lactacao),
            Calc.dividir((rendaBruta - coeLeite), somaVacas),
            rendaBruta - (coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )),
            Calc.dividir(rendaBruta - (coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )), litros),
            Calc.dividir(rendaBruta - (coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )), Calc.dividir(rendaBruta, litros)), 
            Calc.dividir(rendaBruta - (coeLeite + maoObraFam + ( depreciacaoDoLeite/12.0 )), perfil.getAreaPecLeite()),
            rendaBruta - custoTotalLeite,
            Calc.dividir(rendaBruta - custoTotalLeite, litros),
            Calc.dividir(rendaBruta - custoTotalLeite, Calc.dividir(rendaBruta, litros)),
            maoObraFam,
            Calc.dividir(rendaBruta - custoTotalLeite, rendaBruta) * 100.0,
            Calc.dividir((maoObraFam + ( depreciacaoDoLeite/12.0 )), Calc.dividir((rendaBruta - coeLeite),litros))/30.0,
            Calc.dividir((maoObraFam + ( depreciacaoDoLeite/12.0 )) + (capitalEmpatado * (custoOportunidade/12.0)), Calc.dividir((rendaBruta - coeLeite),litros))/30.0,
            Calc.dividir(Calc.dividir(capitalEmpatado, ativLeiteira), Calc.dividir(litros, util.Util.diasDoMes(ano, mes))),
        };
    }
    
    public Object[] getConteudoTecnico(List<DadosTecMensais> dados, List<DadosEconMensais> dems, int mes, int ano){
        double litros = 0.0,   totalVacas = 0.0, rebanhoMedio = 0.0, vacasLactacao = 0.0, vacaSeca = 0.0;
        double novilhas = 0.0, bezerros = 0.0,   bezerras = 0.0,     touros = 0.0,        outros = 0.0;
        double abortos = 0.0,  natimortos = 0.0, retenPlac = 0.0,    morteBez = 0.0,      bezDoentes = 0.0;
        double morteNov = 0.0, morteVacas = 0.0, vacasMastCli = 0.0, maoObraPerm = 0.0 ;
        
        for(int i = 0; i < dems.size(); i++) {
            if(dems.get(i).getAno() == ano && dems.get(i).getMes() == mes && dems.get(i).getEspecificacao().getId() == 7) { // MDO perm.
                maoObraPerm = dems.get(i).getQuantidade();
            }
        }
        for(int i = 0; i < dados.size(); i++) {   
            if (dados.get(i).getAno() == ano && dados.get(i).getMes() == mes) {    
                switch(dados.get(i).getIndicador().getId()) { 
                    case 1: // Total Litros/Mês (L)
                        litros = dados.get(i).getDado();
                        break;
                    case 2:  // N. Vacas Lactacao
                        totalVacas += dados.get(i).getDado();
                        vacasLactacao = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break;
                    case 3:  // N. Vacas Secas
                        totalVacas += dados.get(i).getDado();
                        vacaSeca = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break;           
                    case 4: // Novilhas
                        novilhas = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break;
                    case 5: // Bezerras
                        bezerras = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break; 
                    case 6: // Bezerros
                        bezerros = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break;
                    case 7: // Touro
                        touros = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break;
                    case 8: // Outros
                        outros = dados.get(i).getDado();
                        rebanhoMedio += dados.get(i).getDado();
                        break;    
                    case 12: // N. Abortos
                        abortos = dados.get(i).getDado();
                        break;
                    case 13: // N. Natimortos
                        natimortos = dados.get(i).getDado();
                        break;
                    case 14: // N. de Retencao de Placenta
                        retenPlac = dados.get(i).getDado();
                        break;
                    case 15: // N. de Mortes de Bezerros
                        morteBez = dados.get(i).getDado();
                        break;
                    case 16: // N. Bezerros Doentes
                        bezDoentes = dados.get(i).getDado();
                        break;
                    case 17: // N. Mortes de Novilhas
                        morteNov = dados.get(i).getDado();
                        break;
                    case 18: // N. Mortes de Vacas
                        morteVacas = dados.get(i).getDado();
                        break;
                    case 19: // N. Vacas com Mastite Clínica
                        vacasMastCli = dados.get(i).getDado();
                        break;
                }
            }
        }
        
        return new Object[] {
            "",
            litros,
            ControlePerfil.getInstance().getPerfilSelecionado().getAreaPecLeite(),
            rebanhoMedio,
            totalVacas,
            vacasLactacao,
            vacaSeca,
            novilhas,
            bezerras,
            bezerros,
            touros,
            outros,
            Calc.dividir(vacasLactacao, totalVacas) * 100.0, 
            Calc.dividir(vacasLactacao, rebanhoMedio) * 100.0,
            Calc.dividir(vacasLactacao, ControlePerfil.getInstance().getPerfilSelecionado().getAreaPecLeite()),
            Calc.dividir(vacasLactacao, maoObraPerm),
            Calc.dividir(litros, vacasLactacao) / 30.0, 
            Calc.dividir(litros, maoObraPerm),
            Calc.dividir(litros, ControlePerfil.getInstance().getPerfilSelecionado().getAreaPecLeite()),
            "",
            "",
            abortos,
            natimortos,
            retenPlac,
            morteBez,
            bezDoentes,
            morteNov,
            morteVacas,
            vacasMastCli,
        };       
    }
    
    public double getResumoDepreciacao(InventarioResumo resumo, List<InventarioTerras> terras,
            List<InventarioForrageiras> forrageiras, List<InventarioAnimais> animais, List<InventarioBenfeitorias> benfeitorias,
            List<InventarioMaquinas> maquinas){
        
        double ha = 0, valorHa = 0, depreciacao = 0;
        double capitalReprod = 0.0, animaisDeTrab = 0.0, reprodutores = 0.0, valorInicio = 0.0, valorFinal = 0.0;
        double forragNaoAnuais = 0, totalBenfeitorias = 0.0, totalMaquinas = 0.0, total = 0.0;
                        
        //----------------------------------- Terras
        
        for (int i = 0; i < forrageiras.size(); i++) {

            ha = (terras.get(i).getAreaPropriaInicio() + terras.get(i).getAreaPropriaFinal()) / 2;
            valorHa = forrageiras.get(i).getCustoFormacaoHectare() * ha;
            depreciacao = Calc.dividir(valorHa, forrageiras.get(i).getVidaUtil());

            forragNaoAnuais += (depreciacao);
        }
        
        //----------------------------------- Animais
        
        ArrayList<Double> totalValFinaServ = new ArrayList<>();
        ArrayList<Double> totalValCabeServ = new ArrayList<>();

        for (int i = 0; i < animais.size(); i++) {
            
            if (animais.get(i).getTipoAnimal() == 1) { //Producao

                if (animais.get(i).getCategoria().equalsIgnoreCase("Touro") || 
                        animais.get(i).getCategoria().equalsIgnoreCase("Touros")) {
                    valorInicio = animais.get(i).getValorInicio() * animais.get(i).getValorCabeca();
                    valorFinal = animais.get(i).getValorFinal() * animais.get(i).getValorCabeca();
                                        
                    capitalReprod += Calc.mediaAritmetica(valorInicio, valorFinal);
                    break;
                }
                
            } else if (animais.get(i).getTipoAnimal() == 2) { //servico

                totalValFinaServ.add(animais.get(i).getValorFinal() * 1.0);
                totalValCabeServ.add(animais.get(i).getValorCabeca() * 1.0);
            }
        }
        
        if (resumo != null) {
            reprodutores  = Calc.dividir(capitalReprod, resumo.getVidaUtilReprodutores());
            animaisDeTrab = Calc.dividir(Calc.somaPonderada(totalValFinaServ, totalValCabeServ), resumo.getVidaUtilAnimaisServico());
        }
        
        //----------------------------------- Máquinas
        
        for (int i = 0; i < maquinas.size(); i++) {

            total = maquinas.get(i).getQuantidade() * maquinas.get(i).getValorUnitario();
            depreciacao = Calc.dividir(total, maquinas.get(i).getVidaUtil());

            totalMaquinas += depreciacao;
        }
        
        //----------------------------------- Benfeitorias
        
        for (int i = 0; i < benfeitorias.size(); i++) {

            total = benfeitorias.get(i).getQuantidade() * benfeitorias.get(i).getValorUnitario();
            depreciacao = Calc.dividir(total, benfeitorias.get(i).getVidaUtil());

            totalBenfeitorias += depreciacao;
        }
        
        return forragNaoAnuais + animaisDeTrab + reprodutores + totalMaquinas + totalBenfeitorias;
    }
    
    public double getResumoInventario(List<InventarioTerras> terras, List<InventarioForrageiras> forrageiras,
            List<InventarioAnimais> animais, List<InventarioBenfeitorias> benfeitorias, List<InventarioMaquinas> maquinas){
        double valorTerra = 0.0, forragNaoAnuais = 0.0, valorAnimais = 0.0, total = 0.0;
        double totalMaquinas = 0.0, totalBenfeitorias = 0.0, ha = 0.0, valorHa = 0.0, valorInicio = 0.0, valorFinal = 0.0;
        double depreciacao = 0.0, totalValorInicio = 0.0, totalValorFinal = 0.0;

        //--------------------------- Terras
        
        ArrayList<Double> totalAreaPropInic = new ArrayList<>();
        ArrayList<Double> totalAreaPropFina = new ArrayList<>();
        ArrayList<Double> totalTerraNua = new ArrayList<>();

        for (int i = 0; i < terras.size(); i++) {
            totalAreaPropInic.add(terras.get(i).getAreaPropriaInicio());
            totalAreaPropFina.add(terras.get(i).getAreaPropriaFinal());
            totalTerraNua.add(terras.get(i).getValorTerraNuaPropria());
        }
        
        for (int i = 0; i < forrageiras.size(); i++) {

            ha = (terras.get(i).getAreaPropriaInicio() + terras.get(i).getAreaPropriaFinal()) / 2;
            valorHa = forrageiras.get(i).getCustoFormacaoHectare() * ha;
            depreciacao = Calc.dividir(valorHa, forrageiras.get(i).getVidaUtil());

            forragNaoAnuais += (depreciacao);
        }

        valorTerra =  Calc.mediaAritmetica(Calc.somaPonderada(totalAreaPropInic, totalTerraNua), 
                Calc.somaPonderada(totalAreaPropFina, totalTerraNua));
        
        
        //--------------------------- Animais
        for (int i = 0; i < animais.size(); i++) {
            
            if (animais.get(i).getTipoAnimal() == 1) { //Producao

                valorInicio = animais.get(i).getValorInicio() * animais.get(i).getValorCabeca();
                valorFinal = animais.get(i).getValorFinal() * animais.get(i).getValorCabeca();
                
                totalValorInicio += valorInicio;
                totalValorFinal += valorFinal;

            }
        }
        
        valorAnimais = (totalValorInicio + totalValorFinal) / 2;
        
        //---------------------------- Maquinas
        for (int i = 0; i < maquinas.size(); i++) {

            total = maquinas.get(i).getQuantidade() * maquinas.get(i).getValorUnitario();
            totalMaquinas += total;
        }
        
        //---------------------------- Benfeitorias
        for (int i = 0; i < benfeitorias.size(); i++) {
            total = benfeitorias.get(i).getQuantidade() * benfeitorias.get(i).getValorUnitario();
            totalBenfeitorias += total;
        }
        
        return valorTerra + forragNaoAnuais + valorAnimais + totalMaquinas + totalBenfeitorias;
    }
    
}
