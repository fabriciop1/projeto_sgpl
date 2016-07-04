/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author Jefferson Sales
 */
public class TableModifiedEvent {
    
    public static final int ROW_INSERTED = 1;
    public static final int ROW_UPDATED = 2;
    public static final int ROW_DELETED = 3;
    
    public static final int COLUMN_INSERTED = 4;
    public static final int COLUMN_UPDATED = 5;
    public static final int COLUMN_DELETED = 6;
    
    public static final int AREA_CHANGED = 7;
    
    private GenericTableModifier sourceModifier;
    private JTable sourceTable;
    
    private Object[][] tableData;
    private boolean[][] tableDataModified;
    private ArrayList<Integer> rowsModified;
    private Object customRowData;
    private int eventType;
    
    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable) {
        
        this.sourceModifier = sourceModifier;
        this.sourceTable = sourceTable;
        this.tableData = new Object[1][0];
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, ArrayList<Integer> rowsModified, Object[] rowData, Object customRowData, int eventType) {
        this(sourceModifier,sourceTable);
        
        this.customRowData = customRowData;
        this.eventType = eventType;
        this.rowsModified = rowsModified;
        
        if(rowData != null){
            this.tableData = new Object[1][0];
            this.tableData[0] = rowData;
        }       
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, ArrayList<Integer> rowsModified, Object[][] tableAreaData, Object customRowData, int eventType) {
        this(sourceModifier,sourceTable);
        
        this.customRowData = customRowData;
        this.eventType = eventType;
        this.rowsModified = rowsModified;
        
        if (tableAreaData != null && tableAreaData.length > 0) {
            this.tableData = tableAreaData;
        } else {
            this.tableData = new Object[1][0];
        }
        
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }


    public Object[] getTableRowData() {
        return tableData[0];
    }

    public void setTableRowData(Object[] rowData) {
        
        if(tableData == null){
            this.tableData = new Object[1][0];
        }
        
        this.tableData[0] = rowData;
    }

    public Object getCustomRowData() {
        return customRowData;
    }

    public void setCustomRowData(Object customRowData) {
        this.customRowData = customRowData;
    }

    public GenericTableModifier getSourceModifier() {
        return sourceModifier;
    }

    public JTable getSourceTable() {
        return sourceTable;
    }

    public void setTableAreaData(Object[][] tableData) {
        
        if(tableData != null){
            this.tableData = tableData;
        }
    }

    public Object[][] getTableData() {
        return tableData;
    }

    public void setTableData(Object[][] tableData) {
        this.tableData = tableData;
    }

    public boolean isTableDataModified(int row, int column) {
        
        if(row < 0 || row > tableDataModified.length-1)
            throw new ArrayIndexOutOfBoundsException("O indice da linha passado é inválido: " + row);
        
        if(column < 0 || column > tableDataModified[0].length-1)
            throw new ArrayIndexOutOfBoundsException("O indice da coluna passado é inválido: " + column);
        
        return tableDataModified[row][column];
    }

    public void setTableDataModified(boolean[][] tableDataModified) {
        this.tableDataModified = tableDataModified;
    }
    
    public ArrayList<Integer> getRowsModified() {
        return rowsModified;
    }

    public void setRowsModified(ArrayList<Integer> rowsModified) {
        this.rowsModified = rowsModified;
    }

    public boolean[][] getTableDataModified() {
        return tableDataModified;
    }

}
