package Jsf;

import Modelo.Plandecuenta;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.PlandecuentaFacade;
import Jpa.PlandecuentaFacadeLocal;
import Modelo.Empresa;
import Modelo.Especificocontable;
import Modelo.Grupocontable;
import Modelo.Libromayor;
import Modelo.Subespecificocontable;
import Modelo.Subgrupocontable;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("plandecuentasController")
@ViewScoped
public class PlandecuentasController implements Serializable {

    @EJB
    private Jpa.PlandecuentaFacadeLocal ejbFacade;
    @EJB
    private Jpa.GrupocontableFacadeLocal ejbFacadeG;
    @EJB
    private Jpa.SubgrupocontableFacadeLocal ejbFacadeSG;
    @EJB
    private Jpa.EspecificocontableFacadeLocal ejbFacadeES;
    @EJB
    private Jpa.SubespecificocontableFacadeLocal ejbFacadeSE;
    @EJB
    private Jpa.LibromayorFacadeLocal ejbFacadeLM;
    

    private List<Plandecuenta> items = null;
    private List<Libromayor> itemsvista=null;
    private List<Plandecuenta> itemsmov = null;
    private Plandecuenta selected;

    private List<Grupocontable> lstGrupos;
    private List<Subgrupocontable> lstSubgrupos;
    private List<Especificocontable> lstEspecificos;
    private List<Subespecificocontable> lstSubespecificos;
    @Inject
    private RequerimientosController requerimientosController;
    @Inject
    private Plandecuenta cuentamovimiento;
    @Inject
    private Plandecuenta plandecuenta;
    private double totaldebegeneral = 0;
    private double totalhabergeneral = 0;
    private String resultado="RESULTADO";
    private double resultadoEjercicio=0;
    int visualizarResultado=0;

    public List<Grupocontable> getLstGrupos() {
        return lstGrupos;
    }

    public void setLstGrupos(List<Grupocontable> lstGrupos) {
        this.lstGrupos = lstGrupos;
    }

    @PostConstruct
    public void init() {
        lstGrupos = ejbFacadeG.grupocontableAll(requerimientosController.getEmpresa());
    }

    public List<Subgrupocontable> getLstSubgrupos() {
        return lstSubgrupos;
    }

    public void setLstSubgrupos(List<Subgrupocontable> lstSubgrupos) {
        this.lstSubgrupos = lstSubgrupos;
    }

    public List<Especificocontable> getLstEspecificos() {
        return lstEspecificos;
    }

    public void setLstEspecificos(List<Especificocontable> lstEspecificos) {
        this.lstEspecificos = lstEspecificos;
    }

    public List<Subespecificocontable> getLstSubespecificos() {
        return lstSubespecificos;
    }

    public void setLstSubespecificos(List<Subespecificocontable> lstSubespecificos) {
        this.lstSubespecificos = lstSubespecificos;
    }

    public PlandecuentasController() {
    }

    public Plandecuenta getSelected() {
        return selected;
    }

