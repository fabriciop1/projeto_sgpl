/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.negocio;
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
           
           try {
              ArrayList<DadosEconMensais> dados = dao3.recuperarPorPeriodo(2015, 3, 2015, 6);
              
              for(int i = 0; i < dados.size(); i++) {
                  System.out.println(dados.get(i).getMes());
              }
               
               
           } catch (SQLException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
}
