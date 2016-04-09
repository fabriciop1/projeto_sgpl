/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fabricio
 * @param <T>
 */
public interface InterfaceDAO<T> {

    public void cadastrar(T dado) throws SQLException;

    public void atualizar(T dado) throws SQLException;

    public void remover(int id) throws SQLException;

    public T recuperar(int id) throws SQLException;

    public ArrayList<T> recuperarTodos() throws SQLException;

}
