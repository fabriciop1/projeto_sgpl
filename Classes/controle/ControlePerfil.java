/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

/**
 *
 * @author Alexandre
 */
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import modelo.dao.PerfilDAO;
import modelo.negocio.Perfil;
 
@ManagedBean(name="controlePerfil")
@ViewScoped
public class ControlePerfil implements Serializable {
     
    public List<Perfil> perfis;
     
    @ManagedProperty("#{perfilDAO}")
    private PerfilDAO perfilDao;
 
    public ControlePerfil() {
        perfis = new ArrayList<>();
        perfilDao = new PerfilDAO();
    }
     
    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public PerfilDAO getPerfilDao() {
        return perfilDao;
    }
        
    public void setPerfilDao(PerfilDAO perfilDao) {
        this.perfilDao = perfilDao;
    }
}
