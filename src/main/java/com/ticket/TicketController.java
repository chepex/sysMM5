package com.ticket;

import com.entities.Usuario;
import com.entities.util.JsfUtil;
import com.entities.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

@Named("ticketController")
@SessionScoped
public class TicketController implements Serializable {

    @EJB
    private TicketFacade ejbFacade;
    @EJB
    private TicketMensajeFacade ticketMensajeFacade;    
    @EJB
    private  com.ejb.SB_ticket  sb_ticket;    
    private List<Ticket> items = null;
    private List<TicketMensaje> lmensaje = new ArrayList<TicketMensaje>();
    private Ticket selected;
    private String msg;

    public TicketController() {
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
        System.out.println("select-->"+selected);
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

    
    public void addMensaje(){
        Usuario us = new Usuario ();
        us.setUsuario("root");
        us.setIdusuario(1);
        String msg = sb_ticket.addMesage( selected ,   this.msg, us);
        
        if(msg.equals("OK")){
            JsfUtil.addSuccessMessage("Mensaje agregado correctamente");
        }else{
            JsfUtil.addErrorMessage("Surgio un error al intentar agregar el mensaje");
        }
        
        llenarMensajes();
        this.msg= null;
    }
    
    public void asignar(){
        
        try{
            
            this.msg = "Responsable asignado:"+selected.getIdusuarioAsignado().getUsuario()+"         "+ this.msg;
            String msg = sb_ticket.addMesage( selected ,   this.msg, selected.getIdusuarioAsignado());         
            selected.setIdestado(new TicketEstado(2));
            selected.setFechaAsignado(new Date());
            this.ejbFacade.edit(selected);
            JsfUtil.addSuccessMessage("responsable asignado");         
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Surgio un error ");
        }
        
        llenarMensajes();
        this.msg= null;
    
    }
    
    
       public void finalizar(){
        
        try{
            
            this.msg = "Ticket cerrado";
            String msg = sb_ticket.addMesage( selected ,   this.msg, selected.getIdusuarioAsignado());         
            selected.setIdestado(new TicketEstado(3));
            selected.setFechaFinalizado(new Date());
            this.ejbFacade.edit(selected);
            JsfUtil.addSuccessMessage("Ticket cerrado");         
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Surgio un error ");
        }
        
        llenarMensajes();
        this.msg= null;
    
    }
    
    
    public void create() {
         
       
          Usuario us = new Usuario ();
        us.setUsuario("root");
        us.setIdusuario(1);
        TicketEstado te=  new TicketEstado(1);
        
        selected.setFechaCreacion(new Date());
        selected.setIdestado(te);
        selected.setIdusuario(us);
        
       Long b = this.ejbFacade.correlativo( );
       
        System.out.println(" corelativo: "+b);
        selected.setIdticket(b.intValue()+1);
        System.out.println(" selected: "+selected);
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TicketCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        
        sb_ticket.addMesage( selected ,   msg, us);
        this.msg= null;
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
    
    
    public void llenarMensajes(){
        System.out.println("-->aqui");
        System.out.println("-->aqui");
        System.out.println("-->aqui");
       this.lmensaje =  ticketMensajeFacade.findByTIcket(selected);
       if(!lmensaje.isEmpty()){
        System.out.println("lista-->"+lmensaje);
       }else{
       
       System.out.println(" vacia-->");
        System.out.println(" vacia-->");
        System.out.println(" vacia-->");
       }
        
    
    }

}
