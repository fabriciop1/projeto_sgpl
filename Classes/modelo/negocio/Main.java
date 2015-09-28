/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.negocio;
import modelo.negocio.*;
import modelo.dao.*;

/**
 *
 * @author Fabricio
 */
public class Main {
       public static void main(String[] args) {
           Usuario user = new Usuario(1, "123", "123", 1);
           
           UsuarioDAO dao = new UsuarioDAO();
           
           //Perfil perfil = new Perfil("Adriana", );
           
           dao.cadastrar(user);
           
       }
}
