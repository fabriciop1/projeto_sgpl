/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JDialog;
import util.Cast;

/**
 *
 * @author Fabricio
 */
public class YearSelector extends JDialog {
    
    String selected;
    int index;

    /**
     * Creates new form YearSelector
     * @param parent
     * @param modal
     */
    public YearSelector(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        
        Calendar cal = GregorianCalendar.getInstance();
        
        for(int i = cal.get(Calendar.YEAR); i >= 2010; i--) {
            novoAnoCombo.addItem(Cast.toString(i));
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

        novoAnoCombo = new javax.swing.JComboBox<>();
        cancelaBT = new javax.swing.JButton();
        confirmaBT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(347, 194));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        novoAnoCombo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        novoAnoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        novoAnoCombo.setPreferredSize(new java.awt.Dimension(93, 24));

        cancelaBT.setText("Cancelar");
        cancelaBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelaBTActionPerformed(evt);
            }
        });

        confirmaBT.setText("OK");
        confirmaBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmaBTActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Selecione o novo ano: ");
        jLabel1.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(novoAnoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cancelaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(confirmaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(novoAnoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        novoAnoCombo.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmaBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmaBTActionPerformed
       this.setVisible(false);
       this.selected = (String)novoAnoCombo.getSelectedItem();
       this.index = novoAnoCombo.getSelectedIndex();
    }//GEN-LAST:event_confirmaBTActionPerformed

    private void cancelaBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelaBTActionPerformed
       this.setVisible(false);
    }//GEN-LAST:event_cancelaBTActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.setVisible(false);
    }//GEN-LAST:event_formWindowClosed

    public String getSelected() {
        return this.selected;
    }
    
    public int getIndex() {
        return this.index;
    }

    public void removeItem(String ano) {
        novoAnoCombo.removeItem(ano);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelaBT;
    private javax.swing.JButton confirmaBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> novoAnoCombo;
    // End of variables declaration//GEN-END:variables
}
