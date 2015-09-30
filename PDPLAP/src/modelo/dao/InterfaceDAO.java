/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.ArrayList;

/**
 *
 * @author Fabricio
 */
public interface InterfaceDAO<T> {
    
    public boolean cadastrar(T data);
    public boolean remover(int id);
    public boolean atualizar();
    public T buscar(int id);
    public ArrayList<T> recuperarTodos();
    
}
