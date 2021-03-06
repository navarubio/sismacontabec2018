package Jsf;

import Modelo.Conciliacion;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ConciliacionFacade;
import Jpa.ConciliacionFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.MovimientobancarioFacadeLocal;
import Modelo.Banco;
import Modelo.Cuentabancaria;
import Modelo.Detallelibrodiario;
import Modelo.Empresa;
import Modelo.Movimientobancario;
import Modelo.Usuario;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

@Named("conciliacionController")
@ViewScoped
public class ConciliacionController implements Serializable {

    @EJB
    private Jpa.ConciliacionFacadeLocal ejbFacade;
    @EJB
    private MovimientobancarioFacadeLocal movimientobancarioEJB;
    @EJB
    private EmpresaFacadeLocal empresaEJB;

    @Inject
    private Conciliacion conciliacionbancaria;
    @Inject
    private RequerimientosController requerimientosController;
    @Inject
    private ConsumoscajachicaController consumoscajachicaController;
    @Inject
    private Empresa empresa;
    @Inject
    private LectorexcelController lector;
    @Inject
    private UploadController uploadController;

    private List<Conciliacion> items = null;
    private List<Movimientobancario> movimientosseleccionados = null;
//    private List<Movimientobancario> movimientosmasivos=null;
    private List<Movimientobancario> movimientosmasivos = new ArrayList();

    private Conciliacion selected;
    private Movimientobancario seleccion;
    private Movimientobancario movimientoseleccionado;
    private List<Movimientobancario> movimientosconciliados;

    private Date fecharegistro;
    private Banco bank;
    private Cuentabancaria cuantabank;
    private Date fechaconcilia;
    private Double saldoedocta = 0.0;
    private double saldocontable = 0.0;
    private double notacredito = 0.0;
    private double notadebito = 0.0;
    private double saldocontabajustado = 0.0;
    private double depositos = 0.0;
    private double retiros = 0.0;
    private double saldoedoctaajustado = 0.0;
    private double ajustecontable = 0.0;
    private double ajusteedocta = 0.0;
    private int saldovar;
    private int listavar;
    private int control = 0;
    private int tamaño = 0;
    private int datogenerico = 0;

    private double depositochange = 0.0;
    private double retiroschange = 0.0;

    public ConciliacionController() {
    }

    @PostConstruct
    public void init() {
        saldovar = 0;
        listavar = 0;

    }

