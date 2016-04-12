/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.Pair;


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
    private boolean columnEditableArray[];
    private List<Object> customRowDataList;
    private List<TableModifyListener> tableModifylisteners;
    
    private boolean allowEmptyRows;
    private boolean allowEmptyCells;
    
    public GenericTableModifier(Frame parent, JTable sourceTable, boolean forceCellEditing) {
        super(parent, true);
        
        this.parent = parent;
        this.sourceTable = sourceTable;
        this.forceCellEditing = forceCellEditing;
        this.tableModifylisteners = new ArrayList<>();
        this.customRowDataList = new ArrayList<>();
        this.allowEmptyCells = true;
        this.allowEmptyRows = false;
        
        this.columnEditableArray = new boolean[sourceTable.getColumnCount()];

        initComponents();
        
        editTable.setAutoCreateColumnsFromModel(false);
        editTable.setCellEditor(sourceTable.getCellEditor());
        editTable.setCellSelectionEnabled(true);
        editTable.setShowHorizontalLines(true);
        editTable.setShowVerticalLines(true);
        
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(this.getSize());
        this.setLabelText("");
        this.setVisible(false);
        
        composeEditTable();
        
        this.setLocationRelativeTo(null);
    }
    
    public void addTableModifyListener(TableModifyListener listener){
        tableModifylisteners.add(listener);
    }
    
    public void removeTableModifyListener(TableModifyListener listener){
        tableModifylisteners.remove(listener);
    }
    
    
    public void showEditor(ActionEvent e){
        
        clearEditTable();
        refillEditTable();

        this.setVisible(true);
    }   
    
    protected void composeEditTable(){
        
        Class[] columnTypeArray = new Class[sourceTable.getColumnCount()];
        String[] columnNameArray = new String[sourceTable.getColumnCount()];
        Object[][] dataMatrix = new Object[sourceTable.getColumnCount()][1];  
        
        int minDialogSize = 80;        
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            columnTypeArray[i] = sourceTable.getColumnClass(i);
            columnNameArray[i] = sourceTable.getColumnName(i);
            columnEditableArray[i] = sourceTable.isCellEditable(0, i);
        }
        
        editTable.setModel(new DefaultTableModel(dataMatrix, columnNameArray){
            
            Class[] types = columnTypeArray;
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                if(!isForceCellEditingEnabled()){
                    return GenericTableModifier.this.columnEditableArray[columnIndex];
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
        }
        
        this.setSize(minDialogSize, this.getHeight());
    }
    
    protected void notifyListeners(int modifType, Object data){
        
        for(TableModifyListener listener : tableModifylisteners){
            listener.tableModified(modifType, this, data);
        }
    }
    
    
    
    protected abstract void refillEditTable();
    
    protected abstract void updateSourceTable();
    
    
    
    public void addSourceTableRow(Object[] dataArray, Object customRowData){
        getSourceTableModel().addRow(dataArray);
        getSourceTableModel().fireTableRowsInserted(sourceTable.getRowCount()-1, sourceTable.getRowCount()-1);
        customRowDataList.add(customRowData);
        
        notifyListeners(TableModifyListener.ROW_INSERTED, (Object)Pair.create(dataArray, customRowData));
    }
    
    public Object[] getSourceTableRowData(int row){
        
        Object[] data = new Object[sourceTable.getColumnCount()];
        
        for (int i = 0; i < data.length; i++) {
            data[i] = sourceTable.getValueAt(row, i);
        }
        
        return data;
    }
    
    public void removeSourceTableRow(int row){
        
        Object[] rowData = getSourceTableRowData(row);
        Object customRowData = getCustomRowData(row);
        
        getSourceTableModel().removeRow(row);
        getSourceTableModel().fireTableRowsDeleted(row, row);
        removeCustomRowData(row);
        
        notifyListeners( TableModifyListener.ROW_DELETED, (Object)Pair.create(rowData,customRowData) );
    }
    
    public void updateSourceTableRow(int row, Object[] dataArray){
        
        for (int i = 0; i < dataArray.length; i++) {
            sourceTable.setValueAt(dataArray[i], row, i);
        }
        
        getSourceTableModel().fireTableRowsUpdated(row, row);
        
        Object[] rowData = getSourceTableRowData(row);
        Object customRowData = getCustomRowData(row);
        
        notifyListeners( TableModifyListener.ROW_UPDATED, (Object)Pair.create(rowData, customRowData) );
    }
    
    public JTable getSourceTable() {
        return sourceTable;
    }

    public DefaultTableModel getSourceTableModel(){
        return (DefaultTableModel) sourceTable.getModel();
    }
    
    
    protected void removeEditTableRow(int row){
        
        getEditTableModel().removeRow(row);
        getEditTableModel().fireTableRowsDeleted(row, row);
    }
    
    protected void updateEditTableRow(int row, Object[] dataArray){
        
        for (int i = 0; i < dataArray.length; i++) {
            editTable.setValueAt(dataArray[i], row, i);
        }
        
        getEditTableModel().fireTableRowsUpdated(row, row);
    }
    
    protected void addEditTableRow(Object[] dataArray){
        getEditTableModel().addRow(dataArray);
        getEditTableModel().fireTableRowsInserted(editTable.getRowCount()-1, editTable.getRowCount()-1);
    }
    
    protected Object[] getEditTableRowData(int row){
        
        Object[] data = new Object[editTable.getColumnCount()];
        
        for (int i = 0; i < data.length; i++) {
            data[i] = editTable.getValueAt(row, i);
        }
        
        return data;
    }
    
    protected javax.swing.JTable getEditTable() {
        return editTable;
    }

    protected DefaultTableModel getEditTableModel(){
        return (DefaultTableModel) editTable.getModel();
    }
    
    
    protected void clearEditTable(){
        
        while(editTable.getRowCount() > 0){
            removeEditTableRow(0);
        }
    }
    
    protected void hideEditor(){
        this.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        editTable = new javax.swing.JTable();
        editLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        hideEditor();
        clearEditTable();
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel editLabel;
    protected javax.swing.JTable editTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    protected javax.swing.JButton getCancelButton() {
        return cancelButton;
    }

    protected javax.swing.JButton getSaveButton() {
        return saveButton;
    }
    
    public Object[] getSourceTableSelectedRowData(){
        return getSourceTableRowData(sourceTable.getSelectedRow());
    }
    
    public String getLabelText() {
        return editLabel.getText();
    }

    public void setLabelText(String text) {
        this.editLabel.setText(text);
    }

    public boolean isForceCellEditingEnabled() {
        return forceCellEditing;
    }

    public void setForceCellEditing(boolean forceCellEditing) {
        this.forceCellEditing = forceCellEditing;
    }
    
    public void setColumnEditable(int columnIndex, boolean editable){
        columnEditableArray[columnIndex] = editable;
    }
    
    public boolean isColumnEditable(int columnIndex){
        return columnEditableArray[columnIndex];
    }
    
    public void setAllColumnsEditable(boolean editable){
        
        for(int i=0; i<this.columnEditableArray.length; i++){
            this.columnEditableArray[i] = editable;
        }
    }

    public boolean isAllowedEmptyRows() {
        return allowEmptyRows;
    }

    public void setAllowEmptyRows(boolean allowEmptyRows) {
        this.allowEmptyRows = allowEmptyRows;
    }

    public boolean isAllowedEmptyCells() {
        return allowEmptyCells;
    }

    public void setAllowEmptyCells(boolean allowEmptyCells) {
        this.allowEmptyCells = allowEmptyCells;
    }
    
    public Object setCustomRowData(int row, Object data){
        
        if(row < 0 || row > customRowDataList.size()-1){
            throw new IllegalArgumentException("GenericTableModifier.setCustomRowData(int,Object): O numero da linha da tabela passado é inválido.");
        }
        
        return customRowDataList.set(row, data);
    }
    
    public Object getCustomRowData(int row){
        
        if(row < 0 || row > customRowDataList.size()-1){
            throw new IllegalArgumentException("GenericTableModifier.setCustomRowData(int,Object): O numero da linha da tabela passado é inválido.");
        }
        
        return customRowDataList.get(row);
    }
    
    public Object removeCustomRowData(int row){
        
        if(row < 0 || row > customRowDataList.size()-1){
            throw new IllegalArgumentException("GenericTableModifier.setCustomRowData(int,Object): O numero da linha da tabela passado é inválido.");
        }
        
        return customRowDataList.remove(row);
    }
    
    public List<Object> getCustomRowDataList(){
        return customRowDataList;
    }
}
