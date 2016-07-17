/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControlePerfil;
import controle.ControleIndicadoresMensais;
import flex.db.GenericDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Perfil;
import util.Util;

/**
 * @author Alexandre
 */
public class VisualizarIndicadoresMensais extends javax.swing.JFrame {

    private final Perfil atual;
    private List<DadosEconMensais> dems;
    private List<DadosTecMensais>  dtms;
    private GenericDAO dao;
    private final ControleIndicadoresMensais cim;
    
    /**
     * Creates new form VisualizarRelatoriosMensais
     */
    public VisualizarIndicadoresMensais() {
        initComponents();
        
        tabelaRelatorios.setShowGrid(true);
        tabelaRelatorios.setShowHorizontalLines(true);
        tabelaRelatorios.setCellSelectionEnabled(false);
        tabelaRelatorios.setRowSelectionAllowed(true);
        
        cim = ControleIndicadoresMensais.getInstance();
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Mensais");
        
        if( cim.getTipoIndicador() == 1 ){
            
            dao = new GenericDAO<>(DadosEconMensais.class);
            
            dems = dao.executeSQL("SELECT * "
                                + "FROM dados_economicos_mensais AS d "
                                + "WHERE (d.ano >= " + cim.getAnoIni() + "  AND "
                                       + "d.ano <= " + cim.getAnoFim() + "  AND "
                                       + "d.mes >= " + cim.getMesIni() + "  AND "
                                       + "d.mes <= " + cim.getMesFim() + ") AND "
                                       + "d.idPerfilFK = " + atual.getId()
                                + " ORDER BY d.ano, d.mes");
            
            preencherTabelaIEM(dems);
            
        } else if( cim.getTipoIndicador() == 2 ){
            
            dao = new GenericDAO<>(DadosTecMensais.class);
            
            dtms = dao.executeSQL("SELECT * "
                                + "FROM dados_tecnicos_mensais AS d "
                                + "WHERE (d.ano >= " + cim.getAnoIni() + "  AND "
                                       + "d.ano <= " + cim.getAnoFim() + "  AND "
                                       + "d.mes >= " + cim.getMesIni() + "  AND "
                                       + "d.mes <= " + cim.getMesFim() + ") AND "
                                       + "d.idPerfilFK = " + atual.getId()
                                + " ORDER BY d.ano, d.mes");
            
            preencherTabelaITM(dtms);
        }
    }
    
    private void preencherTabelaIEM(List<DadosEconMensais> iem){
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaRelatorios.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = cim.getAnoIni();
        int anoFim  = cim.getAnoFim();
        int mesCont = cim.getMesIni() - 1;
        int mesFim  = cim.getMesFim();
        Object[] temp = new Object[32];
        
        do{
            temp[1] = "teste " + mesCont;
            
            mesCont++;
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            //----FINAL---------------------------------------------------------
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
           
            Util.clearVector(temp);
        }while(anoCont != anoFim || mesCont != mesFim);
        
        tabelaRelatorios.setModel(modelIndicadores);
    }
    
    private void preencherTabelaITM(List<DadosTecMensais> itm){
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaRelatorios.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = cim.getAnoIni();
        int anoFim  = cim.getAnoFim();
        int mesCont = cim.getMesIni() - 1;
        int mesFim  = cim.getMesFim();
        Object[] temp = new Object[32];
        
        do{
            temp[1] = "teste " + mesCont;
            
            mesCont++;
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            //----FINAL---------------------------------------------------------
          
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
          
            Util.clearVector(temp);
        }while(anoCont != anoFim || mesCont != mesFim);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaRelatorios = new javax.swing.JTable();

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

        tabelaRelatorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Indicadores", "Unidade"
            }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        tabelaRelatorios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaRelatorios.setColumnSelectionAllowed(true);
        tabelaRelatorios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaRelatorios.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaRelatorios);
        tabelaRelatorios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

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
    private javax.swing.JTable tabelaRelatorios;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
