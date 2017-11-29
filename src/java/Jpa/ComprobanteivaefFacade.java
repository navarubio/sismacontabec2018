/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Comprobanteivaef;
import java.text.DecimalFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimar
 */
@Stateless
public class ComprobanteivaefFacade extends AbstractFacade<Comprobanteivaef> implements ComprobanteivaefFacadeLocal{
    @PersistenceContext(unitName = "SismacontabecPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComprobanteivaefFacade() {
        super(Comprobanteivaef.class);
    }
    
    @Override
    public String  siguientecomprobanteformat() {
        String consulta = null;
        Comprobanteivaef ultimo = new Comprobanteivaef();
        int numeracion=0;
        DecimalFormat myFormatter = new DecimalFormat("00000000"); 
        //formatear la cantidad 
        try {
            consulta = "Select c From Comprobanteivaef c Order By c.idcomprobanteivaef Desc";
            Query query = em.createQuery(consulta);
            List<Comprobanteivaef> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultimo = lista.get(0);
                numeracion = ultimo.getIdcomprobanteivaef()+1; 
            }else{
                numeracion=numeracion+1;
            }
                
        } catch (Exception e) {
            throw e;
        }
        
        String output = myFormatter.format(numeracion);
        return output;
    }
    
}
