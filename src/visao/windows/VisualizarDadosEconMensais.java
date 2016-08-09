/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControlePerfil;
import flex.db.GenericDAO;
import flex.table.GenericTableAreaEditor;
import flex.table.TableModifiedEvent;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.Especificacao;
import modelo.negocio.InventarioResumo;
import modelo.negocio.Perfil;
import util.Cast;
import util.ColorRendererDadosEcon;
import util.FixedColumnTable;
import util.Util;

/**
 *
 * @author Alexandre
 */
public class VisualizarDadosEconMensais extends javax.swing.JFrame {

    private final List<Especificacao> especificacoes;    
    private List<DadosEconMensais> dems;
    private final GenericDAO<DadosEconMensais> demdao;
    private final GenericDAO<Especificacao> demespdao;
    private final GenericTableAreaEditor gtae;
    private final Perfil atual;
    private double salarioMensal;
    private final FixedColumnTable fixedTable;
    
    /**
     * Creates new form VisualizarDadosEconMensais
     */
    public VisualizarDadosEconMensais() {
        
        initComponents();
       
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        for(int i=0; i<tabelaDadosEconomicos.getColumnCount( ); i++){
            tabelaDadosEconomicos.getColumnModel( ).getColumn(i).setMinWidth(50);
        }
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("SGPL - " + atual.getNome() + " - Dados Econômicos Mensais");
        
        this.setSalarioMensal();
        
        fixedTable = new FixedColumnTable(1, jScrollPane6);
        fixedTable.getFixedTable().setShowHorizontalLines(true);
        
        fixedTable.getFixedTable().setDefaultRenderer(Object.class, new ColorRendererDadosEcon(false));
        
        tabelaMeses.getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        tabelaDadosEconomicos.setShowGrid(true);
        tabelaDadosEconomicos.setDefaultRenderer(Object.class, new ColorRendererDadosEcon(true));
        demdao = new GenericDAO<>(DadosEconMensais.class);
        demespdao = new GenericDAO<>(Especificacao.class);
        
        especificacoes = demespdao.retrieveAll();
       
        gtae = new GenericTableAreaEditor(this, tabelaDadosEconomicos, false);
        
        PreencherColunaESP(especificacoes);
          
        fillComboBox();
        
        initGTAE();
        
        definirBDListeners();
        
    }
    
