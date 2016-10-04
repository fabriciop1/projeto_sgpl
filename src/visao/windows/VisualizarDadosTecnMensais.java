/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControleLogin;
import controle.ControlePerfil;
import flex.db.GenericDAO;
import flex.table.GenericTableAreaEditor;
import flex.table.TableModifiedEvent;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Indicador;
import modelo.negocio.Perfil;
import modelo.negocio.Usuario;
import util.Calc;
import util.Cast;
import util.ColorRendererDadosTec;
import util.FixedColumnTable;
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
    private final FixedColumnTable fixedTable;
    private final Usuario usuario;
    
    /**
     * Creates new form VisualizarDadosTecnMensais
     */
    public VisualizarDadosTecnMensais() {
        initComponents();
        
        usuario = ControleLogin.getInstance().getUsuario();
        
        if( usuario.getTipoUsuario() == 3 ){ //Usuário apenas visualização
            editarValoresBT.setEnabled(false);
        }
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
      
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        perfilLabel.setText(atual.getNome() + " " + ControlePerfil.getInstance().getAno());
    
        tabelaDadosTecnicos.setShowGrid(true);
        tabelaDadosTecnicos.setShowHorizontalLines(true);
        tabelaDadosTecnicos.getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        
        super.setTitle("SGPL - " + atual.getNome() + " - Dados Técnicos Mensais");
        
        fixedTable = new FixedColumnTable(1, jScrollPane1);
        fixedTable.getFixedTable().setShowHorizontalLines(true);
        fixedTable.getFixedTable().setDefaultRenderer(Object.class, new ColorRendererDadosTec(false));
        
        tabelaDadosTecnicos.setDefaultRenderer(Object.class, new ColorRendererDadosTec(true));
        
        dtmdao = new GenericDAO<>(DadosTecMensais.class);
        
        dtmindao = new GenericDAO<>(Indicador.class);
        indicadores = dtmindao.retrieveAll();
        
        gtae = new GenericTableAreaEditor(this, tabelaDadosTecnicos, false);
      
        dtms = dtmdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(), 
                    ControlePerfil.getInstance().getAno()});
        
        PreencherColunaIND(indicadores);
        
        PreencherTabelaDTM(dtms);
        
        initGTAE();
        
        definirBDListeners();
    }
    
    private void PreencherColunaIND(List<Indicador> ind){
       
        int count = 0;
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaDadosTecnicos.getModel();
        Object[] colunaIND = new Object[tabelaDadosTecnicos.getRowCount()];
        
        for(int i = 0; i < ind.size(); i++){
            
            if(i == 1)   { colunaIND[count] = "MÉDIA Litros/dia (L)"; count++;
                           colunaIND[count] = ""; count++;
                           colunaIND[count] = "INDICADORES PRODUTIVOS"; count++; }
            if(i == 11)  { colunaIND[count] = ""; count++; 
                           colunaIND[count] = "INDICADORES SANITÁRIOS"; count++; }
           
            colunaIND[count] = ind.get(i).getIndicador();
            count++;
        }
        
        for(int i = 0; i < modelIndicadores.getRowCount(); i++) {
            modelIndicadores.setValueAt(colunaIND[i], i, 0);
        }
    }
    
    private void PreencherTabelaDTM(List<DadosTecMensais> dtm){
        
        int indexCol;
        double dadoTemp, tempMedia, divisao;
        
        int ano = ControlePerfil.getInstance().getAno();
        DefaultTableModel modelDadosTecnicos = (DefaultTableModel) tabelaDadosTecnicos.getModel();
        
        Object[] linhaTemp = new Object[14];
        
        for( int i = 0; i < modelDadosTecnicos.getRowCount(); i++) {
            
            for( int j = 0; j < dtm.size(); j++){
                    
                indexCol = dtm.get(j).getMes();
                
                if( modelDadosTecnicos.getValueAt(i, 0).equals(dtm.get(j).getIndicador().getIndicador())){

                    dadoTemp = dtm.get(j).getDado();

                    linhaTemp[indexCol] = dadoTemp;
                }

                if( i == 1 && dtm.get(j).getAno() == ano) {    
                    if (modelDadosTecnicos.getValueAt(0, indexCol) != null) {
                        tempMedia = (Double) modelDadosTecnicos.getValueAt(0, indexCol);

                        linhaTemp[indexCol] = Calc.dividir(tempMedia, Util.diasDoMes(ano, indexCol));
                          
                    }
                }
            }
            divisao = Calc.calcularMedia(linhaTemp);
            
            if (divisao != 0.0) { 
                linhaTemp[13] = divisao; 
            }         

            for(int k = 1; k < linhaTemp.length; k++) {              
                modelDadosTecnicos.setValueAt(linhaTemp[k], i, k);
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
        textoEntrada = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDadosTecnicos = new javax.swing.JTable();
        editarValoresBT = new javax.swing.JButton();
        retornarBT = new javax.swing.JButton();
        avancarBT = new javax.swing.JButton();
        perfilLabel = new javax.swing.JLabel();
        excelBT = new javax.swing.JButton();

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
        textoEntrada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoEntrada.setText("DADOS TÉCNICOS MENSAIS");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabelaDadosTecnicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Indicador", "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO", "Média"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDadosTecnicos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaDadosTecnicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDadosTecnicos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaDadosTecnicos);
        tabelaDadosTecnicos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tabelaDadosTecnicos.getColumnModel().getColumnCount() > 0) {
            tabelaDadosTecnicos.getColumnModel().getColumn(0).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(0).setPreferredWidth(236);
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
            tabelaDadosTecnicos.getColumnModel().getColumn(11).setPreferredWidth(80);
            tabelaDadosTecnicos.getColumnModel().getColumn(12).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(13).setResizable(false);
        }

        editarValoresBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarValoresBT.setToolTipText("Inserir/Editar dados ");
        editarValoresBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarValoresBTActionPerformed(evt);
            }
        });

        retornarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/left_arrow.png"))); // NOI18N
        retornarBT.setToolTipText("Retornar");
        retornarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retornarBTActionPerformed(evt);
            }
        });

        avancarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/right_arrow.png"))); // NOI18N
        avancarBT.setToolTipText("Avançar");
        avancarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avancarBTActionPerformed(evt);
            }
        });

        perfilLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        perfilLabel.setForeground(new java.awt.Color(0, 38, 255));
        perfilLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perfilLabel.setText("jLabel2");

        excelBT.setText("Exportar para Excel");
        excelBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelBTActionPerformed(evt);
            }
        });

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(perfilLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textoEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                        .addComponent(excelBT)
                        .addGap(68, 68, 68)
                        .addComponent(editarValoresBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoEntrada)
                            .addComponent(editarValoresBT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(excelBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(perfilLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
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
        
        telaMes.setTitle("SGPL - DTM - Seleção de Mês");
        telaMes.setVisible(true);
        
        int selecionado = telaMes.getSelected(); 
       
        if (selecionado > 0) {
            gtae.setLabelText("Editar - " + telaMes.getMonthSelected().toUpperCase() );
            gtae.setTitle("Editar D.T.M. - " + telaMes.getMonthSelected().toUpperCase());
            
            configGTAE(selecionado);
            
            gtae.showEditor(evt);
        }
    }//GEN-LAST:event_editarValoresBTActionPerformed

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane1.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 225);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane1.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 225);
    }//GEN-LAST:event_avancarBTActionPerformed

    private void excelBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelBTActionPerformed
        Util.CSVWriter(tabelaDadosTecnicos, textoEntrada.getText());
    }//GEN-LAST:event_excelBTActionPerformed

    private void initGTAE() {
        List<String> indColumnRows = new ArrayList<>(tabelaDadosTecnicos.getRowCount());
        
        for(int i = 0; i < tabelaDadosTecnicos.getRowCount(); i++) {
            indColumnRows.add(Cast.toString(fixedTable.getFixedTable().getValueAt(i, 0).toString()));
        }
        
        gtae.setName("GTAE Dados Técnicos Mensais");
        gtae.addStringColumn(fixedTable.getFixedTable().getColumnModel().getColumn(0).getPreferredWidth(), "Indicador", indColumnRows, 
                fixedTable.getFixedTable().getDefaultRenderer(Object.class));
        
        gtae.setDefaultRenderer(tabelaDadosTecnicos.getDefaultRenderer(Object.class));
        
    }
    
    private void configGTAE(int selected) {
        
        List<Integer> rowsNotEditable = Arrays.asList(1, 2, 3, 14, 15);
        
        gtae.setRowsDisplayed(15);
        
        gtae.getEditTable().getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        
        gtae.setAllowEmptyRows(true);
        gtae.setAllowEmptyCells(true);
        
        gtae.setColumnInterval(selected-1, selected-1);
        
        gtae.setRowInterval(0, tabelaDadosTecnicos.getRowCount()-1);
        
        gtae.processEditor();
        
        for (int i: rowsNotEditable) {
            gtae.setRowEditable(i, false);
        } 
         
    }
    
    private void definirBDListeners() {
        
         gtae.addTableModifyListener((TableModifiedEvent evt) -> {
           
            Object[][] areaData = evt.getTableAreaData();
            List<Integer> linhas = evt.getRowsModified();
                    
            int ano = ControlePerfil.getInstance().getAno();
            
            for (Integer l : linhas) {
                int mes = gtae.getStartColumn() + 1;
   
                String valor = Cast.toString(areaData[l][0]);
                double dado = (valor.isEmpty()? 0.0 : Double.parseDouble(valor));
                
                Indicador ind = dtmindao.retrieveByColumn("indicador", fixedTable.getFixedTable().getValueAt(l, 0)).get(0);

                List<DadosTecMensais> dadosTec = dtmdao.retrieveByColumns(new String[]{"mes", "ano", "idDTM_indicadorFK", "idPerfilFK"}, 
                                                        new Object[]{mes, ano, ind.getId(), atual.getId()});

                if (dadosTec.isEmpty()) {
                    if (!valor.isEmpty()) {
                        DadosTecMensais dadoTec = new DadosTecMensais(mes, ano, dado, ind, atual.getId());

                        dtmdao.insert(dadoTec);
                    }

                } else {
                    DadosTecMensais dtm = dadosTec.get(0);

                    if (valor.isEmpty()) {
                        dtmdao.remove(dtm.getId());
                        continue;
                    }

                    dtm.setDado(dado);

                    dtmdao.update(dtm);
                }
            }
            dtms = dtmdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(), 
                    ControlePerfil.getInstance().getAno()});
            PreencherTabelaDTM(dtms);
         });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton editarValoresBT;
    private javax.swing.JButton excelBT;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel perfilLabel;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaDadosTecnicos;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
