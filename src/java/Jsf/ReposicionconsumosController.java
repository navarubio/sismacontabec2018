package Jsf;

import Modelo.Reposicionconsumos;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ReposicionconsumosFacade;
import Jpa.ReposicionconsumosFacadeLocal;

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

@Named("reposicionconsumosController")
@SessionScoped
public class ReposicionconsumosController implements Serializable {

    @EJB
    private Jpa.ReposicionconsumosFacadeLocal ejbFacade;
    private List<Reposicionconsumos> items = null;
    private Reposicionconsumos selected;

    public ReposicionconsumosController() {
    }

    public Reposicionconsumos getSelected() {
        return selected;
    }

    public void setSelected(Reposicionconsumos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ReposicionconsumosFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Reposicionconsumos prepareCreate() {
        selected = new Reposicionconsumos();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecajachica").getString("ReposicionconsumosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecajachica").getString("ReposicionconsumosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecajachica").getString("ReposicionconsumosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Reposicionconsumos> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecajachica").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecajachica").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Reposicionconsumos getReposicionconsumos(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Reposicionconsumos> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Reposicionconsumos> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Reposicionconsumos.class)
    public static class ReposicionconsumosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReposicionconsumosController controller = (ReposicionconsumosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reposicionconsumosController");
            return controller.getReposicionconsumos(getKey(value));
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
            if (object instanceof Reposicionconsumos) {
                Reposicionconsumos o = (Reposicionconsumos) object;
                return getStringKey(o.getIdresposicionconsumos());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Reposicionconsumos.class.getName()});
                return null;
            }
        }

    }

}
