/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.negocio;
import flex.db.GenericDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.*;

/**
 *
 * @author Fabricio
 */
public class Main {
       public static void main(String[] args) {
           
           InventarioAnimaisDAO dao = new InventarioAnimaisDAO();
           InventarioTerrasDAO dao2 = new InventarioTerrasDAO();
           DadosEconMensaisDAO dao3 = new DadosEconMensaisDAO();
           InventarioResumoDAO dao4 = new InventarioResumoDAO();
           DEMEspecificacaoDAO dao5 = new DEMEspecificacaoDAO();
          
           GenericDAO<Perfil> dao6 = new GenericDAO<>(Perfil.class);
           
           Perfil perfil = null;
           
           perfil = dao6.retrieveByColumn("cidade", "São Bento do Una").get(0);
               System.out.println(perfil.getAreaPecLeite());
           
           //try {
             //  perfil = (new PerfilDAO()).recuperar(1) ;
              
              
             //  dao5.cadastrar("Venda de leite(L)");
             //  dao5.remover(1);
              // System.out.println(dados.get(0));
            //   DadosEconMensais dados = new DadosEconMensais(1, 2015, 1.0, 1600.0, 1, dao5.recuperar(1), perfil);
             //  dao3.cadastrar(dados);
              // InventarioResumo inv = new InventarioResumo(0.0, 0.0, 0.0, 1, 2015, perfil);
             //  dao4.cadastrar(inv);
            //   System.out.println(inv.get(0).getAno());
               
             //  dao4.recuperarPorPerfil(13);
             // ArrayList<DadosEconMensais> dados = dao3.recuperarPorPeriodo(2015, 3, 2015, 6);
              // System.out.println("");
            //  for(int i = 0; i < dados.size(); i++) {
            //      System.out.println(dados.get(i).getMes());
            //  }
               
               
         //  } catch (SQLException ex) {
         //      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         //  }
           
          
       }
}
