/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import javax.swing.JTable;
import util.TrashGen;

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
    
    private final GenericTableModifier sourceModifier;
    private final JTable sourceTable;
    
    private int rowIndex;
    private Object[][] tableData;
    private Object customRowData;
    private int eventType;
    
    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable) {
        
        this.sourceModifier = sourceModifier;
        this.sourceTable = sourceTable;
        this.tableData = new Object[1][0];
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, int rowIndex, Object[] rowData, Object customRowData, int eventType) {
        this(sourceModifier,sourceTable);
        
        this.rowIndex = rowIndex;
        this.customRowData = customRowData;
        this.eventType = eventType;
        
        if(rowData != null){
            this.tableData = new Object[1][0];
            this.tableData[0] = rowData;
        }       
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, int rowIndex, Object[][] tableAreaData, Object customRowData, int eventType) {
        this(sourceModifier,sourceTable);
        
        this.rowIndex = rowIndex;
        this.customRowData = customRowData;
        this.eventType = eventType;
        
        if (tableAreaData != null && tableAreaData.length > 0) {
            this.tableData = tableAreaData;
        }
        
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
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

    public Object[][] getTableAreaData() {
        return tableData;
    }

    public void setTableAreaData(Object[][] tableData) {
        
        if(tableData != null){
            this.tableData = tableData;
        }
    }
    
//    public static void main(String[] args) {
//        
//        String[][] str = new String[1][0];
//        String[] d = new String[30];
//        
//        for (int i = 0; i < d.length; i++) {
//            d[i] = TrashGen.generateString(50, false, true, true);
//        }
//        
//        str[0] = d;
//        
//        for (int i = 0; i < str[0].length; i++) {
//            System.out.println(str[0][i]);            
//        }
//    }
}
