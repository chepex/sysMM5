
package com.entities.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
//@author  Henry Joe Wong Urquiza
public class ManejadorFechas {

    //Metodo usado para obtener la fecha actual
    //@return Retorna un <b>STRING</b> con la fecha actual formato "dd/MM/yyyy"
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(ahora);
    }
    

    //Metodo usado para obtener la hora actual del sistema
    //@return Retorna un <b>STRING</b> con la hora actual formato "hh:mm:ss"
    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
        return formateador.format(ahora);
    }

    //Sumarle dias a una fecha determinada
    //@param fch La fecha para sumarle los dias
    //@param dias Numero de dias a agregar
    //@return La fecha agregando los dias
    public static java.sql.Date sumarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }
    
    //Sumarle dias a una fecha determinada
    //@param fch La fecha para sumarle los dias
    //@param dias Numero de dias a agregar
    //@return La fecha agregando los dias
    public static java.sql.Date sumarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    //Restarle dias a una fecha determinada
    //@param fch La fecha
    //@param dias Dias a restar
    //@return La fecha restando los dias
    public static synchronized java.sql.Date restarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }

    //Diferencias entre dos fechas
    //@param fechaInicial La fecha de inicio
    //@param fechaFinal  La fecha de fin
    //@return Retorna el numero de dias entre dos fechas
    public static synchronized int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }

        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static synchronized java.util.Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static synchronized java.util.Date deStringToDateEng(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-dd-mm");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
        //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static synchronized java.util.Date deStringToDateHora(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static synchronized java.util.Date NowDate() {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-dd-mm");
        Date now = new Date();
        Date fechaEnviar = null;
        try {
            String  nowFormateado  = formatoDelTexto.format(now);
            fechaEnviar = formatoDelTexto.parse(nowFormateado);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static java.util.Date DateToString(Date now) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fechaEnviar = null;
        try {
            String  nowFormateado  = formatoDelTexto.format(now);
            fechaEnviar = formatoDelTexto.parse(nowFormateado);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }    
    
    

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy
    //@param La fecha a convertir a formato date
    //@return Retorna la fecha en formato Date
    public static String DateToString2(Date now) {
  
       
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String reportDate = df.format(now);
        
       
        return  reportDate;
    }     
 
   /**
    * Muestra el mes en letra
    * @param Mes representa el mes en numero
    * @return devuelve el mes en letras.
    */ 
   public static String mesEnLetras(int Mes) {
       String result = "";
       switch (Mes) {
           case 1: {
               result = "Enero";
               break;
           }
           case 2: {
               result = "Febrero";
               break;
           }
           case 3: {
               result = "Marzo";
               break;
           }
           case 4: {
               result = "Abril";
               break;
           }
           case 5: {
               result = "Mayo";
               break;
           }
           case 6: {
               result = "Junio";
               break;
           }
           case 7: {
               result = "Julio";
               break;
           }
           case 8: {
               result = "Agosto";
               break;
           }
           case 9: {
               result = "Septiembre";
               break;
           }
           case 10: {
               result = "Octubre";
               break;
           }
           case 11: {
               result = "Noviembre";
               break;
           }
           case 12: {
               result = "Diciembre";
               break;
           }
       }
       return result;
   }

   /**
    * truncate a una fecha eliminandole el tiempo
    * @param date fecha a enviar 
    * @return devuelve una fecha sin el tiempo
    */
   public static Date truncateTime (Date date) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND, 0);
       return cal.getTime();
   }    
 
   
public static synchronized float diferenciasDeFechasSeg(Date fechaInicial, Date fechaFinal) {

    /*    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String fechaInicioString = df.format(fechaInicial);
        try {
            fechaInicial = df.parse(fechaInicioString);
        } catch (ParseException ex) {
        }

        String fechaFinalString = df.format(fechaFinal);
        try {
            fechaFinal = df.parse(fechaFinalString);
        } catch (ParseException ex) {
        }
       
        long fechaInicialMs = fechaInicial.getTime();
        long fechaFinalMs = fechaFinal.getTime();*/
        
       
        long diferencia = fechaFinal.getTime() - fechaInicial.getTime();
       
        float dias = (float)diferencia / (1000 * 60 );
        
        return (float) dias;
    } 


    public static String yearMonth(Date date){
        
        String mes = ""; 
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    month = month+1;
    if(String.valueOf(month).length()==1 ){
    mes = "0"+month;
    }else{
    mes=String.valueOf(month);
    }
      String fecha =  year+""+mes;
    return fecha;
    }
    
    public static String fechaHoraString(Date fecha) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = sdfDate.format(fecha);
        return strDate;
    }


}
