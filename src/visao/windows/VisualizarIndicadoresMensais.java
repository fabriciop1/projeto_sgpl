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
    private List<DadosTecMensais>  dtms;
    private GenericDAO<DadosEconMensais> demdao;
    private GenericDAO<DadosTecMensais>  dtmdao;
    private final ControleIndicadoresMensais crm;
    
    /**
     * Creates new form VisualizarRelatoriosMensais
     */
    public VisualizarIndicadoresMensais() {
        initComponents();
        
        tabelaIndicadoresMensais.setShowGrid(true);
        tabelaIndicadoresMensais.setShowHorizontalLines(true);
        tabelaIndicadoresMensais.setCellSelectionEnabled(false);
        tabelaIndicadoresMensais.setRowSelectionAllowed(true);
        
        crm = ControleIndicadoresMensais.getInstance();
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Mensais");
          
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
                
        if( crm.getTipoIndicador() == 1 ){    
            preencherTabelaIEM(dems, dtms);            
        } else if( crm.getTipoIndicador() == 2 ){
            preencherTabelaITM(dtms);
        }
    }
    
    private void preencherTabelaIEM(List<DadosEconMensais> iem, List<DadosTecMensais> itm){
        
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Econômicos Mensais");
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadoresMensais.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = crm.getAnoIni();
        int anoFim  = crm.getAnoFim();
        int mesCont = crm.getMesIni() - 1;
        int mesFim  = crm.getMesFim();
        Object[] temp;
                
        modelIndicadores.addColumn("Indicadores", crm.getIndEconomMensais());
        modelIndicadores.addColumn("Unidade", crm.getUniEconomMensais());
        
        do{
            temp = crm.getConteudoEconomico(iem, itm, mesCont, anoCont);
                        
            mesCont++;
            
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            //----FINAL---------------------------------------------------------
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
           
            Util.clearVector(temp);
        }while(anoCont != anoFim || mesCont != mesFim);
        
        tabelaIndicadoresMensais.setModel(modelIndicadores);
        tabelaIndicadoresMensais.getColumnModel().getColumn(0).setPreferredWidth(370);
        tabelaIndicadoresMensais.setDefaultRenderer(Object.class, new DecimalFormatRenderer(false));
        
    }
    
    private void preencherTabelaITM(List<DadosTecMensais> dtms){
        
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Técnicos Mensais");
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaIndicadoresMensais.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = crm.getAnoIni();
        int anoFim  = crm.getAnoFim();
        int mesCont = crm.getMesIni() - 1;
        int mesFim  = crm.getMesFim();
        Object[] temp;
        
        modelIndicadores.addColumn("Indicadores", crm.getIndTecnMensais());
        modelIndicadores.addColumn("Unidade", crm.getUniTecnMensais());
        
        do{
            temp = crm.getConteudoTecnico(dtms, mesCont + 1, anoCont);
            
            mesCont++;
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            //----FINAL---------------------------------------------------------
          
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
          
            Util.clearVector(temp);
        }while(anoCont != anoFim || mesCont != mesFim);
        
        tabelaIndicadoresMensais.getColumnModel().getColumn(0).setPreferredWidth(330);
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
    
    public double getTotalLitroLeite(List<DadosTecMensais> dtm, int mes, int ano){
        for(int i = 0; i < dtm.size(); i++){
            if(dtm.get(i).getIndicador().getIndicador().equals("Total Litros/Mês (L")
                    && dtm.get(i).getMes() == mes && dtm.get(i).getAno()== ano){
                return dtm.get(i).getDado();
            }
        }
        
        return -1;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaIndicadoresMensais = new javax.swing.JTable();

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

        tabelaIndicadoresMensais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelaIndicadoresMensais.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaIndicadoresMensais.setColumnSelectionAllowed(true);
        tabelaIndicadoresMensais.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaIndicadoresMensais.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaIndicadoresMensais);
        tabelaIndicadoresMensais.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(276, 276, 276)
                        .addComponent(textoEntrada)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(textoEntrada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaIndicadoresMensais;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
