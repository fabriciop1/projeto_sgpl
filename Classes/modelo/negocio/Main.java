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
           dao.cadastrar(1, 3);
           dao.cadastrar(1, 4);
           dao.cadastrar(1, 10);
           dao.cadastrar(1, 9);
          
//          Perfil perfil = new Perfil("Adriana Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil2 = new Perfil("Joana Veloso da Silva", "Sao Bento do Una", (float)80.8, (float)80.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil3 = new Perfil("Morgana Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil4 = new Perfil("Evandenuza Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)80.8, (float)240, (float)0.89, 0, 1);
//          Perfil perfil5 = new Perfil("Araci Epaminondas Veloso da Silva", "Sao Bento do Una", (float)80.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//          Perfil perfil6 = new Perfil("Josefina Epaminondas Veloso da Silva", "Sao Bento do Una", (float)80.8, (float)8.8, (float)24, (float)0.89, 0, 1);
//           
//          PerfilDAO perfilDao = new PerfilDAO();
//          
//          perfilDao.cadastrar(perfil);
//          perfilDao.cadastrar(perfil2);
//          perfilDao.cadastrar(perfil3);
//          perfilDao.cadastrar(perfil4);
//          perfilDao.cadastrar(perfil5);
//          perfilDao.cadastrar(perfil6);
          
////          UsuarioDAO usuarioDao = new UsuarioDAO();
//          UsuarioPerfilDAO usuPerfDao = new UsuarioPerfilDAO();
//           
//          perfis = usuPerfDao.buscarPerfisPorUsuario(999);
//          Perfil p = perfis.get(0);
//          
//           System.out.println(p.getNome() + " | "+ p.getAreaPecLeite());
       }
}
