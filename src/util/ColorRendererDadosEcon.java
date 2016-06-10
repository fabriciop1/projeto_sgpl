package util;

/* 
 * ColorRendererDadosEcon.java (compiles with releases 1.2, 1.3, and 1.4) is used by 
 * TableDialogEditDemo.java.
 */

import java.awt.Color;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Font;

public class ColorRendererDadosEcon extends DecimalFormatRenderer {   

    private static final Color BG = null;
    private static final Color COLOR = Color.LIGHT_GRAY;
    private static final int FONT = Font.BOLD;
    
    public ColorRendererDadosEcon(boolean alignment) {
        super(alignment);
        super.setOpaque(true); //MUST do this for background to show up.
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
       
        super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column); 
        
        if(column == 2 || column == 5 || column == 8 || column == 11 || column == 14 || column == 17 || 
           column == 20 || column == 23 || column == 26 || column == 29 || column == 32 || column == 35) {
            this.setBackground(COLOR);
            this.setFont(getFont().deriveFont(FONT));
        
        } else if (row == 0 ||  row == 8 || row == 21|| row == 34 || row == 46 || row == 54 || row == 68 || row == 74 || row == 81 ||
                   row == 86 || row == 88){
            this.setBackground(COLOR);
            
        } else if (row == 7 || row == 20 || row == 33 || row == 45 || row == 53 || row == 67 || row == 73 || row == 80 || row == 85) {
            this.setBackground(COLOR);
            this.setFont(getFont().deriveFont(FONT));
            
        } else if (row == 89 && (column == 1 || column == 4 || column == 7 || column == 10 || column == 13 || column == 16
                || column == 19 || column == 22 || column == 25 || column == 28 || column == 31 || column == 34)) {
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