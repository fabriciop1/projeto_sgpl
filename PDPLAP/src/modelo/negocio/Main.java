/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.negocio;
import java.util.ArrayList;
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
          Perfil perfil = new Perfil(666, "Adriana", "Garanhuns", 2.3f, 4.5f, 6.7f, 8.9f, 20, 300);
           
           PerfilDAO dao = new PerfilDAO();
           
           dao.cadastrar(perfil);
           
           dao.remover(4);
           
           ArrayList<Perfil> perfis = dao.recuperarTodos();
           
           System.out.println("____________________________________________________________\n");           
           for(Perfil p : perfis){
               System.out.println(p.getNome());
           }
       }
}
