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

    public Object[] getIndEconomAnuais() {
        return new Object[] {
            "Renda bruta do leite",
            "Renda bruta da atividade leiteira",
            "Preço médio do leite",
            "COE da atividade leiteira",
            "COT da atividade leiteira",
            "CT da atividade leiteira",
            "COE do leite",
            "COT do leite",
            "Custo total do leite",
            "COE unitário da atividade",
            "COT unitário da atividade",
            "CT unitário da atividade",
            "COE do leite / preço do leite",
            "COT do leite / preço do leite",
            "CT do leite / preço do leite",
            "Gasto com M.D.O. contratada permanente na ativ./renda bruta do leite",
            "Gasto com M.D.O. total na atividade / renda bruta da atividade",
            "Gasto com concentrado na atividade / renda bruta da atividade", 
            "Margem bruta da atividade leiteira",
            "Margem bruta unitária",
            "Margem bruta em equivalente litros de leite",
            "Margem bruta / área",
            "Margem bruta / vaca em lactação",
            "Margem bruta / total de vacas",
            "Margem líquida da atividade leiteira",
            "Margem líquida unitária",
            "Margem líquida em equivalente litros de leite",
            "Margem líquida / área",
            "Lucro total da atividade leiteira",
            "Lucro unitário",
            "Lucro em equivalente litros de leite",
            "Renda do leite / renda da atividade",
            "Estoque de capital da atividade (sem terra)",
            "Estoque de capital da atividade (com terra)",
            "Custo da mão-de-obra familiar",
            "Taxa de remuneração do capital sem terra",
            "Taxa de remuneração do capital com terra",
            "Lucratividade", 
            "Remuneração da mão-de-obra familiar",
            "Ponto de Resíduo (RB = COT)", 
            "Ponto de Nivelamento (RB = CT) - Litros / dia",
            "Capital investido por litro de leite"
        };
    }
    
    public Object[] getUnidadesEconAnuais() {
        return new Object[] {
            "R$/Ano", 
            "R$/Ano",
            "R$/L",
            "R$/Ano",
            "R$/Ano",
            "R$/Ano",
            "R$/Ano",
            "R$/Ano",
            "R$/Ano",
            "R$/L",
            "R$/L",
            "R$/L",
            "%",
            "%",
            "%",
            "%",
            "%",
            "%",
            "R$/Ano",
            "R$/L",
            "L/Ano",
            "R$/ha/Ano",
            "R$/Cab",
            "R$/Cab",
            "R$/Ano",
            "R$/L",
            "L/Ano",
            "R$/ha/Ano",
            "R$/Ano",
            "R$/L",
            "L/Ano",
            "%",
            "R$",
            "R$",
            "R$/Ano",
            "% a.a.",
            "% a.a.",
            "% a.a.",
            "R$/Ano",
            "L/Ano", 
            "L/Ano", 
            "R$/L"
        };
    }    
    
    public Object[] getIndTecnAnuais() {
        return new Object[] {
            "PRODUTIVOS",
            "Produção anual de leite",
            "Área usada para pecuária",
            "Rebanho médio total",
            "Total de vacas", 
            "Vacas em lactação", 
            "Vaca Seca",
            "Novilhas",
            "Bezerras",
            "Bezerros",
            "Touro",
            "Outros",
            "Vacas em lactação / Total de vacas",
            "Vacas em lactação / Rebanho",
            "Vacas em lactação / Área para pecuária",
            "Vacas em lactação / Funcionário",
            "Produção / Vaca em lactação",
            "Produção / Mão-de-obra permanente (contratada)",
            "Produção / Área para pecuária",
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
    
    public Object[] getUnidadesTecAnuais() {
        return new Object[] {
            "",
            "L/Ano",
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
            "L/Ano",
            "L/dh",
            "L/ha/mês",
            "",
            "",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
            "Cab.",
        };
    }
    
    public Object[] getConteudoEconomico(List<DadosEconMensais> dadosEcon, List<DadosTecMensais> dadosTec, int ano) {
    
        double rendaLeite = 0.0, producaoAnualLeite = 0.0, coeAtivLeite = 0.0, custoMaoObra = 0.0, salarioMensal = 0.0,
               valorTotalInicial = 0.0, valorTotalFinal = 0.0, custoOportunidade = 0.0, ativLeiteira = 0.0,
               maoObraPerm = 0.0, subTotalConcentrado = 0.0, somaVacasLactacao = 0.0, somaTotalVacas = 0.0;
        
        Perfil perfil = ControlePerfil.getInstance().getPerfilSelecionado();
        
        GenericDAO<InventarioTerras> itdao = new GenericDAO<>(InventarioTerras.class);
        List<InventarioTerras> it = itdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioForrageiras> ifdao = new GenericDAO<>(InventarioForrageiras.class);
        List<InventarioForrageiras> ifo = ifdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioAnimais> invAnimaisDAO = new GenericDAO<>(InventarioAnimais.class);
        List<InventarioAnimais> listInvAnimais = invAnimaisDAO.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioBenfeitorias> ibdao = new GenericDAO<>(InventarioBenfeitorias.class);
        List<InventarioBenfeitorias> ib = ibdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioMaquinas> imdao = new GenericDAO<>(InventarioMaquinas.class);
        List<InventarioMaquinas> im = imdao.retrieveByColumn("idPerfilFK", perfil.getId());
        
        GenericDAO<InventarioResumo> irdao = new GenericDAO<>(InventarioResumo.class);
        List<InventarioResumo> ir = irdao.retrieveByColumn("idPerfilFK", ControlePerfil.getInstance().getPerfilSelecionado().getId());
        
        if(ir != null && !ir.isEmpty()) {
            custoOportunidade = ir.get(0).getCustoOportunidade() / 100.0;
            ativLeiteira = ir.get(0).getAtividadeLeiteira() / 100.0;
            salarioMensal = (ir.get(0).getSalarioMinimo() * 13 + ir.get(0).getSalarioMinimo() * 0.3) / 12.0;
        }
        
        for(int i = 0; i < listInvAnimais.size(); i++) {
            if(listInvAnimais.get(i).getTipoAnimal() == 1) {
                valorTotalInicial += listInvAnimais.get(i).getValorInicio() * listInvAnimais.get(i).getValorCabeca();
                valorTotalFinal += listInvAnimais.get(i).getValorFinal() * listInvAnimais.get(i).getValorCabeca();
            }
        }
        
        for(int i = 0; i < dadosEcon.size(); i++) {
            if (dadosEcon.get(i).getAno() == ano) {
                if(dadosEcon.get(i).getEspecificacao().getEspecificacao().equals("Venda de Leite (L)")) {
                    rendaLeite += dadosEcon.get(i).getQuantidade() * dadosEcon.get(i).getValorUnitario();
                } 
                if(dadosEcon.get(i).getEspecificacao().getId() > 6 && dadosEcon.get(i).getEspecificacao().getId() < 70) { // A patir de mao de obra permanente - SAIDAS
                    coeAtivLeite += dadosEcon.get(i).getQuantidade() * dadosEcon.get(i).getValorUnitario();
                }
                if(dadosEcon.get(i).getEspecificacao().getEspecificacao().equals("Mão-de-obra familiar (não paga)")) {
                    custoMaoObra += dadosEcon.get(i).getQuantidade() * salarioMensal;
                }
                if(dadosEcon.get(i).getEspecificacao().getEspecificacao().equals("Mão de obra permanente")) {
                    maoObraPerm += dadosEcon.get(i).getQuantidade() * dadosEcon.get(i).getValorUnitario();
                }
                if(dadosEcon.get(i).getEspecificacao().getId() > 28 && dadosEcon.get(i).getEspecificacao().getId() < 39) { // CONCENTRADO
                    subTotalConcentrado += dadosEcon.get(i).getQuantidade() * dadosEcon.get(i).getValorUnitario();
                }
            }
        }
        
        for(int i = 0; i < dadosTec.size(); i++) {
            if (dadosTec.get(i).getAno() == ano) {
                if(dadosTec.get(i).getIndicador().getIndicador().equals("Total Litros/Mês (L)")) {
                    producaoAnualLeite += dadosTec.get(i).getDado();
                }
                if(dadosTec.get(i).getIndicador().getIndicador().equals("Nº V. Lactação")) {
                    somaVacasLactacao += dadosTec.get(i).getDado();
                    somaTotalVacas += dadosTec.get(i).getDado();
                }
                if (dadosTec.get(i).getIndicador().getIndicador().equals("Nº V. Secas")) {
                    somaTotalVacas += dadosTec.get(i).getDado();
                }
            }
        }
    
        double totalResumoDepreciacao = this.getTotalResumoDepreciacao(ir, listInvAnimais, ib, im, it, ifo);
        double depreciacaoLeite = totalResumoDepreciacao * ativLeiteira;
        
        List<Double> totais = this.getTotalResumoCapitalInventario(listInvAnimais, ib, im, it, ifo); 
        double totalResumoInventario = totais.get(0);
        double estoqueCapitalSemTerra = totais.get(1);
        
        double precoMedioLeite = Calc.dividir(rendaLeite, producaoAnualLeite);
        double rendaBrutaAtiv =  rendaLeite + (valorTotalFinal - valorTotalInicial);
        double margemBrutaUnitaria = Calc.dividir(rendaLeite - coeAtivLeite, producaoAnualLeite);
        
        double coeUnitarioAtiv = Calc.dividir(coeAtivLeite, producaoAnualLeite);
        double cotUnitarioAtiv = Calc.dividir(coeAtivLeite + custoMaoObra + totalResumoDepreciacao, producaoAnualLeite);
        
        double cotAtivLeiteira = coeAtivLeite + custoMaoObra + totalResumoDepreciacao;
        double ctAtivLeiteira = coeAtivLeite + custoMaoObra + totalResumoDepreciacao + (totalResumoInventario * custoOportunidade);
        
        double ctUnitarioLeite = Calc.dividir(ctAtivLeiteira, producaoAnualLeite);
        double cotLeite = coeAtivLeite * ativLeiteira + custoMaoObra + depreciacaoLeite;
                
        return new Object[] {
            rendaLeite,                                                                 // renda bruta do leite
            rendaBrutaAtiv,                                                             // renda bruta da atividde
            precoMedioLeite,                                                            // preco medio do leite
            coeAtivLeite,                                                               // COE ativ. leiteira
            cotAtivLeiteira,                                                            // COT ativ. leiteira
            ctAtivLeiteira,                                                             // CT Ativ Leiteira
            coeAtivLeite * ativLeiteira,                                                // COE do leite
            cotLeite,                                                                   // COT do leite
            cotLeite + (totalResumoInventario * ativLeiteira) * custoOportunidade,      // Custo total do leite    
            coeUnitarioAtiv,                                                            // COE unitario da ativ
            cotUnitarioAtiv,                                                            // COT unitario da ativ.
            ctUnitarioLeite,                                                            // CT unitario da ativ.
            Calc.dividir(coeUnitarioAtiv, precoMedioLeite)           * 100.0,           // COE leite / preco medio leite
            Calc.dividir(cotUnitarioAtiv, precoMedioLeite)           * 100.0,           // COT leite / preco medio leite
            Calc.dividir(ctUnitarioLeite, precoMedioLeite)           * 100.0,           // CT leite / preco medio leite 
            Calc.dividir(maoObraPerm, rendaBrutaAtiv)                * 100.0,           // Gasto com MDO contr. perm. / renda bruta da ativ.
            Calc.dividir(maoObraPerm + custoMaoObra, rendaBrutaAtiv) * 100.0,           // Gasto com MDO total / renda bruta da ativ.
            Calc.dividir(subTotalConcentrado, rendaBrutaAtiv)        * 100.0,           // Gasto com concentrado / renda bruta ativ.
            rendaLeite - coeAtivLeite,                                                  // Margem bruta ativ. leiteira
            margemBrutaUnitaria,                                                        // Margem bruta unitaria
            Calc.dividir(rendaLeite - coeAtivLeite, precoMedioLeite),                   // Margem bruta em equivalente litros de leite 
            Calc.dividir(rendaLeite - coeAtivLeite, perfil.getAreaPecLeite()),          // Margem bruta / area 
            Calc.dividir(rendaLeite - coeAtivLeite, somaVacasLactacao / 12.0),          // Margem bruta / vaca lactacao
            Calc.dividir(rendaLeite - coeAtivLeite, somaTotalVacas    / 12.0),          // Margem bruta / total de vacas
            rendaBrutaAtiv - cotAtivLeiteira,                                           // Margem liquida da ativ. leiteira
            Calc.dividir(rendaBrutaAtiv - cotAtivLeiteira, producaoAnualLeite),         // Margem liquida unitaria
            Calc.dividir(rendaBrutaAtiv - cotAtivLeiteira, precoMedioLeite),            // Margem liquida em equivalente litros de leite 
            Calc.dividir(rendaBrutaAtiv - cotAtivLeiteira, perfil.getAreaPecLeite()),   // Margem liquida / area
            rendaBrutaAtiv - ctAtivLeiteira,                                            // Lucro total da atividade leiteira
            Calc.dividir(rendaBrutaAtiv - ctAtivLeiteira, producaoAnualLeite),          // Lucro unitario
            Calc.dividir(rendaBrutaAtiv - ctAtivLeiteira, precoMedioLeite),             // Lucro em equivalente litros de leite
            Calc.dividir(rendaLeite, rendaBrutaAtiv)                 * 100.0,           // renda do leite / renda da ativ.
            estoqueCapitalSemTerra,                                                     // estoque de capital da atividade (sem terra)
            totalResumoInventario,                                                      // estoque de capital da atividade (com terra)
            custoMaoObra,                                                               // custo da mao de obra familiar
            Calc.dividir(rendaBrutaAtiv - cotAtivLeiteira, estoqueCapitalSemTerra) * 100.0, // Taxa de remuneracao capital sem terra
            Calc.dividir(rendaBrutaAtiv - cotAtivLeiteira, totalResumoInventario) * 100.0,  // Taxa de remuneracao capital com terra
            Calc.dividir(rendaBrutaAtiv - ctAtivLeiteira, rendaBrutaAtiv) * 100.0,      // Lucratividade
            (rendaBrutaAtiv - cotAtivLeiteira) + custoMaoObra,                          // Remuneracao da mao de obra familiar
            Calc.dividir(cotAtivLeiteira - coeAtivLeite, margemBrutaUnitaria) / 365.0,  // Ponto de residuo
            Calc.dividir(ctAtivLeiteira - coeAtivLeite, margemBrutaUnitaria)  / 365.0,  // Ponto de nivelamento
            Calc.dividir(totalResumoInventario, producaoAnualLeite            / 365.0)  // Capital investido por litro de leite   
        };
    }
    
    public List<Double> getTotalResumoCapitalInventario(List<InventarioAnimais> ia, List<InventarioBenfeitorias> ib,
            List<InventarioMaquinas> im, List<InventarioTerras> it, List<InventarioForrageiras> ifo) {
        
        double terras = 0.0, rsHa = 0.0, forragNaoAnuais = 0.0, animais = 0.0, valorTotalInicio = 0.0, valorTotalFinal = 0.0,
                benfeitorias = 0.0, maquinas = 0.0;
  
        List<Double> totalAreaPropInic = new ArrayList<>();
        List<Double> totalAreaPropFina = new ArrayList<>();
        List<Double> totalTerraNua = new ArrayList<>();
        
        for(int i = 0; i < it.size(); i++) {
            totalAreaPropInic.add(it.get(i).getAreaPropriaInicio());
            totalAreaPropFina.add(it.get(i).getAreaPropriaFinal());
            totalTerraNua.add(it.get(i).getValorTerraNuaPropria());
            
            rsHa = ( ( it.get(i).getAreaPropriaInicio() + it.get(i).getAreaPropriaFinal() ) / 2 ) * ifo.get(i).getCustoFormacaoHectare();
            forragNaoAnuais += Calc.dividir(rsHa,ifo.get(i).getVidaUtil());
        }
        
        terras = Calc.mediaAritmetica(Calc.somaPonderada(totalAreaPropInic, totalTerraNua), 
                Calc.somaPonderada(totalAreaPropFina, totalTerraNua));
        
        for(int i = 0; i < ia.size(); i++) {
            if(ia.get(i).getTipoAnimal() == 1) { // Animais de Producao
                valorTotalInicio += ia.get(i).getValorInicio() * ia.get(i).getValorCabeca();
                valorTotalFinal += ia.get(i).getValorFinal() * ia.get(i).getValorCabeca();
            }
        }
        
        animais = (valorTotalInicio + valorTotalFinal) / 2.0;
        
        for(int i = 0; i < ib.size(); i++) {
            benfeitorias += ib.get(i).getQuantidade() * ib.get(i).getValorUnitario();
        }
        
        for(int i = 0; i < im.size(); i++) {
            maquinas += im.get(i).getQuantidade() * im.get(i).getValorUnitario();
        }
        
        List<Double> valoresRetorno = new ArrayList<>();
 
        valoresRetorno.add(maquinas + benfeitorias + animais + forragNaoAnuais + terras); // Total Resumo Inventario
        valoresRetorno.add(maquinas + benfeitorias + animais + forragNaoAnuais); // Estoque Capital Sem Terra 
        
        return valoresRetorno;
        
    }
    
    public double getTotalResumoDepreciacao(List<InventarioResumo> ir, List<InventarioAnimais> ia, List<InventarioBenfeitorias> ib,
            List<InventarioMaquinas> im, List<InventarioTerras> it, List<InventarioForrageiras> ifo) {
        
        double rsHa = 0.0, forragNaoAnuais = 0.0, animaisTrabalho = 0.0, capitalReprod = 0.0, valorInicio = 0.0, valorFinal = 0.0,
                reprodutores = 0.0, benfeitorias = 0.0, maquinas = 0.0;
        List<Double> totalValFinaServ = new ArrayList<>();
        List<Double> totalValCabeServ = new ArrayList<>();
        
        
        for(int i = 0; i < it.size(); i++) {
            rsHa = ( ( it.get(i).getAreaPropriaInicio() + it.get(i).getAreaPropriaFinal() ) / 2 ) * ifo.get(i).getCustoFormacaoHectare();
            forragNaoAnuais += Calc.dividir(rsHa,ifo.get(i).getVidaUtil());
        }
        
        for(int i = 0; i < ia.size(); i++) {
            if (ia.get(i).getTipoAnimal() == 1 && (ia.get(i).getCategoria().equalsIgnoreCase("Touro") 
                    || ia.get(i).getCategoria().equalsIgnoreCase("Touros"))) { // Animais de Producao
                
                valorInicio = ia.get(i).getValorInicio() * ia.get(i).getValorCabeca();
                valorFinal = ia.get(i).getValorFinal() * ia.get(i).getValorCabeca();
                capitalReprod += Calc.mediaAritmetica(valorInicio, valorFinal);
                
            } else if(ia.get(i).getTipoAnimal() == 2) { // Animais de Servico
                
                totalValFinaServ.add( ia.get(i).getValorFinal() * 1.0);
                totalValCabeServ.add(ia.get(i).getValorCabeca());
                
            }
        }  
        if (!ir.isEmpty()) { 
            reprodutores = Calc.dividir(capitalReprod, ir.get(0).getVidaUtilReprodutores());
            animaisTrabalho = Calc.dividir(Calc.somaPonderada(totalValFinaServ, totalValCabeServ), ir.get(0).getVidaUtilAnimaisServico());
        }
        
        for(int i = 0; i < ib.size(); i++) {
            benfeitorias += Calc.dividir(ib.get(i).getQuantidade() * ib.get(i).getValorUnitario(), ib.get(i).getVidaUtil()); 
        }
        
        for(int i = 0; i < im.size(); i++) {
            maquinas += Calc.dividir(im.get(i).getQuantidade() * im.get(i).getValorUnitario(), im.get(i).getVidaUtil());
        }
        
        return Calc.somarLista(maquinas, benfeitorias, reprodutores, animaisTrabalho, forragNaoAnuais);
    }
    
    public Object[] getConteudoTecnico(List<DadosTecMensais> dadosTec, List<DadosEconMensais> dadosEcon,int ano) {
        double totalLitros = 0.0, somaTotalVacas = 0.0, somaRebanhoMedio = 0.0, somaVacasLactacao = 0.0, somaVacasSecas = 0.0;
        double somaNovilhas = 0.0, somaBezerros = 0.0, somaBezerras = 0.0, somaTouros = 0.0, somaOutros = 0.0;
        double somaAbortos = 0.0, somaNatimortos = 0.0, somaRetPlac = 0.0, somaMorteBez = 0.0, somaBezDoentes = 0.0, somaMorteNov = 0.0;
        double somaMorteVacas = 0.0, somaVacasMastCli = 0.0, maoObraPerm = 0.0;
        
        for(int i = 0; i < dadosEcon.size(); i++) {
            if(dadosEcon.get(i).getAno() == ano && dadosEcon.get(i).getEspecificacao().getId() == 7) { // MDO perm.
                maoObraPerm += dadosEcon.get(i).getQuantidade();
            }
        }
        
        for(int i = 0; i < dadosTec.size(); i++) {   
            if (dadosTec.get(i).getAno() == ano) {    
                switch(dadosTec.get(i).getIndicador().getId()) { 
                    case 1: // Total Litros/Mês (L)
                        totalLitros += dadosTec.get(i).getDado();
                        break;
                    case 2:  // N. Vacas Lactacao
                        somaTotalVacas += dadosTec.get(i).getDado();
                        somaVacasLactacao += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break;
                    case 3:  // N. Vacas Secas
                        somaTotalVacas += dadosTec.get(i).getDado();
                        somaVacasSecas += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break;           
                    case 4: // Novilhas
                        somaNovilhas += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break;
                    case 5: // Bezerras
                        somaBezerras += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break; 
                    case 6: // Bezerros
                        somaBezerros += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break;
                    case 7: // Touro
                        somaTouros += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break;
                    case 8: // Outros
                        somaOutros += dadosTec.get(i).getDado();
                        somaRebanhoMedio += dadosTec.get(i).getDado();
                        break;    
                    case 12: // N. Abortos
                        somaAbortos += dadosTec.get(i).getDado();
                        break;
                    case 13: // N. Natimortos
                        somaNatimortos += dadosTec.get(i).getDado();
                        break;
                    case 14: // N. de Retencao de Placenta
                        somaRetPlac += dadosTec.get(i).getDado();
                        break;
                    case 15: // N. de Mortes de Bezerros
                        somaMorteBez += dadosTec.get(i).getDado();
                        break;
                    case 16: // N. Bezerros Doentes
                        somaBezDoentes += dadosTec.get(i).getDado();
                        break;
                    case 17: // N. Mortes de Novilhas
                        somaMorteNov += dadosTec.get(i).getDado();
                        break;
                    case 18: // N. Mortes de Vacas
                        somaMorteVacas += dadosTec.get(i).getDado();
                        break;
                    case 19: // N. Vacas com Mastite Clínica
                        somaVacasMastCli += dadosTec.get(i).getDado();
                        break;
                }
            }
        }
        
        return new Object[] {
            "",
            totalLitros,
            ControlePerfil.getInstance().getPerfilSelecionado().getAreaPecLeite(),
            somaRebanhoMedio / 12.0,
            somaTotalVacas / 12.0,
            somaVacasLactacao / 12.0,
            somaVacasSecas / 12.0,
            somaNovilhas / 12.0,
            somaBezerras / 12.0,
            somaBezerros / 12.0,
            somaTouros / 12.0,
            somaOutros / 12.0,
            Calc.dividir(somaVacasLactacao / 12.0, somaTotalVacas / 12.0), 
            Calc.dividir(somaVacasLactacao / 12.0, somaRebanhoMedio / 12.0),
            Calc.dividir(somaVacasLactacao / 12.0, ControlePerfil.getInstance().getPerfilSelecionado().getAreaPecLeite()),
            Calc.dividir(somaVacasLactacao / 12.0, maoObraPerm),
            Calc.dividir(totalLitros, somaVacasLactacao / 12.0), 
            Calc.dividir(totalLitros, maoObraPerm),
            Calc.dividir(totalLitros, ControlePerfil.getInstance().getPerfilSelecionado().getAreaPecLeite()),
            "",
            "",
            somaAbortos / 12.0,
            somaNatimortos / 12.0,
            somaRetPlac / 12.0,
            somaMorteBez / 12.0,
            somaBezDoentes / 12.0,
            somaMorteNov / 12.0,
            somaMorteVacas / 12.0,
            somaVacasMastCli / 12.0,
        };
    }
}
