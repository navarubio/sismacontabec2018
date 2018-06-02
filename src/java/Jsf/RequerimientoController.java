package Jsf;

import Jpa.DepartamentoFacadeLocal;
import Modelo.Requerimiento;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.RequerimientoFacadeLocal;
import Modelo.Departamento;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@ManagedBean(name = "requerimientoController")
@ViewScoped
public class RequerimientoController implements Serializable {

    @EJB
    private RequerimientoFacadeLocal ejbFacade;
    @EJB
    private DepartamentoFacadeLocal ejbDepartamento;
    private List<Requerimiento> items = null;
    private Requerimiento selected;
    @Inject
    private RequerimientosController requerimientosController;

    private List<Departamento> departamentos=null;
    
    public RequerimientoController() {
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public Requerimiento getSelected() {
        return selected;
    }

    public void setSelected(Requerimiento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RequerimientoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Requerimiento prepareCreate() {
        selected = new Requerimiento();
        initializeEmbeddableKey();
        return selected;
    }
    
    public List<Departamento> listaDepartartamentos() {
        try {
            departamentos = ejbDepartamento.findAll();
        } catch (Exception e) {
        }
        return departamentos;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RequerimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RequerimientoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RequerimientoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Requerimiento> getItems() {
        if (items == null) {
            items = getFacade().requerimientosAll(requerimientosController.getEmpresa());
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

    public List<Requerimiento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Requerimiento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Requerimiento.class)
    public static class RequerimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RequerimientoController controller = (RequerimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "requerimientoController");
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
            if (object instanceof Requerimiento) {
                Requerimiento o = (Requerimiento) object;
                return getStringKey(o.getIdrequerimiento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Requerimiento.class.getName()});
                return null;
            }
        }

    }

}
