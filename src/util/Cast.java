/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Jefferson Sales
 */
public final class Cast {
    
    private Cast(){
        
    }
    
    public static Integer toInt(Object o){
        return (o != null ? (Integer)o : 0);
    }
            
    public static Double toDouble(Object o){
        return (o != null ? (Double)o : 0.0);
    }        
    
    public static Float toFloat(Object o){
        return (o != null ? (Float)o : 0.0f);
    }
    
    public static String toString(Object o){
        return (o != null ? o.toString() : "");
    }
    
    public static Boolean toBool(Object o){
        return (o != null ? (Boolean)o : false);
    }
    
    public static String toSQLString(Object o){
        return "\"" + toString(o) + "\"";
    }
}
