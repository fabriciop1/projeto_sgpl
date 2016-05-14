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
    
    public static Object toPrimitiveType(String value, Class valueType){
        
        if(value == null){
            throw new NullPointerException("A string contendo o valor a ser convertido é inválida.");
        }
        else if(valueType == null){
            throw new NullPointerException("O tipo de valor passado é inválido");
        }
        
        if (valueType == Double.class || valueType == Float.class) {
            
            value = value.replaceAll(",", ".");
            
            int counter = 0;
            
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == '.') {
                    counter++;
                }
            }
            
            for (int i = 0; i < counter - 1; i++) {
                value = value.replace(".", "");
            }
        }
        
        if(valueType == Integer.class){ return Integer.parseInt(value); } else 
        if(valueType == Float.class){ return Float.parseFloat(value); } else 
        if(valueType == Double.class){ return Double.parseDouble(value); } else
        if(valueType == String.class){ return value; } else 
        if(valueType == Boolean.class){ return Boolean.parseBoolean(value); } else 
        if(valueType == Short.class){ return Short.parseShort(value); } else 
        if(valueType == Long.class){ return Long.parseLong(value); } else 
        if(valueType == Byte.class){ return Byte.parseByte(value); } else 
        if(valueType == Character.class){ return value.charAt(0); }

        else throw new ClassCastException("O objeto passado não é de um tipo primitivo.");
    }
    
    //public static Object toPrimitiveTypeArray(o){ }
}
