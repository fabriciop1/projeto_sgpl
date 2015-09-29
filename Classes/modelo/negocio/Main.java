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
           
           //UsuarioDAO dao = new UsuarioDAO();
          // Usuario user = new Usuario("12", "12", 1);
          Perfil perfil = new Perfil("Adriana", "Garanhuns", (float)2.3, (float)4.5, (float)6.7, (float)8.9, 20, 3);
           
           PerfilDAO dao = new PerfilDAO();
           
           dao.cadastrar(perfil);
       }
}