    public Movimientobancario getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Movimientobancario seleccion) {
        this.seleccion = seleccion;
        Consultar();
    }

    public Conciliacion getSelected() {
        Consultar();
        return selected;

    }

    public void setSelected(Conciliacion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ConciliacionFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Movimientobancario> getMovimientosseleccionados() {
        return movimientosseleccionados;
    }

    public void setMovimientosseleccionados(List<Movimientobancario> movimientosseleccionados) {
        this.movimientosseleccionados = movimientosseleccionados;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Banco getBank() {
        return bank;
    }

    public void setBank(Banco bank) {
        this.bank = bank;
    }

    public Cuentabancaria getCuantabank() {
        return cuantabank;
    }

    public void setCuantabank(Cuentabancaria cuantabank) {
        this.cuantabank = cuantabank;
    }

    public Date getFechaconcilia() {
        return fechaconcilia;
    }

    public void setFechaconcilia(Date fechaconcilia) {
        this.fechaconcilia = fechaconcilia;
    }

    public Double getSaldoedocta() {
        return saldoedocta;
    }

    public void setSaldoedocta(Double saldoedocta) {
        this.saldoedocta = saldoedocta;
    }

    public double getSaldocontable() {
        return saldocontable;
    }

    public void setSaldocontable(double saldocontable) {
        this.saldocontable = saldocontable;
    }

    public double getNotacredito() {
        return notacredito;
    }

    public void setNotacredito(double notacredito) {
        this.notacredito = notacredito;
    }

    public double getNotadebito() {
        return notadebito;
    }

    public void setNotadebito(double notadebito) {
        this.notadebito = notadebito;
    }

    public double getSaldocontabajustado() {
        return saldocontabajustado;
    }

    public void setSaldocontabajustado(double saldocontabajustado) {
        this.saldocontabajustado = saldocontabajustado;
    }

    public double getDepositos() {
        return depositos;
    }

    public void setDepositos(double depositos) {
        this.depositos = depositos;
    }

    public double getRetiros() {
        return retiros;
    }

    public void setRetiros(double retiros) {
        this.retiros = retiros;
    }

    public double getSaldoedoctaajustado() {
        return saldoedoctaajustado;
    }

    public void setSaldoedoctaajustado(double saldoedoctaajustado) {
        this.saldoedoctaajustado = saldoedoctaajustado;
    }

    public double getAjustecontable() {
        return ajustecontable;
    }

    public void setAjustecontable(double ajustecontable) {
        this.ajustecontable = ajustecontable;
    }

    public double getAjusteedocta() {
        return ajusteedocta;
    }

    public void setAjusteedocta(double ajusteedocta) {
        this.ajusteedocta = ajusteedocta;
    }

    public Conciliacion getConciliacionbancaria() {
        return conciliacionbancaria;
    }

    public void setConciliacionbancaria(Conciliacion conciliacionbancaria) {
        this.conciliacionbancaria = conciliacionbancaria;
    }

    public RequerimientosController getRequerimientosController() {
        return requerimientosController;
    }

    public void setRequerimientosController(RequerimientosController requerimientosController) {
        this.requerimientosController = requerimientosController;
    }

    public ConsumoscajachicaController getConsumoscajachicaController() {
        return consumoscajachicaController;
    }

    public void setConsumoscajachicaController(ConsumoscajachicaController consumoscajachicaController) {
        this.consumoscajachicaController = consumoscajachicaController;
    }

    public int getSaldovar() {
        return saldovar;
    }

    public void setSaldovar(int saldovar) {
        this.saldovar = saldovar;
    }

    public int getListavar() {
        return listavar;
    }

    public void setListavar(int listavar) {
        this.listavar = listavar;
    }

    public List<Movimientobancario> getMovimientosmasivos() {
        return movimientosmasivos;
    }

    public void setMovimientosmasivos(List<Movimientobancario> movimientosmasivos) {
        this.movimientosmasivos = movimientosmasivos;
    }

    public UploadController getUploadController() {
        return uploadController;
    }

    public void setUploadController(UploadController uploadController) {
        this.uploadController = uploadController;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getDatogenerico() {
        return datogenerico;
    }

    public void setDatogenerico(int datogenerico) {
        this.datogenerico = datogenerico;
    }

    public List<Movimientobancario> getMovimientosconciliados() {
        return movimientosconciliados;
    }

    public void setMovimientosconciliados(List<Movimientobancario> movimientosconciliados) {
        this.movimientosconciliados = movimientosconciliados;
    }

    public List<Movimientobancario> buscarMovimientosConciliados() {
        if (selected != null) {
            movimientosconciliados = movimientobancarioEJB.movimientosConciliados(selected);
        }
        return movimientosconciliados;
    }

    public void Consultar() {
        if (selected != null) {
            movimientosconciliados = movimientobancarioEJB.movimientosConciliados(selected);
        }
    }

    public Conciliacion prepareCreate() {
        selected = new Conciliacion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundleconciliacion").getString("ConciliacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundleconciliacion").getString("ConciliacionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundleconciliacion").getString("ConciliacionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Conciliacion> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundleconciliacion").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundleconciliacion").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Conciliacion getConciliacion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Conciliacion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Conciliacion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Conciliacion.class)
    public static class ConciliacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConciliacionController controller = (ConciliacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "conciliacionController");
            return controller.getConciliacion(getKey(value));
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
            if (object instanceof Conciliacion) {
                Conciliacion o = (Conciliacion) object;
                return getStringKey(o.getIdconciliacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Conciliacion.class.getName()});
                return null;
            }
        }

    }

    public void buscarDatos() {

        movimientosseleccionados = movimientobancarioEJB.buscarmovimientosConciliacion(conciliacionbancaria.getIdcuentabancaria(), conciliacionbancaria.getFechaconciliacion());
        try {

            if (!movimientosseleccionados.isEmpty()) {
                saldovar = 1;
                listavar = 1;
                control = 0;
                tamaño = movimientosseleccionados.size();
                saldoedocta = conciliacionbancaria.getSaldoedocuenta();
                retiros = requerimientosController.redondearDecimales(TotalDebitos());
                depositos = requerimientosController.redondearDecimales(TotalCreditos());
                saldoedoctaajustado = requerimientosController.redondearDecimales(saldoedocta + depositos - retiros);
                saldocontable = requerimientosController.redondearDecimales(CalcularSaldocontable());
                saldocontabajustado = saldocontable + notacredito - notadebito;
                ajustecontable = saldocontabajustado - saldoedoctaajustado;
                ajusteedocta = saldoedoctaajustado - saldocontabajustado;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "La busqueda se realizo correctamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "No existenc movimientos cargados para esta Conciliación"));
            }
        } catch (Exception e) {

        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void cargarMasiva() {
        if (!movimientosmasivos.isEmpty()) {
            movimientosmasivos.clear();
        }
        String archivo = uploadController.getArchivo();
        lector.leerArchivoExcel(archivo);
        movimientosmasivos = lector.getMovimientosNuevos();
        saldovar = 2;
        conciliarautomatico();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "La carga se realizo correctamente"));
    }

    public void conciliarautomatico() {
        for (Movimientobancario movBanc : movimientosmasivos) {
            for (Movimientobancario movContab : movimientosseleccionados) {
                if (movBanc.getFecha().equals(movContab.getFecha()) || movBanc.getFecha().after(movContab.getFecha())) {
                    if (movBanc.getFecha().equals(movContab.getFecha())) {
                        if (movContab.getDebito() == null) {
                            movContab.setDebito(0.0);
                        }
                        if (movContab.getCredito() == null) {
                            movContab.setCredito(0.0);
                        }
                        if (movBanc.getDebito().equals(movContab.getDebito()) && movBanc.getCredito().equals(movContab.getCredito())) {
                            movBanc.setConciliado(Boolean.TRUE);
                            if (movContab.getConciliado() == false) {
                                actualizarsaldos(movContab);
                            }
                        }
                    }
                }
            }

        }
    }

    public void actualizarsaldos(Movimientobancario movi) {
        this.movimientoseleccionado = movi;
        if (movimientoseleccionado.getConciliado() == false) {
            if (movi.getCredito() > 0) {
                depositochange = movimientoseleccionado.getCredito();
                depositos = depositos - depositochange;

            } else {
                retiroschange = movimientoseleccionado.getDebito();
                retiros = retiros - retiroschange;
            }
            saldoedoctaajustado = requerimientosController.redondearDecimales(saldoedocta + depositos - retiros);
            saldocontable = requerimientosController.redondearDecimales(CalcularSaldocontable());
            ajustecontable = saldocontabajustado - saldoedoctaajustado;
            ajusteedocta = saldoedoctaajustado - saldocontabajustado;
            movi.setConciliado(Boolean.TRUE);
            tamaño = tamaño - 1;

        } else {
            if (movi.getCredito() > 0) {
                depositochange = movimientoseleccionado.getCredito();
                depositos = depositos + depositochange;

            } else {
                retiroschange = movimientoseleccionado.getDebito();
                retiros = retiros + retiroschange;
            }
            saldoedoctaajustado = requerimientosController.redondearDecimales(saldoedocta + depositos - retiros);
            saldocontable = requerimientosController.redondearDecimales(CalcularSaldocontable());
            ajustecontable = saldocontabajustado - saldoedoctaajustado;
            ajusteedocta = saldoedoctaajustado - saldocontabajustado;
            movi.setConciliado(Boolean.FALSE);
            tamaño = tamaño + 1;
        }
    }

    public void excluirmovimiento(Movimientobancario movi) {
        saldoedoctaajustado = 0;
        saldocontabajustado = 0;
        retiroschange = 0;
        depositochange = 0;
        int indc = movimientosseleccionados.indexOf(movi);
        if (movi.getCredito() == 0) {
            retiroschange = movi.getDebito();
            retiros = retiros - retiroschange;
            saldocontable = saldocontable + retiroschange;

        } else {
            depositochange = movi.getCredito();
            depositos = depositos - depositochange;
            saldocontable = saldocontable - depositochange;
        }
        tamaño = tamaño - 1;
        saldoedoctaajustado = requerimientosController.redondearDecimales(saldoedocta + depositos - retiros);
        saldocontable = requerimientosController.redondearDecimales(saldocontable);
        saldocontabajustado = saldocontable + notacredito - notadebito;
        ajustecontable = saldocontabajustado - saldoedoctaajustado;
        ajusteedocta = saldoedoctaajustado - saldocontabajustado;
        movimientosseleccionados.remove(indc);

    }

    public Double TotalDebitos() {
        double total = 0;

        for (Movimientobancario debe : movimientosseleccionados) {
            if (debe.getDebito() != null) {
                total += debe.getDebito();
            }
        }
        return total;
    }

    public Double TotalCreditos() {
        double total = 0;

        for (Movimientobancario haber : movimientosseleccionados) {
            if (haber.getCredito() != null) {
                total += haber.getCredito();
            }
        }
        return total;
    }

    public Double CalcularSaldocontable() {
        double total = 0.0;
        int marcador = movimientosseleccionados.size() - 1;
        Movimientobancario mov;
        mov = movimientosseleccionados.get(marcador);
        total = mov.getSaldoactual();
        return total;
    }

    public void registraconciliacion() {
        try {
            Usuario usua = requerimientosController.getUsa();
            conciliacionbancaria.setIdusuario(usua);
            int serial = usua.getIddepartamento().getIdempresa().getSerialconciliacion() + 1;
            conciliacionbancaria.setSerialconciliacion(serial);
            conciliacionbancaria.setDebitoscontables(requerimientosController.redondearDecimales(retiros));
            conciliacionbancaria.setCreditoscontables(requerimientosController.redondearDecimales(depositos));
            conciliacionbancaria.setSaldocontable(saldocontable);
            conciliacionbancaria.setSaldocontableajustado(saldocontabajustado);
            conciliacionbancaria.setSaldobancarioajustado(saldoedoctaajustado);
            ejbFacade.create(conciliacionbancaria);

            empresa = usua.getIddepartamento().getIdempresa();
            empresa.setSerialconciliacion(serial);
            empresaEJB.edit(empresa);

            Conciliacion ultimaconciliacion = ejbFacade.ultimaConciliacionInsertada();

            for (Movimientobancario mov : movimientosseleccionados) {
                Movimientobancario movimientoactualizado;
                movimientoactualizado = mov;
                movimientoactualizado.setIdconciliacion(ultimaconciliacion);
                movimientobancarioEJB.edit(movimientoactualizado);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Conciliación se registro exitosamente", "Aviso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar la Conciliación", "Aviso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

}
