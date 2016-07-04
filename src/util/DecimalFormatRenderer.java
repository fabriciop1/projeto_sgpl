/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe necessária para manter apenas duas casas após a vírgula para cada campo double (Aproximação é feita).
 * @author Usuário
 */
public class DecimalFormatRenderer extends DefaultTableCellRenderer{
    private static final NumberFormat FORMATTER = new DecimalFormat( "#.##" );
      
    public DecimalFormatRenderer(boolean alignment) { 
        if(alignment) {  
            super.setHorizontalAlignment(JLabel.RIGHT);
        }
    }
 
    @Override
    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
 
        if (value != null && !value.toString().isEmpty() && (value.getClass() == Double.class || value.getClass() == Integer.class)) {
            value = FORMATTER.format(value);
        }
       
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column );
    }
}
