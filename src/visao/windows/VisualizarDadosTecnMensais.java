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
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Indicador;
import modelo.negocio.Perfil;
import util.Calc;
import util.Cast;
import util.ColorRendererDadosTec;
import util.Util;

/**
 *
 * @author Alexandre
 */
public class VisualizarDadosTecnMensais extends javax.swing.JFrame {

    private final Perfil atual;
    private final GenericDAO<DadosTecMensais> dtmdao; 
    private final GenericDAO<Indicador> dtmindao;
    private List<DadosTecMensais> dtms;
    private final List<Indicador> indicadores;
    private final GenericTableAreaEditor gtae;
    
    /**
     * Creates new form VisualizarDadosTecnMensais
     */
    public VisualizarDadosTecnMensais() {
        initComponents();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
      
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        tabelaDadosTecnicos.setShowGrid(true);
        tabelaIndicadores.setShowHorizontalLines(true);
        
        super.setTitle("SGPL - " + atual.getNome() + " - Dados Técnicos Mensais");
        
        tabelaDadosTecnicos.setDefaultRenderer(Object.class, new ColorRendererDadosTec(true));
        tabelaIndicadores.setDefaultRenderer(Object.class, new ColorRendererDadosTec(false));
        
        dtmdao = new GenericDAO<>(DadosTecMensais.class);
        
        dtmindao = new GenericDAO<>(Indicador.class);
        indicadores = dtmindao.retrieveAll();
        
        gtae = new GenericTableAreaEditor(this, tabelaDadosTecnicos, false);
        
        PreencherTabelaIND(indicadores);
        
        fillComboBox();
        
        initGTAE();
        
        definirBDListeners();
    }
    
    private void PreencherTabelaIND(List<Indicador> ind){
       
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadores.getModel();
        modelIndicadores.setNumRows(0);

        for(int i = 0; i < ind.size(); i++){
            
            if(i == 1)   { modelIndicadores.addRow(new Object[]{ "MÉDIA Litros/dia (L)" });
                           modelIndicadores.addRow(new Object[]{ "" });
                           modelIndicadores.addRow(new Object[]{ "INDICADORES PRODUTIVOS" }); }
            if(i == 10)  { modelIndicadores.addRow(new Object[]{ "" });
                           modelIndicadores.addRow(new Object[]{ "INDICADORES SANITÁRIOS" });}
           
            modelIndicadores.addRow( new Object[]{ ind.get(i).getIndicador()});
        }
    }
    
