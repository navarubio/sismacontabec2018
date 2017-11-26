/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Compra;
import Modelo.Estatusfactura;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface CompraFacadeLocal {

    void create(Compra compra);

    void edit(Compra compra);

    void remove(Compra compra);

    Compra find(Object id);

    List<Compra> findAll();

    List<Compra> findRange(int[] range);

    int count();
    
    Compra ultimacompraInsertada();
    
    List<Compra> buscarcomprasporAutorizar ();
    
    List<Compra> buscarcomprasporPagar();
    
    List<Compra> buscarcomprasPagadas();
    
    List<Compra> buscarcomprasFiltradas (Estatusfactura status, Date fechaini, Date fechafinish);
}
