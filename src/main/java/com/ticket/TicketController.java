package com.ticket;

import com.entities.Usuario;
import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;
import com.entities.util.ManejadorFechas;

import java.io.Serializable;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("ticketController")
@SessionScoped
public class TicketController implements Serializable {

    @EJB
    private TicketFacade ejbFacade;
    @EJB
    private TicketMensajeFacade ticketMensajeFacade;    
    @EJB
    private TicketEstadoFacade ticketEstadoFacade;        
    @EJB
    private  com.ejb.SB_ticket  sb_ticket;    
    @EJB
    private  com.entities.LoginBean  lb;        
    
    private String vuser;
    private String vicon;
  
    
     
    private List<Ticket> items = null;
    private List<TicketMensaje> lmensaje = new ArrayList<TicketMensaje>();
    private Ticket selected;
    private String msg;
    private String estado;
    private Usuario solicitante;
    private Date fi;
    private Date ff;
    private long creados;
    private long finalizados;
    private long asignados;
    private List<Ticket> lticketUsuario = new ArrayList<Ticket>();
    private List<Ticket> lticketDepto = new ArrayList<Ticket>();
    private List<Ticket> lticketTecnico = new ArrayList<Ticket>();
    
    public TicketController() {
    }

    public String getVuser() {
        return vuser;
    }

    public void setVuser(String vuser) {
        this.vuser = vuser;
    }

    public String getVicon() {
        if(vuser.equals("Usuario")){
            vicon = " fa fa-user White";
        }else{
            vicon = " fa fa-users White";
        }
        return vicon;
    }

    public void setVicon(String vicon) {
        this.vicon = vicon;
    }

  
    
   
    
    
    

    public List<Ticket> getLticketTecnico() {
        return lticketTecnico;
    }

    public void setLticketTecnico(List<Ticket> lticketTecnico) {
        this.lticketTecnico = lticketTecnico;
    }

    
    
    public List<Ticket> getLticketUsuario() {
        return lticketUsuario;
    }

    public void setLticketUsuario(List<Ticket> lticketUsuario) {
        this.lticketUsuario = lticketUsuario;
    }

    public List<Ticket> getLticketDepto() {
        return lticketDepto;
    }

    public void setLticketDepto(List<Ticket> lticketDepto) {
        this.lticketDepto = lticketDepto;
    }
    
    public long getFinalizados() {
        return finalizados;
    }

    public void setFinalizados(long finalizados) {
        this.finalizados = finalizados;
    }

    public long getAsignados() {
        return asignados;
    }

    public void setAsignados(long asignados) {
        this.asignados = asignados;
    }

    
    
    public long getCreados() {
        return creados;
    }

    public void setCreados(long creados) {
        this.creados = creados;
    }

    
    
    public Date getFi() {
        return fi;
    }

    public void setFi(Date fi) {
        this.fi = fi;
    }

    public Date getFf() {
        return ff;
    }

    public void setFf(Date ff) {
        this.ff = ff;
    }

    
    
    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    public List<TicketMensaje> getLmensaje() {
        return lmensaje;
    }

    public void setLmensaje(List<TicketMensaje> lmensaje) {
        this.lmensaje = lmensaje;
    }

    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    

    public Ticket getSelected() {
       
        return selected;
    }

    public void setSelected(Ticket selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TicketFacade getFacade() {
        return ejbFacade;
    }

    public Ticket prepareCreate() {
        this.msg= null;
        selected = new Ticket();
        initializeEmbeddableKey();
        return selected;
    }

    
    
    @PostConstruct
    public void init() {              
         llenarListas();   
          vuser= "Usuario";
    }
    
    
    public void addMensaje(){
      
        String msg = sb_ticket.addMesage( selected ,   this.msg, lb.getUser());
        
        if(msg.equals("OK")){
            JsfUtil.addSuccessMessage("Mensaje agregado correctamente");
        }else{
            JsfUtil.addErrorMessage("Surgio un error al intentar agregar el mensaje");
        }
        
        llenarListas();
        this.msg= null;
    }
    
    public void asignar(){
        
        try{
            
            this.msg = "Responsable asignado:"+selected.getIdusuarioAsignado().getUsuario()+"         "+ this.msg;
            String msg = sb_ticket.addMesage( selected ,   this.msg, selected.getIdusuarioAsignado());         
            TicketEstado te =ticketEstadoFacade.find(2);
            selected.setIdestado(te);
            selected.setFechaAsignado(new Date());
            this.ejbFacade.edit(selected);
            JsfUtil.addSuccessMessage("responsable asignado");         
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Surgio un error ");
        }
        
        llenarListas();
        this.msg= null;
    
    }
    
    
    public void finalizar(){        
        
        TicketEstado te =ticketEstadoFacade.find(3);
        this.msg = this.msg+"Ticket cerrado";
        String msg = sb_ticket.addMesage( selected ,   this.msg, selected.getIdusuarioAsignado());         
        selected.setIdestado(te);            
        selected.setFechaFinalizado(new Date());
        this.ejbFacade.edit(selected);
        JsfUtil.addSuccessMessage("Ticket cerrado");         
        llenarListas();
        this.msg= null;
    
    }
    
          
    
    public void create() {
         
       
     
        TicketEstado te=  new TicketEstado(1);
        
        selected.setFechaCreacion(new Date());
        selected.setIdestado(te);
        selected.setIdusuario(lb.getUser());
        
       Long b = this.ejbFacade.correlativo( );
       
      
        selected.setIdticket(b.intValue()+1);
      
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TicketCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        sb_ticket.addMesage( selected ,   msg, lb.getUser());
        this.msg= null;
        llenarListas();
                
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TicketUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TicketDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Ticket> getItems() {
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

    public Ticket getTicket(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Ticket> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Ticket> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Ticket.class)
    public static class TicketControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TicketController controller = (TicketController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ticketController");
            return controller.getTicket(getKey(value));
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
            if (object instanceof Ticket) {
                Ticket o = (Ticket) object;
                return getStringKey(o.getIdticket());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Ticket.class.getName()});
                return null;
            }
        }

    }
    
    
    
    
    public void llenarListas(){
        
        
        System.out.println("vuser-->"+vuser);
        System.out.println("user-->"+lb.getUser());
        
       // lticketDepto = this.ejbFacade.findByDepto(lb.getUser().getIddepto());
        
        creados= this.ejbFacade.totalCreados();
        finalizados= this.ejbFacade.totalFinalizados();
        asignados= this.ejbFacade.totalAsignados();
        if(vuser==null){
            vuser="Usuario";        
        }
        if(vuser.equals("Todos")){
            lticketUsuario = this.ejbFacade.findByUsuarioDepto(lb.getUser());
            lticketTecnico =this.ejbFacade.findByTecnicoDepto(lb.getUser());   
            
            System.out.println("depto List-->"+lticketTecnico);
        }else{
            lticketUsuario = this.ejbFacade.findByUsuario(lb.getUser());
            lticketTecnico =this.ejbFacade.findByTecnico(lb.getUser());            
            System.out.println("tecnico List-->"+lticketTecnico);
        }
         
        if (selected!=null){
         this.lmensaje =  ticketMensajeFacade.findByTIcket(selected);
        }
        
    }
    
    
   
    
    public String stringfecha( Date d){
        return ManejadorFechas.DateToString2(d);
        
    }
    
    public void cambiarFiltro(){        
       if(vuser.equals("Todos")){
            vuser= "Usuario";
        }else{
            vuser= "Todos";
        }
        
        llenarListas();
    }
    
    

}
