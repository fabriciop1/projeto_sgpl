/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

import javax.swing.JTable;

/**
 *
 * @author Jefferson Sales
 */
public class TableModifiedEvent {
    
    private final GenericTableModifier sourceModifier;
    private final JTable sourceTable;
    
    private int rowIndex;
    private Object[] rowData;
    private Object customRowData;
    private int eventType;
    
    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable) {
        this.sourceModifier = sourceModifier;
        this.sourceTable = sourceTable;
    }

    public TableModifiedEvent(GenericTableModifier sourceModifier, JTable sourceTable, int rowIndex, Object[] rowData, Object customData, int eventType) {
        this.sourceModifier = sourceModifier;
        this.sourceTable = sourceTable;
        this.rowIndex = rowIndex;
        this.rowData = rowData;
        this.customRowData = customData;
        this.eventType = eventType;
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

    public Object[] getRowData() {
        return rowData;
    }

    public void setRowData(Object[] rowData) {
        this.rowData = rowData;
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
}
