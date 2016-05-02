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

/**
 *
 * @author Jefferson Sales
 */
public class GenericTableRowEditor extends GenericTableModifier {
    
    public static final int GTRE_INSERT = 1;
    public static final int GTRE_UPDATE = 2;
    
    private int editorType;
    
    public GenericTableRowEditor(Frame parent, JTable sourceTable, boolean forceCellEditing) {
        super(parent, sourceTable, forceCellEditing, true);
        
        this.editorType = GTRE_INSERT;
        
        super.setAllColumnsEditable(true);
    }
    
    @Override
    protected void refillEditTable(){
        
        if (editorType == GTRE_UPDATE) {
            addEditTableRow( getSourceTableSelectedRowData() );
            
        } else if(editorType == GTRE_INSERT) {
            addEditTableRow(new Object[]{ });
        }
    }

    @Override
    protected void updateSourceTable(){        
        
        int numEmptyCells = 0;
        Object[] rowData = getEditTableRowData(0);
        
        for(int i=0; i<rowData.length; i++){

            if(rowData[i] == null){

                if(!isAllowedEmptyCells()){
                    return;
                }
                numEmptyCells++;
            }
        }
        
        if(!isAllowedEmptyRows() && (numEmptyCells == sourceTable.getColumnCount()) ){
            return;
        }
        
        if (editorType == GTRE_UPDATE) {
            updateSourceTableRow(getSourceTable().getSelectedRow(), rowData);
            
        } else if(editorType == GTRE_INSERT) {
            addSourceTableRow(rowData, null);
        }
    }
    
    @Override
    protected DefaultTableModel createEditTableModel(Object[][] dataMatrix, String[] columnNames, Class[] columnTypes) {
        
        return super.createStringEditTableModel(dataMatrix, columnNames); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void showEditor(ActionEvent e) {
        
        if (editorType == GTRE_UPDATE) {
            
            if (sourceTable.getSelectedRowCount() > 0) {
                
                super.showEditor(e);
                
            } else {
                JOptionPane.showMessageDialog(rootPane, "Selecione uma linha da tabela para editar",
                        "Editar - Nenhuma linha selecionada", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if(editorType == GTRE_INSERT){
            
            super.showEditor(e);
        }
    }

    public int getEditorType() {
        return editorType;
    }

    public void setEditorType(int editorType) {
        
        if(editorType < 1 || editorType > 2){
            throw new IllegalArgumentException("GenericTableRowEditor.setEditorType(int): O tipo de editor passado é inválido.");
        }
        
        this.editorType = editorType;
        
        if(editorType == GTRE_INSERT){
            setLabelText("Adicionar");
        } else if(editorType == GTRE_UPDATE){
            setLabelText("Editar");
        }
    }
}
