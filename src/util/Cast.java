/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.NumberFormat;
import java.util.Locale;

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
        
        if(o == null){
            return "";
        }
        
        if(o.getClass() == String.class){
            
            return "\"" + o.toString() + "\"";
        }
        else {
            return o.toString();
        }
    }
    
    public static String toJavaValue(String value){
        
        if(value == null){
            return "";
        }
        
        value = value.replaceAll("\\s", "");
        
        value = value.replaceAll("\\.", "");
        value = value.replaceAll("\\,","\\.");
        
        return value;
    }
    
    public static String toBRLocaleValue(Object value) {

        if(value == null){
            return "";
        }
        
        Object valueClass = value.getClass();
        
        if (valueClass == Integer.class || valueClass == Short.class || valueClass == Long.class ||
            valueClass == Double.class || valueClass == Float.class || valueClass == Byte.class) {
            
            Locale hu3Locale = new Locale("pt", "BR");
            NumberFormat nf = NumberFormat.getInstance(hu3Locale);
            nf.setMaximumFractionDigits(2);
            
            return nf.format(value);
            
        } else {
            
            return value.toString();
        }

    }
    
    public static Object toPrimitiveType(String value, Class valueType){
        
        if(value == null){
            throw new NullPointerException("A string contendo o valor a ser convertido é inválida.");
        }
        else if(valueType == null){
            throw new NullPointerException("O tipo de valor passado é inválido");
        }
        
        if(valueType == Integer.class){ return Integer.parseInt( toJavaValue(value) ); } else 
        if(valueType == Short.class){ return Short.parseShort( toJavaValue(value) ); } else 
        if(valueType == Long.class){ return Long.parseLong( toJavaValue(value) ); } else 
        if(valueType == Byte.class){ return Byte.parseByte( toJavaValue(value) ); } else 
        if(valueType == Float.class){ return Float.parseFloat( toJavaValue(value )); } else 
        if(valueType == Double.class){ return Double.parseDouble( toJavaValue(value) ); } else
            
        if(valueType == String.class){ return value; } else 
        if(valueType == Boolean.class){ return Boolean.parseBoolean(value); } else 
        if(valueType == Character.class){ return value.charAt(0); }

        else throw new ClassCastException("O objeto passado não é de um tipo primitivo.");
    }
    
    public static void main(String[] args) {
        
        System.out.println(toBRLocaleValue(3299.99));
        
    }
}
