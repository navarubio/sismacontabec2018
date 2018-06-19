package Jsf;

import Jpa.BancoFacadeLocal;
import Jpa.CuentabancariaFacadeLocal;
import Jpa.EstatuscontableFacadeLocal;
import Jpa.MaestromovimientoFacadeLocal;
import Jpa.MovimientobancarioFacadeLocal;
import Modelo.Otroingreso;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.OtroingresoFacade;
import Jpa.OtroingresoFacadeLocal;
import Jpa.TipoconjuntoFacadeLocal;
import Jpa.TipoingresoFacadeLocal;
import Modelo.Banco;
import Modelo.Cobroventa;
import Modelo.Cuentabancaria;
import Modelo.Maestromovimiento;
import Modelo.Movimientobancario;
import Modelo.Tipoconjunto;
import Modelo.Usuario;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
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
import javax.servlet.ServletContext;

@Named("otroingresoController")
@ViewScoped
public class OtroingresoController implements Serializable {

    @EJB
    private Jpa.OtroingresoFacadeLocal ejbFacade;
    @EJB
    private CuentabancariaFacadeLocal cuentabancariaEJB;
    @EJB
    private MaestromovimientoFacadeLocal maestromovimientoEJB;
    @EJB
    private TipoconjuntoFacadeLocal tipoconjuntoEJB;
    @EJB
    private EstatuscontableFacadeLocal estatuscontableEJB;
    @EJB
    private MovimientobancarioFacadeLocal movimientoBancarioEJB;
    @EJB
    private BancoFacadeLocal bancoEJB;
    @EJB
    private TipoingresoFacadeLocal tipoingresoEJB;
    @Inject
    private Maestromovimiento maestromovi;
    @Inject
    private Movimientobancario movimientobancario;
    @Inject
    private Cobroventa cobro;
    @Inject
    private Cobroventa cobroauxiliar;
    @Inject
    private RequerimientosController requerimientosController;
    private List<Otroingreso> items = null;
    private List<Banco> bancos;
    private List<Cuentabancaria> lstCuentasSelecc;
    private List<Cuentabancaria> lstCuentasSeleccemisor;
    private double montoingreso;
    private int visual = 0;   
    private Otroingreso selected;
    private Cuentabancaria cuentabancaria;
    private Cuentabancaria cuentaemisora = null;
    private Otroingreso ingreso = new Otroingreso();
    private Date fechaactual = new Date();
    private Usuario usa;
    private RequerimientosController requer = new RequerimientosController();
    private Tipoconjunto tipoconjunto = null;
    private Banco banco;
    private Banco bancoemisor;
    private String mensaje;
    private int tipomovimiento = 0;

    public OtroingresoController() {
    }
    
    public Otroingreso getSelected() {
        return selected;
    }

    public Otroingreso getIngreso() {
        return ingreso;
    }

    public Cobroventa getCobro() {
        return cobro;
    }

    public void setCobro(Cobroventa cobro) {
        this.cobro = cobro;
    }

    public Cuentabancaria getCuentaemisora() {
        return cuentaemisora;
    }

    public void setCuentaemisora(Cuentabancaria cuentaemisora) {
        this.cuentaemisora = cuentaemisora;
    }

    public void setIngreso(Otroingreso ingreso) {
        this.ingreso = ingreso;
    }

    public Banco getBancoemisor() {
        return bancoemisor;
    }

