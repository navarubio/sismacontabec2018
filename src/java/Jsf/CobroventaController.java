package Jsf;

import Modelo.Cobroventa;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.CobroventaFacadeLocal;
import Modelo.Factura;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("cobroventaController")
@ViewScoped
public class CobroventaController implements Serializable {

    @EJB
    private Jpa.CobroventaFacadeLocal ejbFacade;
    private List<Cobroventa> items = null;
    private List<Cobroventa> cobrosporfactura = null;
    @Inject
    private RequerimientosController requerimientosController;

    private Cobroventa selected;

    public CobroventaController() {
    }

    public Cobroventa getSelected() {
        return selected;
    }

    public void setSelected(Cobroventa selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CobroventaFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Cobroventa prepareCreate() {
        selected = new Cobroventa();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecobro").getString("CobroventaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecobro").getString("CobroventaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecobro").getString("CobroventaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cobroventa> getItems() {
        if (items == null) {
            items = getFacade().cobrosAll(requerimientosController.getEmpresa());
        }
        return items;
    }
    
    public List<Cobroventa> cobrosFiltrados(int factu){
        cobrosporfactura = ejbFacade.buscarcobrosporfactura(factu);
        return cobrosporfactura;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecobro").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecobro").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Cobroventa getCobroventa(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Cobroventa> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Cobroventa> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Cobroventa.class)
    public static class CobroventaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CobroventaController controller = (CobroventaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cobroventaController");
            return controller.getCobroventa(getKey(value));
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
            if (object instanceof Cobroventa) {
                Cobroventa o = (Cobroventa) object;
                return getStringKey(o.getIdcobroventa());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cobroventa.class.getName()});
                return null;
            }
        }

    }

}
