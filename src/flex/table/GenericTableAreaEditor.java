/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import util.Cast;

/**
 * @version 1.7.16
 * @author selaS nosreffeJ
 */
public class GenericTableAreaEditor extends GenericTableModifier {

    private int startRow;
    private int endRow;
    
    private int startColumn;
    private int endColumn;
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = 0;
        this.endRow = 0;
        
        this.startColumn = 0;
        this.endColumn = 0;
        
        super.setLabelText("Editar");
        
        setRebuildEditTable(true);
    }
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing, int startRow, int endRow, int startColumn, int endColumn){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        
        composeEditTable();
        refillEditTable();
        
        setRebuildEditTable(false);
        
        super.setLabelText("Editar");
    }
    
    @Override
    public void processEditor(){
        
        composeEditTable();
        
        resizeColumnEditableArray();
        
        refillEditTable();
        
        resizeCellEditableMatrix(startRow, endRow);
    }

    @Override
    public void refillEditTable() {
        
        getEditTableModel().setRowCount((endRow - startRow) + 1);
        
        fillStringColumns();
        
        for(int i = 0; i < editTable.getRowCount(); i++){
            
            for (int j = getStringColumnsOffset(); j < editTable.getColumnCount(); j++) {
                
                setEditTableValue( convertToEditTableValue( getSourceTableValue(startRow + i, startColumn + j - getStringColumnsOffset()) ), i, j);
            }
        }
    }

    @Override
    protected void updateSourceTable() {
        
        Object[][] tableAreaData = new Object[editTable.getRowCount()][editTable.getColumnCount() - getStringColumnsOffset()];
        
        boolean[][] tableCellModified = new boolean[editTable.getRowCount()][editTable.getColumnCount() - getStringColumnsOffset()];
        
        ArrayList<Integer> rowsModified = new ArrayList<>();
        ArrayList<Integer> columnsModified = new ArrayList<>();
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
                        
            boolean rowModified = false;
            
            for (int j = getStringColumnsOffset(); j < editTable.getColumnCount(); j++) {
                
                final int sourceStartColumn = startColumn + j - getStringColumnsOffset();
                
                Object editValue = getEditTableValue(i, j);
                Object sourceValue = getSourceTableValue(startRow + i, sourceStartColumn);
                
                Class sourceColumnClass = sourceTable.getColumnClass(sourceStartColumn);
                
                if(editValue != null &&
                   (sourceColumnClass == Float.class || sourceColumnClass == Double.class ||
                   sourceColumnClass == Integer.class || sourceColumnClass == Short.class ||
                   sourceColumnClass == Long.class)){
                    
                    editValue = Cast.toJavaValue(editValue.toString());
                }
                
                tableAreaData[i][j - getStringColumnsOffset()] = editValue;
                
                if( !Objects.equals(editValue, sourceValue.toString()) ){
                    
                    System.out.println("EV: " + editValue.toString() + " | SV: " + sourceValue.toString());
                    
                    tableCellModified[i][j - getStringColumnsOffset()] = true;
                    
                    if(!rowModified){
                        
                        rowModified = true;
                        rowsModified.add(i);
                    }
                    
                    boolean columnAlreadyAdded = false;
                    
                    for(int k=0; k<columnsModified.size(); k++){
                        
                        if(columnsModified.get(k) == j){
                            
                            columnAlreadyAdded = true;
                            break;
                        }
                    }
                    
                    if(!columnAlreadyAdded){
                        columnsModified.add(j);
                    }
                    
                    setSourceTableValue( convertToSourceTableValue(editValue, sourceStartColumn), startRow + i, sourceStartColumn);
                }
            }
        }
        
        if(rowsModified.size() > 0){
            
            notifyListeners(new TableModifiedEvent(this, sourceTable, rowsModified, columnsModified, tableAreaData, 
                    tableCellModified, TableModifiedEvent.AREA_CHANGED));
        }
        
//        System.out.println("Rows Modified: " + rowsModified.size());
//        System.out.println("Columns Modified: " + columnsModified.size());
    }
    
    @Override
    protected boolean validateEditTableContent(){
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
            
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
                else if(isCellEditable(i, j) && !validateEditTableValue(i, j, startColumn + j - getStringColumnsOffset())){
                    
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
        
        for(int i=0; i<getStringColumnsOffset(); i++){
            
            editColumnNames[i] = getStringColumnsData().get(i).columnTitle;
        }
        
        for (int i = getStringColumnsOffset(); i < editTableColumnCount; i++) {
            
            editColumnNames[i] = columnNames[startColumn + i - getStringColumnsOffset()];
        }
        
//        for(String s : editColumnNames){
//            System.out.println("ColumnName: " +s);
//        }

        DefaultTableModel model = new DefaultTableModel(new Object[][]{ }, editColumnNames){
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
               
                if(!isForceCellEditingEnabled()){
                    
                    return (GenericTableAreaEditor.this.isCellEditable(rowIndex, columnIndex));
                } 
                else {
                    
                    return true;
                }
            }
        
        };
        
        return model;
    }
    
    @Override
    protected void copySourceTableEditors() {
        
        for(int i = getStringColumnsOffset(); i<editTable.getColumnCount(); i++){
            
            TableCellEditor sourceCellEditor = sourceTable.getColumnModel().getColumn(startColumn + i - getStringColumnsOffset()).getCellEditor();
            
            editTable.getColumnModel().getColumn(i).setCellEditor(sourceCellEditor);
        }
        
        for(int i=0; i<getStringColumnsOffset(); i++){
            
            StringColumnData data = getStringColumnsData().get(i);
            
            if(data.columnRenderer != null){
                editTable.getColumnModel().getColumn(i).setCellRenderer(data.columnRenderer);
            } 
            else {
                editTable.getColumnModel().getColumn(i).setCellRenderer(editTable.getDefaultRenderer(Object.class));
            }
        }
    }

    @Override
    protected void copySourceTableRenderers() {
        
        for(int i = getStringColumnsOffset(); i<editTable.getColumnCount(); i++){
            
            TableCellRenderer sourceCellRenderer = sourceTable.getColumnModel().getColumn(startColumn + i - getStringColumnsOffset()).getCellRenderer();
            
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
    
}
