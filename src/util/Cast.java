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
    
    public static Object toPrimitiveType(String stringValue, Class valueType){
        
        if(stringValue == null){
            throw new NullPointerException("A string contendo o valor a ser convertido é inválida.");
        }
        else if(valueType == null){
            throw new NullPointerException("O tipo de valor passado é inválido");
        }
        
        if(valueType == Integer.class){ return Integer.parseInt(stringValue); } else 
        if(valueType == Float.class){ return Float.parseFloat(stringValue); } else 
        if(valueType == Double.class){ return Double.parseDouble(stringValue); } else
        if(valueType == String.class){ return stringValue; } else 
        if(valueType == Boolean.class){ return Boolean.parseBoolean(stringValue); } else 
        if(valueType == Short.class){ return Short.parseShort(stringValue); } else 
        if(valueType == Long.class){ return Long.parseLong(stringValue); } else 
        if(valueType == Byte.class){ return Byte.parseByte(stringValue); } else 
        if(valueType == Character.class){ return stringValue.charAt(0); }

        else throw new ClassCastException("O objeto passado não é de um tipo primitivo.");
    }
    
    //public static Object toPrimitiveTypeArray(o){ }
}
