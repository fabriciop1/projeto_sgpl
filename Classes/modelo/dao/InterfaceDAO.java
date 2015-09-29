/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

/**
 *
 * @author Fabricio
 */
public interface InterfaceDAO<T> {
    
    public void cadastrar(T data);
    public boolean remover();
    public boolean atualizar();
    public T buscar(String data);
    
}
