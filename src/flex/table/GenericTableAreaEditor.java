/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author selaS nosreffeJ
 */
public class GenericTableAreaEditor extends GenericTableModifier {

    private int startRow;
    private int endRow;
    
    private int startColumn;
    private int endColumn;
    
    private boolean sourceRowEditable[];

    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = 0;
        this.endRow = sourceTable.getRowCount() - 1;
        
        this.startColumn = 0;
        this.endColumn = sourceTable.getColumnCount() - 1;
        
        this.sourceRowEditable = new boolean[sourceTable.getRowCount()];
        
        super.setLabelText("Editar");
        setAllSourceRowsEditable(true);
    }
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing, int startRow, int endRow, int startColumn, int endColumn){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        
        this.sourceRowEditable = new boolean[sourceTable.getRowCount()];
        
        super.setLabelText("Editar");
    }
    
    
    @Override
    protected void refillEditTable() {
        
        getEditTableModel().setRowCount((endRow - startRow) + 1);
        
        for(int i = 0; i < editTable.getRowCount(); i++){
            
            for (int j = getStringColumnsOffset(); j < editTable.getColumnCount(); j++) {
                
                setEditTableValue( getSourceTableValue(startRow + i, startColumn + j), i, j);
            }
        }
        
        fillStringColumns();
    }

    @Override
    protected void updateSourceTable() {
        
        Object[][] tableAreaData = new Object[editTable.getRowCount()][editTable.getColumnCount() - getStringColumnsOffset()];
        boolean[][] dataModified = new boolean[editTable.getRowCount()][editTable.getColumnCount() - getStringColumnsOffset()];
        ArrayList<Integer> rowsModified = new ArrayList<>();
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
                        
            boolean rowModified = false;
            
            for (int j = getStringColumnsOffset(); j < editTable.getColumnCount(); j++) {
                
                Object editValue = getEditTableValue(i, j);
                Object sourceValue = getSourceTableValue(startRow + i, startColumn + j);
                
                tableAreaData[i][j] = editValue;
                
                if( !Objects.equals(editValue, sourceValue) ){
                    
                    dataModified[i][j] = true;
                    
                    if(!rowModified){
                        
                        rowModified = true;
                        rowsModified.add(i);
                    }
                    
                    setSourceTableValue( convertToSourceTableValue(editValue, startColumn + j), startRow + i, startColumn + j);
                }
            }
        }
        
        if(rowsModified.size() > 0){
            
            notifyListeners(new TableModifiedEvent(this, sourceTable, rowsModified, tableAreaData, 
                    null, TableModifiedEvent.AREA_CHANGED));
        }
    }
    
    @Override
    protected boolean validateEditTableContent(){
        
        for (int i = getStringColumnsOffset(); i < editTable.getRowCount(); i++) {
            
            if(checkEmptyRow(i)){ 
                
                if(!isAllowedEmptyRows()){
                
                    JOptionPane.showMessageDialog(this, "Não são permitidas linhas vazias.", "Linha Vazia", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                continue;
            }
            
            for (int j = getStringColumnsOffset(); j < editTable.getColumnCount(); j++) {
                
                if (checkEmptyValue(i, j)) {
                    
                    if (!isAllowedEmptyCells()) {
                        
                        JOptionPane.showMessageDialog(this, "Não são permitidas células vazias.", "Valor de Coluna Vazio", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
                else if(sourceRowEditable[i + startRow] && !validateEditTableValue(i, j, startColumn + j)){
                    
                    JOptionPane.showMessageDialog(this, "O valor da coluna \"" + editTable.getColumnName(j) + "\" na linha " + (i+1) +
                            " é inválido.", "Valor de Coluna Inválido", JOptionPane.ERROR_MESSAGE);
                    
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    protected DefaultTableModel createEditTableModel(Object[][] dataMatrix, String[] columnNames,  Class[] columnTypes){
        
        int editTableColumnCount = (endColumn - startColumn) + getStringColumnsOffset() + 1;
        
        String[] editColumnNames = new String[editTableColumnCount];
        
        
        for (int i = getStringColumnsOffset(); i < editTableColumnCount; i++) {
            
            editColumnNames[i] = columnNames[startColumn + i];
        }
        
        DefaultTableModel model = new DefaultTableModel(new Object[][]{ }, editColumnNames){
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                if(!isForceCellEditingEnabled()){
                    
                    int editRowIndex = rowIndex + GenericTableAreaEditor.this.startRow;
                    
                    return (GenericTableAreaEditor.this.isColumnEditable(columnIndex) && 
                            GenericTableAreaEditor.this.sourceRowEditable[editRowIndex]);
                } else {
                    
                    return true;
                }
            }
        
        };
        
        return model;
    }
    
    @Override
    protected void copySourceTableEditors() {
        
        for(int i = getStringColumnsOffset(); i<editTable.getColumnCount(); i++){
            
            TableCellEditor sourceCellEditor = sourceTable.getColumnModel().getColumn(startColumn + i).getCellEditor();
            
            editTable.getColumnModel().getColumn(i).setCellEditor(sourceCellEditor);
        }
    }

    @Override
    protected void copySourceTableRenderers() {
        
        for(int i = getStringColumnsOffset(); i<editTable.getColumnCount(); i++){
            
            TableCellRenderer sourceCellRenderer = sourceTable.getColumnModel().getColumn(startColumn + i).getCellRenderer();
            
            editTable.getColumnModel().getColumn(i).setCellRenderer(sourceCellRenderer);
        }
    }
    

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        
        if(startRow < 0 || startRow > sourceTable.getRowCount()-1 || startRow > endRow){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + startRow);
        }
        
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        
        if(endRow < 0 || endRow > sourceTable.getRowCount()-1 || endRow < startRow){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + endRow);
        }
        
        this.endRow = endRow;
    }

    public int getStartColumn() {
        return startColumn;
    }


    public int getEndColumn() {
        return endColumn;
    }

    public void setColumnInterval(int startColumn, int endColumn){
        
        if(startColumn > endColumn){
            throw new IllegalArgumentException("O indice da coluna inicial deve ser menor ou igual ao indice da coluna final");
        }
        
        if(startColumn < 0 || startColumn > sourceTable.getColumnCount()-1 || startColumn > endColumn){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + startColumn);
        }
        
        if (startColumn != this.startColumn) {
            
            this.startColumn = startColumn;
            setRebuildEditTable(true);
        }
        
        if(endColumn < 0 || endColumn > sourceTable.getColumnCount()-1 || endColumn < startColumn){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + endColumn);
        }
        
        if (endColumn != this.endColumn) {
            
            this.endColumn = endColumn;
            setRebuildEditTable(true);
        }
        
        if(isRebuildEditTableNeeded()){
            composeEditTable();
            
        }
    }
    
    public void setRowInterval(int startRow, int endRow){
        
        if(startRow > endRow){
            throw new IllegalArgumentException("O indice da linha inicial deve ser menor ou igual ao indice da linha final");
        }
        
        setStartRow(startRow);
        setEndRow(endRow);
    }

    public void setInterval(int startRow, int endRow, int startColumn, int endColumn){
        
        setColumnInterval(startColumn, endColumn);
        setRowInterval(startRow, endRow);
    }
    
    public void setSourceRowEditable(int rowIndex, boolean editable) {
        
        if(rowIndex < 0 || rowIndex > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("O numero da linha passado é inválido: " + rowIndex);
        }
        
        sourceRowEditable[rowIndex] = editable;
    }
    
    public void setAllSourceRowsEditable(boolean editable) {
        
        for (int i = 0; i < this.sourceRowEditable.length; i++) {
            
            this.sourceRowEditable[i] = editable;
        }
    }
    
    public boolean isSourceRowEditable(int rowIndex) {
        return sourceRowEditable[rowIndex];
    }
}
