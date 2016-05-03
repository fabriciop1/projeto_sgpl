/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import util.Cast;

/**
 *
 * @author selaS nosreffeJ
 */
public class GenericTableAreaEditor extends GenericTableModifier {

    private int startRow;
    private int endRow;
    
    private int startColumn;
    private int endColumn;
    
    private final int columnOffset;
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = 0;
        this.endRow = sourceTable.getRowCount() - 1;
        
        this.startColumn = 0;
        this.endColumn = sourceTable.getColumnCount() - 1;
        
        this.columnOffset = 0;
        
        super.setLabelText("Editar");
    }
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing, int startRow, int endRow, int startColumn, int endColumn){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        
        this.columnOffset = 0;
        
        super.setLabelText("Editar");
    }
    
    
    @Override
    protected void refillEditTable() {
        
        getEditTableModel().setRowCount((endRow - startRow) + 1);
        
        for(int i = 0; i < editTable.getRowCount(); i++){
            
            for (int j = 0; j < editTable.getColumnCount() - columnOffset; j++) {
                
                setEditTableValue( Cast.toString( getSourceTableValue(startRow + i, startColumn + j) ), i, j + columnOffset);
            }
        }
    }

    @Override
    protected void updateSourceTable() {
        
        Object[][] tableAreaData = new Object[editTable.getRowCount()][editTable.getColumnCount() - columnOffset];
        
        for(int i=0; i<editTable.getRowCount(); i++){
            
            checkEmptyRow(i, columnOffset);
        }
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
                        
            for (int j = 0; j < editTable.getColumnCount() - columnOffset; j++) {
                
                tableAreaData[i][j] = getEditTableValue(i, j + columnOffset);
                
                setSourceTableValue( convertToSourceTableValue(tableAreaData[i][j], startColumn + j), startRow + i, startColumn + j);
            }
        }
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, -1, tableAreaData, null, TableModifiedEvent.AREA_CHANGED));
    }
    
    @Override
    protected boolean validateEditTableContent(){
        
        for (int i = 0; i < editTable.getRowCount() - columnOffset; i++) {
            
            if(checkEmptyRow(i, columnOffset) && !isAllowedEmptyRows()){
                JOptionPane.showMessageDialog(this, "Não são permitidas linhas vazias.", "Linha Vazia", JOptionPane.ERROR_MESSAGE);
                    return false;
            }
            
            for (int j = 0; j < editTable.getColumnCount() - columnOffset; j++) {
                
                if(checkEmptyValue(i, j + columnOffset) && !isAllowedEmptyCells()){
                    JOptionPane.showMessageDialog(this, "Não são permitidas células vazias.", "Valor de Coluna Vazio", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else if(!validateEditTableValue(i, j + columnOffset, startColumn + j)){
                    
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
        
        int partialSize = (endColumn - startColumn) + columnOffset + 1;
        
        String[] editColumnNames = new String[partialSize];
        
        for (int i = 0; i < editColumnNames.length; i++) {
            
            editColumnNames[i] = columnNames[startColumn + i];
        }
        
        return super.createStringEditTableModel(new Object[][]{ }, editColumnNames);
    }
    
    @Override
    protected void copySourceTableEditors() {
        
        for(int i = 0; i<editTable.getColumnCount() - columnOffset; i++){
            
            TableCellEditor sourceCellEditor = sourceTable.getColumnModel().getColumn(startColumn + i).getCellEditor();
            
            editTable.getColumnModel().getColumn(i + columnOffset).setCellEditor(sourceCellEditor);
        }
    }

    @Override
    protected void copySourceTableRenderers() {
        
        for(int i = 0; i<editTable.getColumnCount() - columnOffset; i++){
            
            TableCellRenderer sourceCellRenderer = sourceTable.getColumnModel().getColumn(startColumn + i).getCellRenderer();
            
            editTable.getColumnModel().getColumn(i + columnOffset).setCellRenderer(sourceCellRenderer);
        }
    }
    
    @Override
    public int getSourceTableColumnWidth(int column){
        
        return super.getSourceTableColumnWidth(startColumn + column - columnOffset);
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

    public void setStartColumn(int startColumn) {
        
        if(startColumn < 0 || startColumn > sourceTable.getColumnCount()-1 || startColumn > endColumn){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + startColumn);
        }
        
        if (startColumn != this.startColumn) {
            
            this.startColumn = startColumn;
            setRebuildEditTable(true);
        }
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        
        if(endColumn < 0 || endColumn > sourceTable.getColumnCount()-1 || endColumn < startColumn){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + endColumn);
        }
        
        if (endColumn != this.endColumn) {
            
            this.endColumn = endColumn;
            setRebuildEditTable(true);
        }
    }

    public void setColumnInterval(int startColumn, int endColumn){
        
        if(startColumn > endColumn){
            throw new IllegalArgumentException("O indice da coluna inicial deve ser menor ou igual ao indice da coluna final");
        }
        
        setStartColumn(startColumn);
        setEndColumn(endColumn);
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
