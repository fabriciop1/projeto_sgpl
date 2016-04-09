/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dao.DadosEconMensaisDAO;
import modelo.dao.InventarioAnimaisDAO;
import modelo.dao.InventarioBenfeitoriasDAO;
import modelo.dao.InventarioMaquinasDAO;
import modelo.dao.InventarioTerrasDAO;
import modelo.dao.PerfilDAO;
import modelo.dao.RotaDAO;
import modelo.dao.UsuarioDAO;
import modelo.dao.UsuarioPerfilDAO;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.InventarioAnimais;
import modelo.negocio.InventarioBenfeitorias;
import modelo.negocio.InventarioMaquinas;
import modelo.negocio.InventarioResumo;
import modelo.negocio.InventarioTerras;
import modelo.negocio.Perfil;
import modelo.negocio.Rota;
import modelo.negocio.Usuario;

/**
 *
 * @author Jefferson Sales
 */
public final class TrashGen {
    
    private static final char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l',
                                    'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    private static final char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
    
    private static final char[] symbols = {'*','%','(',')','&','?','!','<','>',
                                           '.',',',' ',':',';','-','_','+','=','$','#' };
    
    public static String generateString(int length, boolean fixedLength, boolean hasNumbers, boolean hasSymbols){
        
        Random rand = new Random();
        
        String str = "";
        
        if(!fixedLength){
            length = rand.nextInt(10000) % length;
        }
        
        int i=0;
        
        do{          
            
            str += letters[rand.nextInt(29000)  % letters.length]; i++;
            if(i >= length) break;
            
            if(hasNumbers){
                str += numbers[rand.nextInt(77000) % numbers.length]; i++;
                if(i >= length) break;
            }
            
            if(hasSymbols){
                str += symbols[rand.nextInt(40500) % symbols.length]; i++;
                if(i >= length) break;
            }
        }
        while(i < length);
        
        return str;
    }
    
    
    public static void generateGarbageInDatabase(int insertions, String login, String password, boolean createUser) throws SQLException{
        
        Random rand = new Random();
        
        Usuario u = null;
        Rota rota = new RotaDAO().recuperar(1);
        
        if(createUser){
            u = new Usuario(login, password, rota);
            new UsuarioDAO().cadastrar(u);
        }
        
        u = new UsuarioDAO().recuperarPorLogin(login);
        
        Perfil p = new Perfil(generateString(20, false, false, false), generateString(20,false,false,false), 
                rand.nextInt() % 1000, rand.nextInt() % 1000, rand.nextInt() % 1000, rand.nextInt() % 1000, 
                rand.nextInt() % 1000, rota);
        
        new PerfilDAO().cadastrar(p);
        
        new UsuarioPerfilDAO().cadastrar(u, p);
        
        for(int i=0; i<insertions; i++){

            InventarioAnimais ia = new InventarioAnimais();
            ia.setPerfil(p); ia.setCategoria(generateString(200,false,false,false));
            ia.setCompra(rand.nextInt() % 10000); ia.setMorte(rand.nextInt() % 10000);
            ia.setNascimento(rand.nextInt() % 10000);ia.setTipoAnimal(rand.nextInt() % 10000);ia.setValorCabeca(rand.nextInt() % 10000);
            ia.setValorFinal(rand.nextInt() % 10000);ia.setValorGastoCompraAnimais(rand.nextInt() % 10000);
            ia.setValorInicio(rand.nextInt() % 10000);ia.setVenda(rand.nextInt() % 10000);ia.setVidaUtilAnimaisServico(rand.nextInt() % 10000);
            ia.setVidaUtilReprodutores(rand.nextInt() % 10000);ia.setPerfil(p);

            InventarioBenfeitorias ib = new InventarioBenfeitorias(generateString(20,false,false,false), generateString(10,false,false,false),
                    rand.nextInt() % 1000, rand.nextInt() % 10000, 0, p);

            InventarioMaquinas im = new InventarioMaquinas(generateString(20,false,false,true), generateString(10,false,false,false), rand.nextInt() % 1000,
                    rand.nextInt() % 1000, rand.nextInt() % 1000, p);

            InventarioTerras it = new InventarioTerras(generateString(20,false,false,true), rand.nextInt() % 1000, rand.nextInt() % 1000, rand.nextInt() % 1000,
                    rand.nextInt() % 1000, rand.nextInt() % 1000, rand.nextInt() % 1000, rand.nextInt() % 1000, p);
//            
            new InventarioAnimaisDAO().cadastrar(ia);
            new InventarioBenfeitoriasDAO().cadastrar(ib);
            new InventarioMaquinasDAO().cadastrar(im);
            new InventarioTerrasDAO().cadastrar(it);
               
        }
    }
    
    public static void main(String[] args){
        
        try {
            generateGarbageInDatabase(700, "adriana","adriana",false);
        } catch (SQLException ex) {
            Logger.getLogger(TrashGen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
