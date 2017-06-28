/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */







 


import com.ejb.SB_Cliente;
import com.ejb.SB_Usuario;
import com.entities.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
 
import java.util.List;

import javax.ejb.EJB; 
 

import javax.faces.context.FacesContext;

import javax.inject.Named;
 

 
import javax.servlet.http.HttpServletRequest;
import javax.ejb.Stateless; 
import javax.servlet.http.HttpSession;
/**
 *
 * @author mmixco
 */




/**
 *
 * @author mmixco
 */
@Named("loginBean")
@Stateless
public class LoginBean  implements Serializable {
    private static final long serialVersionUID = 5443351151396868724L;
    private String username;
    private String password;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private SB_Usuario sb_Usuario;
    
    
    private String rol ;
    private String token;
    private Usuario user;
    
    public LoginBean() {
    }

    public Usuario getUser() {  
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);          
        
        user =  (Usuario)session.getAttribute("SSUSER"); 
        
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    
     
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
     

    public String getToken() {
        
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
    
   
  public String  login() throws IOException    {
        
        System.out.println("usuario*--->"+username);
        System.out.println("password*--->"+password);
        List<Usuario> lusuario = usuarioFacade.findByLogin2(this.username, this.password);
        System.out.println("lusuario*--->"+lusuario);
        
       
        if(lusuario.isEmpty()){
            
            JsfUtil.addErrorMessage( "Usuario o Password invalido");
            return "error";
            
        }else{
            
                sb_Usuario.setUser(lusuario.get(0));
                
                             
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
                pasarGarbageCollector();
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);          
                request.getSession(true);                
                request.getSession().setMaxInactiveInterval(900);   
                session.setAttribute("SSUSUARIO", username.toUpperCase() );  
                session.setAttribute("SSUSER",lusuario.get(0) );  
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard.xhtml");
        }      
        
        
        
        
        
        
        return "ok";
    }
    
    
    public String  logout() throws IOException   {
         
       // pasarGarbageCollector();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
       if (session != null) {
               session.removeAttribute("SSUSUARIO");
              
     //         FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       
       }
     /*  FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");*/       
       // test.conectado( "0");
        
        return "ok";
    }      
        
    public void pasarGarbageCollector(){
 
        Runtime garbage = Runtime.getRuntime();
        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory() );
        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory() );
 
        garbage.gc();
 
        System.out.println("Memoria total: "+ garbage.totalMemory());
        System.out.println("Memoria libre antes de limpieza: "+ garbage.freeMemory() );
    } 
    
    public boolean validarSesion() throws IOException{
       
        boolean msg = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
       
            String vusuario = (String) session.getAttribute("SSUSUARIO");
            if(vusuario!=null){
             
                 msg=true;
            }
         
        return msg;
    }
    
    
    public String ssuser(){
    
          HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
         String vusuario = (String) session.getAttribute("SSUSUARIO");
         
         return vusuario;
    }
       
    
            
}
