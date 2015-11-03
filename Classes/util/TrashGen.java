/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Random;

/**
 *
 * @author Jefferson Sales
 */
public final class TrashGen {
    
    private static final char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l',
                                    'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    private static final char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
    
    private static final char[] symbols = {'*','%','(',')','&','?','!','<','>',
                                           '.',',',' ',':',';','-','_','+','=','$','#' };
    
    public static String generateString(int length, boolean fixedLength, boolean hasNumbers, boolean hasSymbols){
        
        Random rand = new Random();
        
        String str = "";
        
        if(!fixedLength){
            length = rand.nextInt(10000) % length;
        }
        
        int i=0;
        
        do{          
            
            str += letters[rand.nextInt(29000)  % letters.length]; i++;
            if(i >= length) break;
            
            if(hasNumbers){
                str += numbers[rand.nextInt(77000) % numbers.length]; i++;
                if(i >= length) break;
            }
            
            if(hasSymbols){
                str += symbols[rand.nextInt(40500) % symbols.length]; i++;
                if(i >= length) break;
            }
        }
        while(i < length);
        
        return str;
    }
    
    public static void main(String[] args){
        
        for(int i=0; i<20; i++){
            System.out.println(generateString(50, false, true, false));
        }
        //Funfou legal
    }
}
