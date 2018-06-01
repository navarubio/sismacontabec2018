package Jsf;

import Modelo.Auxiliarrequerimiento;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.AuxiliarrequerimientoFacade;
import Jpa.AuxiliarrequerimientoFacadeLocal;
import Modelo.Empresa;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;

@ManagedBean(name = "auxiliarrequerimientoController1")
@ViewScoped
public class AuxiliarrequerimientoController1 implements Serializable {

    @EJB
    private AuxiliarrequerimientoFacadeLocal ejbFacade;
    private List<Auxiliarrequerimiento> items = null; 
    private List<Auxiliarrequerimiento> requerimientosactivos = null;
    private Auxiliarrequerimiento selected;
    private Empresa empresa;

    @PostConstruct
    public void init (){
        empresa= (Empresa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("empresa");
        requerimientosactivos = ejbFacade.buscarrequerimientosActivos(empresa);

    }
   
    public AuxiliarrequerimientoController1() {
    }

    public Auxiliarrequerimiento getSelected() {
        return selected;
    }

    public void setSelected(Auxiliarrequerimiento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AuxiliarrequerimientoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Auxiliarrequerimiento> getRequerimientosactivos() {
        return requerimientosactivos;
    }

    public void setRequerimientosactivos(List<Auxiliarrequerimiento> requerimientosactivos) {
        this.requerimientosactivos = requerimientosactivos;
    }
    
    public List<Auxiliarrequerimiento> buscarRequerimientosActivos() {
        requerimientosactivos = ejbFacade.buscarrequerimientosActivos(empresa);
        return requerimientosactivos;
    }

    public Auxiliarrequerimiento prepareCreate() {
        selected = new Auxiliarrequerimiento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AuxiliarrequerimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AuxiliarrequerimientoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AuxiliarrequerimientoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Auxiliarrequerimiento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Auxiliarrequerimiento getAuxiliarrequerimiento(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Auxiliarrequerimiento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Auxiliarrequerimiento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Auxiliarrequerimiento.class)
    public static class AuxiliarrequerimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AuxiliarrequerimientoController1 controller = (AuxiliarrequerimientoController1) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "auxiliarrequerimientoController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof Auxiliarrequerimiento) {
                Auxiliarrequerimiento o = (Auxiliarrequerimiento) object;
                return getStringKey(o.getIdauxiliarrequerimiento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Auxiliarrequerimiento.class.getName()});
                return null;
            }
        }

    }
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/requerimientos.jasper");
       
        rArticulo.getReporte(ruta);        
        FacesContext.getCurrentInstance().responseComplete();               
    }

}
