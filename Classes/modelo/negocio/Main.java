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
           //UsuarioDAO dao = new UsuarioDAO();
          // Usuario user = new Usuario("12", "12", 1);
          Perfil perfil = new Perfil("Adriana Veloso da Silva", "Sao Bento do Una", (float)8.8, (float)8.8, (float)24, (float)0.89, 0, 1);
           
           PerfilDAO dao = new PerfilDAO();
           
          dao.cadastrar(perfil);
           
          // perfis = dao.buscarTodos();
          // System.out.println(perfis.get(0).getIdPerfil());
           
           perfil.setCidade("Capoeiras");
           dao.atualizar(perfil);
           
           System.out.println(perfil.getCidade());
           
          // dao.remover(7);
           
           perfis = dao.buscar("Adriana Veloso da Silva");
           System.out.println(perfis.get(0).getNome());
          
       }
}
