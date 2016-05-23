/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.Month;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Fabricio
 */
public final class Data {
    
    private Data (){ }
        
    public static int diasDoMes(int ano, int mes){
        
        YearMonth yearmonth = YearMonth.of(ano, mes);
        return yearmonth.lengthOfMonth();
        
    }
    
}
