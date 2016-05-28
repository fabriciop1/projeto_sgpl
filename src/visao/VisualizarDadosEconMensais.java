/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.ControlePerfil;
import flex.db.GenericDAO;
import flex.table.GenericTableAreaEditor;
import flex.table.TableModifiedEvent;
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

/**
 *
 * @author Alexandre
 */
public class VisualizarDadosEconMensais extends javax.swing.JFrame {

    private final List<Especificacao> especificacoes;    
    private List<DadosEconMensais> dems;
    private final GenericDAO<DadosEconMensais> demdao;
    private final GenericDAO<Especificacao> demespdao;
    private GenericDAO<InventarioResumo> irdao;
    private final GenericTableAreaEditor gtae;
    private Perfil atual;
    
    /**
     * Creates new form VisualizarDadosEconMensais
     */
    public VisualizarDadosEconMensais() {
        initComponents();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
      
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setTitle("SGPL - " + atual.getNome() + " - Dados Econômicos Mensais");
        
        tabelaEspecificacao.setShowHorizontalLines(true);
        
        tabelaDadosEconomicos.setShowGrid(true);
        
        tabelaDadosEconomicos.setDefaultRenderer(Object.class, new ColorRendererDadosEcon(true));
        tabelaEspecificacao.setDefaultRenderer(Object.class, new ColorRendererDadosEcon(false));
        
        demdao = new GenericDAO<>(DadosEconMensais.class);
                
        demespdao = new GenericDAO<>(Especificacao.class);
             
        especificacoes = demespdao.retrieveAll();
        
        gtae = new GenericTableAreaEditor(this, tabelaDadosEconomicos, false);
        
        PreencherTabelaESP(especificacoes);
        
        fillComboBox();
        
        definirBDListeners();
    }
    
