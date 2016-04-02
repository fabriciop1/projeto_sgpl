/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

/**
 *
 * @author Jefferson Sales
 */
public class GenericTableRowInserter extends GenericTableModifier{
    
    private boolean allowEmptyRows;
    private boolean allowEmptyCells;
    
    public GenericTableRowInserter(Frame parent, JTable sourceTable, boolean allowEmptyRows, boolean allowEmptyCells) {
        super(parent, sourceTable, true);
        
        this.allowEmptyCells = allowEmptyCells;
        this.allowEmptyRows = allowEmptyRows;
        
        this.setLabelText("Adicionar");
    }
    
    @Override
    protected void refillEditTable(){
        
        addEditTableRow(new Object[]{ });
    }
    
    @Override
    protected void updateSourceTable(){
            
        int numEmptyCells = 0;
        Object[] dataArray = new Object[sourceTable.getColumnCount()];
        
        for(int i=0; i<sourceTable.getColumnCount(); i++){

            if(getEditTable().getValueAt(0, i) == null){

                if(!allowEmptyCells){
                    return;
                }
                numEmptyCells++;
            }
            dataArray[i] = getEditTable().getValueAt(0, i);
        }

        if(!allowEmptyRows && numEmptyCells == sourceTable.getColumnCount()){
            return;
        }
        
        addSourceTableRow(dataArray);
    }
    
    @Override
    public void showEditor(ActionEvent e){
        
        clearEditTable();
        refillEditTable();

        this.setVisible(true);
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
