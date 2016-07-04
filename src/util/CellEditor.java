/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Fabricio
 */
public class CellEditor extends DefaultCellEditor {
    
    public CellEditor() {
        super(new JTextField());
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        final JTextField component = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        
        SwingUtilities.invokeLater(() -> {
            component.selectAll();
        });
        
        return component;
    }

}
