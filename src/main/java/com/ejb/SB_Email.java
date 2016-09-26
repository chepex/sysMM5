/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mmixco
 */
@Stateless
public class SB_Email {

 public String enviar(String TO, String Asunto, String Mensaje) throws MessagingException{
        
        
     
        
      //Mensaje = template1(Mensaje);
        Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, null);
        Transport transport = session.getTransport("smtp");
        transport.connect("informatica@caricia.com", "caricia2016");
        Message message = new MimeMessage(session);
        message.setSubject(Asunto);
        
        message.setContent(Mensaje, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress("informatica@caricia.com"));
        System.out.println("para "+ TO);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         message.setRecipient(Message.RecipientType.CC, new InternetAddress("informatica@caricia.com"));
        transport.sendMessage(message, message.getAllRecipients());         
        
        return "";
    }
}