    public void setBancoemisor(Banco bancoemisor) {
        this.bancoemisor = bancoemisor;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public double getMontoingreso() {
        return montoingreso;
    }

    public void setMontoingreso(double montoingreso) {
        this.montoingreso = montoingreso;
    }

    public Cobroventa getCobroauxiliar() {
        return cobroauxiliar;
    }

    public void setCobroauxiliar(Cobroventa cobroauxiliar) {
        this.cobroauxiliar = cobroauxiliar;
    }

    public List<Cuentabancaria> getLstCuentasSelecc() {
        return lstCuentasSelecc;
    }

    public void setLstCuentasSelecc(List<Cuentabancaria> lstCuentasSelecc) {
        this.lstCuentasSelecc = lstCuentasSelecc;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    public void setSelected(Otroingreso selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OtroingresoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public int getVisual() {
        return visual;
    }

    public void setVisual(int visual) {
        this.visual = visual;
    }

    public List<Cuentabancaria> getLstCuentasSeleccemisor() {
        return lstCuentasSeleccemisor;
    }

    public void setLstCuentasSeleccemisor(List<Cuentabancaria> lstCuentasSeleccemisor) {
        this.lstCuentasSeleccemisor = lstCuentasSeleccemisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getTipomovimiento() {
        return tipomovimiento;
    }

    public void setTipomovimiento(int tipomovimiento) {
        this.tipomovimiento = tipomovimiento;
    }

    @PostConstruct
    public void init() {
        visual = 3;
        ingreso = new Otroingreso();
        cuentabancaria = null;
        cuentaemisora = null;
        ingreso.setFechaingreso(fechaactual);
        bancos = bancoEJB.findAll();

    }

    public Otroingreso prepareCreate() {
        selected = new Otroingreso();
        initializeEmbeddableKey();
        return selected;
    }

    public Cuentabancaria getCuentabancaria() {
        return cuentabancaria;
    }

    public void setCuentabancaria(Cuentabancaria cuentabancaria) {
        this.cuentabancaria = cuentabancaria;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundleingreso").getString("OtroingresoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundleingreso").getString("OtroingresoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundleingreso").getString("OtroingresoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Otroingreso> getItems() {
        if (items == null) {
            items = getFacade().otrosingresosAll(requerimientosController.getEmpresa());
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundleingreso").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundleingreso").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public void selecciontipomovimiento() {
        if (mensaje.equals("Ingreso")) {
            tipomovimiento = 1;
            visual=0;
        } else if (mensaje.equals("Traspaso")) {
            tipomovimiento = 2;
            visual=1;
        } else {
            tipomovimiento = 0;
        }

    }

    public Otroingreso getOtroingreso(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Otroingreso> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Otroingreso> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Otroingreso.class)
    public static class OtroingresoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OtroingresoController controller = (OtroingresoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "otroingresoController");
            return controller.getOtroingreso(getKey(value));
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
            if (object instanceof Otroingreso) {
                Otroingreso o = (Otroingreso) object;
                return getStringKey(o.getIdotroingreso());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Otroingreso.class.getName()});
                return null;
            }
        }
    }

    public void registrar() {
        try {
            cuentabancaria = cobro.getIdcuentabancaria();
            ingreso.setIdcuentabancaria(cuentabancaria);
            usa = requer.getUsa();
            ingreso.setIdusuario(usa);
            double saldoactualbanco = 0;
            double saldoanteriorbanco = 0;
            double saldoanterioremisor = 0;
            double saldoactualbancoemisor = 0;

            saldoanteriorbanco = cuentabancaria.getSaldo();
            saldoactualbanco = requer.redondearDecimales(montoingreso + saldoanteriorbanco);
            
            
            if (visual == 1) {
                saldoanterioremisor = cuentaemisora.getSaldo();
                saldoactualbancoemisor = requer.redondearDecimales(saldoanterioremisor - montoingreso);
                ingreso.setIdtipoingreso(tipoingresoEJB.devolverTipoingresoTraspaso());
            }

            cuentabancaria.setSaldo(saldoactualbanco);
            
            ingreso.setMontoingresado(montoingreso);
            ingreso.setIdusuario(requerimientosController.getUsuariodeprol().getIdusuario());
            ejbFacade.create(ingreso);
            
            cuentabancariaEJB.edit(cuentabancaria);

            if (visual == 1) {
                cuentaemisora.setSaldo(saldoactualbancoemisor);
                cuentabancariaEJB.edit(cuentaemisora);
            }

            int tipoconj = 1;
            tipoconjunto = tipoconjuntoEJB.cambiartipoConjunto(tipoconj);
            maestromovi.setIdotroingreso(ejbFacade.ultimoingreso());
            maestromovi.setFechamovimiento(ingreso.getFechaingreso());
            maestromovi.setIdtipoconjunto(tipoconjunto);
            maestromovi.setIdestatuscontable(estatuscontableEJB.estatusContablePorRegistrar());
            maestromovi.setIdempresa(requerimientosController.getEmpresa().getIdempresa());
            maestromovimientoEJB.create(maestromovi);

            movimientobancario.setFecha(ingreso.getFechaingreso());
            movimientobancario.setIdcuentabancaria(cuentabancaria);
            movimientobancario.setSaldoanterior(saldoanteriorbanco);
            movimientobancario.setDebito(0.0);
            movimientobancario.setCredito(ingreso.getMontoingresado());
            movimientobancario.setSaldoactual(saldoactualbanco);
            movimientobancario.setIdotroingreso(ingreso);
            movimientobancario.setConciliado(Boolean.FALSE);
            movimientoBancarioEJB.create(movimientobancario);
            if (visual == 1) {
                movimientobancario.setFecha(ingreso.getFechaingreso());
                movimientobancario.setIdcuentabancaria(cuentaemisora);
                movimientobancario.setSaldoanterior(saldoanterioremisor);
                movimientobancario.setDebito(ingreso.getMontoingresado());
                movimientobancario.setCredito(0.0);
                movimientobancario.setSaldoactual(saldoactualbancoemisor);
                movimientobancario.setIdotroingreso(ingreso);
                movimientobancario.setConciliado(Boolean.FALSE);
                movimientoBancarioEJB.create(movimientobancario);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Ingreso fue Almacenado", "Su Ingreso fue Almacenado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Ingreso", "Error al Grabar Ingreso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void selecciontipoingreso() {
        if (ingreso.getIdtipoingreso().getIdtipoingreso() == 7) {
            visual = 1;
        } else {
            visual = 0;
        }

    }

    public List<Cuentabancaria> refrescarCuentasBancarias() {
        try {
            lstCuentasSelecc = cuentabancariaEJB.espxBanco(banco.getIdbanco(), requerimientosController.getEmpresa());
        } catch (Exception e) {
        }
        cobro.setIdcuentabancaria(lstCuentasSelecc.get(0));
        return lstCuentasSelecc;
    }

    public List<Cuentabancaria> refrescarCuentasBancariasemisoras() {
        try {
            lstCuentasSeleccemisor = cuentabancariaEJB.espxBanco(bancoemisor.getIdbanco(), requerimientosController.getEmpresa());
        } catch (Exception e) {
        }
        cuentaemisora = (lstCuentasSeleccemisor.get(0));
        return lstCuentasSeleccemisor;
    }
    
        public void verOrdendePago(Otroingreso item) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigootroingreso = item.getIdotroingreso();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/comprobanteingreso.jasper");

        rArticulo.getComprobanteIngreso(ruta, codigootroingreso);
        FacesContext.getCurrentInstance().responseComplete();
    }

}
