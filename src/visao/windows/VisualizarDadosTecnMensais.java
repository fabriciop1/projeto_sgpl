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
        tabelaDadosTecnicos.setShowHorizontalLines(true);
        tabelaDadosTecnicos.getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        
        super.setTitle("SGPL - " + atual.getNome() + " - Dados Técnicos Mensais");
        
        tabelaDadosTecnicos.setDefaultRenderer(Object.class, new ColorRendererDadosTec(true));
        tabelaDadosTecnicos.getColumnModel().getColumn(0).setCellRenderer(new ColorRendererDadosTec(false));
        
        dtmdao = new GenericDAO<>(DadosTecMensais.class);
        
        dtmindao = new GenericDAO<>(Indicador.class);
        indicadores = dtmindao.retrieveAll();
        
        gtae = new GenericTableAreaEditor(this, tabelaDadosTecnicos, false);
      
        PreencherColunaIND(indicadores);
        
        fillComboBox();
        
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
            if(i == 10)  { colunaIND[count] = ""; count++; 
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
        
        int ano = Integer.parseInt(anoCombo.getSelectedItem().toString());
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
            divisao = calcularMedia(linhaTemp);
            
            if (divisao != 0.0) { 
                linhaTemp[13] = divisao; 
            }         

            for(int j = 1; j < linhaTemp.length; j++) {              
                modelDadosTecnicos.setValueAt(linhaTemp[j], i, j);
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
        jLabel1 = new javax.swing.JLabel();
        anoCombo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
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
        tabelaDadosTecnicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDadosTecnicos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaDadosTecnicos);
        tabelaDadosTecnicos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tabelaDadosTecnicos.getColumnModel().getColumnCount() > 0) {
            tabelaDadosTecnicos.getColumnModel().getColumn(0).setResizable(false);
            tabelaDadosTecnicos.getColumnModel().getColumn(0).setPreferredWidth(240);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
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
                    .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane1.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 250);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane1.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 250);
    }//GEN-LAST:event_avancarBTActionPerformed

    private void initGTAE() {
        List<String> indColumnRows = new ArrayList<>(tabelaDadosTecnicos.getRowCount());
        
        for(int i = 0; i < tabelaDadosTecnicos.getRowCount(); i++) {
            indColumnRows.add(Cast.toString(tabelaDadosTecnicos.getValueAt(i, 0).toString()));
        }
        
        gtae.setName("GTAE Dados Técnicos Mensais");
        gtae.addStringColumn(tabelaDadosTecnicos.getColumnModel().getColumn(0).getPreferredWidth(), "Indicador", indColumnRows, 
                tabelaDadosTecnicos.getColumnModel().getColumn(0).getCellRenderer());
        
        gtae.setDefaultRenderer(tabelaDadosTecnicos.getDefaultRenderer(Object.class));
        
    }
    
    private void configGTAE(int selected) {
        
        List<Integer> rowsNotEditable = Arrays.asList(1, 2, 3, 13, 14);
        
        gtae.setRowsDisplayed(14);
        
        gtae.getEditTable().getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        
        gtae.setAllowEmptyRows(true);
        gtae.setAllowEmptyCells(true);
        
        gtae.setColumnInterval(selected, selected);
        
        gtae.setRowInterval(0, tabelaDadosTecnicos.getRowCount()-1);
        
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
                int mes = gtae.getStartColumn();
   
                String valor = Cast.toString(areaData[l][0]);
                double dado = (valor.isEmpty()? 0.0 : Double.parseDouble(valor));
                
                Indicador ind = dtmindao.retrieveByColumn("indicador", tabelaDadosTecnicos.getValueAt(l, 0)).get(0);

                List<DadosTecMensais> dadosTec = dtmdao.retrieveByColumns(new String[]{"mes", "ano", "idDTM_indicadorFK", "idPerfilFK"}, 
                                                        new Object[]{mes, ano, ind.getId(), atual.getId()});

                if (dadosTec.isEmpty()) {
                    DadosTecMensais dadoTec = new DadosTecMensais(mes, ano, dado, ind, atual.getId());

                    dtmdao.insert(dadoTec);

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaDadosTecnicos;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