    private void PreencherTabelaDTM(List<DadosTecMensais> dtm){
        
        int indexCol;
        double dadoTemp, tempMedia, divisao;
        
        int ano = Integer.parseInt(anoCombo.getSelectedItem().toString());
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadores.getModel();
        DefaultTableModel modelDadosTecnicos = (DefaultTableModel) tabelaDadosTecnicos.getModel();
        modelDadosTecnicos.setNumRows(0);
        
        Object[] linhaTemp = new Object[36];
        
        for( int i = 0; i < modelIndicadores.getRowCount(); i++) {
            
            for( int j = 0; j < dtm.size(); j++){
                    
                indexCol = dtm.get(j).getMes() - 1;
                if( modelIndicadores.getValueAt(i, 0).equals(dtm.get(j).getIndicador().getIndicador())){

                    dadoTemp = dtm.get(j).getDado();

                    linhaTemp[indexCol] = dadoTemp;
                }

                if( i == 1 && dtm.get(j).getAno() == ano) {    
                    if (modelDadosTecnicos.getValueAt(0, indexCol) != null) {
                        tempMedia = (Double) modelDadosTecnicos.getValueAt(0, indexCol);

                        if( tempMedia != 0.0 ) {
                            linhaTemp[indexCol] = Calc.dividir(tempMedia, Util.diasDoMes(ano, indexCol + 1));
                        }  
                    }
                }
            }
            divisao = calcularMedia(linhaTemp);
            
            if (divisao != 0.0) { 
                linhaTemp[12] = divisao; 
            }         

            modelDadosTecnicos.addRow(linhaTemp);
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
        textoEntrada = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        anoCombo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaIndicadores = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaDadosTecnicos = new javax.swing.JTable();
        adicionarAnoBT = new javax.swing.JButton();
        editarAnoBT = new javax.swing.JButton();
        retornarBT = new javax.swing.JButton();
        avancarBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVoltar.setText("Voltar");
        btnVoltar.setToolTipText("Menu Principal");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setText("DADOS TÉCNICOS MENSAIS");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Ano:");

        anoCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                anoComboItemStateChanged(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabelaIndicadores.setModel(new javax.swing.table.DefaultTableModel(
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
                {null}
            },
            new String [] {
                "Indicador"
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
        tabelaIndicadores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaIndicadores.getTableHeader().setReorderingAllowed(false);
        tabelaIndicadores.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tabelaIndicadoresMouseDragged(evt);
            }
        });
        tabelaIndicadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaIndicadoresMousePressed(evt);
            }
        });
        tabelaIndicadores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaIndicadoresKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaIndicadores);
        if (tabelaIndicadores.getColumnModel().getColumnCount() > 0) {
            tabelaIndicadores.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 240, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tabelaDadosTecnicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO", "Média"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDadosTecnicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDadosTecnicos.getTableHeader().setReorderingAllowed(false);
        tabelaDadosTecnicos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tabelaDadosTecnicosMouseDragged(evt);
            }
        });
        tabelaDadosTecnicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaDadosTecnicosMousePressed(evt);
            }
        });
        tabelaDadosTecnicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaDadosTecnicosKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tabelaDadosTecnicos);
        if (tabelaDadosTecnicos.getColumnModel().getColumnCount() > 0) {
            tabelaDadosTecnicos.getColumnModel().getColumn(0).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(1).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(2).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(3).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(4).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(5).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(6).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(7).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(8).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(9).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(10).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(11).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(12).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1032, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 347, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1098, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        adicionarAnoBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarAnoBT.setToolTipText("Adicionar novo ano");
        adicionarAnoBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarAnoBTActionPerformed(evt);
            }
        });

        editarAnoBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarAnoBT.setToolTipText("Inserir/Editar dados ");
        editarAnoBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarAnoBTActionPerformed(evt);
            }
        });

        retornarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/left_arrow.png"))); // NOI18N
        retornarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retornarBTActionPerformed(evt);
            }
        });

        avancarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/right_arrow.png"))); // NOI18N
        avancarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avancarBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(textoEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adicionarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adicionarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textoEntrada)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(avancarBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void tabelaIndicadoresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaIndicadoresKeyPressed
        int temp;
        if(evt.getKeyCode() == 40 && tabelaIndicadores.getSelectedRow() != (tabelaIndicadores.getRowCount() - 1)) {
            temp = tabelaIndicadores.getSelectedRow() + 1;
            tabelaDadosTecnicos.setRowSelectionInterval(temp, temp);
        } else if (evt.getKeyCode() == 38 && tabelaIndicadores.getSelectedRow() != 0){
            temp = tabelaIndicadores.getSelectedRow() - 1;
            tabelaDadosTecnicos.setRowSelectionInterval(temp, temp);
        }
    }//GEN-LAST:event_tabelaIndicadoresKeyPressed

    private void tabelaDadosTecnicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaDadosTecnicosKeyPressed
        int temp;
        if(evt.getKeyCode() == 40 && tabelaDadosTecnicos.getSelectedRow() != (tabelaDadosTecnicos.getRowCount() - 1)) {
            temp =  tabelaDadosTecnicos.getSelectedRow() + 1;
            tabelaIndicadores.setRowSelectionInterval(temp, temp);

        } else if (evt.getKeyCode() == 38 &&  tabelaDadosTecnicos.getSelectedRow() != 0){
            temp =  tabelaDadosTecnicos.getSelectedRow() - 1;
            tabelaIndicadores.setRowSelectionInterval(temp, temp);
        }
    }//GEN-LAST:event_tabelaDadosTecnicosKeyPressed

    private void tabelaIndicadoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaIndicadoresMousePressed
        tabelaDadosTecnicos.setRowSelectionInterval(tabelaIndicadores.getSelectedRow(), tabelaIndicadores.getSelectedRow());
    }//GEN-LAST:event_tabelaIndicadoresMousePressed

    private void tabelaDadosTecnicosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDadosTecnicosMousePressed
       tabelaIndicadores.setRowSelectionInterval(tabelaDadosTecnicos.getSelectedRow(), tabelaDadosTecnicos.getSelectedRow());
    }//GEN-LAST:event_tabelaDadosTecnicosMousePressed

    private void tabelaDadosTecnicosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDadosTecnicosMouseDragged
        tabelaDadosTecnicosMousePressed(evt);
    }//GEN-LAST:event_tabelaDadosTecnicosMouseDragged

    private void tabelaIndicadoresMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaIndicadoresMouseDragged
        tabelaIndicadoresMousePressed(evt);
    }//GEN-LAST:event_tabelaIndicadoresMouseDragged

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane4.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 695);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane4.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 695);
    }//GEN-LAST:event_avancarBTActionPerformed

    private void adicionarAnoBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarAnoBTActionPerformed
        boolean existe = false;
        
        SingleYearSelector telaNovoAno = new SingleYearSelector(this, true);
        telaNovoAno.setTitle("SGPL - DTM - Seleção de ano");
        telaNovoAno.setVisible(true);
        
        String ano = telaNovoAno.getSelected();
        
        if (ano == null) {
            return;
        }
        
        for(int i = 0; i < anoCombo.getItemCount(); i++) {
            if (anoCombo.getItemAt(i).equals(ano)) {
                existe = true;
                break;
            }
        }
        if (existe == true) {
            JOptionPane.showMessageDialog(this, "Este ano já foi inserido para o perfil de " + atual.getNome()
                    + ".", "Alerta - Inserção de ano já cadastrado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        anoCombo.addItem(ano);
        anoCombo.setSelectedItem(ano);
    }//GEN-LAST:event_adicionarAnoBTActionPerformed

    private void editarAnoBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarAnoBTActionPerformed
        MonthSelector telaMes = new MonthSelector(this, true);
        
        telaMes.setTitle("SGPL - DTM - Seleção de Trimestre");
        telaMes.setVisible(true);
        
        int selecionado = telaMes.getSelected(); 
       
        if (selecionado > 0) {
            gtae.setLabelText("Editar - " + telaMes.getMonthSelected().toUpperCase() );
            gtae.setTitle("Editar D.T.M. - " + telaMes.getMonthSelected().toUpperCase());
            
            configGTAE(selecionado);
            
            gtae.showEditor(evt);
        }
    }//GEN-LAST:event_editarAnoBTActionPerformed

    private void anoComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_anoComboItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) { 
            dtms = dtmdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(), 
                    Integer.parseInt(anoCombo.getSelectedItem().toString())});
            PreencherTabelaDTM(dtms);
        }
    }//GEN-LAST:event_anoComboItemStateChanged

    private void initGTAE() {
        List<String> indColumnRows = new ArrayList<>(tabelaIndicadores.getRowCount());
        
        for(int i = 0; i < tabelaIndicadores.getRowCount(); i++) {
            indColumnRows.add(Cast.toString(tabelaIndicadores.getValueAt(i, 0).toString()));
        }
        
        gtae.setName("GTAE Dados Técnicos Mensais");
        gtae.addStringColumn(tabelaIndicadores.getColumnModel().getColumn(0).getWidth(), "Indicador", indColumnRows, 
                tabelaIndicadores.getDefaultRenderer(Object.class));
        
        gtae.setDefaultRenderer(tabelaDadosTecnicos.getDefaultRenderer(Object.class));
        
    }
    
    private void configGTAE(int selected) {
        
        List<Integer> rowsNotEditable = Arrays.asList(1, 2, 3, 13, 14);
        
        gtae.setRowsDisplayed(14);
        
        gtae.setAllowEmptyRows(true);
        gtae.setAllowEmptyCells(true);
        
        gtae.setColumnInterval(selected-1, selected-1);
        
        gtae.setRowInterval(0, tabelaIndicadores.getRowCount()-1);
        
        gtae.processEditor();
        
        for (int i: rowsNotEditable) {
            gtae.setRowEditable(i, false);
        } 
         
    }
    
    private void fillComboBox() {
        
        List<DadosTecMensais> dados = dtmdao.retrieveByColumn("idPerfilFK", atual.getId(), "ano", "ano DESC");
        
        if (dados.isEmpty()) {
            Calendar cal = GregorianCalendar.getInstance();
            anoCombo.addItem(Cast.toString(cal.get(Calendar.YEAR)));
        }

        for(int i = 0; i < dados.size(); i++) {
            anoCombo.addItem(Cast.toString(dados.get(i).getAno()));
        }    
    }
    
    private void definirBDListeners() {
        
         gtae.addTableModifyListener((TableModifiedEvent evt) -> {
           
            Object[][] areaData = evt.getTableAreaData();
            List<Integer> linhas = evt.getRowsModified();
                    
            int ano = Integer.parseInt(anoCombo.getSelectedItem().toString());
            
            for (Integer l : linhas) {
                int mes = gtae.getStartColumn() + 1;
                
                String valor = Cast.toString(areaData[l][0]);
                double dado = (valor.isEmpty()? 0.0 : Double.parseDouble(valor));
                
                Indicador ind = dtmindao.retrieveByColumn("indicador", tabelaIndicadores.getValueAt(l, 0)).get(0);

                List<DadosTecMensais> dadosTec = dtmdao.retrieveByColumns(new String[]{"mes", "ano", "idDTM_indicadorFK", "idPerfilFK"}, 
                                                        new Object[]{mes, ano, ind.getId(), atual.getId()});

                if (dadosTec.isEmpty()) {
                    if (dado != 0.0) {
                        DadosTecMensais dadoTec = new DadosTecMensais(mes, ano, dado, ind, atual.getId());

                        dtmdao.insert(dadoTec);
                    }

                } else {
                    DadosTecMensais dtm = dadosTec.get(0);

                    if (valor.isEmpty() || dado == 0.0) {
                        dtmdao.remove(dtm.getId());
                        continue;
                    }

                    dtm.setDado(dado);

                    dtmdao.update(dtm);
                }
            }
            dtms = dtmdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(), 
                    Integer.parseInt(anoCombo.getSelectedItem().toString())});
            PreencherTabelaDTM(dtms);
         });
    }
    
    public static double calcularMedia(Object[] vetor){
        
        int cont = 0;
        double soma = 0.0;
        
        for (Object vetor1 : vetor) {
            if (vetor1 != null) {
                soma += (Double) vetor1;
                cont++;
            }
        }
                
        return Calc.dividir(soma, cont);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarAnoBT;
    private javax.swing.JComboBox<String> anoCombo;
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton editarAnoBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaDadosTecnicos;
    private javax.swing.JTable tabelaIndicadores;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
