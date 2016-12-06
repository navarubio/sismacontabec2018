/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Factura;
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
public class FacturaFacade extends AbstractFacade<Factura> implements FacturaFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
    @Override
    public Factura ultimaInsertada() {
        String consulta = null;
        Factura ultima = new Factura();
        try {
            consulta = "Select f From Factura f Order By f.numerofact Desc";
            Query query = em.createQuery(consulta);
            List<Factura> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return ultima;
    }
    
    @Override
    public int siguientefactura() {
        String consulta = null;
        Factura ultima = new Factura();
        int numeracion;
        try {
            consulta = "Select f From Factura f Order By f.numerofact Desc";
            Query query = em.createQuery(consulta);
            List<Factura> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        numeracion = ultima.getNumerofact()+1;
        return numeracion;
    }
    
    
}
