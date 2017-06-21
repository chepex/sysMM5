/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;





 
//import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;



import java.io.IOException;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.ExternalContext;

 
import javax.faces.context.FacesContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 *
 * @author mmixco
 */

@ManagedBean(name="loginBean")
@Stateless
public class LoginBean  implements Serializable {

    private static final long serialVersionUID = 5443351151396868724L;
    private String username;
    private String password;
    public String usuario;
    
    public String theme;
    public String Ocia;
    public String NumOrden;
    public String OTipodoc;
    public String serverIP;
    

   
 
 
   
    
    
    public LoginBean() {
    }

    
    
    public String getServerIP() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String port = String.valueOf(request.getLocalPort());
        serverIP = request.getLocalAddr()+":"+port ;
        System.out.println("direccion--->"+serverIP);
        if(serverIP.equals("127.0.0.1:8080")){    
            serverIP= "localhost:8080";
        }else if(serverIP == null){
            serverIP= "192.168.10.223:8080";
        }
        
        return serverIP;
    }

 
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    
 
    
 
 
  
  public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
   
     public void login(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
            pasarGarbageCollector();
            
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
            
        try {             
	    
           
            
         
             //String user  =  (String) session.getAttribute("SSUSUARIO") ;
            if(request.getUserPrincipal()!=null){
             
             FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
            
           // log(username.toUpperCase(),password.toUpperCase()) ;
            System.out.println("usuario--->"+username);
            System.out.println("pass--->"+password);
 
            request.getSession().setMaxInactiveInterval(900);            
            request.login(username.toUpperCase(), password.toUpperCase());
           
         session.setAttribute("SSUSUARIO", username.toUpperCase() ); 
           
            System.out.println("aqui validado");
          
	  
	 
           
          
	   
            /*if(from != null){
                FacesContext.getCurrentInstance().getExternalContext().redirect(from);                
            }*/
                        
            
/*            System.out.println("cantidad de sesiones"+SessionCounterListener.getTotalActiveSession());
            System.out.println("cantidad de sesiones"+SessionCounterListener.getTotalActiveSession());*/
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

        } catch (Exception e) {
	    
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Su usuario o password no son correctos "+e.getMessage(), null));
         

        }
    }
     
    
     
     
      
 
         public void logout() throws ServletException, IOException{
             String vip= this.getServerIP();
             System.out.println("---->----->---->"+vip);
             System.gc();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        
        
        request.logout();
        //Desarrollo
        //FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/cariciasys-web/");
        //Produccion
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext.getCurrentInstance().getExternalContext().redirect(context.getRequestContextPath() +"/faces/login.xhtml");
       //FacesContext.getCurrentInstance().getExternalContext().redirect("http://192.168.20.136:8080/cariciasys-web");
    }
         
	 
         
 
	  
	 
	  
	  
	  public String ssuser (){	      
	      String user ="";	      
	      try
		{
		  HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
		    if(session != null){		    
			user  =  (String) session.getAttribute("SSUSUARIO") ;
		    }
		}
	     catch(NullPointerException e){ 
		return user ;	
	     }

                     
	    return user ;	
	    
	      
	  }
             
	  
 
      
          
      
    public void pasarGarbageCollector(){
 
        Runtime garbage = Runtime.getRuntime();
        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory() );
        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory() );
 
        garbage.gc();
 
        System.out.println("Memoria total: "+ garbage.totalMemory());
        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory() );
    }       
    
    
    public String  verifyAccessOut() throws IOException{
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        if(request.getUserPrincipal()==null){
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('redirectDialog').show()");
        }
    return "";
    }   
        
          
    
            
}
