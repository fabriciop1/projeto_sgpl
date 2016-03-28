/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jefferson Sales
 */
public class GenericTableRowEditor extends javax.swing.JDialog implements ActionListener {

    /**
     * Creates new form GenericTableRowEditor
     */
    
    private JTable sourceTable;
    private JButton editButton;
    private int selectedRow;
    private final int columnCount;
    
    public GenericTableRowEditor(java.awt.Frame parent, JTable sourceTable, JButton editButton) {
        super(parent, true);
        
        this.sourceTable = sourceTable;
        this.editButton = editButton;
        
        this.columnCount = sourceTable.getColumnCount();
        
        initComponents();
        
        composeEditTable();
        clearEditTable();
        
        editButton.addActionListener(this);
        
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(this.getSize());
        //this.pack();
    }
    
    private void composeEditTable(){
        
        editTable.setAutoCreateColumnsFromModel(false);
        editTable.setCellEditor(sourceTable.getCellEditor());
        editTable.setCellSelectionEnabled(true);
        editTable.setShowHorizontalLines(true);
        editTable.setShowVerticalLines(true);
        
        //boolean[] isColumnEditableArray = new boolean[columnCount];
        Class[] columnTypeArray = new Class[columnCount];
        String[] columnNameArray = new String[columnCount];
        Object[][] dataMatrix = new Object[columnCount][0];  
        
        int minDialogSize = 80;        
        
        for(int i=0; i<columnCount; i++){
            columnTypeArray[i] = sourceTable.getColumnClass(i);
            columnNameArray[i] = sourceTable.getColumnName(i);
            //isColumnEditableArray[i] = sourceTable.isCellEditable(selectedRow, i);
        }
        
        editTable.setModel(new DefaultTableModel(dataMatrix, columnNameArray){
            
            Class[] types = columnTypeArray;
            
            //boolean[] isColumnEditable = isColumnEditableArray;
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                //return isColumnEditable[columnIndex];
                return true;
            }
        });
        
        editTable.createDefaultColumnsFromModel();
        
        for(int i=0; i<columnCount; i++){
            
            editTable.getColumnModel().getColumn(i).setCellRenderer(
                    sourceTable.getCellRenderer(selectedRow, i));
            
            int sourceColumnWidth = sourceTable.getColumnModel().getColumn(i).getWidth();
            minDialogSize += sourceColumnWidth;
            
            editTable.getColumnModel().getColumn(i).setMinWidth(sourceColumnWidth);
            
            System.out.println("" + (i+1) + ". Source: " + sourceTable.getColumnClass(i).getName());
            System.out.println("" + (i+1) + ". Edit: " + editTable.getColumnClass(i).getName());

        }
        
        System.out.println("------------------------------------------------");
        
        this.setSize(minDialogSize, this.getHeight());
    }
    
    private void refillEditTable(){
        
        Object[] dataArray = new Object[columnCount];
        
        for(int i=0; i<columnCount; i++){
            dataArray[i] = sourceTable.getValueAt(selectedRow, i);
        }
        
        ((DefaultTableModel)editTable.getModel()).addRow(dataArray);
    }
    
    private void clearEditTable(){
        
        while(editTable.getRowCount() > 0){
            ((DefaultTableModel)editTable.getModel()).removeRow(0); 
        }
    }
    
    private void updateSourceTable(){
        
        for(int i=0; i<columnCount; i++){
            
            Object value = editTable.getValueAt(0, i);
            
            if(value == null){
                System.err.println("Warning: NULL value at R=0 C=" + i);
                continue;
            }
            
            sourceTable.setValueAt(value, selectedRow, i);
            
            ((DefaultTableModel)sourceTable.getModel()).fireTableCellUpdated(selectedRow, i);
        }
        
        ((DefaultTableModel)sourceTable.getModel()).fireTableRowsUpdated(selectedRow, selectedRow);
    }
    
    private void hideEditor(){
        this.setVisible(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == editButton){
            
            if(sourceTable.getSelectedRowCount() > 0){
                
                clearEditTable();
                refillEditTable();
            
                this.selectedRow = sourceTable.getSelectedRow();
                this.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para editar",
                        "Editar - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        editTable = new javax.swing.JTable();
        editLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        editTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        editTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        editTable.setRowHeight(25);
        jScrollPane1.setViewportView(editTable);

        editLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        editLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editLabel.setText("Editar ");
        editLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editLabel.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                editLabelAncestorResized(evt);
            }
        });

        saveButton.setText("Salvar");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(editLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(editLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editLabelAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_editLabelAncestorResized
        // TODO add your handling code here:
    }//GEN-LAST:event_editLabelAncestorResized

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
                        
        updateSourceTable();
        clearEditTable();
        
        hideEditor();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed

        hideEditor();
        clearEditTable();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GenericTableRowEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GenericTableRowEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GenericTableRowEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GenericTableRowEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                GenericTableRowEditor dialog = new GenericTableRowEditor(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel editLabel;
    private javax.swing.JTable editTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
