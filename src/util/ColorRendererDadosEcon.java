package util;

/* 
 * ColorRendererDadosEcon.java (compiles with releases 1.2, 1.3, and 1.4) is used by 
 * TableDialogEditDemo.java.
 */

import java.awt.Color;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;

public class ColorRendererDadosEcon extends DecimalFormatRenderer {   

    public ColorRendererDadosEcon() {
        super.setHorizontalAlignment(JLabel.RIGHT);
        super.setOpaque(true); //MUST do this for background to show up.
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column); 
        
        if(column == 2 || column == 5 || column == 8 || column == 11 || column == 14 || column == 17 || 
           column == 20 || column == 23 || column == 26 || column == 29 || column == 32 || column == 35) {
            comp.setBackground(Color.LIGHT_GRAY);
            comp.setFont(comp.getFont().deriveFont(Font.BOLD));
        
        } else if (row == 0 ||  row == 8 || row == 21|| row == 34 || row == 46 || row == 54 || row == 68 || row == 74 || row == 81 ||
                   row == 86 || row == 88){
            comp.setBackground(Color.LIGHT_GRAY);
            
        } else if (row == 7 || row == 20 || row == 33 || row == 45 || row == 53 || row == 67 || row == 73 || row == 80 || row == 85) {
            comp.setBackground(Color.LIGHT_GRAY);
            comp.setFont(comp.getFont().deriveFont(Font.BOLD));
            
        } else if (row == 89 && (column == 1 || column == 4 || column == 7 || column == 10 || column == 13 || column == 16
                || column == 19 || column == 22 || column == 25 || column == 28 || column == 31 || column == 34)) {
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