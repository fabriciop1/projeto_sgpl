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
 * @author Usuário
 */
public class GenericTableAreaEditor extends GenericTableModifier {

    private int startRow;
    private int endRow;
    
    private int startColumn;
    private int endColumn;
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = 0;
        this.endRow = sourceTable.getRowCount();
        
        this.startColumn = 0;
        this.endColumn = sourceTable.getColumnCount();
    }
    
    public GenericTableAreaEditor(Frame parent, JTable sourceTable, boolean forceCellEditing, int startRow, int endRow, int startColumn, int endColumn){
        super(parent, sourceTable, forceCellEditing);
        
        this.startRow = startRow;
        this.endRow = endRow;
        
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }
    
    @Override
    protected void refillEditTable() {
        
        
    }

    @Override
    protected void updateSourceTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected DefaultTableModel createEditTableModel(Object[][] sourceDataMatrix, String[] columnNameArray, Class[] columnTypeArray, boolean[] columnEditableArray){
        
        Object[][] matrix = new Object[(endRow+1) - startRow][(endColumn+1) - startColumn];
        String[] columnNames = new String[(endColumn+1) - startColumn];
        
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j < endColumn; j++) {
                matrix[i][j] = sourceDataMatrix[i][j];
            }
        }
        
        for (int i = 0; i < columnNames.length; i++) {
            // TODO
        }
        
        DefaultTableModel model = new DefaultTableModel(sourceDataMatrix, columnNameArray){
            
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
}
