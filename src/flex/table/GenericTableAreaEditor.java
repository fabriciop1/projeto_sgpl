/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author selaS nosreffeJ
 */
public class GenericTableAreaEditor extends GenericTableModifier {

    private int startRow;
    private int endRow;
    
    private int startColumn;
    private int endColumn;
    
    private int columnOffset;
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = 0;
        this.endRow = 0; //sourceTable.getRowCount();
        
        this.startColumn = 0;
        this.endColumn = sourceTable.getColumnCount();
        
        this.columnOffset = 0;
    }
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing, int startRow, int endRow, int startColumn, int endColumn){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        
        this.columnOffset = 0;
    }
    
    protected void setEditTableValue(Object value, int row, int column){
        
        if(row < 0 || row > editTable.getRowCount()-1){
            throw new IllegalArgumentException("editTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > editTable.getColumnCount()-1){
            throw new IllegalArgumentException("editTable - Coluna Inválida: " + column);
        }
        
        editTable.setValueAt(value, row, column + columnOffset);
    }
    
    public Object getEditTableValue(int row, int column){
        
        if(row < startRow || row > editTable.getRowCount()-1){
            throw new IllegalArgumentException("editTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > editTable.getColumnCount()-1){
            throw new IllegalArgumentException("editTable - Coluna Inválida: " + column);
        }
        
        return editTable.getValueAt(row, column + columnOffset);
    }
    
    
    protected void setSourceTableValue(Object value, int row, int column){
        
        if(row < 0 || row > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + column);
        }
        
        sourceTable.setValueAt(value, row + startRow, column + startColumn);
    }
    
    public Object getSourceTableValue(int row, int column){
        
        if(row < 0 || row > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + row);
        }
        else if(column < 0 || column > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + column);
        }
        
        return sourceTable.getValueAt(row + startRow, column + startColumn);
    }
    
    
    @Override
    protected boolean validateEditTableValues(){
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
            if(super.va)
        }
        
        return true;
    }
    
    @Override
    protected void refillEditTable() {
        
        for(int i = 0; i < editTable.getRowCount(); i++){
            
            for (int j = 0; j < editTable.getColumnCount(); j++) {
                
                setEditTableValue( getSourceTableValue(i, j) , i, j);
            }
        }
    }

    @Override
    protected void updateSourceTable() {
        
        for(int i = 0; i < editTable.getRowCount(); i++){
            
            for (int j = 0; j < editTable.getColumnCount(); j++) {
                
                setSourceTableValue( convertToSourceTableValue( getEditTableValue(i, j), j),  i, j);
            }
        }        
    }
    
    @Override
    protected DefaultTableModel createEditTableModel(Object[][] sourceDataMatrix, String[] sourceColumnNames, Class[] sourceColumnTypes, boolean[] sourceColumnEditable){
        
        Object[][] editDataMatrix = new Object[(endRow - startRow) + 1][(endColumn - startColumn) + columnOffset + 1];
        String[] editColumnNames = new String[(endColumn - startColumn) + columnOffset + 1];
        
        for (int i = 0; i < editDataMatrix.length; i++) {
            
            for (int j = 0; j < editDataMatrix[0].length; j++) {
                
                editDataMatrix[i][j] = sourceDataMatrix[startRow + i][startColumn + j];
            }
        }
        
        for (int i = 0; i < editColumnNames.length; i++) {
            
            editColumnNames[i] = sourceColumnNames[startColumn + i];
        }
        
        DefaultTableModel model = new DefaultTableModel(editDataMatrix, editColumnNames){
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                
                if(!isForceCellEditingEnabled()){
                    return GenericTableAreaEditor.this.isColumnEditable(columnIndex);
                } else {
                    return true;
                }
            }
        };
        
        return model;
        
    }
    
    
    
    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        
        if(startRow < 0 || startRow > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + startRow);
        }
        
        this.startRow = startRow;
        
        clearEditTable();
        refillEditTable();
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        
        if(endRow < 0 || endRow > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + endRow);
        }
        
        this.endRow = endRow;
        
        clearEditTable();
        refillEditTable();
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        
        if(startColumn < 0 || startColumn > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + startColumn);
        }
        
        this.startColumn = startColumn;
        
        composeEditTable();
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        
        if(endColumn < 0 || endColumn > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + endColumn);
        }
        
        this.endColumn = endColumn;
        
        composeEditTable();
    }
}