    public void setSelected(Plandecuenta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PlandecuentaFacadeLocal getFacade() {
        return ejbFacade;
    }
    
    public double getTotaldebegeneral() {
        return totaldebegeneral;
    }

    public void setTotaldebegeneral(double totaldebegeneral) {
        this.totaldebegeneral = totaldebegeneral;
    }

    public double getTotalhabergeneral() {
        return totalhabergeneral;
    }

    public void setTotalhabergeneral(double totalhabergeneral) {
        this.totalhabergeneral = totalhabergeneral;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getVisualizarResultado() {
        return visualizarResultado;
    }

    public void setVisualizarResultado(int visualizarResultado) {
        this.visualizarResultado = visualizarResultado;
    }

    public double getResultadoEjercicio() {
        return resultadoEjercicio;
    }

    public void setResultadoEjercicio(double resultadoEjercicio) {
        this.resultadoEjercicio = resultadoEjercicio;
    }

    public Plandecuenta prepareCreate() {
        selected = new Plandecuenta();
        selected.setCodigocuenta(0);
        selected.setIdempresa(requerimientosController.getEmpresa().getIdempresa());
        initializeEmbeddableKey();
        return selected;
    }

    public List<Subgrupocontable> refrescarSubgrupos() {
        try {
            lstSubgrupos = ejbFacadeSG.subgxGrupo(selected.getIdgrupocontable(), requerimientosController.getEmpresa());
        } catch (Exception e) {
        }
        return lstSubgrupos;
    }

    public List<Especificocontable> refrescarEspecificos() {
        try {
            lstEspecificos = ejbFacadeES.espxSGrupo(selected.getIdgrupocontable(), selected.getIdsubgrupocontable(), requerimientosController.getEmpresa());
        } catch (Exception e) {
        }
        return lstEspecificos;
    }

    public List<Subespecificocontable> refrescarSubespecificos() {
        try {
            lstSubespecificos = ejbFacadeSE.subespxEspecifico(selected.getIdgrupocontable(), selected.getIdsubgrupocontable(), selected.getIdespecificocontable(),requerimientosController.getEmpresa());
        } catch (Exception e) {
        }
        return lstSubespecificos;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecontable").getString("PlandecuentaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecontable").getString("PlandecuentaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecontable").getString("PlandecuentaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Plandecuenta> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Plandecuenta> getItemsordenados() {
        items = getFacade().itemsordenados(requerimientosController.getEmpresa());
        return items;
    }
    
    public List<Plandecuenta> getItemsBalance() {
        items = getFacade().itemseeff(requerimientosController.getEmpresa(),1);
        return items;
    }
    
    public List<Plandecuenta> getItemsResultados() {
        items = getFacade().itemseeff(requerimientosController.getEmpresa(),2);
        return items;
    }
    
    public List<Plandecuenta> getItemsComprobacion() {
        items = getFacade().itemseeff(requerimientosController.getEmpresa(),3);
        return items;
    }
    
    public List<Plandecuenta> getCuentasdemovimiento() {
        itemsmov = getFacade().cuentasdeMovimiento();
        return itemsmov;
    }
    
        public void clonarCuentasdeMovimiento() {
        try {
            Empresa empresa = requerimientosController.getEmpresa();
            int estructura = 0;
            for (Plandecuenta movi : itemsmov) {

                plandecuenta.setCodigocuenta(movi.getCodigocuenta());
                plandecuenta.setIdgrupocontable(movi.getIdgrupocontable());
                plandecuenta.setIdsubgrupocontable(movi.getIdsubgrupocontable());
                plandecuenta.setIdespecificocontable(movi.getIdespecificocontable());
                plandecuenta.setIdsubespecificocontable(movi.getIdsubespecificocontable());
                plandecuenta.setIdgeneralcuenta(movi.getIdgeneralcuenta());
                plandecuenta.setSaldogeneral(0.0);
                plandecuenta.setIdempresa(empresa.getIdempresa());
                plandecuenta.setDescripcioncuenta(movi.getDescripcioncuenta());
                plandecuenta.setIdtipopartidacontable(movi.getIdtipopartidacontable());
                plandecuenta.setIdtipocuentacontable(movi.getIdtipocuentacontable());
                plandecuenta.setIdtiposaldocontable(movi.getIdtiposaldocontable());
                plandecuenta.setFujodeefectivo(movi.getFujodeefectivo());                
                ejbFacade.create(plandecuenta);
            }
            items = ejbFacade.itemsordenados(empresa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Cuentas de Movimiento Modelo Clonado Satisfactoriamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Clonar las Cuentas de Movimiento Modelo", "Error al Clonar las Cuentas de Movimiento Modelo"));
        }
    }
    
    public String getComprobacionTotal() {
        double total = 0;
 
        for(Plandecuenta plandcta : getItems()) {
            if (plandcta.getSaldogeneral()!=null){
                if (plandcta.getSaldogeneral()<0 || plandcta.getSaldogeneral()>0){
                    total += plandcta.getSaldogeneral();            
                }
            }
        }
        return new DecimalFormat("###,###.##").format(total);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecontable").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecontable").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Plandecuenta getPlandecuenta(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Plandecuenta> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Plandecuenta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Plandecuenta.class)
    public static class PlandecuentaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlandecuentasController controller = (PlandecuentasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "plandecuentaController");
            return controller.getPlandecuenta(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Plandecuenta) {
                Plandecuenta o = (Plandecuenta) object;
                return getStringKey(o.getIdplandecuenta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Plandecuenta.class.getName()});
                return null;
            }
        }

    }
    public String getComprobacionTotalDebe() {
        double total = 0;
 
        for(Plandecuenta plandcta : items) {
            if (plandcta.getSaldogeneral()!=null){
                if (plandcta.getSaldogeneral()>0){
                    total += plandcta.getSaldogeneral();            
                }
            }
        }
        totaldebegeneral=total;        
        return new DecimalFormat("###,###.##").format(total);
    }
    public String getComprobacionTotalHaber() {
        double total = 0;
 
        for(Plandecuenta plandcta : items) {
            if (plandcta.getSaldogeneral()!=null){
                if (plandcta.getSaldogeneral()<0){
                    total += plandcta.getSaldogeneral();            
                }
            }
        }
        totalhabergeneral=total;
        resultadoEjercicio = totaldebegeneral+totalhabergeneral;
        if (resultadoEjercicio>0){
            resultado="UTILIDAD EN EL EJERCICIO";
            resultadoEjercicio = resultadoEjercicio*-1;
            visualizarResultado=1;
            totalhabergeneral= totalhabergeneral+resultadoEjercicio;
        }else{
            resultado="PERDIDA EN EL EJERCICIO";
            resultadoEjercicio = resultadoEjercicio*-1;            
            visualizarResultado=2;
            totaldebegeneral= totaldebegeneral+resultadoEjercicio;
        }
        return new DecimalFormat("###,###.##").format(total);
    } 
    public String getResultadoFinal() {
        
        return new DecimalFormat("###,###.##").format(resultadoEjercicio);
    }
    
    public String getTotalDeudor() {
        return new DecimalFormat("###,###.##").format(totaldebegeneral);
    }

    public String getTotalAcreedor() {
        
        return new DecimalFormat("###,###.##").format(totalhabergeneral);
    }
    
        public String getComprobacionDebe() {
        double total = 0;
 
        for(Plandecuenta plandcta : items) {
            if (plandcta.getSaldogeneral()!=null){
                if (plandcta.getSaldogeneral()>0){
                    total += plandcta.getSaldogeneral();            
                }
            }
        }
        totaldebegeneral=total;        
        return new DecimalFormat("###,###.##").format(total);
    }
    
    public String getComprobacionHaber() {
        double total = 0;
 
        for(Plandecuenta plandcta : items) {
            if (plandcta.getSaldogeneral()!=null){
                if (plandcta.getSaldogeneral()<0){
                    total += plandcta.getSaldogeneral();            
                }
            }
        }
        totalhabergeneral=total;
        resultadoEjercicio = totaldebegeneral+totalhabergeneral;
        if (resultadoEjercicio<0){
            resultado="UTILIDAD EN EL EJERCICIO";
            resultadoEjercicio = resultadoEjercicio*-1;
            visualizarResultado=1;
            totaldebegeneral= totaldebegeneral+resultadoEjercicio;
        }else{
            resultado="PERDIDA EN EL EJERCICIO";
            resultadoEjercicio = resultadoEjercicio*-1;            
            visualizarResultado=2;
            totalhabergeneral= totalhabergeneral+resultadoEjercicio;
        }
        return new DecimalFormat("###,###.##").format(total);
    }
}
