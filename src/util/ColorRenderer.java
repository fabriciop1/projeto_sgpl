/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package util;

/* 
 * ColorRenderer.java (compiles with releases 1.2, 1.3, and 1.4) is used by 
 * TableDialogEditDemo.java.
 */

import java.awt.Color;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {   

    public ColorRenderer(boolean isBordered) {
        super.setOpaque(true); //MUST do this for background to show up.
    }
    
    public ColorRenderer() {}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column); 
       
        if(column == 2 || column == 5 || column == 8 || column == 11 || column == 14 || column == 17 || 
           column == 20 || column == 23 || column == 26 || column == 29 || column == 32 || column == 35) {
            comp.setBackground(Color.LIGHT_GRAY);
        
        } else if (row == 0 || row == 7|| row == 8 || row == 20 || row == 21|| row == 33 || row == 34|| row == 45 || 
                   row == 46 || row == 53 || row == 54 || row == 67 || row == 68 || row == 73 || row == 74 || row == 80 || row == 81 ||
                   row == 85 || row == 86 || row == 88){
            comp.setBackground(Color.LIGHT_GRAY);
        } else {
            comp.setBackground(table.getBackground());
        }
        
        if (table == null) {
            return this;
        }

        Color bg = null;

        if (isSelected) {
            super.setBackground(bg == null ? table.getSelectionBackground(): bg);
        } 
       
        return this;
     }
}