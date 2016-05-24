/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Fabricio
 */
public class ColorRendererEspecificacao extends DefaultTableCellRenderer {
    
    public ColorRendererEspecificacao() {
        super.setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column); 
       
        if (row == 0 || row == 7 || row == 8 || row == 20 || row == 21 || row == 33 || row == 34 || row == 45 || row == 46 || 
                row == 53 || row == 54 || row == 67 || row == 68 || row == 73 || row == 74 || row == 80 || row == 81 || row == 85 || 
                row == 86 || row == 88) {
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
