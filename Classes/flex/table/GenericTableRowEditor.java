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

/**
 *
 * @author Jefferson Sales
 */
public class GenericTableRowEditor extends GenericTableModifier {
    
    private final int columnCount;
    
    public GenericTableRowEditor(Frame parent, JTable sourceTable, boolean forceCellEditing) {
        super(parent, sourceTable, forceCellEditing);
        
        this.columnCount = sourceTable.getColumnCount();
        
        this.setLabelText("Editar");
    }
    
    @Override
    protected void refillEditTable(){
        
        Object[] dataArray = new Object[columnCount];
        
        for(int i=0; i<columnCount; i++){
            dataArray[i] = sourceTable.getValueAt(sourceTable.getSelectedRow(), i);
        }
        
        addEditTableRow(dataArray);
    }

    @Override
    protected void updateSourceTable(){        
        
        if(editTable.isEditing()){
            editTable.getCellEditor().stopCellEditing();
        }
        
        for(int i=0; i<columnCount; i++){
            
            Object value = editTable.getValueAt(0, i);
            
            if(value == null){
                System.err.println("Warning: NULL value at R=0 C=" + i);
                continue;
            }
            
            sourceTable.setValueAt(value, sourceTable.getSelectedRow(), i);
            
            getSourceTableModel().fireTableCellUpdated(sourceTable.getSelectedRow(), i);
        }
        
        getSourceTableModel().fireTableRowsUpdated(sourceTable.getSelectedRow(), sourceTable.getSelectedRow());
    }
    
    @Override
    public void showEditor(ActionEvent e) {
        
        if(sourceTable.getSelectedRowCount() > 0){

            super.showEditor(e);

        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para editar",
                    "Editar - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
