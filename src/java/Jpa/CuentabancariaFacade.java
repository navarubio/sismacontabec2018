/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Cuentabancaria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimarye
 */
@Stateless
public class CuentabancariaFacade extends AbstractFacade<Cuentabancaria> implements CuentabancariaFacadeLocal{

    @PersistenceContext(unitName = "SismacontabecPU")
    private EntityManager em;
    private List<Cuentabancaria> lista = null;
    private String consulta;
    private Cuentabancaria cuentabancaria=null;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentabancariaFacade() {
        super(Cuentabancaria.class);
    }
    
    @Override
    public List<Cuentabancaria> espxBanco(int idbank) {
        try { 
            consulta = "From Cuentabancaria c where c.idbanco.idbanco= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idbank);
            
            lista = query.getResultList();
//            if (!lista.isEmpty()) {
//                usuario = lista.get(0);
//            }
        } catch (Exception e) {
            throw e;
}
        return lista;
    }
    @Override
    public double saldoencuenta(List <Cuentabancaria> lista){
        double saldo=0;
        if (!lista.isEmpty()) {
            saldo = lista.get(0).getSaldo();
        }
        return saldo;
    }
    
    @Override
    public List<Cuentabancaria> cuentasAll() {
        try { 
            consulta = "From Cuentabancaria c order by c.idbanco";
            Query query = em.createQuery(consulta);
            
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
}
        return lista;
    }
}
