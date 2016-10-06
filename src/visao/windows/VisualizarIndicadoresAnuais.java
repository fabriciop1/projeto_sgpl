/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControleIndicadoresAnuais;
import controle.ControlePerfil;
import flex.db.GenericDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Perfil;
import util.DecimalFormatRenderer;
import util.FixedColumnTable;
import util.Util;

/**
 *
 * @author Fabricio
 */
public class VisualizarIndicadoresAnuais extends javax.swing.JFrame {

    private final Perfil atual;
    private List<DadosEconMensais> dems;
    private List<DadosTecMensais>  dtms;
    private final GenericDAO<DadosEconMensais> demdao;
    private final GenericDAO<DadosTecMensais> dtmdao;
    private final ControleIndicadoresAnuais cia;
    private FixedColumnTable fixedTable;
    
    /**
     * Creates new form VisualizarRelatoriosAnuais
     */
    
    public VisualizarIndicadoresAnuais() {
        
        initComponents();
        
        Image image = new ImageIcon(getClass().getResource("/visao/images/cattle.png")).getImage();
        this.setIconImage(image);
        
        tabelaIndicadoresAnuais.setShowGrid(true);
        tabelaIndicadoresAnuais.getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        tabelaIndicadoresAnuais.getTableHeader().setResizingAllowed(false);
        
        cia = ControleIndicadoresAnuais.getInstance();
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        perfilLabel.setText(atual.getNome() + " " + ControlePerfil.getInstance().getAno() );
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        
        dtmdao = new GenericDAO<>(DadosTecMensais.class);
        demdao = new GenericDAO<>(DadosEconMensais.class);

        if (cia.getTipoIndicador() == 1) { //Tipo Indicadores Econômicos
            dems = demdao.executeSQL("SELECT ano, quantidade, valorUnitario, idDEM_especificacaoFK "
                            + "FROM dados_economicos_mensais AS dem "
                            + "WHERE (dem.ano <= " + cia.getAnosSelecionados().get(0) + " AND "
                                  + "dem.ano >= " + cia.getAnosSelecionados().get(cia.getAnosSelecionados().size()-1) + ") AND "
                                  + "dem.idPerfilFK = " + atual.getId()
                                  + " ORDER BY dem.ano");
            
            dtms = dtmdao.executeSQL("SELECT ano, dado, idDTM_indicadorFK "
                            + "FROM dados_tecnicos_mensais AS dtm "
                            + "WHERE (dtm.ano <= " + cia.getAnosSelecionados().get(0) + " AND "
                                  + "dtm.ano >= " + cia.getAnosSelecionados().get(cia.getAnosSelecionados().size()-1) + ") AND "
                                  + "dtm.idPerfilFK = " + atual.getId() + " AND dtm.idDTM_indicadorFK <= 3"
                                  + " ORDER BY dtm.ano");
            
            preencherTabelaIEA(dems, dtms);
            fixedTable = new FixedColumnTable(2, jScrollPane2);
        } else if(cia.getTipoIndicador() == 2) { // Tipo Indicadores Técnicos
            
            dems = demdao.executeSQL("SELECT ano, quantidade, idDEM_especificacaoFK "
                            + "FROM dados_economicos_mensais AS dem "
                            + "WHERE (dem.ano <= " + cia.getAnosSelecionados().get(0) + " AND "
                                  + "dem.ano >= " + cia.getAnosSelecionados().get(cia.getAnosSelecionados().size()-1) + ") AND "
                                  + "dem.idPerfilFK = " + atual.getId() + " AND (dem.idDEM_especificacaoFK = 7 OR dem.idDEM_especificacaoFK = 70)"
                                  + " ORDER BY dem.ano");
            
            dtms = dtmdao.executeSQL("SELECT ano, dado, idDTM_indicadorFK "
                            + "FROM dados_tecnicos_mensais AS dtm "
                            + "WHERE (dtm.ano <= " + cia.getAnosSelecionados().get(0) + " AND "
                                  + "dtm.ano >= " + cia.getAnosSelecionados().get(cia.getAnosSelecionados().size()-1) + ") AND "
                                  + "dtm.idPerfilFK = " + atual.getId()
                                  + " ORDER BY dtm.ano");
            preencherTabelaITA(dtms, dems);
            
            fixedTable = new FixedColumnTable(2, jScrollPane2);
            fixedTable.getFixedTable().setDefaultRenderer(Object.class, tabelaIndicadoresAnuais.getDefaultRenderer(Object.class));
        } 
        
        fixedTable.getFixedTable().getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
                 
        
    }
    
