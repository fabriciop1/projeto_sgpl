/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import flex.table.GenericTableAreaEditor;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 *
 * @author Usuário
 */
public class MonthSelector extends javax.swing.JDialog {

    int selected;
    /**
     * Creates new form MonthSelector
     * @param parent
     * @param modal
     */
    public MonthSelector(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        super.setTitle("SGPL - DEM - Seleção de Mês");
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mesCombo = new javax.swing.JComboBox<>();
        confirmaBT = new javax.swing.JButton();
        cancelaBT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        mesCombo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        mesCombo.setMaximumRowCount(13);
        mesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));

        confirmaBT.setText("OK");
        confirmaBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmaBTActionPerformed(evt);
            }
        });

        cancelaBT.setText("Cancelar");
        cancelaBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelaBTActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Selecione o mês para inserção/edição dos dados: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(cancelaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(confirmaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(mesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(confirmaBT, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(cancelaBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmaBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmaBTActionPerformed
        this.setVisible(false);        
        this.selected = mesCombo.getSelectedIndex();
    }//GEN-LAST:event_confirmaBTActionPerformed

    private void cancelaBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelaBTActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelaBTActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.setVisible(false);
    }//GEN-LAST:event_formWindowClosed

   public int getSelected() {
       return this.selected;
   }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelaBT;
    private javax.swing.JButton confirmaBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> mesCombo;
    // End of variables declaration//GEN-END:variables
}
