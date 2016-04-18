/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import flex.table.GenericTableRowEditor;
import static flex.table.GenericTableRowEditor.*;
import controle.ControlePerfil;
import flex.db.GenericDAO;
import flex.table.GenericTableModifier;
import flex.table.TableModifiedEvent;
import flex.table.TableModifyListener;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.dao.InventarioAnimaisDAO;
import modelo.dao.InventarioBenfeitoriasDAO;
import modelo.dao.InventarioMaquinasDAO;
import modelo.dao.InventarioResumoDAO;
import modelo.dao.InventarioTerrasDAO;
import modelo.negocio.InventarioAnimais;
import modelo.negocio.InventarioBenfeitorias;
import modelo.negocio.InventarioMaquinas;
import modelo.negocio.InventarioResumo;
import modelo.negocio.InventarioTerras;
import modelo.negocio.Perfil;
import util.Calc;
import util.Pair;
import util.Cast;


/**
 *
 * @author Alexandre
 */
public class VisualizarInventario extends javax.swing.JFrame {

    private GenericTableRowEditor tabelaMaquinasGTRE;
    private GenericTableRowEditor tabelaBenfeitoriasGTRE;
    private GenericTableRowEditor tabelaTerrasGTRE;
    private GenericTableRowEditor tabelaForrageirasGTRE;
    private GenericTableRowEditor tabelaAnimaisProdGTRE;
    private GenericTableRowEditor tabelaAnimaisServGTRE;
    
    private InventarioResumo resumo;
    private Perfil perfilAtual;
    
    private InventarioTerrasDAO       itdao;
    private InventarioAnimaisDAO      iadao;
    private InventarioBenfeitoriasDAO ibdao;
    private InventarioMaquinasDAO     imdao;
    private InventarioResumoDAO       irdao;
    
