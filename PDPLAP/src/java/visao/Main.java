/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visao;
import java.util.ArrayList;
import modelo.dao.*;
import modelo.negocio.Perfil;

/**
 *
 * @author Fabricio
 */
public class Main {
       public static void main(String[] args) {
           
            //UsuarioDAO dao = new UsuarioDAO();
            //Usuario user = new Usuario("12", "12", 1);
            Perfil perfil = new Perfil("Vera", "Recife", 1.1f, 2.2f, 3.3f, 4.4f, 55, 33);
           
            PerfilDAO dao = new PerfilDAO();
           
            dao.cadastrar(perfil);

            //dao.remover(2);

            ArrayList<Perfil> perfis = dao.recuperarTodos();

            System.out.println("____________________________________________________________\n");           
            for(Perfil p : perfis){
                System.out.println(p.getNome());
            }
       }
}
