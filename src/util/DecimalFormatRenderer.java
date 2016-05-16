/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Usuário
 */
public class DecimalFormatRenderer extends DefaultTableCellRenderer{
      private static final DecimalFormat formatter = new DecimalFormat( "#.##" );
      
      public DecimalFormatRenderer() {
          setHorizontalAlignment(JLabel.RIGHT);
      }
 
      @Override
      public Component getTableCellRendererComponent(
         JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column) {
 
         // First format the cell value as required
      
         if (value != null && !value.toString().isEmpty() && value.getClass() == Double.class) {
             value = formatter.format((Number)value);
         }
           
 
            // And pass it on to parent class
 
         return super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column );
      }
}
