/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Distribucionusomaquinaria;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sofimar
 */
@Stateless
public class DistribucionusomaquinariaFacade extends AbstractFacade<Distribucionusomaquinaria> implements DistribucionusomaquinariaFacadeLocal{
    @PersistenceContext(unitName = "SismacontabecPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistribucionusomaquinariaFacade() {
        super(Distribucionusomaquinaria.class);
    }
    
}
