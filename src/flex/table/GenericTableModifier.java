/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jefferson Sales
 */
public abstract class GenericTableModifier extends javax.swing.JDialog{

    /**
     * Creates new form GenericTableModifier
     */
    
    protected JTable sourceTable;
    
    private boolean forceCellEditing;
    private Frame parent;
    
    public GenericTableModifier(Frame parent, JTable sourceTable, boolean forceCellEditing) {
        super(parent, true);
        
        this.parent = parent;
        this.sourceTable = sourceTable;
        this.forceCellEditing = forceCellEditing;
        
        initComponents();
        
        editTable.setAutoCreateColumnsFromModel(false);
        editTable.setCellEditor(sourceTable.getCellEditor());
        editTable.setCellSelectionEnabled(true);
        editTable.setShowHorizontalLines(true);
        editTable.setShowVerticalLines(true);
        
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(this.getSize());
        this.setLabelText("");
        this.setVisible(false);
        
        composeEditTable();
        
        this.setLocation((screenWidth / 2) - (this.getWidth() / 2), (screenHeight / 2) - (this.getHeight() / 2));
    }
    
    protected void composeEditTable(){
        
        boolean[] columnEditableArray = new boolean[sourceTable.getColumnCount()];
        Class[] columnTypeArray = new Class[sourceTable.getColumnCount()];
        String[] columnNameArray = new String[sourceTable.getColumnCount()];
        Object[][] dataMatrix = new Object[sourceTable.getColumnCount()][1];  
        
        int minDialogSize = 80;        
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            columnTypeArray[i] = sourceTable.getColumnClass(i);
            columnNameArray[i] = sourceTable.getColumnName(i);
            columnEditableArray[i] = sourceTable.isCellEditable(sourceTable.getSelectedRow(), i);
        }
        
        editTable.setModel(new DefaultTableModel(dataMatrix, columnNameArray){
            
            Class[] types = columnTypeArray;
            boolean[] isColumnEditable = columnEditableArray;
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                if(!isForceCellEditingEnabled()){
                    return isColumnEditable[columnIndex];
                } else {
                    return true;
                }
            }
        });
        
        editTable.createDefaultColumnsFromModel();
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            
            editTable.getColumnModel().getColumn(i).setCellRenderer(
                    sourceTable.getCellRenderer(sourceTable.getSelectedRow(), i));
            
            int sourceColumnWidth = sourceTable.getColumnModel().getColumn(i).getWidth();
            minDialogSize += sourceColumnWidth;
            
            editTable.getColumnModel().getColumn(i).setMinWidth(sourceColumnWidth);
            
            //System.out.println("" + (i+1) + ". Source: " + sourceTable.getColumnClass(i).getName());
            //System.out.println("" + (i+1) + ". Edit: " + editTable.getColumnClass(i).getName());

        }
        
        System.out.println("------------------------------------------------");
        
        this.setSize(minDialogSize, this.getHeight());
    }
    
    public void showEditor(ActionEvent e){
        
        clearEditTable();
        refillEditTable();

        this.setVisible(true);
    }    
    
    protected abstract void refillEditTable();
    
    protected abstract void updateSourceTable();
    
    
    protected void addEditTableRow(Object[] dataArray){
        ((DefaultTableModel)editTable.getModel()).addRow(dataArray);
        getEditTableModel().fireTableRowsInserted(editTable.getRowCount()-1, editTable.getRowCount()-1);
    }
    
    protected void addSourceTableRow(Object[] dataArray){
        getSourceTableModel().addRow(dataArray);
        getSourceTableModel().fireTableRowsInserted(sourceTable.getRowCount()-1, sourceTable.getRowCount()-1);
    }
    
    protected int getEditorColumnCount(){
        return editTable.getColumnCount();
    }
    
    protected void clearEditTable(){
        
        while(editTable.getRowCount() > 0){
            ((DefaultTableModel)editTable.getModel()).removeRow(0); 
        }
    }
    
    protected void hideEditor(){
        this.setVisible(false);
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
        editLabel.setText("Sample Text");
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel editLabel;
    protected javax.swing.JTable editTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    
    
    protected JTable getSourceTable() {
        return sourceTable;
    }

    protected DefaultTableModel getSourceTableModel(){
        return (DefaultTableModel) sourceTable.getModel();
    }

    public int getSelectedRow() {
        return sourceTable.getSelectedRow();
    }

    protected javax.swing.JButton getCancelButton() {
        return cancelButton;
    }

    protected String getLabelText() {
        return editLabel.getText();
    }

    protected javax.swing.JTable getEditTable() {
        return editTable;
    }

    protected DefaultTableModel getEditTableModel(){
        return (DefaultTableModel) editTable.getModel();
    }
    
    protected javax.swing.JButton getSaveButton() {
        return saveButton;
    }

    protected void setLabelText(String text) {
        this.editLabel.setText(text);
    }

    public boolean isForceCellEditingEnabled() {
        return forceCellEditing;
    }

    public void setForceCellEditingEnabled(boolean forceCellEditing) {
        this.forceCellEditing = forceCellEditing;
    }

    
}
