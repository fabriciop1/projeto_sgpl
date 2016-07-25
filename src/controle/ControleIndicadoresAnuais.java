/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import flex.db.GenericDAO;
import java.util.List;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.InventarioAnimais;
import modelo.negocio.InventarioResumo;
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
    
        double rendaLeite = 0.0, producaoAnualLeite = 0.0, coeAtivLeite = 0.0, custoMaoObra = 0.0;
        double valorTotalInicial = 0.0, valorTotalFinal = 0.0;
        
        GenericDAO<InventarioResumo> irdao = new GenericDAO<>(InventarioResumo.class);
        List<InventarioResumo> ir = irdao.retrieveByColumn("idPerfilFK", ControlePerfil.getInstance().getPerfilSelecionado().getId());
        
        GenericDAO<InventarioAnimais> invAnimaisDAO = new GenericDAO<>(InventarioAnimais.class);
        List<InventarioAnimais> listInvAnimais = invAnimaisDAO.retrieveByColumn("idPerfilFK", 
                ControlePerfil.getInstance().getPerfilSelecionado().getId());
        
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
                if(dadosEcon.get(i).getEspecificacao().getId() > 6) { // A patir de mao de obra permanente - SAIDAS
                    coeAtivLeite += dadosEcon.get(i).getQuantidade() * dadosEcon.get(i).getValorUnitario();
                }
                if(dadosEcon.get(i).getEspecificacao().getEspecificacao().equals("Mão-de-obra familiar (não paga)")) {
                    custoMaoObra += dadosEcon.get(i).getQuantidade() * getSalarioMensalEconomico(ir);
                }
            }
        }
        
        for(int i = 0; i < dadosTec.size(); i++) {
            if (dadosTec.get(i).getAno() == ano) {
                if(dadosTec.get(i).getIndicador().getIndicador().equals("Total Litros/Mês (L)")) {
                    producaoAnualLeite += dadosTec.get(i).getDado();
                }
            }
        }
        
        return new Object[] {
            rendaLeite,
            rendaLeite + (valorTotalFinal - valorTotalInicial),
            Calc.dividir(rendaLeite, producaoAnualLeite),
            coeAtivLeite,
        };
    }
    
    public double getSalarioMensalEconomico(List<InventarioResumo> ir) {
        double salarioMin, salarioMensal = 0.0;
        
        if (ir.size() > 0) {
             salarioMin = ir.get(0).getSalarioMinimo();
             salarioMensal = (salarioMin * 13 + salarioMin * 0.3) / 12;
        }
        
        return salarioMensal;
    }
    public Object[] getConteudoTecnico(List<DadosTecMensais> dados, int ano) {
        double totalLitros = 0.0, somaTotalVacas = 0.0, somaRebanhoMedio = 0.0, somaVacasLactacao = 0.0, somaVacasSecas = 0.0;
        double somaNovilhas = 0.0, somaBezerros = 0.0, somaBezerras = 0.0, somaTouros = 0.0, somaOutros = 0.0;
        double somaAbortos = 0.0, somaNatimortos = 0.0, somaRetPlac = 0.0, somaMorteBez = 0.0, somaBezDoentes = 0.0, somaMorteNov = 0.0;
        double somaMorteVacas = 0.0, somaVacasMastCli = 0.0;
        
        for(int i = 0; i < dados.size(); i++) {   
            if (dados.get(i).getAno() == ano) {    
                switch(dados.get(i).getIndicador().getId()) { 
                    case 1: // Total Litros/Mês (L)
                        totalLitros += dados.get(i).getDado();
                        break;
                    case 2:  // N. Vacas Lactacao
                        somaTotalVacas += dados.get(i).getDado();
                        somaVacasLactacao += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break;
                    case 3:  // N. Vacas Secas
                        somaTotalVacas += dados.get(i).getDado();
                        somaVacasSecas += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break;           
                    case 4: // Novilhas
                        somaNovilhas += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break;
                    case 5: // Bezerras
                        somaBezerras += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break; 
                    case 6: // Bezerros
                        somaBezerros += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break;
                    case 7: // Touro
                        somaTouros += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break;
                    case 8: // Outros
                        somaOutros += dados.get(i).getDado();
                        somaRebanhoMedio += dados.get(i).getDado();
                        break;    
                    case 12: // N. Abortos
                        somaAbortos += dados.get(i).getDado();
                        break;
                    case 13: // N. Natimortos
                        somaNatimortos += dados.get(i).getDado();
                        break;
                    case 14: // N. de Retencao de Placenta
                        somaRetPlac += dados.get(i).getDado();
                        break;
                    case 15: // N. de Mortes de Bezerros
                        somaMorteBez += dados.get(i).getDado();
                        break;
                    case 16: // N. Bezerros Doentes
                        somaBezDoentes += dados.get(i).getDado();
                        break;
                    case 17: // N. Mortes de Novilhas
                        somaMorteNov += dados.get(i).getDado();
                        break;
                    case 18: // N. Mortes de Vacas
                        somaMorteVacas += dados.get(i).getDado();
                        break;
                    case 19: // N. Vacas com Mastite Clínica
                        somaVacasMastCli += dados.get(i).getDado();
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
            Calc.dividir(somaVacasLactacao / 12.0, ControlePerfil.getInstance().getPerfilSelecionado().getEmpPermanentes()),
            Calc.dividir(totalLitros, somaVacasLactacao / 12.0), 
            Calc.dividir(totalLitros, ControlePerfil.getInstance().getPerfilSelecionado().getEmpPermanentes()),
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
