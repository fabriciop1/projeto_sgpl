/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author Fabricio
 */
public class ColorRendererDadosTec extends DecimalFormatRenderer {
    
    public ColorRendererDadosTec(boolean alignment) {
        super();
        if (alignment == true) {
            super.setHorizontalAlignment(JLabel.RIGHT);
        }
        super.setOpaque(true);
    }
    
     @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column); 
        
        if(column == 12 || row == 1 || row == 3 || row == 14) {
            comp.setBackground(Color.LIGHT_GRAY);
            comp.setFont(comp.getFont().deriveFont(Font.BOLD));
        } else {
            comp.setBackground(table.getBackground());
        }
        
        Color bg = null;

        if (isSelected) {
            super.setBackground(bg == null ? table.getSelectionBackground(): bg);
        } 
       
        return this;
    }
}
