/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.YearMonth;

/**
 *
 * @author Fabricio
 */
public final class Util {
    
    private Util (){ }
        
    public static int diasDoMes(int ano, int mes){
        
        YearMonth yearmonth = YearMonth.of(ano, mes);
        return yearmonth.lengthOfMonth();
        
    }
    
    public static Object[] clearVector(Object[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = null; 
        }
        return vector;
    }
    
}
