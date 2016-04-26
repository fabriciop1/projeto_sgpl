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
public final class Regex {
    
    private Regex(){ }
    
    public static final String INTEGER_VALUE = "^\\s*[+-]?\\d{1,10}\\s*$";    
    
    public static final String UNSIGNED_INTEGER_VALUE = "^\\s*[+]?\\d{1,10}\\s*$";
    
    
    public static final String LONG_VALUE = "^\\s*[-+]?\\d{1,19}\\s*$";
    
    public static final String UNSIGNED_LONG_VALUE = "^\\s*[+]?\\d{1,19}\\s*$";
    
    
    public static final String DOUBLE_VALUE = "^\\s*[+-]?\\d{1,13}([\\.|\\,]\\d+)?\\s*$";
    
    public static final String UNSIGNED_DOUBLE_VALUE = "^\\s*[+]?\\d{1,13}([\\.|\\,]\\d+)?\\s*$";
    
    
    public static final String BOOLEAN_VALUE = "^\\s*(([fF][aA][lL][sS][eE])|([tT][rR][uU][eE])|([0-1]))\\s*$";
    
    
    public static final String STRING_SINGLE_LINE = "^\\s*(.*)\\s*$";
    
    
    public static String getRegexFromType(Class type){
        
        if(type == Integer.class){ return INTEGER_VALUE; } 
        else if(type == Long.class){ return LONG_VALUE; }
        else if(type == Double.class || type == Float.class){ return DOUBLE_VALUE; } 
        else if(type == Boolean.class){ return BOOLEAN_VALUE; }
        else if(type == String.class){ return STRING_SINGLE_LINE; }
        
        return null;
    }
    
//    public static void main(String[] args) {
//        
//        if("      -9".matches(DOUBLE_VALUE)){
//            System.out.println("MATCHES");
//        }
//        else {
//            System.out.println("DOESN'T MATCH");
//        }
//        
//    }
}