    private void preencherTabelaIEA(List<DadosEconMensais> dems, List<DadosTecMensais> dtms) {
        
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Econômicos Anuais");
        textoEntrada.setText("INDICADORES ECONÔMICOS ANUAIS");
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadoresAnuais.getModel();
        modelIndicadores.setNumRows(0);
        
        modelIndicadores.addColumn("Indicador", cia.getIndEconomAnuais());
        modelIndicadores.addColumn("Unidade", cia.getUnidadesEconAnuais());
        
        for(int i = cia.getAnosSelecionados().size() - 1; i >= 0;  i--) {           
            modelIndicadores.addColumn(cia.getAnosSelecionados().get(i), cia.getConteudoEconomico(dems, dtms, cia.getAnosSelecionados().get(i)));
        }
        
        for(int i = 2; i < tabelaIndicadoresAnuais.getColumnCount(); i++) {
            tabelaIndicadoresAnuais.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
        
        tabelaIndicadoresAnuais.getColumnModel().getColumn(0).setPreferredWidth(390);
        tabelaIndicadoresAnuais.setDefaultRenderer(Object.class, new DecimalFormatRenderer(false));

    }

    private void preencherTabelaITA(List<DadosTecMensais> dtms, List<DadosEconMensais> dems) {
        
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Técnicos Anuais");
        textoEntrada.setText("INDICADORES TÉCNICOS ANUAIS");
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadoresAnuais.getModel();
        modelIndicadores.setNumRows(0);
        
        modelIndicadores.addColumn("Indicador", cia.getIndTecnAnuais());
        modelIndicadores.addColumn("Unidade", cia.getUnidadesTecAnuais());
        
        for(int i = cia.getAnosSelecionados().size() - 1; i >= 0;  i--) {
            modelIndicadores.addColumn(cia.getAnosSelecionados().get(i), cia.getConteudoTecnico(dtms, dems, cia.getAnosSelecionados().get(i))); 
        }
        tabelaIndicadoresAnuais.getColumnModel().getColumn(0).setPreferredWidth(380);
        
        for(int i = 2; i < tabelaIndicadoresAnuais.getColumnCount(); i++) {
            tabelaIndicadoresAnuais.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
        tabelaIndicadoresAnuais.setDefaultRenderer(Object.class, new DecimalFormatRenderer(false) {
            private final Color BG = null;
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                
                if (row == 0 || row == 22) {
                    this.setBackground(Color.LIGHT_GRAY);
                    this.setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    this.setBackground(null);
                }
                
                if (isSelected) {
                    super.setBackground(BG == null ? table.getSelectionBackground(): null);
                }
                return this;
            }
        });      
 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoEntrada = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaIndicadoresAnuais = new javax.swing.JTable();
        retornarBT = new javax.swing.JButton();
        avancarBT = new javax.swing.JButton();
        perfilLabel = new javax.swing.JLabel();
        excelBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoEntrada.setText("INDICADORES ANUAIS");

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tabelaIndicadoresAnuais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
            }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        tabelaIndicadoresAnuais.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaIndicadoresAnuais.setAutoscrolls(false);
        tabelaIndicadoresAnuais.setColumnSelectionAllowed(false);
        tabelaIndicadoresAnuais.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaIndicadoresAnuais.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabelaIndicadoresAnuais);
        tabelaIndicadoresAnuais.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tabelaIndicadoresAnuais.getColumnModel().getColumnCount() > 0) {
            tabelaIndicadoresAnuais.getColumnModel().getColumn(0).setResizable(false);
            tabelaIndicadoresAnuais.getColumnModel().getColumn(1).setResizable(false);
        }

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

        perfilLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        perfilLabel.setForeground(new java.awt.Color(0, 38, 255));
        perfilLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perfilLabel.setText("jLabel1");

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1070, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(279, 279, 279)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(perfilLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textoEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(98, 98, 98)
                        .addComponent(excelBT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoEntrada)
                            .addComponent(excelBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(avancarBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addComponent(perfilLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane2.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 600);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane2.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 600);
    }//GEN-LAST:event_avancarBTActionPerformed

    private void excelBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelBTActionPerformed
        Util.CSVWriter(tabelaIndicadoresAnuais, textoEntrada.getText());
    }//GEN-LAST:event_excelBTActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton excelBT;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel perfilLabel;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaIndicadoresAnuais;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
