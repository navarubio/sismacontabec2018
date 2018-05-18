package Jsf;

import Jpa.DetalleretencionislrefFacadeLocal;
import Modelo.Detalleretencionislrsp;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.DetalleretencionislrspFacade;
import Jpa.DetalleretencionislrspFacadeLocal;
import Modelo.Detalleretencionislref;

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

@Named("detalleretencionislrspController")
@SessionScoped
public class DetalleretencionislrspController implements Serializable {

    @EJB
    private Jpa.DetalleretencionislrspFacadeLocal ejbFacade;
    @EJB
    private DetalleretencionislrefFacadeLocal detalleretencionislrefEJB;

    private List<Detalleretencionislrsp> items = null;
    private Detalleretencionislrsp selected;
    private List<Detalleretencionislref> listaactiva = null;

    public DetalleretencionislrspController() {
    }

    public Detalleretencionislrsp getSelected() {
        return selected;
    }

    public void setSelected(Detalleretencionislrsp selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DetalleretencionislrspFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Detalleretencionislrsp prepareCreate() {
        selected = new Detalleretencionislrsp();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundletributos").getString("DetalleretencionislrspCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Detalleretencionislref> buscarlistaactiva() {
        listaactiva = detalleretencionislrefEJB.buscarretencionesActivas();
        return listaactiva;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundletributos").getString("DetalleretencionislrspUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundletributos").getString("DetalleretencionislrspDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Detalleretencionislrsp> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundletributos").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundletributos").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Detalleretencionislrsp getDetalleretencionislrsp(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Detalleretencionislrsp> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Detalleretencionislrsp> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Detalleretencionislrsp.class)
    public static class DetalleretencionislrspControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DetalleretencionislrspController controller = (DetalleretencionislrspController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleretencionislrspController");
            return controller.getDetalleretencionislrsp(getKey(value));
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
            if (object instanceof Detalleretencionislrsp) {
                Detalleretencionislrsp o = (Detalleretencionislrsp) object;
                return getStringKey(o.getIddetalleretencionislrsp());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Detalleretencionislrsp.class.getName()});
                return null;
            }
        }

    }

}
