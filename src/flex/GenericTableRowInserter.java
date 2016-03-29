/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jefferson Sales
 */
public class GenericTableRowInserter extends GenericTableRowEditor{
    
    private boolean allowEmptyRows;
    private boolean allowEmptyCells;
    
    public GenericTableRowInserter(Frame parent, JTable sourceTable, JButton actionButton, boolean allowEmptyRows, boolean allowEmptyCells) {
        super(parent, sourceTable, actionButton, true);
        
        this.allowEmptyCells = allowEmptyCells;
        this.allowEmptyRows = allowEmptyRows;
    }
    
    @Override
    protected void refillEditTable(){
        
        addEditTableRow(new Object[]{ });
    }
    
    @Override
    protected void updateSourceTable(){
            
        int numEmptyCells = 0;
        Object[] dataArray = new Object[getColumnCount()];
        
        for(int i=0; i<getColumnCount(); i++){

            if(getEditTable().getValueAt(0, i) == null){

                if(!allowEmptyCells){
                    return;
                }
                numEmptyCells++;
            }
            dataArray[i] = getEditTable().getValueAt(0, i);
        }

        if(!allowEmptyRows && numEmptyCells == getColumnCount()){
            return;
        }
        
        addSourceTableRow(dataArray);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == getActionButton()){
            
            clearEditTable();
            refillEditTable();
            
            this.setVisible(true);
        }
    }
    
    public boolean isAllowEmptyRows() {
        return allowEmptyRows;
    }

    public void setAllowEmptyRows(boolean allowEmptyRows) {
        this.allowEmptyRows = allowEmptyRows;
    }

    public boolean isAllowEmptyCells() {
        return allowEmptyCells;
    }

    public void setAllowEmptyCells(boolean allowEmptyCells) {
        this.allowEmptyCells = allowEmptyCells;
    }
}
