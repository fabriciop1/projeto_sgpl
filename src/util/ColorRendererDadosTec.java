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

/**
 *
 * @author Fabricio
 */
public class ColorRendererDadosTec extends DecimalFormatRenderer {
    
    private static final Color BG = null;
    private static final Color COLOR = Color.LIGHT_GRAY;  
    private static final int FONT = Font.BOLD;
    
    public ColorRendererDadosTec(boolean alignment) {
        super(alignment); 
        super.setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
        
        if(column == 12 || row == 1 || row == 3 || row == 15) {
            this.setBackground(COLOR);
            this.setFont(getFont().deriveFont(FONT));
        } else {
            this.setBackground(null);
        }
        
        if (isSelected) {
            super.setBackground(BG == null ? table.getSelectionBackground(): BG);
        } 
       
        return this;
    }
    
}
