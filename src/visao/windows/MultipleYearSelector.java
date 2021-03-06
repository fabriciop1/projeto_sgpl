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
import java.awt.Cursor;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Perfil;
import util.Cast;

/**
 *
 * @author Fabricio
 */
public class MultipleYearSelector extends javax.swing.JDialog {

     private final Perfil atual;
     private final List<Integer> selectedYears;
     private int tipoIndicador;
    /**
     * Creates new form MultipleYearSelector
     * @param parent
     * @param modal
     */
    public MultipleYearSelector(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Image image = new ImageIcon(getClass().getResource("/visao/images/cattle.png")).getImage();
        this.setIconImage(image);
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setTitle("SGPL - Relatório de Indicadores Anuais");
        
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.getRootPane().setDefaultButton(confirmaBT);
        
        listaAnos.setBackground(Color.WHITE);
        listaAnos.setSelectionBackground(Color.GRAY);
        listaAnos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        selectedYears = new ArrayList<>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        tipoTecnico = new javax.swing.JRadioButton();
        tipoEconomico = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaAnos = new javax.swing.JList<>();
        confirmaBT = new javax.swing.JButton();
        cancelarBT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel4.setText("Tipo de Indicadores:");

        buttonGroup1.add(tipoTecnico);
        tipoTecnico.setText("Técnicos");
        tipoTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoTecnicoActionPerformed(evt);
            }
        });

        buttonGroup1.add(tipoEconomico);
        tipoEconomico.setText("Econômicos");
        tipoEconomico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoEconomicoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("INDICADORES");

        listaAnos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Selecione um tipo de indicador" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaAnos);

        confirmaBT.setText("OK");
        confirmaBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmaBTActionPerformed(evt);
            }
        });

        cancelarBT.setText("Cancelar");
        cancelarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarBTActionPerformed(evt);
            }
        });

        jLabel1.setText("Selecione o(s) ano(s) desejado(s): ");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Segure Ctrl e clique nos anos para selecionar vários");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addComponent(confirmaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(35, 35, 35)
                        .addComponent(tipoTecnico)
                        .addGap(18, 18, 18)
                        .addComponent(tipoEconomico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tipoEconomico)
                    .addComponent(tipoTecnico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tipoTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoTecnicoActionPerformed
        listaAnos.removeAll();
        
        DefaultListModel listModel = new DefaultListModel();
        
        GenericDAO<DadosTecMensais> dadosTecDAO = new GenericDAO(DadosTecMensais.class);
        
        List<DadosTecMensais> dados = dadosTecDAO.executeSQL("" + 
                "SELECT ano FROM inventario_terras        AS it  WHERE it.idPerfilFK  = " + atual.getId() + " UNION " + 
                "SELECT ano FROM inventario_forrageiras   AS ifo WHERE ifo.idPerfilFK = " + atual.getId() + " UNION " +
                "SELECT ano FROM inventario_maquinas      AS im  WHERE im.idPerfilFK  = " + atual.getId() + " UNION " + 
                "SELECT ano FROM inventario_benfeitorias  AS ib  WHERE ib.idPerfilFK  = " + atual.getId() + " UNION " + 
                "SELECT ano FROM inventario_animais       AS ia  WHERE ia.idPerfilFK  = " + atual.getId() + " UNION " + 
                "SELECT ano FROM inventario_resumo        AS ir  WHERE ir.idPerfilFK  = " + atual.getId() + " UNION " +
                "SELECT ano FROM dados_economicos_mensais AS dem WHERE dem.idPerfilFK = " + atual.getId() + " UNION " +
                "SELECT ano FROM dados_tecnicos_mensais   AS dtm WHERE dtm.idPerfilFK = " + atual.getId() +
                " ORDER BY ano");


        if (dados.isEmpty()) {
            listModel.addElement("Nenhum ano cadastrado.");
        }

        for(int i = 0; i < dados.size(); i++) {
            listModel.addElement(Cast.toString(dados.get(i).getAno()));
        }
        
        listaAnos.setModel(listModel);
    }//GEN-LAST:event_tipoTecnicoActionPerformed

    private void tipoEconomicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoEconomicoActionPerformed
        tipoTecnicoActionPerformed(evt);
    }//GEN-LAST:event_tipoEconomicoActionPerformed

    private void cancelarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarBTActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_cancelarBTActionPerformed

    private void confirmaBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmaBTActionPerformed
        
        if (tipoEconomico.isSelected()) {
            setTipoIndicador(1);
        } else if (tipoTecnico.isSelected()) {
            setTipoIndicador(2);
        } else {
             JOptionPane.showMessageDialog(this, "Nenhum Tipo de Indicadores foi selecionado.", "Alerta - Seleção de Tipo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (listaAnos.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum ano foi selecionado.", "Alerta - Seleção de Ano", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (listaAnos.getSelectedValue().equals("Nenhum ano cadastrado.")) {
            JOptionPane.showMessageDialog(this, "Nenhum ano foi cadastrado para este perfil.", 
                    "Alerta - Nenhum ano cadastrado", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        List<String> selectedYearsStr = listaAnos.getSelectedValuesList();
        
        setSelectedYears(selectedYearsStr);
        
        ControleIndicadoresAnuais controle = ControleIndicadoresAnuais.getInstance();
        controle.gerarIndicadores(getTipoIndicador(), getSelectedYears());
        
        Cursor wait = new Cursor(Cursor.WAIT_CURSOR);
        this.setCursor(wait);
        
        new VisualizarIndicadoresAnuais().setVisible(true);
        
        this.getOwner().setVisible(false);
        this.getOwner().dispose();
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_confirmaBTActionPerformed

    private void setSelectedYears(List<String> selectedYears) {
        for (int i = 0; i < selectedYears.size(); i++) {
            this.selectedYears.add(Integer.parseInt(selectedYears.get(i)));
        }
    }
    
    public List<Integer> getSelectedYears() {
        return this.selectedYears;
    }

    public int getTipoIndicador() {
        return tipoIndicador;
    }

    public void setTipoIndicador(int tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelarBT;
    private javax.swing.JButton confirmaBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaAnos;
    private javax.swing.JRadioButton tipoEconomico;
    private javax.swing.JRadioButton tipoTecnico;
    // End of variables declaration//GEN-END:variables
}