    public VisualizarInventario() {
        
        initComponents();
             
        showTableLines();
   
        setLocationRelativeTo(null);
        this.setResizable(false);
        
        Calendar cal = GregorianCalendar.getInstance();
	int anoAtual = cal.get(Calendar.YEAR);
        int mesAtual = cal.get(Calendar.MONTH);
        
        ArrayList<InventarioTerras> terras = new ArrayList<>();
        ArrayList<InventarioAnimais> animais = new ArrayList<>();
        ArrayList<InventarioBenfeitorias> benfeitorias = new ArrayList<>();
        ArrayList<InventarioMaquinas> maquinas = new ArrayList<>();
        
        inicializarGTRE();
        
        //Totais
        double totalAreaArreInic  = 0;
        double totalAreaArreFina  = 0;
        double totalHa            = 0;
        double totalValorHa       = 0;
        double totalDepreciacao   = 0;
        double totalValInicProd   = 0;
        double totalValInicServ   = 0;
        double totalNascProd      = 0;
        double totalMorteProd     = 0;
        double totalVendaProd     = 0;
        double totalCompraProd    = 0;
        double totalNascServ      = 0;
        double totalMorteServ     = 0;
        double totalVendaServ     = 0;
        double totalCompraServ    = 0;
        double totalValFinaProd   = 0;
        double totalValorInicio   = 0;
        double totalValorFinal    = 0;
        double totalValorBenfeit  = 0;
        double totalValorMaquin   = 0;
        double totalDeprecBenfeit = 0;
        double totalDeprecMaquin  = 0;
        
        ArrayList<Double> totalAreaPropInic  = new ArrayList<>();
        ArrayList<Double> totalAreaPropFina  = new ArrayList<>();
        ArrayList<Double> totalTerraNua      = new ArrayList<>();
        ArrayList<Double> totalValFinaServ   = new ArrayList<>();
        ArrayList<Double> totalValCabeServ   = new ArrayList<>(); 
            
        itdao = new InventarioTerrasDAO();
        iadao = new InventarioAnimaisDAO();
        ibdao = new InventarioBenfeitoriasDAO();
        imdao = new InventarioMaquinasDAO();
        irdao = new InventarioResumoDAO();
        
        perfilAtual = ControlePerfil.getInstance().getPerfilSelecionado();
     
        try {
            terras = itdao.recuperarPorPerfil(perfilAtual.getId());
            animais = iadao.recuperarPorPerfil(perfilAtual.getId());
            benfeitorias = ibdao.recuperarPorPerfil(perfilAtual.getId());
            maquinas = imdao.recuperarPorPerfil(perfilAtual.getId());
            resumo = irdao.recuperarPorPerfil(perfilAtual.getId());
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        if(resumo == null){
            
            resumo = new InventarioResumo();
            resumo.setPerfil(perfilAtual);
            resumo.setMes(mesAtual);
            resumo.setAno(anoAtual);
            
            try {
                irdao.cadastrar(resumo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar um novo resumo");
            }
            
        }
        
        tabelaTerrasGTRE.getSourceTableModel().setRowCount(0);
          
        for(int i = 0; i < terras.size(); i++){
            
            tabelaTerrasGTRE.addSourceTableRow(new Object[]{
                terras.get(i).getEspecificacao(),
                terras.get(i).getAreaArrendadaInicio(),
                terras.get(i).getAreaPropriaInicio(),
                terras.get(i).getAreaArrendadaFinal(),
                terras.get(i).getAreaPropriaFinal(),
                terras.get(i).getValorTerraNuaPropria(),
                
            }, terras.get(i).getId());
            
            totalAreaArreInic += (terras.get(i).getAreaArrendadaInicio());
            totalAreaPropInic.add(terras.get(i).getAreaPropriaInicio());
            totalAreaArreFina += (terras.get(i).getAreaArrendadaFinal());
            totalAreaPropFina.add(terras.get(i).getAreaPropriaFinal());
            totalTerraNua.add(terras.get(i).getValorTerraNuaPropria());
            
        }
        verificaTabelaVazia(tabelaTerrasGTRE.getSourceTableModel(), editarInvTerrasBT, removerInvTerrasBT);
        
        tabelaForrageirasGTRE.getSourceTableModel().setRowCount(0);
        
        for(int i = 0; i < terras.size(); i++){
            
            double ha = (terras.get(i).getAreaPropriaInicio() + terras.get(i).getAreaPropriaFinal())/2;
            double valorHa = terras.get(i).getCustoFormacaoHectare() * ha;
            double depreciacao = valorHa/terras.get(i).getVidaUtil();
            
            tabelaForrageirasGTRE.addSourceTableRow(new Object[]{
                terras.get(i).getEspecificacao(),
                terras.get(i).getCustoFormacaoHectare(),
                ha,
                valorHa,
                terras.get(i).getVidaUtil(),
                depreciacao,
                
            }, terras.get(i).getId());
            
            totalHa += (ha);
            totalValorHa += (valorHa);
            totalDepreciacao += (depreciacao);
        }
        verificaTabelaVazia(tabelaForrageirasGTRE.getSourceTableModel(), editarInvTerrasBT, removerInvTerrasBT);
        
        total1.setText("" + (totalAreaArreInic));
        total2.setText("" + Calc.somarLista(totalAreaPropInic));
        total3.setText("" + (totalAreaArreFina));
        total4.setText("" + Calc.somarLista(totalAreaPropFina));
        total5.setText("" + (Double.parseDouble(total1.getText()) + Double.parseDouble(total2.getText())));
        total6.setText("" + (Double.parseDouble(total3.getText()) + Double.parseDouble(total4.getText())));
        total7.setText(String.format("R$ %.2f", Calc.somaPonderada(totalAreaPropInic, totalTerraNua)).replace(',','.'));
        total8.setText(String.format("R$ %.2f", Calc.somaPonderada(totalAreaPropFina, totalTerraNua)).replace(',','.'));
        total9.setText(String.format("R$ %.2f", Calc.mediaAritmetica(Double.parseDouble(total7.getText().substring(2)), Double.parseDouble(total8.getText().substring(2)))).replace(',','.'));
        total10.setText("" + (totalHa));
        total11.setText(String.format("R$ %.2f", totalValorHa));
        total12.setText(String.format("R$ %.2f", totalDepreciacao));
        
        tabelaAnimaisProdGTRE.getSourceTableModel().setRowCount(0);
        tabelaAnimaisServGTRE.getSourceTableModel().setRowCount(0);
           
        for(int i = 0; i < animais.size(); i++){
            if(animais.get(i).getTipoAnimal() == 1){ //Producao

                double valorInicio = animais.get(i).getValorInicio() * animais.get(i).getValorCabeca();
                double valorFinal = animais.get(i).getValorFinal() * animais.get(i).getValorCabeca();
                
                if(animais.get(i).getCategoria().equals("Touro")){
                    total34.setText(String.format("R$ %.2f", Calc.mediaAritmetica(valorInicio, valorFinal)));
                }
                
                tabelaAnimaisProdGTRE.addSourceTableRow(new Object[]{
                    animais.get(i).getCategoria(),
                    animais.get(i).getValorInicio(),
                    animais.get(i).getNascimento(),
                    animais.get(i).getMorte(),
                    animais.get(i).getVenda(),
                    animais.get(i).getCompra(),
                    animais.get(i).getValorFinal(),
                    animais.get(i).getValorCabeca(),
                    valorInicio,
                    valorFinal,
                }, animais.get(i).getId());
                
                    totalValInicProd += (animais.get(i).getValorInicio() * 1.0);  
                    totalNascProd += (animais.get(i).getNascimento() * 1.0);
                    totalMorteProd += (animais.get(i).getMorte() * 1.0); 
                    totalVendaProd += (animais.get(i).getVenda() * 1.0); 
                    totalCompraProd += (animais.get(i).getCompra() * 1.0);
                    totalValFinaProd += (animais.get(i).getValorFinal() * 1.0);
                    totalValorInicio += (valorInicio);
                    totalValorFinal += (valorFinal);

                } else if (animais.get(i).getTipoAnimal() == 2) { //servico

                    tabelaAnimaisServGTRE.addSourceTableRow(new Object[]{
                        animais.get(i).getCategoria(),
                        animais.get(i).getValorInicio(),
                        animais.get(i).getNascimento(),
                        animais.get(i).getMorte(),
                        animais.get(i).getVenda(),
                        animais.get(i).getCompra(),
                        animais.get(i).getValorFinal(),
                        animais.get(i).getValorCabeca(),
                        
                    }, animais.get(i).getId());

                    totalValInicServ += (animais.get(i).getValorInicio() * 1.0);
                    totalNascServ += (animais.get(i).getNascimento() * 1.0);  
                    totalMorteServ += (animais.get(i).getMorte() * 1.0);
                    totalVendaServ += (animais.get(i).getVenda() * 1.0); 
                    totalCompraServ += (animais.get(i).getCompra() * 1.0);
                    totalValFinaServ.add(animais.get(i).getValorFinal() * 1.0);
                    totalValCabeServ.add(animais.get(i).getValorCabeca() * 1.0);
                }

            }
            if (tabelaInveAnimaisProd.getRowCount() == 0 && tabelaInveAnimaisServ.getRowCount() == 0) {
                editarInvAnimaisBT.setEnabled(false);
                removerInvAnimaisBT.setEnabled(false);
            }

            total13.setText("" + (totalValInicProd));
            total14.setText("" + (totalNascProd));
            total15.setText("" + (totalMorteProd));
            total16.setText("" + (totalVendaProd));
            total17.setText("" + (totalCompraProd));
            total18.setText("" + (totalValFinaProd));
                        
            total19.setText(String.format("R$ %.2f", totalValorInicio));
            total20.setText(String.format("R$ %.2f", totalValorFinal));
            total21.setText("" + (totalValInicServ));
            total22.setText("" + (totalNascServ));
            total23.setText("" + (totalMorteServ));
            total24.setText("" + (totalVendaServ));
            total25.setText("" + (totalCompraServ));
            total26.setText("" + Calc.somarLista(totalValFinaServ));

            total28.setText("" + (Double.parseDouble(total13.getText().replace(',','.')) + 
                    Double.parseDouble(total21.getText().replace(',','.'))));
            total29.setText("" + (Double.parseDouble(total26.getText().replace(',','.')) +
                    Double.parseDouble(total18.getText().substring(1).replace(',','.'))));

            total31.setText(String.format("R$ %.2f", ((Double.parseDouble(total19.getText().substring(2).replace(',','.')) +
                    Double.parseDouble(total20.getText().substring(2).replace(',','.'))) / 2 )));
            
            if(resumo != null){
                total32.setText(String.format("R$ %.2f", resumo.getValorGastoCompraAnimais()));
                total35.setText(String.format("%d", resumo.getVidaUtilReprodutores()));
            }
            
            total33.setText(String.format("R$ %.2f", (Double.parseDouble(total20.getText().substring(2).replace(',','.')) -
                        Double.parseDouble(total19.getText().substring(2).replace(',','.')) - Double.parseDouble(total32.getText().substring(2).replace(',','.')))));
            
            try{
                total36.setText(String.format("%.2f", Calc.dividir(Double.parseDouble(total34.getText().substring(2).replace(',','.')),
                        Double.parseDouble(total35.getText().replace(',','.')))));
            } catch (IllegalArgumentException e) {
                total36.setText("0.00");
                //System.out.println("Erro em total 36 - Divisão Inválida. " + e.getMessage());
            }
            
            total37.setText(String.format("R$ %.2f", Calc.somaPonderada(totalValFinaServ, totalValCabeServ)));
            
            if(resumo != null){
                total38.setText(String.format("%d", resumo.getVidaUtilAnimaisServico()));
            }
            
            try{
                total39.setText(String.format("%.2f", Calc.dividir(Double.parseDouble(total37.getText().substring(2).replace(',','.')), 
                        Double.parseDouble(total38.getText().replace(',','.')))));
            } catch (IllegalArgumentException e){
               total39.setText("0.00"); // Caso haja divisão por 0
               //System.out.println("Erro em total39 - Divisão Inválida " + e.getMessage());
            }
                
            tabelaBenfeitoriasGTRE.getSourceTableModel().setRowCount(0);

            for(int i = 0; i < benfeitorias.size(); i++){

                double total = benfeitorias.get(i).getQuantidade() * benfeitorias.get(i).getValorUnitario();
                double depreciacao = Calc.dividir(total, benfeitorias.get(i).getVidaUtil());

                tabelaBenfeitoriasGTRE.addSourceTableRow(new Object[] {
                    benfeitorias.get(i).getEspecificacao(),
                    benfeitorias.get(i).getUnidade(),
                    benfeitorias.get(i).getQuantidade(),
                    benfeitorias.get(i).getValorUnitario(),
                    total,
                    benfeitorias.get(i).getVidaUtil(),
                    depreciacao,
                    
                }, benfeitorias.get(i).getId());

                totalValorBenfeit += (total);
                totalDeprecBenfeit += (depreciacao);
            }
            
            verificaTabelaVazia(tabelaBenfeitoriasGTRE.getSourceTableModel(), editarInvBenfeitoriasBT, removerInvBenfeitoriasBT);

            total40.setText(String.format("R$ %.2f", totalValorBenfeit));
            total41.setText(String.format("R$ %.2f", totalDeprecBenfeit));
            tabelaMaquinasGTRE.getSourceTableModel().setRowCount(0);

            for(int i = 0; i < maquinas.size(); i++){

                double total = maquinas.get(i).getQuantidade() * maquinas.get(i).getValorUnitario();
                double depreciacao = Calc.dividir(total, maquinas.get(i).getVidaUtil());

                tabelaMaquinasGTRE.addSourceTableRow(new Object[] {
                    maquinas.get(i).getEspecificacao(),
                    maquinas.get(i).getUnidade(),
                    maquinas.get(i).getQuantidade(),
                    maquinas.get(i).getValorUnitario(),
                    total,
                    maquinas.get(i).getVidaUtil(),
                    depreciacao,
                    
                }, maquinas.get(i).getId());

                totalValorMaquin += (total);
                totalDeprecMaquin += (depreciacao);
            }
            verificaTabelaVazia(tabelaMaquinasGTRE.getSourceTableModel(), editarInvMaquinasBT, removerInvMaquinasBT);

            total42.setText(String.format("R$ %.2f", totalValorMaquin));
            total43.setText(String.format("R$ %.2f", totalDeprecMaquin));

            //Resumo
            if(resumo != null){
                atividadeLeite.setText(String.format("%.2f", resumo.getAtividadeLeiteira()));
                custoOportunidade.setText(String.format("%.2f", resumo.getCustoOportunidade()));
                salarioMinimo.setText(String.format("%.2f", resumo.getSalarioMinimo()));
            }
            
            total44.setText(total12.getText().substring(2));
            total45.setText(total39.getText());
            total46.setText(total36.getText());
            total47.setText(String.format("%.2f", (Double.parseDouble(total41.getText().substring(2).replace(',','.')))));
            total48.setText(total43.getText().substring(2));
            total49.setText(String.format("R$ %.2f", (Double.parseDouble(total44.getText().replace(',','.')) + 
                                  Double.parseDouble(total45.getText().replace(',','.')) +
                                  Double.parseDouble(total46.getText().replace(',','.')) +
                                  Double.parseDouble(total47.getText().replace(',','.')) +
                                  Double.parseDouble(total48.getText().replace(',','.')))));
            total50.setText(String.format("R$ %.2f", ((Double.parseDouble(atividadeLeite.getText().replace(',','.'))/100) * 
                    Double.parseDouble(total49.getText().substring(2).replace(',','.')))));

            total51.setText(total9.getText().substring(2));
            total52.setText(total12.getText().substring(2));
            total53.setText(total31.getText().substring(2));
            total54.setText(total40.getText().substring(2));
            total55.setText(total42.getText().substring(2));
            total56.setText(String.format("R$ %.2f", (Double.parseDouble(total51.getText()) + 
                                  Double.parseDouble(total52.getText().replace(',','.')) +
                                  Double.parseDouble(total53.getText().replace(',','.')) +
                                  Double.parseDouble(total54.getText().replace(',','.')) +
                                  Double.parseDouble(total55.getText().replace(',','.')))));
            total57.setText(String.format("R$ %.2f", ((Double.parseDouble(custoOportunidade.getText().replace(',','.')))/100 * 
                    Double.parseDouble(total56.getText().substring(2).replace(',','.')))));
   
            total58.setText(salarioMinimo.getText());
            total59.setText(String.format("%.2f", (Double.parseDouble(salarioMinimo.getText().replace(',','.')) * 0.3)));
            total60.setText(String.format("R$ %.2f", ((Double.parseDouble(total58.getText().replace(',','.')) * 13 + 
                                  (Double.parseDouble(total58.getText().replace(',','.'))) * 0.3)) / 12));
            
            definirBDListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaInveTerras = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaInveForrageiras = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        total4 = new javax.swing.JLabel();
        total3 = new javax.swing.JLabel();
        total7 = new javax.swing.JLabel();
        total6 = new javax.swing.JLabel();
        total8 = new javax.swing.JLabel();
        total5 = new javax.swing.JLabel();
        total9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        total10 = new javax.swing.JLabel();
        total11 = new javax.swing.JLabel();
        total12 = new javax.swing.JLabel();
        editarInvTerrasBT = new javax.swing.JButton();
        adicionarInvTerrasBT = new javax.swing.JButton();
        removerInvTerrasBT = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaInveAnimaisServ = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        total13 = new javax.swing.JLabel();
        total14 = new javax.swing.JLabel();
        total15 = new javax.swing.JLabel();
        total16 = new javax.swing.JLabel();
        total17 = new javax.swing.JLabel();
        total18 = new javax.swing.JLabel();
        total19 = new javax.swing.JLabel();
        total20 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaInveAnimaisProd = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        total21 = new javax.swing.JLabel();
        total22 = new javax.swing.JLabel();
        total23 = new javax.swing.JLabel();
        total24 = new javax.swing.JLabel();
        total25 = new javax.swing.JLabel();
        total26 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        total28 = new javax.swing.JLabel();
        total29 = new javax.swing.JLabel();
        total31 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        total32 = new javax.swing.JLabel();
        total33 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        total34 = new javax.swing.JLabel();
        total35 = new javax.swing.JLabel();
        total36 = new javax.swing.JLabel();
        total37 = new javax.swing.JLabel();
        total38 = new javax.swing.JLabel();
        total39 = new javax.swing.JLabel();
        editarInvAnimaisBT = new javax.swing.JButton();
        valorGastoAnimaisBT = new javax.swing.JButton();
        removerInvAnimaisBT = new javax.swing.JButton();
        adicionarInvAnimaisBT = new javax.swing.JButton();
        vidaUtilReprodBT = new javax.swing.JButton();
        vidaUtilServBT = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaBenfeitorias = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        total40 = new javax.swing.JLabel();
        total41 = new javax.swing.JLabel();
        editarInvBenfeitoriasBT = new javax.swing.JButton();
        adicionarInvBenfeitoriasBT = new javax.swing.JButton();
        removerInvBenfeitoriasBT = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelaMaquinas = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        total42 = new javax.swing.JLabel();
        total43 = new javax.swing.JLabel();
        editarInvMaquinasBT = new javax.swing.JButton();
        removerInvMaquinasBT = new javax.swing.JButton();
        adicionarInvMaquinasBT = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        total44 = new javax.swing.JLabel();
        total45 = new javax.swing.JLabel();
        total46 = new javax.swing.JLabel();
        total47 = new javax.swing.JLabel();
        total48 = new javax.swing.JLabel();
        total49 = new javax.swing.JLabel();
        total50 = new javax.swing.JLabel();
        total51 = new javax.swing.JLabel();
        total52 = new javax.swing.JLabel();
        total53 = new javax.swing.JLabel();
        total54 = new javax.swing.JLabel();
        total55 = new javax.swing.JLabel();
        total56 = new javax.swing.JLabel();
        total57 = new javax.swing.JLabel();
        total58 = new javax.swing.JLabel();
        total59 = new javax.swing.JLabel();
        total60 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        atividadeLeiteBT = new javax.swing.JButton();
        custoOportBT = new javax.swing.JButton();
        salarioMinimoBT = new javax.swing.JButton();
        atividadeLeite = new javax.swing.JLabel();
        custoOportunidade = new javax.swing.JLabel();
        salarioMinimo = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(38, 81));

        tabelaInveTerras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Área Arrendada INÍCIO", "Área Própria INÍCIO", "Área Arrendada FINAL", "Área Própria FINAL", "Valor da terra Nua Própria(R$/Ha)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInveTerras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaInveTerras.getTableHeader().setReorderingAllowed(false);
        tabelaInveTerras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaInveTerrasFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaInveTerras);
        if (tabelaInveTerras.getColumnModel().getColumnCount() > 0) {
            tabelaInveTerras.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabelaInveTerras.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(1).setPreferredWidth(20);
            tabelaInveTerras.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(2).setPreferredWidth(20);
            tabelaInveTerras.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(3).setPreferredWidth(20);
            tabelaInveTerras.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(4).setPreferredWidth(20);
            tabelaInveTerras.getColumnModel().getColumn(5).setResizable(false);
        }

        tabelaInveForrageiras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Forrageiras Não Anuais", "R$/Ha", "Ha", "R$/total", "Vida Útil Anos", "Depreciação(R$/Ano)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInveForrageiras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaInveForrageiras.getTableHeader().setReorderingAllowed(false);
        tabelaInveForrageiras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaInveForrageirasFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaInveForrageiras);
        if (tabelaInveForrageiras.getColumnModel().getColumnCount() > 0) {
            tabelaInveForrageiras.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabelaInveForrageiras.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(1).setPreferredWidth(20);
            tabelaInveForrageiras.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(2).setPreferredWidth(20);
            tabelaInveForrageiras.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(3).setPreferredWidth(20);
            tabelaInveForrageiras.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(4).setPreferredWidth(20);
            tabelaInveForrageiras.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("TOTAL PARA PECUÁRIA");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("ÁREA TOTAL UTILIZADA PARA PECUÁRIA");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("VALOR DA TERRA NUA PRÓPRIA");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("VALOR DA TERRA NUA PRÓPRIA MÉDIO");

        total1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total1.setText("<total1>");

        total2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total2.setText("<total2>");

        total4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total4.setText("<total4>");

        total3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total3.setText("<total3>");

        total7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total7.setText("<total7>");

        total6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total6.setText("<total6>");

        total8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total8.setText("<total8>");

        total5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total5.setText("<total5>");

        total9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total9.setText("<total9>");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("TOTAL");

        total10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total10.setText("<total10>");

        total11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total11.setText("<total11>");

        total12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total12.setText("<total12>");

        editarInvTerrasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarInvTerrasBT.setPreferredSize(new java.awt.Dimension(80, 25));
        editarInvTerrasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarInvTerrasBTActionPerformed(evt);
            }
        });

        adicionarInvTerrasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarInvTerrasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarInvTerrasBTActionPerformed(evt);
            }
        });

        removerInvTerrasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/delete.png"))); // NOI18N
        removerInvTerrasBT.setPreferredSize(new java.awt.Dimension(80, 50));
        removerInvTerrasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerInvTerrasBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(total5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(total1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(total7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(total6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(total3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(total8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(total9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(167, 167, 167))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addComponent(total10, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(total12, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(adicionarInvTerrasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editarInvTerrasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removerInvTerrasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(total1)
                    .addComponent(total2)
                    .addComponent(total3)
                    .addComponent(total4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(total5)
                    .addComponent(total6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(total7)
                    .addComponent(total8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(total9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(total10)
                    .addComponent(total11)
                    .addComponent(total12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adicionarInvTerrasBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editarInvTerrasBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removerInvTerrasBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Terras", jPanel1);

        tabelaInveAnimaisServ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Categoria", "Início", "Nasci/to", "Morte", "Venda", "Compra", "Final", "Valor (R$/Cab)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInveAnimaisServ.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaInveAnimaisServ.getTableHeader().setReorderingAllowed(false);
        tabelaInveAnimaisServ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaInveAnimaisServFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaInveAnimaisServ);
        if (tabelaInveAnimaisServ.getColumnModel().getColumnCount() > 0) {
            tabelaInveAnimaisServ.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaInveAnimaisServ.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(5).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(6).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TOTAL DE ANIMAIS DE PRODUÇÃO");

        total13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total13.setText("<total13>");

        total14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total14.setText("<total14>");

        total15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total15.setText("<total15>");

        total16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total16.setText("<total16>");

        total17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total17.setText("<total17>");

        total18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total18.setText("<total18>");

        total19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total19.setText("<total19>");

        total20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total20.setText("<total20>");

        tabelaInveAnimaisProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Categoria", "Início", "Nasci/to", "Morte", "Venda", "Compra", "Final", "Valor (R$/Cab)", "Valor Inicial(R$))", "Valor Final(R$)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInveAnimaisProd.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaInveAnimaisProd.getTableHeader().setReorderingAllowed(false);
        tabelaInveAnimaisProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaInveAnimaisProdFocusGained(evt);
            }
        });
        jScrollPane4.setViewportView(tabelaInveAnimaisProd);
        if (tabelaInveAnimaisProd.getColumnModel().getColumnCount() > 0) {
            tabelaInveAnimaisProd.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaInveAnimaisProd.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(5).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(6).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(7).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(8).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(9).setResizable(false);
        }

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TOTAL DE ANIMAIS DE SERVIÇO");

        total21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total21.setText("<total21>");

        total22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total22.setText("<total22>");

        total23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total23.setText("<total23>");

        total24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total24.setText("<total24>");

        total25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total25.setText("<total25>");

        total26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total26.setText("<total26>");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TOTAL DE ANIMAIS");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("VALOR MÉDIO DO REBANHO DE PRODUÇÃO");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Valor Gasto com Compra de Animais");

        total28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total28.setText("<total28>");

        total29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total29.setText("<total29>");

        total31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total31.setText("<total31>");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("VARIAÇÃO DE INVENTÁRIO ANIMAL");

        total32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total32.setText("R$ 0.0");

        total33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total33.setText("R$ 0.0");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Dados Adicionais");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Capital Investido em Reprodutores");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Vida Útil dos Reprodutores (anos)");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Depreciação dos Reprodutores - R$/ano");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Capital Investido em Animais de Serviços");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Vida Útil dos Animais de Serviços (anos)");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Depreciação dos Animais de Serviços - R$/ano");

        total34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total34.setText("R$ 0,00");

        total35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total35.setText("0");

        total36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total36.setText("<total36>");

        total37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total37.setText("R$ 0,00");

        total38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total38.setText("0");

        total39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total39.setText("<total39>");

        editarInvAnimaisBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarInvAnimaisBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarInvAnimaisBTActionPerformed(evt);
            }
        });

        valorGastoAnimaisBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        valorGastoAnimaisBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valorGastoAnimaisBTActionPerformed(evt);
            }
        });

        removerInvAnimaisBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/delete.png"))); // NOI18N
        removerInvAnimaisBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerInvAnimaisBTActionPerformed(evt);
            }
        });

        adicionarInvAnimaisBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarInvAnimaisBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarInvAnimaisBTActionPerformed(evt);
            }
        });

        vidaUtilReprodBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        vidaUtilReprodBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vidaUtilReprodBTActionPerformed(evt);
            }
        });

        vidaUtilServBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        vidaUtilServBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vidaUtilServBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total32, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valorGastoAnimaisBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total33, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(total34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(total36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(total35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vidaUtilReprodBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(total37, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                            .addComponent(total38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(total39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(vidaUtilServBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(adicionarInvAnimaisBT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editarInvAnimaisBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removerInvAnimaisBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total13, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total16, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total17, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(total19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total20, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(total31, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total21, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(231, 231, 231)
                                        .addComponent(total28, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(total22, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(total23, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(total24, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(total25, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(total29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total26, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 1, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(total13)
                    .addComponent(total20)
                    .addComponent(total19)
                    .addComponent(total14)
                    .addComponent(total15)
                    .addComponent(total16)
                    .addComponent(total17)
                    .addComponent(total18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(total21)
                    .addComponent(total22)
                    .addComponent(total23)
                    .addComponent(total24)
                    .addComponent(total25)
                    .addComponent(total26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total29)
                    .addComponent(jLabel7)
                    .addComponent(total28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(total31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valorGastoAnimaisBT)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(total32, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(total33)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(total34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(total37, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(total38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(total35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(vidaUtilReprodBT)))
                            .addComponent(vidaUtilServBT))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(total36)
                            .addComponent(total39))
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(removerInvAnimaisBT)
                            .addComponent(editarInvAnimaisBT)
                            .addComponent(adicionarInvAnimaisBT))
                        .addGap(24, 24, 24))))
        );

        jTabbedPane1.addTab("Animais", jPanel2);

        tabelaBenfeitorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Unidade", "Quantidade", "Valor Unitário (R$)", "Valor Total (R$)", "Vida Útil (anos)", "R$/ano"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaBenfeitorias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaBenfeitorias.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tabelaBenfeitorias);
        if (tabelaBenfeitorias.getColumnModel().getColumnCount() > 0) {
            tabelaBenfeitorias.getColumnModel().getColumn(0).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaBenfeitorias.getColumnModel().getColumn(1).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(2).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(3).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(4).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(5).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("DEPRECIAÇÃO");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("VALOR DO PREÇO DE NOVO");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("TOTAL");

        total40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total40.setText("<total40>");

        total41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total41.setText("<total41>");

        editarInvBenfeitoriasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarInvBenfeitoriasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarInvBenfeitoriasBTActionPerformed(evt);
            }
        });

        adicionarInvBenfeitoriasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarInvBenfeitoriasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarInvBenfeitoriasBTActionPerformed(evt);
            }
        });

        removerInvBenfeitoriasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/delete.png"))); // NOI18N
        removerInvBenfeitoriasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerInvBenfeitoriasBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                .addComponent(total40, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(total41, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(adicionarInvBenfeitoriasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editarInvBenfeitoriasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removerInvBenfeitoriasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total41)
                    .addComponent(total40)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removerInvBenfeitoriasBT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editarInvBenfeitoriasBT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(adicionarInvBenfeitoriasBT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Benfeitorias", jPanel3);

        tabelaMaquinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Unidade", "Quantidade", "Valor Unitário (R$)", "Valor Total (R$)", "Vida Útil (anos)", "R$/ano"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaMaquinas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaMaquinas.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tabelaMaquinas);
        if (tabelaMaquinas.getColumnModel().getColumnCount() > 0) {
            tabelaMaquinas.getColumnModel().getColumn(0).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaMaquinas.getColumnModel().getColumn(1).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(2).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(3).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(4).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(5).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("VALOR DO PREÇO DE NOVO");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("DEPRECIAÇÃO");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("TOTAL");

        total42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total42.setText("<total42>");

        total43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total43.setText("<total43>");

        editarInvMaquinasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarInvMaquinasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarInvMaquinasBTActionPerformed(evt);
            }
        });

        removerInvMaquinasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/delete.png"))); // NOI18N
        removerInvMaquinasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerInvMaquinasBTActionPerformed(evt);
            }
        });

        adicionarInvMaquinasBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarInvMaquinasBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarInvMaquinasBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane7)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(total42, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(total43, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(852, Short.MAX_VALUE)
                .addComponent(adicionarInvMaquinasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editarInvMaquinasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removerInvMaquinasBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total43)
                    .addComponent(total42)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removerInvMaquinasBT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editarInvMaquinasBT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(adicionarInvMaquinasBT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Máquinas", jPanel4);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel26.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel26.setText("RESUMO DA DEPRECIAÇÃO (em reais)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(20, 1, 1, 1);
        jPanel5.add(jLabel26, gridBagConstraints);

        jLabel27.setText("Forrageiras não anuais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel27, gridBagConstraints);

        jLabel28.setText("Animais de trabalho");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel28, gridBagConstraints);

        jLabel29.setText("Reprodutores");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel29, gridBagConstraints);

        jLabel30.setText("Benfeitorias utilizadas para pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel30, gridBagConstraints);

        jLabel31.setText("Máquinas utilizadas na pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel31, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 20, 0, 0);
        jPanel5.add(jLabel32, gridBagConstraints);

        jLabel33.setText("Leite/atividade leiteira (%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel33, gridBagConstraints);

        jLabel34.setText("Depreciação do leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel34, gridBagConstraints);

        jLabel35.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel35.setText("RESUMO DO INVENTÁRIO (em reais)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(20, 1, 1, 1);
        jPanel5.add(jLabel35, gridBagConstraints);

        jLabel36.setText("Terras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel36, gridBagConstraints);

        jLabel37.setText("Forrageiras não anuais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel37, gridBagConstraints);

        jLabel38.setText("Animais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel38, gridBagConstraints);

        jLabel39.setText("Benfeitorias utilizadas na pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel39, gridBagConstraints);

        jLabel40.setText("Máquinas utilizadas na pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel40, gridBagConstraints);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 20, 0, 0);
        jPanel5.add(jLabel41, gridBagConstraints);

        jLabel42.setText("Salário mínimo (R$)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel42, gridBagConstraints);

        jLabel43.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        jPanel5.add(jLabel43, gridBagConstraints);

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText("Capital empatado leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel44, gridBagConstraints);

        jLabel45.setText("Custo de oportunidade (%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel45, gridBagConstraints);

        jLabel46.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel46.setText("MÃO DE OBRA FAMILIAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(20, 1, 1, 1);
        jPanel5.add(jLabel46, gridBagConstraints);

        jLabel47.setText("Décimo terceiro (R$) ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel47, gridBagConstraints);

        jLabel48.setText("Terço de férias (R$)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel48, gridBagConstraints);

        total44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total44.setText("<total44>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total44, gridBagConstraints);

        total45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total45.setText("<total45>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total45, gridBagConstraints);

        total46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total46.setText("<total46>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total46, gridBagConstraints);

        total47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total47.setText("<total47>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total47, gridBagConstraints);

        total48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total48.setText("<total48>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total48, gridBagConstraints);

        total49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total49.setText("<total49>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total49, gridBagConstraints);

        total50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total50.setText("<total50>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total50, gridBagConstraints);

        total51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total51.setText("<total51>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total51, gridBagConstraints);

        total52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total52.setText("<total52>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total52, gridBagConstraints);

        total53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total53.setText("<total53>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total53, gridBagConstraints);

        total54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total54.setText("<total54>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total54, gridBagConstraints);

        total55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total55.setText("<total55>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total55, gridBagConstraints);

        total56.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total56.setText("<total56>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total56, gridBagConstraints);

        total57.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total57.setText("<total57>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total57, gridBagConstraints);

        total58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total58.setText("<total58>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total58, gridBagConstraints);

        total59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total59.setText("<total59>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total59, gridBagConstraints);

        total60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total60.setText("<total60>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(total60, gridBagConstraints);

        jLabel49.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        jPanel5.add(jLabel49, gridBagConstraints);

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel50.setText("Custo total do salário mensal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel50, gridBagConstraints);

        atividadeLeiteBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        atividadeLeiteBT.setPreferredSize(new java.awt.Dimension(47, 29));
        atividadeLeiteBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atividadeLeiteBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel5.add(atividadeLeiteBT, gridBagConstraints);

        custoOportBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        custoOportBT.setPreferredSize(new java.awt.Dimension(47, 29));
        custoOportBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                custoOportBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel5.add(custoOportBT, gridBagConstraints);

        salarioMinimoBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        salarioMinimoBT.setPreferredSize(new java.awt.Dimension(47, 29));
        salarioMinimoBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salarioMinimoBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel5.add(salarioMinimoBT, gridBagConstraints);

        atividadeLeite.setText("     0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(atividadeLeite, gridBagConstraints);

        custoOportunidade.setText(" 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(custoOportunidade, gridBagConstraints);

        salarioMinimo.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(salarioMinimo, gridBagConstraints);

        jTabbedPane1.addTab("Resumo", jPanel5);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(51, 153, 255));
        textoEntrada.setText("INVENTÁRIO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textoEntrada)
                .addGap(381, 381, 381))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(textoEntrada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
	private void calcularValoresInvTerras(Object[] terrasRowData, Object[] forrageirasRowData, int forrageirasRow){
      
        forrageirasRowData[2] = Calc.dividir(Cast.toDouble(terrasRowData[2]) + Cast.toDouble(terrasRowData[4]), 2.0);
        //R$/Total = (R$/Ha) * Ha
        forrageirasRowData[3] = Cast.toDouble(forrageirasRowData[1]) * Cast.toDouble(forrageirasRowData[2]);
        //Depreciacao (R$/Ano) = (R$/Total) / VidaUtilAnos
        forrageirasRowData[5] = Calc.dividir(Cast.toDouble(forrageirasRowData[3]), (double)Cast.toInt(forrageirasRowData[4]));

        for (int i = 0; i < tabelaInveForrageiras.getColumnCount(); i++) {
            tabelaInveForrageiras.setValueAt(forrageirasRowData[i], forrageirasRow, i);
        }
    }
	
    private void inicializarGTRE() {
        tabelaMaquinasGTRE = new GenericTableRowEditor(this, tabelaMaquinas, false);       
        tabelaBenfeitoriasGTRE = new GenericTableRowEditor(this, tabelaBenfeitorias, false);
        tabelaForrageirasGTRE = new GenericTableRowEditor(this, tabelaInveForrageiras, false);
        tabelaTerrasGTRE = new GenericTableRowEditor(this, tabelaInveTerras, false);
        tabelaAnimaisProdGTRE = new GenericTableRowEditor(this, tabelaInveAnimaisProd, false);
        tabelaAnimaisServGTRE = new GenericTableRowEditor(this, tabelaInveAnimaisServ, false);
       
        tabelaForrageirasGTRE.setColumnEditable(2, false);
        tabelaForrageirasGTRE.setColumnEditable(3, false);
        tabelaForrageirasGTRE.setColumnEditable(5, false);
        
        tabelaAnimaisProdGTRE.setColumnEditable(8, false);
        tabelaAnimaisProdGTRE.setColumnEditable(9, false);
        
        tabelaBenfeitoriasGTRE.setColumnEditable(4, false);
        tabelaBenfeitoriasGTRE.setColumnEditable(6, false);
        
        tabelaMaquinasGTRE.setColumnEditable(4, false);
        tabelaMaquinasGTRE.setColumnEditable(6, false);
        
    }
    
    private void definirBDListeners(){
        
        tabelaMaquinasGTRE.addTableModifyListener(new TableModifyListener() {
            @Override
            public void tableModified(TableModifiedEvent event) {
                
                InventarioMaquinasDAO dao = new InventarioMaquinasDAO();
                Perfil perfil = ControlePerfil.getInstance().getPerfilSelecionado();
                
                Object[] rowData = event.getRowData();
                Integer rowID = (Integer)event.getCustomRowData();
                GenericTableModifier modifier = event.getSourceModifier();
                int modifType = event.getEventType();
                
                if(modifType == TableModifyListener.ROW_INSERTED){
                    
                    try {
                        InventarioMaquinas inv = new InventarioMaquinas(Cast.toString(rowData[0]),Cast.toString(rowData[1]),Cast.toDouble(rowData[2]),
                                                                        Cast.toDouble(rowData[3]),Cast.toInt(rowData[5]),perfil);
                        dao.cadastrar(inv);
                        
                        modifier.setCustomRowData(modifier.getSourceTable().getRowCount()-1, inv.getId());
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(modifType == TableModifyListener.ROW_UPDATED){
                    
                    try {
                        InventarioMaquinas inv = new InventarioMaquinas(Cast.toString(rowData[0]),Cast.toString(rowData[1]),Cast.toDouble(rowData[2]),
                                                                        Cast.toDouble(rowData[3]),Cast.toInt(rowData[5]),perfil);
                        inv.setId(rowID);
                        
                        dao.atualizar(inv);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(modifType == TableModifyListener.ROW_DELETED){
                    try {
                        dao.remover(rowID);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        tabelaBenfeitoriasGTRE.addTableModifyListener(new TableModifyListener() {
            @Override
            public void tableModified(TableModifiedEvent event) {
                
                InventarioBenfeitoriasDAO dao = new InventarioBenfeitoriasDAO();
                
                Perfil perfil = ControlePerfil.getInstance().getPerfilSelecionado();
                Object[] rowData = event.getRowData();
                Integer rowID = (Integer)event.getCustomRowData();
                GenericTableModifier modifier = event.getSourceModifier();
                int modifType = event.getEventType();
                
                if(modifType == TableModifyListener.ROW_INSERTED){
                    
                    try {
                        InventarioBenfeitorias inv = new InventarioBenfeitorias(Cast.toString(rowData[0]),Cast.toString(rowData[1]),Cast.toDouble(rowData[2]),
                                                                                Cast.toDouble(rowData[3]),Cast.toInt(rowData[5]),perfil);
                        dao.cadastrar(inv);
                        
                        modifier.setCustomRowData(modifier.getSourceTable().getRowCount()-1, inv.getId());  
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(modifType == TableModifyListener.ROW_UPDATED){
                    
                    try {
                        InventarioBenfeitorias inv = new InventarioBenfeitorias(Cast.toString(rowData[0]),Cast.toString(rowData[1]),Cast.toDouble(rowData[2]),
                                                                                Cast.toDouble(rowData[3]),Cast.toInt(rowData[5]),perfil);
                        inv.setId(rowID);
                        
                        dao.atualizar(inv);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(modifType == TableModifyListener.ROW_DELETED){
                    
                    try {
                        dao.remover(rowID);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VisualizarInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        tabelaTerrasGTRE.addTableModifyListener(new TableModifyListener() {
            @Override
            public void tableModified(TableModifiedEvent event) {

                GenericDAO<InventarioTerras> dao = new GenericDAO<>(InventarioTerras.class);
                Perfil perfil = ControlePerfil.getInstance().getPerfilSelecionado();
                
                Object[] rowData = event.getRowData();
                Integer rowID = (Integer) event.getCustomRowData();
                GenericTableModifier modifier = event.getSourceModifier();
                int modifType = event.getEventType();
                int rowIndex = event.getRowIndex();

                if (modifType == TableModifyListener.ROW_INSERTED || modifType == TableModifyListener.ROW_UPDATED) {

                    InventarioTerras terras = new InventarioTerras(Cast.toString(rowData[0]), Cast.toDouble(rowData[1]), Cast.toDouble(rowData[2]),
                            Cast.toDouble(rowData[3]), Cast.toDouble(rowData[4]), Cast.toDouble(rowData[5]), 0, 0, perfil);

                    if (modifType == TableModifyListener.ROW_INSERTED) {

                        tabelaForrageirasGTRE.addSourceTableRow(new Object[]{terras.getEspecificacao()}, null);
                        dao.insert(terras);
                        
                        modifier.setCustomRowData(rowIndex, terras.getId());
                        tabelaForrageirasGTRE.setCustomRowData(rowIndex, terras.getId());
                        
                    } else if (modifType == TableModifyListener.ROW_UPDATED) {

                        Object[] forrageirasRowData = tabelaForrageirasGTRE.getSourceTableRowData(rowIndex);

                        terras.setVidaUtil( Integer.parseInt(Cast.toString(forrageirasRowData[4])) );
                        terras.setCustoFormacaoHectare(Cast.toDouble(forrageirasRowData[1]));
                        terras.setId(rowID);
                        
                        calcularValoresInvTerras(rowData, forrageirasRowData, rowIndex);
                        
                        dao.update(terras);
                    }
                } else if (modifType == TableModifyListener.ROW_DELETED) {
                    dao.remove(rowID);
                }
            }
        });
        
        tabelaForrageirasGTRE.addTableModifyListener(new TableModifyListener() {
            @Override
            public void tableModified(TableModifiedEvent event) {

                if (event.getEventType() == TableModifyListener.ROW_UPDATED) {

                    GenericDAO<InventarioTerras> dao = new GenericDAO<>(InventarioTerras.class);
                    
                    Object[] rowData = event.getRowData();
                    GenericTableModifier modifier = event.getSourceModifier();
                    int rowIndex = event.getRowIndex();
                    Integer rowID = (Integer) event.getCustomRowData();

                    Object[] terrasRowData = tabelaTerrasGTRE.getSourceTableRowData(rowIndex);

                    calcularValoresInvTerras(terrasRowData, rowData, rowIndex);

                    InventarioTerras terras = dao.retrieve(rowID);
                    terras.setVidaUtil(Cast.toInt(rowData[4]));
                    terras.setCustoFormacaoHectare(Cast.toDouble(rowData[1]));
                    
                    dao.update(terras);
                }

            }
        });
        
        TableModifyListener animaisTMListener = new TableModifyListener() {
            @Override
            public void tableModified(TableModifiedEvent event) {

                GenericDAO<InventarioAnimais> dao = new GenericDAO<>(InventarioAnimais.class);

                Perfil perfil = ControlePerfil.getInstance().getPerfilSelecionado();
                Object[] rowData = event.getRowData();
                Integer rowID = (Integer) event.getCustomRowData();
                GenericTableModifier modifier = event.getSourceModifier();
                int modifType = event.getEventType();

                int tipoAnimal = 0;

                if (modifier.getSourceTable() == tabelaInveAnimaisProd) {
                    tipoAnimal = 1;
                } else if (modifier.getSourceTable() == tabelaInveAnimaisServ) {
                    tipoAnimal = 2;
                }

                if (modifType == TableModifyListener.ROW_INSERTED || modifType == TableModifyListener.ROW_UPDATED) {

                    InventarioAnimais inv = new InventarioAnimais(Cast.toString(rowData[0]), Cast.toInt(rowData[1]), Cast.toInt(rowData[2]),
                            Cast.toInt(rowData[3]), Cast.toInt(rowData[4]), Cast.toInt(rowData[5]), Cast.toInt(rowData[6]),
                            Cast.toDouble(rowData[7]), tipoAnimal, perfil);

                    if (modifType == TableModifyListener.ROW_INSERTED) {
                        dao.insert(inv);
                        modifier.setCustomRowData(modifier.getSourceTable().getRowCount() - 1, inv.getId());

                    } else if (modifType == TableModifyListener.ROW_UPDATED) {
                        inv.setId(rowID);
                        dao.update(inv);
                    }

                } else if (modifType == TableModifyListener.ROW_DELETED) {
                    dao.remove(rowID);
                }
            }
        };
        
        tabelaAnimaisProdGTRE.addTableModifyListener(animaisTMListener);
        tabelaAnimaisServGTRE.addTableModifyListener(animaisTMListener);
    }
    
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void editarInvAnimaisBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarInvAnimaisBTActionPerformed
        
        if(tabelaInveAnimaisProd.getSelectedRowCount() > 0){
            tabelaAnimaisProdGTRE.setEditorType(GTRE_UPDATE);
            tabelaAnimaisProdGTRE.showEditor(evt);
        }
        else if(tabelaInveAnimaisServ.getSelectedRowCount() > 0){
            tabelaAnimaisServGTRE.setEditorType(GTRE_UPDATE);
            tabelaAnimaisServGTRE.showEditor(evt);
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para editar",
                        "Editar - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_editarInvAnimaisBTActionPerformed

    private void valorGastoAnimaisBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valorGastoAnimaisBTActionPerformed
        try{
            Double temp = Double.parseDouble(JOptionPane.showInputDialog("Valor Gasto com Compra de Animais: "));
            
            if(temp >= 0.0){
                total32.setText(String.format("R$ %.2f", temp));
                total33.setText(String.format("R$ %.2f", (Double.parseDouble(total20.getText().substring(2).replace(',','.')) -
                        Double.parseDouble(total19.getText().substring(2).replace(',','.')) - Double.parseDouble(total32.getText().substring(2).replace(',','.')))));
                
                if(resumo != null){
                    resumo.setValorGastoCompraAnimais(temp);
                    irdao.atualizar(resumo);
                } 
            } else {
                JOptionPane.showMessageDialog(null, "Insira um valor maior que zero!");
            }
        } catch (IllegalArgumentException | SQLException | NullPointerException e){
            JOptionPane.showMessageDialog(null, "Insira um valor válido!");
        }
    }//GEN-LAST:event_valorGastoAnimaisBTActionPerformed

    private void editarInvTerrasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarInvTerrasBTActionPerformed
       
        if(tabelaInveTerras.getSelectedRowCount() > 0){
            tabelaTerrasGTRE.setEditorType(GTRE_UPDATE);
            tabelaTerrasGTRE.showEditor(evt);
        }
        else if(tabelaInveForrageiras.getSelectedRowCount() > 0){
            tabelaForrageirasGTRE.setEditorType(GTRE_UPDATE);
            tabelaForrageirasGTRE.showEditor(evt);
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para editar",
                        "Editar - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_editarInvTerrasBTActionPerformed

    private void adicionarInvTerrasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarInvTerrasBTActionPerformed
        
        tabelaTerrasGTRE.setEditorType(GTRE_INSERT);
        tabelaTerrasGTRE.showEditor(evt);
        verificaTabelaVazia(tabelaTerrasGTRE.getSourceTableModel(), editarInvTerrasBT, removerInvTerrasBT);
    }//GEN-LAST:event_adicionarInvTerrasBTActionPerformed

    private void editarInvMaquinasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarInvMaquinasBTActionPerformed
        tabelaMaquinasGTRE.setEditorType(GTRE_UPDATE);
        tabelaMaquinasGTRE.showEditor(evt);
    }//GEN-LAST:event_editarInvMaquinasBTActionPerformed

    private void tabelaInveTerrasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaInveTerrasFocusGained
        tabelaInveForrageiras.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabelaInveTerrasFocusGained

    private void tabelaInveForrageirasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaInveForrageirasFocusGained
        tabelaInveTerras.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabelaInveForrageirasFocusGained

    private void tabelaInveAnimaisProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaInveAnimaisProdFocusGained
        tabelaInveAnimaisServ.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabelaInveAnimaisProdFocusGained

    private void tabelaInveAnimaisServFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaInveAnimaisServFocusGained
        tabelaInveAnimaisProd.getSelectionModel().clearSelection();
    }//GEN-LAST:event_tabelaInveAnimaisServFocusGained

    private void editarInvBenfeitoriasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarInvBenfeitoriasBTActionPerformed
        tabelaBenfeitoriasGTRE.setEditorType(GTRE_UPDATE);
        tabelaBenfeitoriasGTRE.showEditor(evt);
    }//GEN-LAST:event_editarInvBenfeitoriasBTActionPerformed

    private void removerInvTerrasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerInvTerrasBTActionPerformed

        if(tabelaInveTerras.getSelectedRowCount() > 0){
            
            tabelaForrageirasGTRE.removeSourceTableRow(tabelaInveTerras.getSelectedRow());
            tabelaTerrasGTRE.removeSourceTableRow(tabelaInveTerras.getSelectedRow());
        }
        else if(tabelaInveForrageiras.getSelectedRowCount() > 0){
            
            tabelaTerrasGTRE.removeSourceTableRow(tabelaInveForrageiras.getSelectedRow());
            tabelaForrageirasGTRE.removeSourceTableRow(tabelaInveForrageiras.getSelectedRow());
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para remover",
                        "Remover - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (tabelaInveTerras.getRowCount() == 0 && tabelaInveForrageiras.getRowCount() == 0) {
            editarInvTerrasBT.setEnabled(false);
            removerInvTerrasBT.setEnabled(false);
        }
    }//GEN-LAST:event_removerInvTerrasBTActionPerformed

    private void adicionarInvMaquinasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarInvMaquinasBTActionPerformed
        tabelaMaquinasGTRE.setEditorType(GTRE_INSERT);
        tabelaMaquinasGTRE.showEditor(evt);
         verificaTabelaVazia(tabelaMaquinasGTRE.getSourceTableModel(), editarInvMaquinasBT, removerInvMaquinasBT);
    }//GEN-LAST:event_adicionarInvMaquinasBTActionPerformed

    private void adicionarInvAnimaisBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarInvAnimaisBTActionPerformed
        
        ArrayList<Pair<String,JTable>> tabelas = new ArrayList<>();
        
        tabelas.add(Pair.create("Tabela - Animais de Produção", tabelaInveAnimaisProd));
        tabelas.add(Pair.create("Tabela - Animais de Serviço", tabelaInveAnimaisServ));
        
        ItemSelector<JTable> selector = new ItemSelector<>(this, tabelas);
        selector.setLabel("Selecione uma tabela para adicionar:");
        
        JTable selecionada = selector.showSelector();
        
        if(selecionada == tabelaInveAnimaisProd){
            tabelaAnimaisProdGTRE.setEditorType(GTRE_INSERT);
            tabelaAnimaisProdGTRE.showEditor(evt);
            verificaTabelaVazia(tabelaAnimaisProdGTRE.getSourceTableModel(), editarInvAnimaisBT, removerInvAnimaisBT);
           
        }
        else if(selecionada == tabelaInveAnimaisServ){
            tabelaAnimaisServGTRE.setEditorType(GTRE_INSERT);
            tabelaAnimaisServGTRE.showEditor(evt);
             verificaTabelaVazia(tabelaAnimaisServGTRE.getSourceTableModel(), editarInvAnimaisBT, removerInvAnimaisBT);
        }
    }//GEN-LAST:event_adicionarInvAnimaisBTActionPerformed

    private void removerInvBenfeitoriasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerInvBenfeitoriasBTActionPerformed
        
        if(tabelaBenfeitorias.getSelectedRowCount() > 0){
            tabelaBenfeitoriasGTRE.removeSourceTableRow(tabelaBenfeitorias.getSelectedRow());
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para remover",
                        "Remover - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
        verificaTabelaVazia(tabelaBenfeitoriasGTRE.getSourceTableModel(), editarInvBenfeitoriasBT, removerInvBenfeitoriasBT);
    }//GEN-LAST:event_removerInvBenfeitoriasBTActionPerformed

    private void removerInvMaquinasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerInvMaquinasBTActionPerformed
       
        if(tabelaMaquinas.getSelectedRowCount() > 0){
            tabelaMaquinasGTRE.removeSourceTableRow(tabelaMaquinas.getSelectedRow());
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para remover",
                        "Remover - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
        verificaTabelaVazia(tabelaMaquinasGTRE.getSourceTableModel(), editarInvMaquinasBT, removerInvMaquinasBT);
    }//GEN-LAST:event_removerInvMaquinasBTActionPerformed

    private void removerInvAnimaisBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerInvAnimaisBTActionPerformed
        
        if(tabelaInveAnimaisProd.getSelectedRowCount() > 0){
            tabelaAnimaisProdGTRE.removeSourceTableRow(tabelaInveAnimaisProd.getSelectedRow());
        } 
        else if(tabelaInveAnimaisServ.getSelectedRowCount() > 0){
            tabelaAnimaisServGTRE.removeSourceTableRow(tabelaInveAnimaisServ.getSelectedRow());
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para remover",
                        "Remover - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
        if (tabelaInveAnimaisProd.getRowCount() == 0 && tabelaInveAnimaisServ.getRowCount() == 0) {
            editarInvAnimaisBT.setEnabled(false);
            removerInvAnimaisBT.setEnabled(false);
        }
    }//GEN-LAST:event_removerInvAnimaisBTActionPerformed

    private void atividadeLeiteBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atividadeLeiteBTActionPerformed
        try{
            Double temp = Double.parseDouble(JOptionPane.showInputDialog("Inserir atividade leiteira (%): "));
            
            if(temp >= 0.0){
                
                if(resumo != null){
                    resumo.setAtividadeLeiteira(temp);
                    irdao.atualizar(resumo);
                }
                
                atividadeLeite.setText("" + temp);
                total50.setText(String.format("R$ %.2f", Double.parseDouble(total49.getText().substring(2).replace(',','.')) * (temp/100)));
                
            } else {
                JOptionPane.showMessageDialog(null, "Insira um valor maior que zero!");
            }
        } catch (HeadlessException | NumberFormatException | SQLException | NullPointerException e){
            JOptionPane.showMessageDialog(null, "Insira um valor válido!");
        }
    }//GEN-LAST:event_atividadeLeiteBTActionPerformed

    private void custoOportBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_custoOportBTActionPerformed
        try{
            Double temp = Double.parseDouble(JOptionPane.showInputDialog("Inserir custo de oportunidade (%): "));
            
            if(temp >= 0.0){
                
                if(resumo != null){
                    resumo.setCustoOportunidade(temp);
                    irdao.atualizar(resumo);
                }
                
                custoOportunidade.setText("" + temp);
                total57.setText(String.format("R$ %.2f", Double.parseDouble(total56.getText().substring(2).replace(',','.')) * (temp/100)));
            } else {
                JOptionPane.showMessageDialog(null, "Insira um valor maior que zero!");
            }
        } catch (HeadlessException | NumberFormatException | SQLException | NullPointerException e){
            JOptionPane.showMessageDialog(null, "Insira um valor válido!");
        }
    }//GEN-LAST:event_custoOportBTActionPerformed

    private void salarioMinimoBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salarioMinimoBTActionPerformed
        try{
            Double temp = Double.parseDouble(JOptionPane.showInputDialog("Inserir salário mínimo: ").replace(",", "."));
            
            if(temp >= 0.0){
                salarioMinimo.setText(String.format("%.2f", temp));
                total58.setText(String.format("%.2f", temp));
                total59.setText(String.format("%.2f", (Double.parseDouble(salarioMinimo.getText().replace(',','.')) * 0.3)));
                
                if(resumo != null){
                    resumo.setSalarioMinimo(temp);
                    irdao.atualizar(resumo);
                } 
                
                total60.setText(String.format("R$ %.2f", (temp*13 + temp*0.3)/12));
                
            } else {
                JOptionPane.showMessageDialog(null, "Insira um valor maior que zero!");
            }
        } catch (HeadlessException | NumberFormatException | SQLException | NullPointerException e){
            JOptionPane.showMessageDialog(null, "Insira um valor válido!");
        }
    }//GEN-LAST:event_salarioMinimoBTActionPerformed

    private void adicionarInvBenfeitoriasBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarInvBenfeitoriasBTActionPerformed
        tabelaBenfeitoriasGTRE.setEditorType(GTRE_INSERT);
        tabelaBenfeitoriasGTRE.showEditor(evt);
        verificaTabelaVazia(tabelaBenfeitoriasGTRE.getSourceTableModel(), editarInvBenfeitoriasBT, removerInvBenfeitoriasBT);
    }//GEN-LAST:event_adicionarInvBenfeitoriasBTActionPerformed

    private void vidaUtilReprodBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vidaUtilReprodBTActionPerformed
        try{
            Integer temp = Integer.parseInt(JOptionPane.showInputDialog("Vida últil dos reprodutores: "));
            
            if(temp >= 0){
                total35.setText(String.format("%d", temp));
                total36.setText(String.format("%.2f", (Double.parseDouble(total34.getText().substring(2).replace(',','.')) /
                        Double.parseDouble(total35.getText().replace(',','.')))));
                
                resumo.setVidaUtilReprodutores(temp);
                               
                if(resumo != null){
                    resumo.setVidaUtilReprodutores(temp);
                    irdao.atualizar(resumo);
                } 
            } else {
                JOptionPane.showMessageDialog(null, "Insira um valor maior que zero!");
            }
        } catch (IllegalArgumentException | SQLException | NullPointerException e){
            JOptionPane.showMessageDialog(null, "Insira um valor válido!");
        }
    }//GEN-LAST:event_vidaUtilReprodBTActionPerformed

    private void vidaUtilServBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vidaUtilServBTActionPerformed
        try{
            Integer temp = Integer.parseInt(JOptionPane.showInputDialog("Capital Investido em Animais de Serviços: "));
            
            if(temp >= 0) {
                total38.setText(String.format("%d", temp));
                total39.setText(String.format("%.2f", (Double.parseDouble(total37.getText().substring(2).replace(',','.')) /
                        Double.parseDouble(total38.getText().replace(',','.')))));
                               
                if(resumo != null){
                    resumo.setVidaUtilAnimaisServico(temp);
                    irdao.atualizar(resumo);        
                } 
            } else {
                JOptionPane.showMessageDialog(null, "Insira um valor maior que zero!");
            }
        } catch (IllegalArgumentException | NullPointerException | SQLException e){
            JOptionPane.showMessageDialog(null, "Insira um valor válido!");
        }
    }//GEN-LAST:event_vidaUtilServBTActionPerformed
    
    private void verificaTabelaVazia(DefaultTableModel table, JButton editarBtn, JButton removerBtn) {
        if(table.getRowCount() == 0) {
            editarBtn.setEnabled(false);
            removerBtn.setEnabled(false);
        } else {
            editarBtn.setEnabled(true);
            removerBtn.setEnabled(true);
        }
    }

    private void showTableLines() {
        tabelaBenfeitorias.setShowHorizontalLines(true);
        tabelaBenfeitorias.setShowVerticalLines(true);
    
        tabelaInveAnimaisProd.setShowHorizontalLines(true);
        tabelaInveAnimaisProd.setShowVerticalLines(true);
    
        tabelaInveAnimaisServ.setShowHorizontalLines(true);
        tabelaInveAnimaisServ.setShowVerticalLines(true);
    
        tabelaInveForrageiras.setShowHorizontalLines(true);
        tabelaInveForrageiras.setShowVerticalLines(true);
    
        tabelaInveTerras.setShowHorizontalLines(true);
        tabelaInveTerras.setShowVerticalLines(true);
    
        tabelaMaquinas.setShowHorizontalLines(true);
        tabelaMaquinas.setShowVerticalLines(true);
    }
    
    private void setTotaisTerras() {
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarInvAnimaisBT;
    private javax.swing.JButton adicionarInvBenfeitoriasBT;
    private javax.swing.JButton adicionarInvMaquinasBT;
    private javax.swing.JButton adicionarInvTerrasBT;
    private javax.swing.JLabel atividadeLeite;
    private javax.swing.JButton atividadeLeiteBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton custoOportBT;
    private javax.swing.JLabel custoOportunidade;
    private javax.swing.JButton editarInvAnimaisBT;
    private javax.swing.JButton editarInvBenfeitoriasBT;
    private javax.swing.JButton editarInvMaquinasBT;
    private javax.swing.JButton editarInvTerrasBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton removerInvAnimaisBT;
    private javax.swing.JButton removerInvBenfeitoriasBT;
    private javax.swing.JButton removerInvMaquinasBT;
    private javax.swing.JButton removerInvTerrasBT;
    private javax.swing.JLabel salarioMinimo;
    private javax.swing.JButton salarioMinimoBT;
    private javax.swing.JTable tabelaBenfeitorias;
    private javax.swing.JTable tabelaInveAnimaisProd;
    private javax.swing.JTable tabelaInveAnimaisServ;
    private javax.swing.JTable tabelaInveForrageiras;
    private javax.swing.JTable tabelaInveTerras;
    private javax.swing.JTable tabelaMaquinas;
    private javax.swing.JLabel textoEntrada;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total10;
    private javax.swing.JLabel total11;
    private javax.swing.JLabel total12;
    private javax.swing.JLabel total13;
    private javax.swing.JLabel total14;
    private javax.swing.JLabel total15;
    private javax.swing.JLabel total16;
    private javax.swing.JLabel total17;
    private javax.swing.JLabel total18;
    private javax.swing.JLabel total19;
    private javax.swing.JLabel total2;
    private javax.swing.JLabel total20;
    private javax.swing.JLabel total21;
    private javax.swing.JLabel total22;
    private javax.swing.JLabel total23;
    private javax.swing.JLabel total24;
    private javax.swing.JLabel total25;
    private javax.swing.JLabel total26;
    private javax.swing.JLabel total28;
    private javax.swing.JLabel total29;
    private javax.swing.JLabel total3;
    private javax.swing.JLabel total31;
    private javax.swing.JLabel total32;
    private javax.swing.JLabel total33;
    private javax.swing.JLabel total34;
    private javax.swing.JLabel total35;
    private javax.swing.JLabel total36;
    private javax.swing.JLabel total37;
    private javax.swing.JLabel total38;
    private javax.swing.JLabel total39;
    private javax.swing.JLabel total4;
    private javax.swing.JLabel total40;
    private javax.swing.JLabel total41;
    private javax.swing.JLabel total42;
    private javax.swing.JLabel total43;
    private javax.swing.JLabel total44;
    private javax.swing.JLabel total45;
    private javax.swing.JLabel total46;
    private javax.swing.JLabel total47;
    private javax.swing.JLabel total48;
    private javax.swing.JLabel total49;
    private javax.swing.JLabel total5;
    private javax.swing.JLabel total50;
    private javax.swing.JLabel total51;
    private javax.swing.JLabel total52;
    private javax.swing.JLabel total53;
    private javax.swing.JLabel total54;
    private javax.swing.JLabel total55;
    private javax.swing.JLabel total56;
    private javax.swing.JLabel total57;
    private javax.swing.JLabel total58;
    private javax.swing.JLabel total59;
    private javax.swing.JLabel total6;
    private javax.swing.JLabel total60;
    private javax.swing.JLabel total7;
    private javax.swing.JLabel total8;
    private javax.swing.JLabel total9;
    private javax.swing.JButton valorGastoAnimaisBT;
    private javax.swing.JButton vidaUtilReprodBT;
    private javax.swing.JButton vidaUtilServBT;
    // End of variables declaration//GEN-END:variables
}