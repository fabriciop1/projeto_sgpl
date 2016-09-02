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
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
        
        if (table.getColumnName(column).equals("Total (R$)")) {
            this.setBackground(COLOR);
            this.setFont(getFont().deriveFont(FONT));
        
        } else if (row == 0 ||  row == 8 || row == 21|| row == 34 || row == 46 || row == 54 || row == 68 || row == 74 || row == 81 ||
                   row == 86 || row == 88){
            this.setBackground(COLOR);
            
        } else if (row == 7 || row == 20 || row == 33 || row == 45 || row == 53 || row == 67 || row == 73 || row == 80 || row == 85) {
            this.setBackground(COLOR);
            this.setFont(getFont().deriveFont(FONT));
            
        } else if (row == 89 && table.getColumnName(column).equals("Valor Unit.")) {
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