/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControlePerfil;
import controle.ControleIndicadoresMensais;
import flex.db.GenericDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Perfil;
import util.DecimalFormatRenderer;
import util.Util;

/**
 * @author Alexandre
 */
public class VisualizarIndicadoresMensais extends javax.swing.JFrame {

    private final Perfil atual;
    private final List<DadosEconMensais> dems;
    private final List<DadosTecMensais>  dtms;
    private final GenericDAO<DadosEconMensais> demdao;
    private final GenericDAO<DadosTecMensais>  dtmdao;
    private final ControleIndicadoresMensais crm;
    
    /**
     * Creates new form VisualizarRelatoriosMensais
     */
    public VisualizarIndicadoresMensais() {
        initComponents();
        
        tabelaIndicadoresMensais.setShowGrid(true);
        tabelaIndicadoresMensais.getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
        tabelaIndicadoresMensais.getTableHeader().setResizingAllowed(false);
        crm = ControleIndicadoresMensais.getInstance();
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        
        demdao = new GenericDAO<>(DadosEconMensais.class);
        dems = demdao.executeSQL("SELECT * "
                        + "FROM dados_economicos_mensais AS d "
                        + "WHERE (d.ano >= " + crm.getAnoIni() + "  AND "
                               + "d.ano <= " + crm.getAnoFim() + "  AND "
                               + "d.mes >= " + crm.getMesIni() + "  AND "
                               + "d.mes <= " + crm.getMesFim() + ") AND "
                               + "d.idPerfilFK = " + atual.getId()
                        + " ORDER BY d.ano, d.mes");
             
        dtmdao = new GenericDAO<>(DadosTecMensais.class);
        dtms = dtmdao.executeSQL("SELECT * "
                        + "FROM dados_tecnicos_mensais AS d "
                        + "WHERE (d.ano >= " + crm.getAnoIni() + "  AND "
                               + "d.ano <= " + crm.getAnoFim() + "  AND "
                               + "d.mes >= " + crm.getMesIni() + "  AND "
                               + "d.mes <= " + crm.getMesFim() + ") AND "
                               + "d.idPerfilFK = " + atual.getId()
                        + " ORDER BY d.ano, d.mes");
                
        if( crm.getTipoIndicador() == 1 ) { // Tipo Indicadores Econômicos   
            
            preencherTabelaIEM(dems, dtms);            
            
        } else if( crm.getTipoIndicador() == 2 ){ // Tipo Indicadores Técnicos
            
            preencherTabelaITM(dtms, dems);
        }
        
    }
    
    private void preencherTabelaIEM(List<DadosEconMensais> iem, List<DadosTecMensais> itm){
        
        textoEntrada.setText("INDICADORES ECONÔMICOS MENSAIS");
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Econômicos Mensais");
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadoresMensais.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = crm.getAnoIni();
        int anoFim  = crm.getAnoFim();
        int mesCont = crm.getMesIni();
        int mesFim  = crm.getMesFim();
        Object[] temp;        
        modelIndicadores.addColumn("Indicadores", crm.getIndEconomMensais());
        modelIndicadores.addColumn("Unidade", crm.getUniEconomMensais());
        
        do{
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            temp = crm.getConteudoEconomico(iem, itm, mesCont, anoCont);
            
            //----FINAL---------------------------------------------------------
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
            Util.clearVector(temp);
            mesCont++;
        }while(anoCont < anoFim || mesCont <= mesFim);
        
        tabelaIndicadoresMensais.setModel(modelIndicadores);
        tabelaIndicadoresMensais.getColumnModel().getColumn(0).setPreferredWidth(390);
        tabelaIndicadoresMensais.setDefaultRenderer(Object.class, new DecimalFormatRenderer(false));
        
    }
    
    private void preencherTabelaITM(List<DadosTecMensais> dtms, List<DadosEconMensais> dems){
        
        textoEntrada.setText("INDICADORES TÉCNICOS MENSAIS");
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Técnicos Mensais");
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadoresMensais.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = crm.getAnoIni();
        int anoFim  = crm.getAnoFim();
        int mesCont = crm.getMesIni();
        int mesFim  = crm.getMesFim();
        Object[] temp;
        
        modelIndicadores.addColumn("Indicadores", crm.getIndTecnMensais());
        modelIndicadores.addColumn("Unidade", crm.getUniTecnMensais());
        
        do{
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            temp = crm.getConteudoTecnico(dtms, dems, mesCont, anoCont);
            
            //----FINAL---------------------------------------------------------
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
          
            Util.clearVector(temp);
            mesCont++;
        }while(anoCont < anoFim || mesCont <= mesFim);
        
        tabelaIndicadoresMensais.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabelaIndicadoresMensais.setDefaultRenderer(Object.class, new DecimalFormatRenderer(false) {
            private final Color BG = null;
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                
                if (row == 0 || row == 20) {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaIndicadoresMensais = new javax.swing.JTable();
        retornarBT = new javax.swing.JButton();
        avancarBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setText("INDICADORES MENSAIS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tabelaIndicadoresMensais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }

        )   {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        tabelaIndicadoresMensais.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaIndicadoresMensais.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaIndicadoresMensais.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaIndicadoresMensais);
        tabelaIndicadoresMensais.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

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
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(textoEntrada)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(avancarBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textoEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
        JScrollBar barPanel = jScrollPane1.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() - 695);
    }//GEN-LAST:event_retornarBTActionPerformed

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
        JScrollBar barPanel = jScrollPane1.getHorizontalScrollBar();
        barPanel.setValue(barPanel.getValue() + 695);
    }//GEN-LAST:event_avancarBTActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaIndicadoresMensais;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