    private void PreencherColunaESP(List<Especificacao> esp){
        
        int cont = 0;
        DefaultTableModel modelEspecificacao = (DefaultTableModel) tabelaDadosEconomicos.getModel();
        Object[] colunaESP = new Object[tabelaDadosEconomicos.getRowCount()];

        
        for(int i = 0; i < esp.size(); i++){
            
            if(i == 0)  { colunaESP[cont] = "A - ENTRADAS"; cont += 1; }
            if(i == 6)  { colunaESP[cont] = "TOTAL DE ENTRADAS"; cont += 1;
                          colunaESP[cont] = "B - SAÍDA"; cont += 1; }
            if(i == 17) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "DESPESAS COM VOLUMOSO"; cont += 1; }
            if(i == 28) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "CONCENTRADO"; cont += 1; }
            if(i == 38) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "MINERALIZAÇÃO"; cont += 1; }
            if(i == 44) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "MEDICAMENTOS"; cont += 1; }
            if(i == 56) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "ORDENHA"; cont += 1; }
            if(i == 60) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "INSEMINAÇÃO ARTIFICIAL"; cont += 1; }
            if(i == 65) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "DESPESAS DE INVESTIMENTO"; cont += 1; }
            if(i == 68) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "DESPESAS DE EMPRÉSTIMOS"; cont += 1; }
            if(i == 69) { colunaESP[cont] = "COE DE ATIVIDADE LEITEIRA"; cont += 1; }
            
            colunaESP[cont] = esp.get(i).getEspecificacao();
            cont++;
        }
        
        for(int i = 0; i < modelEspecificacao.getRowCount(); i++) {
            modelEspecificacao.setValueAt(colunaESP[i], i, 0);
        }
    }
    
    private void PreencherTabelaDEM(List<DadosEconMensais> dem) {
        
        DefaultTableModel modelDadosEconomicos = (DefaultTableModel) tabelaDadosEconomicos.getModel();
        
        Object[] linhaTemp = new Object[36];
        double[] tempTotais = new double[36];
        double[] coeAtivLeite = new double[12];
    
        for(int i = 0; i < modelDadosEconomicos.getRowCount(); i++){
            
            for(int j = 0; j < dem.size(); j++){
                    
                    int indexCol = (dem.get(j).getMes() * 3) - 2;
                     
                    if( modelDadosEconomicos.getValueAt(i, 0).equals(dem.get(j).getEspecificacao().getEspecificacao())){

                        linhaTemp[indexCol ] = dem.get(j).getQuantidade();
                        linhaTemp[indexCol + 1] = dem.get(j).getValorUnitario();
                        linhaTemp[indexCol + 2] = dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();

                        tempTotais[indexCol] += dem.get(j).getQuantidade();
                        tempTotais[indexCol + 2] += dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();

                    }

                    if( modelDadosEconomicos.getValueAt(i, 0).equals("SUB-TOTAL") || i == 87) {    
                        if (tempTotais[indexCol + 2] != 0.0) {
                            linhaTemp[indexCol + 2] = tempTotais[indexCol + 2];
                            coeAtivLeite[dem.get(j).getMes() - 1] += tempTotais[indexCol + 2];
                            tempTotais[indexCol + 2] = 0.0;
                        } 
                    } 

                    if(i == 7) {

                        if (tempTotais[indexCol] != 0.0) { 
                            linhaTemp[indexCol] = (int)tempTotais[indexCol];
                            tempTotais[indexCol] = 0.0;
                        }

                        if (tempTotais[indexCol + 2] != 0.0) {
                            linhaTemp[indexCol + 2] = tempTotais[indexCol + 2];
                            tempTotais[indexCol + 2] = 0.0;
                        } 

                    }
                    
                    if( i == 88 ){

                        if (coeAtivLeite[dem.get(j).getMes() - 1] != 0.0) {
                            linhaTemp[indexCol + 2] = coeAtivLeite[dem.get(j).getMes() - 1];
                            coeAtivLeite[dem.get(j).getMes() - 1] = 0.0;
                        } 

                    }

                    if( i == 89 ){
                        if (linhaTemp != null && this.getSalarioMensal() != 0.0) {
                            linhaTemp[indexCol + 1] = this.getSalarioMensal();
                        }

                        if (linhaTemp[indexCol] != null && this.getSalarioMensal() != 0.0) {
                            linhaTemp[indexCol + 2] = Double.parseDouble(linhaTemp[indexCol].toString()) * 
                                    Double.parseDouble(linhaTemp[indexCol + 1].toString());
                        }
                    }
                    
                    
                }
            
                for(int j = 1; j < linhaTemp.length; j++) {
                    modelDadosEconomicos.setValueAt(linhaTemp[j], i, j);
                }
        
                Util.clearVector(linhaTemp);
        }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        anoCombo = new javax.swing.JComboBox<>();
        adicionarAnoBT = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaDadosEconomicos = new javax.swing.JTable();
        textoEntrada = new javax.swing.JLabel();
        avancarBT = new javax.swing.JButton();
        retornarBT = new javax.swing.JButton();
        editarValoresBT = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaMeses = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.setToolTipText("Menu Principal");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Ano:");

        anoCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                anoComboItemStateChanged(evt);
            }
        });

        adicionarAnoBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarAnoBT.setToolTipText("Adicionar novo ano");
        adicionarAnoBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarAnoBTActionPerformed(evt);
            }
        });

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setAutoscrolls(true);

        tabelaDadosEconomicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total (R$)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDadosEconomicos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaDadosEconomicos.setPreferredSize(new java.awt.Dimension(800, 1441));
        tabelaDadosEconomicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDadosEconomicos.getTableHeader().setResizingAllowed(false);
        tabelaDadosEconomicos.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tabelaDadosEconomicos);
        tabelaDadosEconomicos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tabelaDadosEconomicos.getColumnModel().getColumnCount() > 0) {
            tabelaDadosEconomicos.getColumnModel().getColumn(0).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(1).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(2).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(3).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(4).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(5).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(6).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(7).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(8).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(9).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(10).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(11).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(12).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(13).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(14).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(15).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(16).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(17).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(18).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(19).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(20).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(21).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(22).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(23).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(24).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(25).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(26).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(27).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(28).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(29).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(30).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(31).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(32).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(33).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(34).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(35).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(36).setResizable(false);
        }

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setText("DADOS ECONÔMICOS MENSAIS");

        avancarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/right_arrow.png"))); // NOI18N
        avancarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avancarBTActionPerformed(evt);
            }
        });

        retornarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/left_arrow.png"))); // NOI18N
        retornarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retornarBTActionPerformed(evt);
            }
        });

        editarValoresBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarValoresBT.setToolTipText("Inserir/Editar dados");
        editarValoresBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarValoresBTActionPerformed(evt);
            }
        });

        tabelaMeses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaMeses.setColumnSelectionAllowed(true);
        tabelaMeses.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tabelaMeses);
        tabelaMeses.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tabelaMeses.getColumnModel().getColumnCount() > 0) {
            tabelaMeses.getColumnModel().getColumn(0).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(1).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(2).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(3).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(4).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(5).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(6).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(7).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(8).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(9).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(10).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(11).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(textoEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adicionarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editarValoresBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(editarValoresBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(adicionarAnoBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(textoEntrada))
                                    .addGap(1, 1, 1))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avancarBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void editarValoresBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarValoresBTActionPerformed
        MonthSelector telaMes = new MonthSelector(this, true);
        telaMes.setTitle("SGPL - DEM - Seleção de Mês");

        telaMes.setVisible(true);
        int selecionado = telaMes.getSelected();
        
        if (selecionado > 0) {
            gtae.setLabelText("Editar - " + telaMes.getMonthSelected().toUpperCase());
       
            gtae.setTitle("Editar Dados Econômicos Mensais - " + telaMes.getMonthSelected().toUpperCase());

            configGTAE(selecionado);

            gtae.showEditor(evt);          
        }
    }//GEN-LAST:event_editarValoresBTActionPerformed

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_formMouseWheelMoved

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane6.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 695);
    }//GEN-LAST:event_avancarBTActionPerformed

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane6.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 695);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void adicionarAnoBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarAnoBTActionPerformed
        boolean existe = false;
        
        SingleYearSelector telaNovoAno = new SingleYearSelector(this, true);
        telaNovoAno.setTitle("SGPL - DEM - Seleção de ano");
        telaNovoAno.setVisible(true);
        
        String ano = telaNovoAno.getSelected();
        
        if(ano == null) {
            return;
        }
        
        for(int i = 0; i < anoCombo.getItemCount(); i++) {
            if (anoCombo.getItemAt(i).equals(ano)) {
                existe = true;
                break;
            }
        }
        if (existe) {
            JOptionPane.showMessageDialog(this, "Este ano já foi inserido para o perfil de " + atual.getNome()
                    + ".", "Alerta - Inserção de ano já cadastrado", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        anoCombo.addItem(ano);
        anoCombo.setSelectedItem(ano);
        
    }//GEN-LAST:event_adicionarAnoBTActionPerformed

    private void anoComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_anoComboItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
           dems = demdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(), 
                                    Integer.parseInt(anoCombo.getSelectedItem().toString())});    
           PreencherTabelaDEM(dems);
        }    
    }//GEN-LAST:event_anoComboItemStateChanged

    private void fillComboBox() {
         
        List<DadosEconMensais> dados = demdao.retrieveByColumn("idPerfilFK", atual.getId(), "ano", "ano DESC");
        
        if (dados.isEmpty()) {
            Calendar cal = GregorianCalendar.getInstance();
            anoCombo.addItem(Cast.toString(cal.get(Calendar.YEAR)));
        }
        
        for(int i = 0; i < dados.size(); i++) {
            anoCombo.addItem(Cast.toString(dados.get(i).getAno()));
        }
        
    }
    
    private void moveScrollBar(java.awt.event.MouseWheelEvent evt) {
        JScrollBar barPanel3 = jScrollPane6.getVerticalScrollBar();     
        
        if(evt.getWheelRotation() > 0) { //Up
            barPanel3.setValue(barPanel3.getValue()+25); // velocidade de rolagem      
        } else { //Down
            barPanel3.setValue(barPanel3.getValue()-25);
        }     
    }
    
    private void definirBDListeners() {
        
        gtae.addTableModifyListener((TableModifiedEvent evt) -> {
       
            Object[][] areaData = evt.getTableAreaData();
            List<Integer> linhas = evt.getRowsModified();
            
            if(areaData == null){
                System.out.println("areaData is NULL in GTAE \"" + gtae.getName() + "\"");
            }
            
            for (Integer l : linhas) {

                Especificacao espec = demespdao.retrieveByColumn("especificacao", fixedTable.getFixedTable().getValueAt(l, 0)).get(0);
                int ano = Integer.parseInt(anoCombo.getSelectedItem().toString());
                int mes = ( (gtae.getStartColumn() + 1) / 3 ) + 1;
                int quantidade = 0;
                double valorUnitario = 0.0;
                
                List<DadosEconMensais> dadosEM = demdao.retrieveByColumns(new String[]{"mes", "ano", "idDEM_especificacaoFK", "idPerfilFK"}, 
                                                                    new Object[]{mes, ano, espec.getId(), atual.getId()});

                if (!Cast.toString(areaData[l][0]).isEmpty()) {

                    quantidade = (Integer.valueOf(areaData[l][0].toString()));
                } 

                if (!Cast.toString(areaData[l][1]).isEmpty()) {

                    valorUnitario = (Double.parseDouble(areaData[l][1].toString()));
                } 

                if(dadosEM.isEmpty()){

                    DadosEconMensais dem = new DadosEconMensais(mes, ano, quantidade, valorUnitario, espec, atual.getId());

                    demdao.insert(dem);

                } else {

                    DadosEconMensais dem = dadosEM.get(0);
                    
                    if (Cast.toString(areaData[l][0]).isEmpty() && Cast.toString(areaData[l][1]).isEmpty()) {
                        demdao.remove(dem.getId());
                        continue;
                    }
                    
                    dem.setValorUnitario(valorUnitario);
                    dem.setQuantidade(quantidade);

                    demdao.update(dem);
                }
            }
            
            dems = demdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(),
                    Integer.parseInt(anoCombo.getSelectedItem().toString())});
            PreencherTabelaDEM(dems);
            
        });
    }
    
    public double getSalarioMensal() {
        return salarioMensal;
    }
    
    private void setSalarioMensal() {
        double salarioMin, salarioMes = 0.0;
        
        GenericDAO<InventarioResumo> irdao = new GenericDAO<>(InventarioResumo.class);
        
        List<InventarioResumo> ir = irdao.retrieveByColumn("idPerfilFK", atual.getId());
        
        if (ir.size() > 0) {
             salarioMin = ir.get(0).getSalarioMinimo();
             salarioMes = (salarioMin * 13 + salarioMin * 0.3) / 12;
        }
        
        this.salarioMensal = salarioMes;
    }
            
    private void initGTAE(){
        
        List<String> especColumnRows = new ArrayList<>(tabelaDadosEconomicos.getRowCount());
        
        for(int i=0; i<tabelaDadosEconomicos.getRowCount(); i++){
            
            especColumnRows.add(Cast.toString(fixedTable.getFixedTable().getValueAt(i, 0)));
        }
        
        gtae.setName("GTAE DadosEconMensais");
        
        gtae.addStringColumn(tabelaDadosEconomicos.getColumnModel().getColumn(0).getWidth(), "Especificação", especColumnRows,
                        tabelaDadosEconomicos.getDefaultRenderer(Object.class));
       
        gtae.setDefaultRenderer( tabelaDadosEconomicos.getDefaultRenderer(Object.class) );
    }
    
    private void configGTAE(int selected) {
       
        List<Integer> rowsNotEditable = Arrays.asList(0, 7, 8, 20, 21, 33, 34, 45, 46, 53, 54, 67, 68, 73, 74, 80, 81, 85, 86, 88); 
        
        gtae.setRowsDisplayed(21);
        gtae.setAllowEmptyRows(true);
        
        gtae.setColumnInterval(((selected-1) * 3), ((selected-1)*3) + 2);
        
        gtae.setRowInterval(0, tabelaDadosEconomicos.getRowCount()-1); //Linha 1 a 90
        
        //Configura o editor de acordo com o intervalo de linhas e colunas especificado.
        gtae.processEditor();
       
        /*setCellEditable, setColumnEditable e setRowEditable sobrescrevem uns aos outros.
         Ex: setCellEditable(false,0,0); == cell 0,0 is not editable
         setColumnEditable(true,0); == cell 0,0 is now editable (afeta todas as linhas, naquela coluna)
         setRowEditable(false,0); == cell 0,0 is not editable (again).  */
        
        //setColumnEditable afeta todas as células da coluna especificada, em todas linhas do GTAE 
        
        for (int i: rowsNotEditable) {
            gtae.setRowEditable(i, false);
        }
        
        gtae.setColumnEditable(2, false);
        
        gtae.setCellEditable(89, 1, false);
        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarAnoBT;
    private javax.swing.JComboBox<String> anoCombo;
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton editarValoresBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaDadosEconomicos;
    private javax.swing.JTable tabelaMeses;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
