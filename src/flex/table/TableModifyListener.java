/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.table;

/**
 *
 * @author Jefferson Sales
 */
public interface TableModifyListener {
    
    public static final int ROW_INSERTED = 1;
    public static final int ROW_UPDATED = 2;
    public static final int ROW_DELETED = 3;
    
    public static final int COLUMN_INSERTED = 4;
    public static final int COLUMN_UPDATED = 5;
    public static final int COLUMN_DELETED = 6;
    
    public static final int AREA_CHANGED = 7;
    
    public abstract void tableModified(TableModifiedEvent event);
}
