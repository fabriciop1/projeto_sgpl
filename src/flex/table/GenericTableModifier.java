/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import util.Cast;
import util.Regex;


/**
 *
 * @author Jefferson Sales
 */
public abstract class GenericTableModifier extends JDialog{

    /**
     * Creates new form GenericTableModifier
     */
    
    protected JTable sourceTable;
    
    private Frame parent;
    private List<Object> customRowDataList;
    private List<TableModifyListener> tableModifylisteners;
    private HashMap<Integer, String> columnRegexMap;
    
    private boolean allowEmptyRows;
    private boolean allowEmptyCells;
    
    private boolean forceCellEditing;
    private boolean editorColumnEditable[];

    
    protected GenericTableModifier(Frame parent, JTable sourceTable, boolean forceCellEditing, boolean composeEditor) {
        super(parent, true);
        
        this.parent = parent;
        this.sourceTable = sourceTable;
        this.forceCellEditing = forceCellEditing;
        this.tableModifylisteners = new ArrayList<>();
        this.customRowDataList = new ArrayList<>();
        this.columnRegexMap = new HashMap<>();
        this.allowEmptyCells = true;
        this.allowEmptyRows = false;
        
        this.editorColumnEditable = new boolean[sourceTable.getColumnCount()];

        initComponents();
        
        editTable.setAutoCreateColumnsFromModel(false);
        editTable.setCellSelectionEnabled(true);
        editTable.setShowHorizontalLines(true);
        editTable.setShowVerticalLines(true);
        
        this.setMinimumSize(this.getSize());
        this.setLabelText("");
        this.setVisible(false);
        
        if (composeEditor) {
            composeEditTable();
        }
        
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
        
        Class[] sourceColumnTypes = new Class[sourceTable.getColumnCount()];
        String[] sourceColumnNames = new String[sourceTable.getColumnCount()];
        Object[][] sourceDataMatrix = new Object[sourceTable.getRowCount()][sourceTable.getColumnCount()];  
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            
            sourceColumnTypes[i] = sourceTable.getColumnClass(i);
            sourceColumnNames[i] = sourceTable.getColumnName(i);
            editorColumnEditable[i] = sourceTable.isCellEditable(0, i);
            
            for (int j = 0; j < sourceTable.getRowCount(); j++) {
                
                sourceDataMatrix[j][i] = sourceTable.getValueAt(j, i);
            }
        }
        
        editTable.setModel( createEditTableModel(sourceDataMatrix, sourceColumnNames, sourceColumnTypes, editorColumnEditable) );
        
        editTable.createDefaultColumnsFromModel();
        
        copySourceTableRenderers();
        copySourceTableEditors();
        
