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
    
    public static String nomeMes(int mesNum){
        
        switch(mesNum){
            case 1:  return "JAN";
            case 2:  return "FEV";
            case 3:  return "MAR";
            case 4:  return "ABR";
            case 5:  return "MAI";
            case 6:  return "JUN";
            case 7:  return "JUL";
            case 8:  return "AGO";
            case 9:  return "SET";
            case 10: return "OUT";
            case 11: return "NOV";
            case 12: return "DEZ";
        }
        
        return "";
    }
    
}
