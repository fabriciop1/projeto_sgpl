/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JOptionPane;

/**
 *
 * @author Jefferson Sales
 */
public final class Regex {
    
    private Regex(){ }
    
    //^[0-9]{1,3}(,[0-9]{3})*\.[0-9]+$
    //^\\s*[+-]?(\\d{1,3}(\\.\\d{3}){0,3})\\s*$  
    public static final String INTEGER_VALUE = "^\\s*[+-]?(\\d{1,3}(\\.\\d{3}){0,3})\\s*$";
    
    public static final String UNSIGNED_INTEGER_VALUE = "^\\s*[+]?\\d{1,10}([\\,|\\.][eE][-+]?\\d+)?\\s*$";
    
    
    public static final String LONG_VALUE = "^\\s*[-+]?\\d{1,19}([\\.|\\,][eE][-+]?\\d+)?\\s*$";
    
    public static final String UNSIGNED_LONG_VALUE = "^\\s*[+]?\\d{1,19}\\s*$";
    
    
    public static final String DOUBLE_VALUE = "^\\s*[+-]?\\d{1,13}([\\.|\\,]\\d+)?([eE][-+]?\\d+)?\\s*$";
    
    public static final String UNSIGNED_DOUBLE_VALUE = "^\\s*[+]?\\d{1,13}([\\.|\\,]\\d+)?([eE][-+]?\\d+)?\\s*$";
    
    
    public static final String BOOLEAN_VALUE = "^\\s*(([fF][aA][lL][sS][eE])|([tT][rR][uU][eE])|([0-1]))\\s*$";
    
    
    public static final String STRING_SINGLE_LINE = "^\\s*(.*)\\s*$";
    
    
    public static String getRegexFromType(Class type){
        
        if(type == null){
            throw new NullPointerException("A classe passada é inválida.");
        }
        
        if(type == Integer.class){ return INTEGER_VALUE; } 
        else if(type == Long.class){ return LONG_VALUE; }
        else if(type == Double.class || type == Float.class){ return DOUBLE_VALUE; } 
        else if(type == Boolean.class){ return BOOLEAN_VALUE; }
        else if(type == String.class){ return STRING_SINGLE_LINE; }
        
        throw new IllegalArgumentException("Não existe regex definida para a classe " + type.getName());
    }
    
    public static void main(String[] args) {
        
        final String number = JOptionPane.showInputDialog(null, "Insert the number"); 
        
        if(number.matches("^\\s*[+-]?(\\d{1,3}(\\.\\d{3}){0,3})\\s*$")){
            System.out.println("Funfou");                        
        } else {
            System.out.println("Nao funfou");
        }
    }
}
