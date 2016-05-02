/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.awt.Frame;
import java.awt.event.ActionEvent;
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
    
    private int columnOffset;
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing){
        super(parent, sourceTable, forceCellEditing, false);
        
        this.startRow = 0;
        this.endRow = sourceTable.getRowCount() - 1;
        
        this.startColumn = 0;
        this.endColumn = sourceTable.getColumnCount() - 1;
        
        this.columnOffset = 0;
        
        setLabelText("Editar");
        
        composeEditTable();
    }
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing, int startRow, int endRow, int startColumn, int endColumn){
        super(parent, sourceTable, forceCellEditing, false);
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        
        this.columnOffset = 0;
        
        composeEditTable();
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
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
                        
            for (int j = 0; j < editTable.getColumnCount() - columnOffset; j++) {
                
                tableAreaData[i][j] = getEditTableValue(i, j + columnOffset);
                
                setSourceTableValue( convertToSourceTableValue(tableAreaData[i][j], startColumn + j), startRow + i, startColumn + j);
                
                //System.out.println("Row: " + i);
                //System.out.println("Col: " + j);
                //System.out.println("Src Row: " + (startRow+i));
                //System.out.println("Src Col: " + (startColumn+j));
            }
        }
        
        notifyListeners(new TableModifiedEvent(this, sourceTable, -1, tableAreaData, null, TableModifiedEvent.AREA_CHANGED));
    }
    
    @Override
    protected boolean validateEditTableContent(){
        
        for (int i = 0; i < editTable.getRowCount(); i++) {
            
            for (int j = 0; j < editTable.getColumnCount() - columnOffset; j++) {
                
                if(!validateEditTableValue(i, j + columnOffset, startColumn + j)){
                    
                    JOptionPane.showMessageDialog(this, "O valor da coluna \"" + editTable.getColumnName(j) + "\" na linha " + (i+1) +
                            " é inválido.", "Valor de Coluna Inválido", JOptionPane.ERROR_MESSAGE);
                    
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    protected DefaultTableModel createEditTableModel(Object[][] dataMatrix, String[] columnNames, Class[] columnTypes){
        
        String[] editColumnNames = new String[(endColumn - startColumn) + columnOffset + 1];
        
        for (int i = 0; i < editColumnNames.length; i++) {
            
            editColumnNames[i] = columnNames[startColumn + i];
            System.out.println(editColumnNames[i]);
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

    public void setColumnInterval(int startColumn, int endColumn){
        
        if(endColumn < 0 || endColumn > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + endColumn);
        }
        else if(startColumn < 0 || startColumn > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + startColumn);
        }
        else if(startColumn > endColumn){
            throw new IllegalArgumentException("O indice da coluna inicial deve ser menor ou igual ao indice da coluna final");
        }
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        
        composeEditTable();
    }
    
    public void setRowInterval(int startRow, int endRow){
        
        if(startRow < 0 || startRow > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + startRow);
        }
        else if(endRow < 0 || endRow > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + endRow);
        }
        else if(startRow > endRow){
            throw new IllegalArgumentException("O indice da linha inicial deve ser menor ou igual ao indice da linha final");
        }
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        clearEditTable();
        refillEditTable();
    }

    public void setInterval(int startRow, int endRow, int startColumn, int endColumn){
        
        if(endColumn < 0 || endColumn > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + endColumn);
        }
        else if(startColumn < 0 || startColumn > sourceTable.getColumnCount()-1){
            throw new IllegalArgumentException("sourceTable - Coluna Inválida: " + startColumn);
        }
        else if(startColumn > endColumn){
            throw new IllegalArgumentException("O indice da coluna inicial deve ser menor ou igual ao indice da coluna final");
        }
        
        if(startRow < 0 || startRow > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + startRow);
        }
        else if(endRow < 0 || endRow > sourceTable.getRowCount()-1){
            throw new IllegalArgumentException("sourceTable - Linha inválida: " + endRow);
        }
        else if(startRow > endRow){
            throw new IllegalArgumentException("O indice da linha inicial deve ser menor ou igual ao indice da linha final");
        }
        
        this.startRow = startRow;
        this.startColumn = startColumn;
        
        this.endRow = endRow;
        this.endColumn = endColumn;
        
        clearEditTable();
        composeEditTable();
        refillEditTable();
    }

}
