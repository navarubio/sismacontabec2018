/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Cobroventa;
import Modelo.Conciliacion;
import Modelo.Cuentabancaria;
import Modelo.Movimientobancario;
import Modelo.Otroingreso;
import Modelo.Pagocompra;
import Modelo.Plandecuenta;
import Modelo.Reposicioncajachica;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface MovimientobancarioFacadeLocal {

    void create(Movimientobancario movimientobancario);

    void edit(Movimientobancario movimientobancario);

    void remove(Movimientobancario movimientobancario);

    Movimientobancario find(Object id);

    List<Movimientobancario> findAll();

    List<Movimientobancario> findRange(int[] range);

    int count();  
    
    List<Movimientobancario> buscarmovimiento (Otroingreso otro); 
    
    List<Movimientobancario> buscarmovimiento (Reposicioncajachica reposicion);
    
    List<Movimientobancario> buscarmovimientoporfecha (Cuentabancaria cuenta, Date fechaini, Date fechafinish);
    
    Movimientobancario buscarmovimientoxIdcobro(Cobroventa cobro);
    
    Movimientobancario buscarmovimientoxIdotroingresoCtaContable(Otroingreso otro, Plandecuenta cuentacontab);
    
    Movimientobancario buscarmovimientoxIdpago(Pagocompra pago);
    
    Movimientobancario buscarmovimientoxreposicion(Reposicioncajachica reposicion);
    
    List<Movimientobancario> buscarmovimientosConciliacion(Cuentabancaria cuenta, Date fechafinish);

    List<Movimientobancario> movimientosparaConciliacionInterna (Cuentabancaria cuenta, Date fechafinish);
    
    List<Movimientobancario> movimientosConciliados(Conciliacion concilia);
  }
