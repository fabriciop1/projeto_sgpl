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
    
    public static int toInt(Object o){
        return (o != null ? (Integer)o : 0);
    }
            
    public static double toDouble(Object o){
        return (o != null ? (Double)o : 0.0);
    }        
    
    public static float toFloat(Object o){
        return (o != null ? (Float)o : 0.0f);
    }
    
    public static String toString(Object o){
        return (o != null ? o.toString() : "");
    }
    
    public static boolean toBool(Object o){
        return (o != null ? (Boolean)o : false);
    }
    
    public static String toSQLValue(Object o){
        
        if(o.getClass() == String.class){
            
            return "\"" + o.toString() + "\"";
        }
        else {
            return o.toString();
        }
    }
    
    public static Object stringToPrimitiveType(String string, Class valueType){
        
        if(string != null && valueType != null){
            
            if(valueType == Integer.class){ return Integer.parseInt(string); } else 
            if(valueType == Float.class){ return Float.parseFloat(string); } else 
            if(valueType == Double.class){ return Double.parseDouble(string); } else
            if(valueType == String.class){ return string; } else 
            if(valueType == Boolean.class){ return Boolean.parseBoolean(string); } else 
            if(valueType == Short.class){ return Short.parseShort(string); } else 
            if(valueType == Long.class){ return Long.parseLong(string); } else 
            if(valueType == Byte.class){ return Byte.parseByte(string); } else 
            if(valueType == Character.class){ return string.charAt(0); } else 
                
            throw new ClassCastException("O objeto passado não é de um tipo primitivo.");
        }   
        else {
            return null;
        }
        
    }
    
    //public static Object toPrimitiveTypeArray(o){ }
}
