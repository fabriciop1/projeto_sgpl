/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;

/**
 * Classe para o cálculo genérico de valores no sistema com checagem de erros e inconsistências.
 * 
 * @author Jefferson Sales
 */
public final class Calc {
    
    private Calc(){ }
    
    public static double somarLista(List<Double> list){
        
        if(list == null){
            throw new NullPointerException("Calc.somarLista(L<Double>): A lista passada é igual a NULL");
        }
        
        double result = 0.0;
        
        for(Double d : list){
            result += d;
        }
        
        return result;
    }
    
    public static double mediaAritmetica(double a, double b){
        
        return (a + b) / 2.0;
    }
    
    public static double mediaAritmetica(List<Double> list){
        
        if(list == null){
            throw new NullPointerException("Calc.mediaAritmetica(L<Double>): A lista passada é igual a NULL");
        }
        else if(list.isEmpty()){
            return 0.0;
        }
        
        double result = 0.0;
        
        for(Double d : list){
            result += d;
        }
        
        return (result / list.size());
    }

    public static double somaPonderada(List<Double> pesos, List<Double> valores){
        
        if(pesos == null){
            throw new NullPointerException("Calc.somaPonderada(L<Double>,L<Double>): A lista de pesos passada é igual a NULL");
        }
        else if(valores == null){
            throw new NullPointerException("Calc.somaPonderada(L<Double>,L<Double>): A lista de valores passada é igual a NULL");
        }
        else if(pesos.isEmpty()){
            throw new IllegalArgumentException("Calc.somaPonderada(L<Double>,L<Double>): A lista de pesos está vazia");
        }
        else if(valores.isEmpty()){
            throw new IllegalArgumentException("Calc.somaPonderada(L<Double>,L<Double>): A lista de valores está vazia");
        }
        else if(pesos.size() != valores.size()){
            throw new IllegalArgumentException("Calc.somaPonderada(L<Double>,L<Double>): As listas de pesos e valores tem tamanhos diferentes");
        }
        
        double result = 0.0;
        
        for(int i=0; i<pesos.size(); i++){
            result += (pesos.get(i) * valores.get(i));
        }
        
        return result;
    }

    public static double mediaPonderada(List<Double> pesos, List<Double> valores){
        
        if(pesos == null){
            throw new NullPointerException("Calc.mediaPonderada(L<Double>,L<Double>): A lista de pesos passada é igual a NULL");
        }
        else if(valores == null){
            throw new NullPointerException("Calc.mediaPonderada(L<Double>,L<Double>): A lista de valores passada é igual a NULL");
        }
        else if(pesos.isEmpty()){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>): A lista de pesos está vazia");
        }
        else if(valores.isEmpty()){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>): A lista de valores está vazia");
        }
        else if(pesos.size() != valores.size()){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>): As listas de pesos e valores tem tamanhos diferentes");

        }
        
        double result = 0.0;
        
        for(int i=0; i<pesos.size(); i++){
            result += (pesos.get(i) * valores.get(i));
        }
        
        return (result / (double)pesos.size());
    }

    public static double mediaPonderada(List<Double> pesos, List<Double> valores, double denominador){
        
        if(pesos == null){
            throw new NullPointerException("Calc.mediaPonderada(L<Double>,L<Double>,double): A lista de pesos passada é igual a NULL");
        }
        else if(valores == null){
            throw new NullPointerException("Calc.mediaPonderada(L<Double>,L<Double>,double): A lista de valores passada é igual a NULL");
        }
        else if(pesos.isEmpty()){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>,double): A lista de pesos está vazia");
        }
        else if(valores.isEmpty()){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>,double): A lista de valores está vazia");
        }
        else if(pesos.size() != valores.size()){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>,double): As listas de pesos e valores tem tamanhos diferentes");

        }
        else if(denominador == 0){
            throw new IllegalArgumentException("Calc.mediaPonderada(L<Double>,L<Double>,double)");
        }
        
        double result = 0.0;
        
        for(int i=0; i<pesos.size(); i++){
            result += (pesos.get(i) * valores.get(i));
        }
        
        return (result / denominador);
    }
}
