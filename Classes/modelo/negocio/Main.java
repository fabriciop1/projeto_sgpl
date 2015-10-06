/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.negocio;
import java.util.ArrayList;
import modelo.dao.*;

/**
 *
 * @author Fabricio
 */
public class Main {
       public static void main(String[] args) {
           ArrayList<Perfil> perfis = new ArrayList<>();
          
           UsuarioPerfilDAO dao = new UsuarioPerfilDAO();
           dao.cadastrar(1, 1);
           dao.cadastrar(1, 2);
           dao.cadastrar(2, 3);
           dao.cadastrar(2, 4);
           dao.cadastrar(3, 5);
           dao.cadastrar(3, 6);
           dao.cadastrar(4, 7);
           dao.cadastrar(4, 8);
           dao.cadastrar(5, 9);
           dao.cadastrar(5, 10);
           dao.cadastrar(6, 11);
           dao.cadastrar(6, 12);
          
//          Perfil perfil = new Perfil("Carla Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil2 = new Perfil("Marcos Veloso da Silva", "Sao Bento do Una", (float)80.8, (float)80.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil3 = new Perfil("Fernando Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil4 = new Perfil("Alex Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)80.8, (float)240, (float)0.89, 0, 1);
//          Perfil perfil5 = new Perfil("Daniela Jurema Veloso da Silva", "Sao Bento do Una", (float)80.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil6 = new Perfil("Silva Veloso da Silva", "Sao Bento do Una", (float)80.8, (float)8.8, (float)24, (float)0.89, 0, 1);
           
//          PerfilDAO perfilDao = new PerfilDAO();
          
//          perfilDao.cadastrar(perfil);
//          perfilDao.cadastrar(perfil2);
//          perfilDao.cadastrar(perfil3);
//          perfilDao.cadastrar(perfil4);
//          perfilDao.cadastrar(perfil5);
//          perfilDao.cadastrar(perfil6);
          
//          Usuario usuario = new Usuario("alexandre", "12345");
//          Usuario usuario2 = new Usuario("fabricio", "12345");
//          Usuario usuario3 = new Usuario("jefferson", "12345");
//          Usuario usuario4 = new Usuario("diego", "12345");
//          Usuario usuario5 = new Usuario("alana", "12345");
           
//          UsuarioDAO usuarioDao = new UsuarioDAO();
          
//          usuarioDao.cadastrar(usuario);
//          usuarioDao.cadastrar(usuario2);
//          usuarioDao.cadastrar(usuario3);
//          usuarioDao.cadastrar(usuario4);
//          usuarioDao.cadastrar(usuario5);
           
//          perfis = usuPerfDao.buscarPerfisPorUsuario(999);
//          Perfil p = perfis.get(0);
          
//          System.out.println(p.getNome() + " | "+ p.getAreaPecLeite());
       }
}
