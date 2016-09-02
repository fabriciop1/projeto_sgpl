/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import java.util.ArrayList;
import javax.swing.JTable;

/**
 * @version 1.7.13
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
    
    private Object[][] tableAreaData;
    private boolean[][] tableCellModified;
    private ArrayList<Integer> rowsModified;
    private ArrayList<Integer> columnsModified;
    private Object customRowData;
    private int eventType;
    
    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable) {
        
        this.sourceModifier = sourceModifier;
        this.sourceTable = sourceTable;
        this.tableAreaData = new Object[1][0];
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, ArrayList<Integer> rowsModified, Object[] rowData, Object customRowData, int eventType) {
        this(sourceModifier,sourceTable);
        
        this.customRowData = customRowData;
        this.eventType = eventType;
        this.rowsModified = rowsModified;
        
        if(rowData != null){
            this.tableAreaData = new Object[1][0];
            this.tableAreaData[0] = rowData;
        }       
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, ArrayList<Integer> rowsModified, ArrayList<Integer> columnsModified, 
            Object[][] tableAreaData, boolean[][] tableCellModified,  int eventType) {
        this(sourceModifier,sourceTable);
        
        this.customRowData = customRowData;
        this.eventType = eventType;
        this.rowsModified = rowsModified;
        this.columnsModified = columnsModified;
        this.tableCellModified = tableCellModified;
        
        if (tableAreaData != null && tableAreaData.length > 0) {
            this.tableAreaData = tableAreaData;
        } else {
            this.tableAreaData = new Object[1][0];
        }
        
    }

    public GenericTableModifier getSourceModifier() {
        return sourceModifier;
    }

    public void setSourceModifier(GenericTableModifier sourceModifier) {
        this.sourceModifier = sourceModifier;
    }

    public JTable getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(JTable sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Object[][] getTableAreaData() {
        return tableAreaData;
    }

    public Object[] getTableRowData(){
        return tableAreaData[0];
    }
    
    public void setTableAreaData(Object[][] tableAreaData) {
        this.tableAreaData = tableAreaData;
    }

    public boolean[][] getTableCellModified() {
        return tableCellModified;
    }

    public void setTableCellModified(boolean[][] tableCellModified) {
        this.tableCellModified = tableCellModified;
    }

    public ArrayList<Integer> getRowsModified() {
        return rowsModified;
    }

    public void setRowsModified(ArrayList<Integer> rowsModified) {
        this.rowsModified = rowsModified;
    }

    public ArrayList<Integer> getColumnsModified() {
        return columnsModified;
    }

    public void setColumnsModified(ArrayList<Integer> columnsModified) {
        this.columnsModified = columnsModified;
    }

    public Object getCustomRowData() {
        return customRowData;
    }

    public void setCustomRowData(Object customRowData) {
        this.customRowData = customRowData;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    

}
