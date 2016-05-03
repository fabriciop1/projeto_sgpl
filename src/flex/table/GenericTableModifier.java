/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Frame parent;
    protected JTable sourceTable;
    
    private final List<Object> customRowDataList;
    private final List<TableModifyListener> tableModifylisteners;
    private final HashMap<Integer, String> columnRegexMap;
    
    private int rowsDisplayed;
    
    private boolean allowEmptyRows;
    private boolean allowEmptyCells;
    
    private boolean forceCellEditing;
    private final boolean editorColumnEditable[];

    boolean rebuildEditTable;
    
    
    protected GenericTableModifier(Frame parent, JTable sourceTable, boolean forceCellEditing) {
        super(parent, true);
        
        this.parent = parent;
        this.sourceTable = sourceTable;
        this.forceCellEditing = forceCellEditing;
        this.tableModifylisteners = new ArrayList<>();
        this.customRowDataList = new ArrayList<>();
        this.columnRegexMap = new HashMap<>();
        this.allowEmptyCells = true;
        this.allowEmptyRows = false;
        this.rowsDisplayed = 1;
        this.editorColumnEditable = new boolean[sourceTable.getColumnCount()];
        this.rebuildEditTable = true;
        
        initComponents();
        
        editTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        editTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        editTable.setAutoCreateColumnsFromModel(false);
        editTable.setCellSelectionEnabled(true);
        editTable.setShowHorizontalLines(true);
        editTable.setShowVerticalLines(true);
        
        this.setLabelText("");
        this.setVisible(false);
        this.setResizable(false);
    }
    
    public String createLog(){
        
        final String line = "--------------------------------------------------------------------------------";
        
        String log = "";
        
        log += line + "\nModifier Name: " + getName() + "\n";
        log += "Modifier Class: " + this.getClass().getCanonicalName() + "\n\n";
        
        log += "Number of Registered TableModifyListeners: " + tableModifylisteners.size() + "\n\n";
        
        if(columnRegexMap.size() > 0){
            for(Map.Entry<Integer, String> entry : columnRegexMap.entrySet()){
                log += "Regex Defined for Column #" + entry.getKey() + ": " + entry.getValue() + "\n";
            }
            log += "\n";
        } else { log += "Column Regex Map is empty" + "\n\n"; }
        
        log += "Allow Empty Rows: " + allowEmptyRows + "\n";
        log += "Allow Empty Cells: " + allowEmptyCells + "\n";
        log += "Force Cell Editing: " + forceCellEditing + "\n\n";
        
        log += "EditTable Columns Editable: ";
        for (int i = 0; i < editorColumnEditable.length; i++) {
            log += "[" + i + "-" + editorColumnEditable[i] + "] "; 
        }
        log += "\n\n";
        log += "Editor Window Width: " + this.getWidth() + "\n";
        log += "EditTable Width: " + editTable.getWidth() + "\n";
        log += "EditTable ScrollPane Width: " + editTableScroll.getWidth() + "\n\n";
        
        log += "EditTable Columns Width: ";
        for (int i = 0; i < editTable.getColumnCount(); i++) {
            log += "[" + i + "-(" + editTable.getColumnModel().getColumn(i).getWidth() + ")] ";
        }
        log += "\n\n";
        
        log += "Edit Table Row Count: " + editTable.getRowCount() + " row(s)" + "\n";
        log += "Edit Table Column Count: " + editTable.getColumnCount() + " column(s)" + "\n\n";
        
        log += "Source Table Row Count: " + sourceTable.getRowCount() + " row(s)" + "\n";
        log += "Source Table Column Count: " + sourceTable.getColumnCount() + " column(s)" + "\n\n";
        
        log += "Rows Displayed in EditTable: " + this.rowsDisplayed + "\n";
        
        return log + "\n";
    }
    
    
    public void addTableModifyListener(TableModifyListener listener){
        tableModifylisteners.add(listener);
    }
    
    public void removeTableModifyListener(TableModifyListener listener){
        tableModifylisteners.remove(listener);
    }
    
    
    protected String[] getSourceTableColumnNames(){
        
        String[] sourceColumnNames = new String[sourceTable.getColumnCount()];

        for(int i=0; i<sourceTable.getColumnCount(); i++){
            
            sourceColumnNames[i] = sourceTable.getColumnName(i);
        }
        
        return sourceColumnNames;
    }
    
    protected Class[] getSourceTableColumnTypes(){
        
        Class[] sourceColumnTypes = new Class[sourceTable.getColumnCount()];
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            
            sourceColumnTypes[i] = sourceTable.getColumnClass(i);
        }
        
        return sourceColumnTypes;
    }
    
    protected Object[][] getSourceTableDataMatrix(){
        
        Object[][] sourceDataMatrix = new Object[sourceTable.getRowCount()][sourceTable.getColumnCount()];  
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){
            for (int j = 0; j < sourceTable.getRowCount(); j++) {
                
                sourceDataMatrix[j][i] = sourceTable.getValueAt(j, i);
            }
        }
        
        return sourceDataMatrix;
    }
    
    
    protected void composeEditTable(){
        
        editTable.setModel( createEditTableModel( getSourceTableDataMatrix(), getSourceTableColumnNames(), getSourceTableColumnTypes() ) );
        
        editTable.createDefaultColumnsFromModel();
        
        copySourceTableRenderers();
        copySourceTableEditors();
        
        getEditTableModel().fireTableStructureChanged();
    }    
    
    protected final DefaultTableModel createStringEditTableModel(Object[][] dataMatrix, String[] columnNames){
        
        DefaultTableModel model = new DefaultTableModel(dataMatrix, columnNames){
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                if(!isForceCellEditingEnabled()){
                    return GenericTableModifier.this.editorColumnEditable[columnIndex];
                } else {
                    return true;
                }
            }
        };
        
        return model;
    }

    protected final DefaultTableModel createDefaultEditTableModel(Object[][] dataMatrix, String[] columnNames, Class[] columnTypes){
        
        DefaultTableModel model = new DefaultTableModel(dataMatrix, columnNames){
            
            private final Class[] types = columnTypes;
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                if(!isForceCellEditingEnabled()){
                    return GenericTableModifier.this.editorColumnEditable[columnIndex];
                } else {
                    return true;
                }
            }
        };
        
        return model;
    }
    
    
    protected void notifyListeners(TableModifiedEvent event){
        
        if(event == null){
            throw new NullPointerException("Evento inválido");
        }
        
        for(TableModifyListener listener : tableModifylisteners){
            listener.tableModified(event);
        }
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
    
    protected final void repackEditor() {

        int minDialogWidth = 0;
        
        for (int i = 0; i < editTable.getColumnCount(); i++) {

            int sourceColumnWidth = getSourceTableColumnWidth(i);
            editTable.getColumnModel().getColumn(i).setPreferredWidth(sourceColumnWidth);
            
            minDialogWidth += sourceColumnWidth + 3;
        }

        
        int minEditTableHeight = rowsDisplayed * editTable.getRowHeight();
        
        editTable.setPreferredScrollableViewportSize((new Dimension(minDialogWidth, minEditTableHeight)));
        
        this.setSize(new Dimension(60 + minDialogWidth, 170 + minEditTableHeight));
        this.setLocationRelativeTo(null);
    }
    
    
    protected abstract void refillEditTable();
    
    protected abstract void updateSourceTable();
    
    protected abstract DefaultTableModel createEditTableModel(Object[][] dataMatrix, String[] columnNames, Class[] columnTypes);
    
    
    protected void setSourceTableValue(Object value, int row, int column){
        
        if(row < 0 || row > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + column);
        }
        
        sourceTable.setValueAt(value, row, column);
    }
    
    public Object getSourceTableValue(int row, int column){
        
        if(row < 0 || row > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + column);
        }
        
        return sourceTable.getValueAt(row, column);
    }
    
    public void addSourceTableRow(Object[] dataArray, Object customRowData){
        
        getSourceTableModel().addRow(dataArray);
        getSourceTableModel().fireTableRowsInserted(sourceTable.getRowCount()-1, sourceTable.getRowCount()-1);
        customRowDataList.add(customRowData);
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, sourceTable.getRowCount()-1, dataArray, 
                            customRowData, TableModifiedEvent.ROW_INSERTED));
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
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, row, rowData, customRowData, TableModifiedEvent.ROW_DELETED));
    }
    
    public void updateSourceTableRow(int row, Object[] dataArray){
        
        for (int i = 0; i < dataArray.length; i++) {
            sourceTable.setValueAt(dataArray[i], row, i);
        }
        
        getSourceTableModel().fireTableRowsUpdated(row, row);
        
        Object[] rowData = getSourceTableRowData(row);
        Object customRowData = getCustomRowData(row);
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, row, rowData, 
                            customRowData, TableModifiedEvent.ROW_UPDATED));
    }
    
    public JTable getSourceTable() {
        return sourceTable;
    }

    public DefaultTableModel getSourceTableModel(){
        return (DefaultTableModel) sourceTable.getModel();
    }
    
    public int getSourceTableColumnWidth(int column){
        
        if(column < 0 || column > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("O valor da coluna passado é inválido.");
        }        

        return sourceTable.getColumnModel().getColumn(column).getWidth();
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
    
    
    protected void setEditTableValue(Object value, int row, int column){
        
        if(row < 0 || row > editTable.getRowCount()-1){
            throw new IllegalArgumentException("editTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > editTable.getColumnCount()-1){
            throw new IllegalArgumentException("editTable - Coluna Inválida: " + column);
        }
        
        editTable.setValueAt(value, row, column);
    }
    
    public Object getEditTableValue(int row, int column){
        
        if(row < 0 || row > editTable.getRowCount()-1){
            throw new IllegalArgumentException("editTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > editTable.getColumnCount()-1){
            throw new IllegalArgumentException("editTable - Coluna Inválida: " + column);
        }
        
        return editTable.getValueAt(row, column);
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
    
    
    protected void processEditTable(){
        
        clearEditTable();
        
        if(rebuildEditTable){
            
            composeEditTable();
            
            rebuildEditTable = false;
        }
        
        refillEditTable();
        repackEditor();
    }
    
    public void showEditor(ActionEvent e){
        
        processEditTable();

        this.setVisible(true);
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
        
        Object cellValue = getEditTableValue(editRow, editColumn);
        
        if(!validateValue(cellValue, sourceColumn)){
            
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
            
            if(checkEmptyRow(i, 0) && !isAllowedEmptyRows()){
                JOptionPane.showMessageDialog(this, "Não são permitidas linhas vazias.", "Linha Vazia", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
            
            for (int j = 0; j < editTable.getColumnCount(); j++) {
                
                if(checkEmptyValue(i, j) && !isAllowedEmptyCells()){
                    JOptionPane.showMessageDialog(this, "Não são permitidas células vazias.", "Valor de Coluna Vazio", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else if(!validateEditTableValue(i, j, j)){
                    
                    JOptionPane.showMessageDialog(this, "O valor da coluna \"" + editTable.getColumnName(j) + "\" na linha " + (i+1) +
                            " é inválido.", "Valor de Coluna Inválido", JOptionPane.ERROR_MESSAGE);
                    
                    return false;
                }
            }
        }
        
        return true;
    }
    
    protected boolean checkEmptyValue(int editRow, int editColumn){
        
        Object cellValue = getEditTableValue(editRow, editColumn);
        
        return Cast.toString(cellValue).isEmpty();
    }
    
    protected boolean checkEmptyRow(int editRow, int columnOffset){
        
        int numEmptyCells = 0;
        
        for(int i=0; i<editTable.getColumnCount() - columnOffset; i++){

            if(checkEmptyValue(editRow, i + columnOffset)){

                numEmptyCells++;
            }
        }
        
        if(numEmptyCells == editTable.getColumnCount() - columnOffset){
            return true;
        }
        
        return false;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editTableScroll = new javax.swing.JScrollPane();
        editTable = new javax.swing.JTable();
        editLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
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
        editTableScroll.setViewportView(editTable);

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
                    .addComponent(editTableScroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(editLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editTableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editLabelAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_editLabelAncestorResized
        // TODO add your handling code here:
    }//GEN-LAST:event_editLabelAncestorResized

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

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        stopCellEditing();

        if (validateEditTableContent()) {
            
            updateSourceTable();
            clearEditTable();
            hideEditor();
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel editLabel;
    protected javax.swing.JTable editTable;
    private javax.swing.JScrollPane editTableScroll;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    
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
    
    public int getRowsDisplayed() {
        return rowsDisplayed;
    }

    public void setRowsDisplayed(int rowsDisplayed) {
        
        if(rowsDisplayed <= 0){
            throw new IllegalArgumentException("O número de linhas a serem exibidas passado é inválido.");
        }
        
        this.rowsDisplayed = rowsDisplayed;
    }

    protected boolean isRebuildEditTableNeeded() {
        return rebuildEditTable;
    }

    protected void setRebuildEditTable(boolean rebuildEditTable) {
        this.rebuildEditTable = rebuildEditTable;
    }
}