        repackEditor();
    }
    
    protected void copySourceTableRenderers(){
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            
            TableCellRenderer sourceCellRenderer = sourceTable.getColumnModel().getColumn(i).getCellRenderer();
            
            editTable.getColumnModel().getColumn(i).setCellRenderer(sourceCellRenderer);
        }
    }
    
    protected void copySourceTableEditors(){
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            
            TableCellEditor sourceCellEditor = sourceTable.getColumnModel().getColumn(i).getCellEditor();
            
            editTable.getColumnModel().getColumn(i).setCellEditor(sourceCellEditor);
        }
    }
    
    protected void repackEditor() {

        int minDialogSize = 80;

        for (int i = 0; i < editTable.getColumnCount(); i++) {

            int sourceColumnWidth = sourceTable.getColumnModel().getColumn(i).getWidth();
            minDialogSize += sourceColumnWidth;

            editTable.getColumnModel().getColumn(i).setMinWidth(sourceColumnWidth);
        }

        this.setSize(minDialogSize, this.getHeight());
    }
    
    
    protected void notifyListeners(TableModifiedEvent event){
        
        if(event == null){
            throw new NullPointerException("Evento inválido");
        }
        
        for(TableModifyListener listener : tableModifylisteners){
            listener.tableModified(event);
        }
    }
    
    
    protected abstract void refillEditTable();
    
    protected abstract void updateSourceTable();
    
    protected abstract DefaultTableModel createEditTableModel(Object[][] sourceDataMatrix, String[] sourceColumnNames, Class[] sourceColumnTypes, boolean[] sourceColumnEditable);
    
    
    
    public void addSourceTableRow(Object[] dataArray, Object customRowData){
        
        getSourceTableModel().addRow(dataArray);
        getSourceTableModel().fireTableRowsInserted(sourceTable.getRowCount()-1, sourceTable.getRowCount()-1);
        customRowDataList.add(customRowData);
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, sourceTable.getRowCount()-1, dataArray, 
                            customRowData, TableModifyListener.ROW_INSERTED));
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
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, row, rowData, customRowData, TableModifyListener.ROW_DELETED));
    }
    
    public void updateSourceTableRow(int row, Object[] dataArray){
        
        for (int i = 0; i < dataArray.length; i++) {
            sourceTable.setValueAt(dataArray[i], row, i);
        }
        
        getSourceTableModel().fireTableRowsUpdated(row, row);
        
        Object[] rowData = getSourceTableRowData(row);
        Object customRowData = getCustomRowData(row);
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, row, rowData, 
                            customRowData, TableModifyListener.ROW_UPDATED));
    }
    
    public JTable getSourceTable() {
        return sourceTable;
    }

    public DefaultTableModel getSourceTableModel(){
        return (DefaultTableModel) sourceTable.getModel();
    }
    
    
    protected Object convertToSourceTableValue(Object value, int sourceColumn){

        if (value != null) {

            Object sourceValue = Cast.toPrimitiveType(value.toString(), sourceTable.getColumnClass(sourceColumn));

            if (sourceValue.getClass() == sourceTable.getColumnClass(sourceColumn)) {
                return sourceValue;
                
            } else {
                throw new IllegalArgumentException("O valor não é do mesmo tipo da coluna correspondente na tabela.");
            }
        } else {
            return null; 
        }
    }
    
    protected Object[] convertToSourceTableValues(Object[] rowData){
        
        if(rowData == null){
            throw new NullPointerException("O array de dados da linha da tabela passado é inválido.");
        } 
        else if(rowData.length != sourceTable.getColumnCount()){
            throw new IllegalArgumentException("O número de valores do array passado é diferente no número de colunas da tabela.");
        }
        
        Object[] sourceRowData = new Object[sourceTable.getColumnCount()];
        
        for (int i = 0; i < rowData.length; i++) {
            
            sourceRowData[i] = convertToSourceTableValue(rowData[i], i);
        }
        
        return sourceRowData;
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
    
    protected Object[] getEditTableRowStringData(int row){
        
        Object[] data = new Object[editTable.getColumnCount()];
        
        for (int i = 0; i < data.length; i++) {
            data[i] = editTable.getValueAt(row, i);
        }
        
        return (data);
    }
    
    protected Object[] getEditTableRowData(int row){
        return convertToSourceTableValues( getEditTableRowStringData(row) );
    }
    
    public javax.swing.JTable getEditTable() {
        return editTable;
    }

    protected DefaultTableModel getEditTableModel(){
        return (DefaultTableModel) editTable.getModel();
    }
    
    
    protected void clearEditTable(){
        
        getEditTableModel().setRowCount(0);
    }
    
    protected void hideEditor(){
        this.setVisible(false);
    }
    
    protected void stopCellEditing(){
        
        if(editTable.isEditing()){
            editTable.getCellEditor().stopCellEditing();
        }
    }
    
    protected void cancelCellEditing(){
        
        if(editTable.isEditing()){
            editTable.getCellEditor().cancelCellEditing();
        }
    }
    
    
    protected boolean validateValue(Object value, int sourceColumn){
        
        boolean isValueValid = true;

        if (value == null) {
            
            if(isAllowedEmptyCells()){
                return true;
            }
            
            return false;
        } 

        String stringCellValue = Cast.toString(value);

        if (columnRegexMap.containsKey(sourceColumn)) {

            String regex = columnRegexMap.get(sourceColumn);

            if (!stringCellValue.matches(regex)) {
                isValueValid = false;
            }
            
        } else {
            
            String regex = Regex.getRegexFromType(sourceTable.getColumnClass(sourceColumn));

            if (regex != null && !stringCellValue.matches(regex)) {
                isValueValid = false;
            }
        }

        return isValueValid;
    }
    
    protected boolean validateEditTableValue(int editRow, int editColumn, int sourceColumn){
        
        Object cellValue = editTable.getValueAt(editRow, editColumn);
        
        if(!validateValue(cellValue, sourceColumn)){
            
            System.out.println("Invalid Value at Row: " + editRow + " Column: " + editColumn);
            
            JOptionPane.showMessageDialog(this, "O valor da coluna \"" + editTable.getColumnName(editColumn) + "\" na linha " + (editRow+1) +" é inválido.",
                    "Valor de Coluna Inválido: " + editTable.getColumnName(editColumn), JOptionPane.ERROR_MESSAGE);
            
            return false;
        }
        
        return true;
    }
    
    protected boolean validateEditTableRow(int editRowIndex){
        
        Object[] rowData = getEditTableRowStringData(editRowIndex);
        
        for (int i = 0; i < rowData.length; i++) {
            
            if(!validateEditTableValue(editRowIndex, i, i)){
                
                return false;
            }
        }
        
        return true;
    }
    
    protected boolean validateEditTableContent(){
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
            
            if(!validateEditTableRow(i)){
                
                return false;
            }
        }
        
        return true;
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        editTable = new javax.swing.JTable();
        editLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

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
        editTable.getTableHeader().setReorderingAllowed(false);
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
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        
        stopCellEditing();
        
        if (validateEditTableContent()) {
            
            updateSourceTable();
            clearEditTable();            
            hideEditor();
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        
        cancelCellEditing();
        hideEditor();
        clearEditTable();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        cancelCellEditing();
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
        editorColumnEditable[columnIndex] = editable;
    }
    
    public boolean isColumnEditable(int columnIndex){
        return editorColumnEditable[columnIndex];
    }
    
    public void setAllColumnsEditable(boolean editable){
        
        for(int i=0; i<this.editorColumnEditable.length; i++){
            this.editorColumnEditable[i] = editable;
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
