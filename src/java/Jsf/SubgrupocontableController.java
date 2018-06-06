package Jsf;

import Modelo.Subgrupocontable;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.SubgrupocontableFacade;
import Jpa.SubgrupocontableFacadeLocal;
import Modelo.Empresa;
import Modelo.Subgrupo;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Named("subgrupocontableController")
@ViewScoped
public class SubgrupocontableController implements Serializable {

    @EJB
    private Jpa.SubgrupocontableFacadeLocal ejbFacade;
    private List<Subgrupocontable> items = null;
    private List<Subgrupocontable> itemsmodelo = null;
    
    private Subgrupocontable selected;
    @Inject
    private RequerimientosController requerimientosController;
    @Inject
    private Subgrupocontable subg;

    public SubgrupocontableController() {
    }

    public Subgrupocontable getSelected() {
        return selected;
    }

    public void setSelected(Subgrupocontable selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SubgrupocontableFacadeLocal getFacade() {
        return ejbFacade;
    }
    
    private String text;
    private String fragmento;

    public String getFragmento() {
        return fragmento;
    }

    public void setFragmento(String fragmento) {
        this.fragmento = fragmento;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String codigocuentaarmado(){
        text=text+fragmento+000;
        return text;
    }

    public List<Subgrupocontable> getItemsmodelo() {
        return itemsmodelo;
    }

    public void setItemsmodelo(List<Subgrupocontable> itemsmodelo) {
        this.itemsmodelo = itemsmodelo;
    }
    
    public void clonarSubgrupos(){
        try {
            Empresa empresa=requerimientosController.getEmpresa();
            for (Subgrupocontable submodelo : itemsmodelo){
                subg=submodelo;
                subg.setIdempresa(empresa.getIdempresa());
                ejbFacade.create(subg);
            }
            items=ejbFacade.subgrupocontableAll(empresa);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Subgrupo Modelo Clonado Satisfactoriamente", ""));
        }catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Clonar Subgrupo Mondelo", ""));
        }
    }

    public Subgrupocontable prepareCreate() {
        selected = new Subgrupocontable();
        selected.setIdempresa(requerimientosController.getEmpresa().getIdempresa());
        selected.setCodigocuenta(0);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecontable").getString("SubgrupocontableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecontable").getString("SubgrupocontableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecontable").getString("SubgrupocontableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Subgrupocontable> getItems() {
        if (items == null) {
            items = getFacade().subgrupocontableAll(requerimientosController.getEmpresa());
        }
        return items;
    }
    
    public List<Subgrupocontable> getItemsModelo() {
        if (itemsmodelo == null) {
            itemsmodelo = getFacade().subgrupocontableModelo();
        }
        return itemsmodelo;
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

    public Subgrupocontable getSubgrupocontable(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Subgrupocontable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Subgrupocontable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Subgrupocontable.class)
    public static class SubgrupocontableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SubgrupocontableController controller = (SubgrupocontableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subgrupocontableController");
            return controller.getSubgrupocontable(getKey(value));
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
            if (object instanceof Subgrupocontable) {
                Subgrupocontable o = (Subgrupocontable) object;
                return getStringKey(o.getCodigocuenta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Subgrupocontable.class.getName()});
                return null;
            }
        }

    }

}