    private void PreencherTabelaESP(List<Especificacao> esp){
       
        
        DefaultTableModel modelEspecificacao = (DefaultTableModel) tabelaEspecificacao.getModel();
        modelEspecificacao.setNumRows(0);

        for(int i = 0; i < esp.size(); i++){
            
            if(i == 0)  { modelEspecificacao.addRow(new Object[]{ "A - ENTRADAS" }); }
            if(i == 6)  { modelEspecificacao.addRow(new Object[]{ "TOTAL DE ENTRADAS" });
                          modelEspecificacao.addRow(new Object[]{ "B - SAÍDA" }); }
            if(i == 17) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "DESPESAS COM VOLUMOSO" }); }
            if(i == 28) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" }); 
                          modelEspecificacao.addRow(new Object[]{ "CONCENTRADO" }); }
            if(i == 38) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "MINERALIZAÇÃO" }); }
            if(i == 44) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "MEDICAMENTOS" }); }
            if(i == 56) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "ORDENHA" }); }
            if(i == 60) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "INSEMINAÇÃO ARTIFICIAL" }); }
            if(i == 65) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "DESPESAS DE INVESTIMENTO" }); }
            if(i == 68) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "DESPESAS DE EMPRÉSTIMOS" }); }
            if(i == 69) { modelEspecificacao.addRow(new Object[]{ "COE DE ATIVIDADE LEITEIRA" }); }
            
            modelEspecificacao.addRow( new Object[]{ esp.get(i).getEspecificacao() });
        }
    }
    
    private void PreencherTabelaDEM(int ano, List<DadosEconMensais> dem){
        
        DefaultTableModel modelEspecificacao = (DefaultTableModel) tabelaEspecificacao.getModel();
        DefaultTableModel modelDadosEconomicos = (DefaultTableModel) tabelaDadosEconomicos.getModel();
        modelDadosEconomicos.setNumRows(0);
        
        Object[] linhaTemp;
        double[] tempTotais = new double[36];
        double[] coeAtivLeite = new double[12];
        
        for(int i = 0; i < modelEspecificacao.getRowCount(); i++){
            
            linhaTemp = new Object[36];
            
            for(int j = 0; j < dem.size(); j++){
                
                int indexCol = (dem.get(j).getMes() - 1) * 3;
                
                if( modelEspecificacao.getValueAt(i, 0).equals(dem.get(j).getEspecificacao().getEspecificacao())
                        && dem.get(j).getAno() == ano ){
                                      
                    linhaTemp[indexCol ] = dem.get(j).getQuantidade();
                    linhaTemp[indexCol + 1] = dem.get(j).getValorUnitario();
                    linhaTemp[indexCol + 2] = dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();
                    
                    tempTotais[indexCol] += dem.get(j).getQuantidade();
                    tempTotais[indexCol + 2] += dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();
                    
                }
                
                if( tabelaEspecificacao.getModel().getValueAt(i, 0).equals("SUB-TOTAL")) {    
                    if (tempTotais[indexCol + 2] != 0.0) {
                        linhaTemp[indexCol + 2] = tempTotais[indexCol + 2];
                        coeAtivLeite[dem.get(j).getMes() - 1] += tempTotais[indexCol + 2];
                        tempTotais[indexCol + 2] = 0.0;
                    } 
                } 
                
                if( tabelaEspecificacao.getModel().getValueAt(i, 0).equals("TOTAL DE ENTRADAS")){
                                        
                    if (tempTotais[indexCol] != 0.0) { 
                        linhaTemp[indexCol] = tempTotais[indexCol];
                        tempTotais[indexCol] = 0.0;
                    }
                    
                    if (tempTotais[indexCol + 2] != 0.0) {
                        linhaTemp[indexCol + 2] = tempTotais[indexCol + 2];
                        tempTotais[indexCol + 2] = 0.0;
                    } 
                    
                }
                
                if( tabelaEspecificacao.getModel().getValueAt(i, 0).equals("COE DE ATIVIDADE LEITEIRA") ){
                    
                    if (coeAtivLeite[dem.get(j).getMes() - 1] != 0.0) {
                        linhaTemp[indexCol + 2] = coeAtivLeite[dem.get(j).getMes() - 1];
                        coeAtivLeite[dem.get(j).getMes() - 1] = 0.0;
                    } 
                    
                }
                
                if( tabelaEspecificacao.getModel().getValueAt(i, 0).equals("Mão-de-obra familiar (não paga)") ){
                    
                    double salario = 0.0;
                    irdao = new GenericDAO<>(InventarioResumo.class);
                    List<InventarioResumo> ir = irdao.retrieveByColumn("idPerfilFK", atual.getId());
                    if (linhaTemp != null && ir.size() > 0) {
                        salario = ir.get(0).getSalarioMinimo();
                        linhaTemp[indexCol + 1] = (salario * 13 + salario * 0.3) / 12;
                    }
                                                            
                    if (linhaTemp[indexCol] != null && salario != 0.0) {
                        linhaTemp[indexCol + 2] = (Double) linhaTemp[indexCol] * (Double) linhaTemp[indexCol + 1];
                    }
                    
                }
            }
            modelDadosEconomicos.addRow(linhaTemp);
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaEspecificacao = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaMeses = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaDadosEconomicos = new javax.swing.JTable();
        textoEntrada = new javax.swing.JLabel();
        avancarBT = new javax.swing.JButton();
        retornarBT = new javax.swing.JButton();
        editarValoresBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Ano:");

        anoCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anoComboActionPerformed(evt);
            }
        });

        adicionarAnoBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarAnoBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarAnoBTActionPerformed(evt);
            }
        });

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel3.setPreferredSize(new java.awt.Dimension(2720, 1531));
        jPanel3.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jPanel3MouseWheelMoved(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabelaEspecificacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Especificação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaEspecificacao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabelaEspecificacao.setPreferredSize(new java.awt.Dimension(270, 1440));
        tabelaEspecificacao.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaEspecificacao.getTableHeader().setReorderingAllowed(false);
        tabelaEspecificacao.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tabelaEspecificacaoMouseDragged(evt);
            }
        });
        tabelaEspecificacao.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                tabelaEspecificacaoMouseWheelMoved(evt);
            }
        });
        tabelaEspecificacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaEspecificacaoMousePressed(evt);
            }
        });
        tabelaEspecificacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaEspecificacaoKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaEspecificacao);
        if (tabelaEspecificacao.getColumnModel().getColumnCount() > 0) {
            tabelaEspecificacao.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(813, 1514));

        jPanel2.setPreferredSize(new java.awt.Dimension(2780, 1512));

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
        tabelaMeses.setPreferredSize(new java.awt.Dimension(800, 0));
        tabelaMeses.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabelaMeses);
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

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane5.setPreferredSize(new java.awt.Dimension(800, 1424));

        tabelaDadosEconomicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDadosEconomicos.setPreferredSize(new java.awt.Dimension(800, 1441));
        tabelaDadosEconomicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDadosEconomicos.getTableHeader().setResizingAllowed(false);
        tabelaDadosEconomicos.getTableHeader().setReorderingAllowed(false);
        tabelaDadosEconomicos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tabelaDadosEconomicosMouseDragged(evt);
            }
        });
        tabelaDadosEconomicos.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                tabelaDadosEconomicosMouseWheelMoved(evt);
            }
        });
        tabelaDadosEconomicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaDadosEconomicosMousePressed(evt);
            }
        });
        tabelaDadosEconomicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaDadosEconomicosKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tabelaDadosEconomicos);
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
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 2790, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1531, Short.MAX_VALUE)
        );

        jScrollPane6.setViewportView(jPanel3);

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(51, 153, 255));
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
        editarValoresBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarValoresBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(textoEntrada)
                .addGap(69, 69, 69)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adicionarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editarValoresBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void anoComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anoComboActionPerformed
        PreencherTabelaDEM(Integer.parseInt(anoCombo.getSelectedItem().toString()) , dems);
    }//GEN-LAST:event_anoComboActionPerformed

    private void editarValoresBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarValoresBTActionPerformed
        MonthSelector telaMes = new MonthSelector(this, true);
        telaMes.setTitle("SGPL - DEM - Seleção de Mês");

        telaMes.setVisible(true);
        int selecionado = telaMes.getSelected();
        
        if (selecionado != 0) {
             
            gtae.setTitle("Editar Dados Econômicos Mensais - " + telaMes.getMonthSelected().toUpperCase());
            
            configGTAE(selecionado);
            
            gtae.showEditor(evt);
           
        }
    }//GEN-LAST:event_editarValoresBTActionPerformed

    private void tabelaEspecificacaoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoMouseDragged
        tabelaEspecificacaoMousePressed(evt);
    }//GEN-LAST:event_tabelaEspecificacaoMouseDragged

    private void tabelaEspecificacaoMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoMouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_tabelaEspecificacaoMouseWheelMoved

    private void tabelaEspecificacaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoMousePressed
        tabelaDadosEconomicos.setRowSelectionInterval(tabelaEspecificacao.getSelectedRow(), tabelaEspecificacao.getSelectedRow());
    }//GEN-LAST:event_tabelaEspecificacaoMousePressed

    private void tabelaEspecificacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoKeyPressed
        int temp;
        if(evt.getKeyCode() == 40 && tabelaEspecificacao.getSelectedRow() != (tabelaEspecificacao.getRowCount() - 1)) {
            temp = tabelaEspecificacao.getSelectedRow() + 1;
            tabelaDadosEconomicos.setRowSelectionInterval(temp, temp);
        } else if (evt.getKeyCode() == 38 && tabelaEspecificacao.getSelectedRow() != 0){
            temp = tabelaEspecificacao.getSelectedRow() - 1;
            tabelaDadosEconomicos.setRowSelectionInterval(temp, temp);
        }
    }//GEN-LAST:event_tabelaEspecificacaoKeyPressed

    private void jPanel3MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jPanel3MouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_jPanel3MouseWheelMoved

    private void tabelaDadosEconomicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosKeyPressed
        int temp;
        if(evt.getKeyCode() == 40 && tabelaDadosEconomicos.getSelectedRow() != (tabelaDadosEconomicos.getRowCount() - 1)) {
            temp =  tabelaDadosEconomicos.getSelectedRow() + 1;
            tabelaEspecificacao.setRowSelectionInterval(temp, temp);

        } else if (evt.getKeyCode() == 38 &&  tabelaDadosEconomicos.getSelectedRow() != 0){
            temp =  tabelaDadosEconomicos.getSelectedRow() - 1;
            tabelaEspecificacao.setRowSelectionInterval(temp, temp);
        }
    }//GEN-LAST:event_tabelaDadosEconomicosKeyPressed

    private void tabelaDadosEconomicosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosMousePressed
        tabelaEspecificacao.setRowSelectionInterval(tabelaDadosEconomicos.getSelectedRow(), tabelaDadosEconomicos.getSelectedRow());
    }//GEN-LAST:event_tabelaDadosEconomicosMousePressed

    private void tabelaDadosEconomicosMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosMouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_tabelaDadosEconomicosMouseWheelMoved

    private void tabelaDadosEconomicosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosMouseDragged
        tabelaDadosEconomicosMousePressed(evt);
    }//GEN-LAST:event_tabelaDadosEconomicosMouseDragged

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_formMouseWheelMoved

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane2.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 695);
    }//GEN-LAST:event_avancarBTActionPerformed

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane2.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 695);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void adicionarAnoBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarAnoBTActionPerformed
        boolean existe = false;
        
        YearSelector telaNovoAno = new YearSelector(this, true);
        telaNovoAno.setTitle("SGPL - DEM - Seleção de ano");
        telaNovoAno.setVisible(true);
        
        String ano = telaNovoAno.getSelected();
        int index = telaNovoAno.getIndex();
        
        for(int i = 0; i < anoCombo.getItemCount(); i++) {
            if (anoCombo.getItemAt(i).equals(ano)) {
                existe = true;
                break;
            }
        }
        if (existe == true) {
            JOptionPane.showMessageDialog(this, "Este ano já foi inserido para o perfil de " + atual.getNome()
                    + ".", "Alerta - Inserção de ano já cadastrado", JOptionPane.WARNING_MESSAGE);
        }
        if (index != 0 && existe == false) {
            anoCombo.addItem(ano);
            anoCombo.setSelectedItem(ano);
            telaNovoAno.removeItem(ano);
            PreencherTabelaDEM(Integer.parseInt(ano), dems);
        }
    }//GEN-LAST:event_adicionarAnoBTActionPerformed

    private void fillComboBox() {
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        List<DadosEconMensais> dados = demdao.retrieveByColumn("idPerfilFK", atual.getId(), "ano", "ano DESC");
        dems = demdao.retrieveByColumn("idPerfilFK", atual.getId());
        
        if (dados.isEmpty()) {
            Calendar cal = GregorianCalendar.getInstance();
            anoCombo.addItem(Cast.toString(cal.get(Calendar.YEAR)));
        }

        for(int i = 0; i < dados.size(); i++) {
            anoCombo.addItem(Cast.toString(dados.get(i).getAno()));
        }
        
        PreencherTabelaDEM(Integer.parseInt( (String) anoCombo.getSelectedItem() ), dems);
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
            int modiftype = evt.getEventType();
            
            if (modiftype == TableModifiedEvent.AREA_CHANGED) {
            for (int i = 0; i < areaData.length; i++) {
                if (gtae.getEditTable().isCellEditable(i, 0)) {
                DadosEconMensais dado = new DadosEconMensais();
                int quant = Cast.toInt(areaData[i][0]);
                System.out.println(quant);
                
               // System.out.println(dado.getQuantidade());
                }
            }
            }
        });
    }
    
    private void configGTAE(int selected) {
        
        gtae.setColumnInterval((selected-1) * 3, ((selected-1) * 3) + 2);
        gtae.getEditTable().setDefaultRenderer(Object.class, new ColorRendererDadosEcon(true));
        gtae.setName("GTAE DadosEconMensais");
        gtae.setRowsDisplayed(10);
        gtae.setAllowEmptyRows(true);
        gtae.setColumnEditable(0, true);
        gtae.setColumnEditable(1, true);
        gtae.setSourceRowEditable(0, false);
        gtae.setSourceRowEditable(7, false);
        gtae.setSourceRowEditable(8, false);
        gtae.setSourceRowEditable(20, false);
        gtae.setSourceRowEditable(21, false);
        gtae.setSourceRowEditable(33, false);
        gtae.setSourceRowEditable(34, false);
        gtae.setSourceRowEditable(45, false);
        gtae.setSourceRowEditable(46, false);
        gtae.setSourceRowEditable(53, false);
        gtae.setSourceRowEditable(54, false);
        gtae.setSourceRowEditable(67, false);
        gtae.setSourceRowEditable(68, false);
        gtae.setSourceRowEditable(73, false);
        gtae.setSourceRowEditable(74, false);
        gtae.setSourceRowEditable(80, false);
        gtae.setSourceRowEditable(81, false);
        gtae.setSourceRowEditable(85, false);
        gtae.setSourceRowEditable(86, false);
        gtae.setSourceRowEditable(88, false); 
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarAnoBT;
    private javax.swing.JComboBox<String> anoCombo;
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton editarValoresBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaDadosEconomicos;
    private javax.swing.JTable tabelaEspecificacao;
    private javax.swing.JTable tabelaMeses;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
