package co.gov.supernotariado.bachue.dispositivos.persistence.dto;


import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlTransient;

import weblogic.logging.LoggingHelper;

import java.sql.Timestamp;
import java.util.Date;

/**
 * DTO de base que contiene las propiedades comunes de los DTOs.
 */
public class BaseDTO {
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   /**
    * IP
    */
   protected String is_ip;

   /**
    * Fecha y hora
    */
   protected Timestamp it_time;

   /**
    * Retorna la IP
    * @return IP
    */
   @XmlTransient
   public String getIp() {
      return is_ip;
   }

   /**
    * Establece la IP
    * @param as_ip IP
    */
   public void setIp(String as_ip) {
      this.is_ip = as_ip;
   }

   /**
    * Retorna la fecha y hora
    * @return fecha y hora
    */
   @XmlTransient
   public Timestamp getTime() {
      return it_time;
   }

   /**
    * Establece la fecha y hora
    * @param at_time fecha y hora
    */
   public void setTime(Timestamp at_time) {
      this.it_time = at_time;
   }

  /**
   * Método que agrega los campos de auditoria que son obtenibles desde el request.
   * @param ahsr_req Request con la informacion HTTP de la peticion recibida.
   */
   public void agregarValoresAuditoria(HttpServletRequest ahsr_req) {
      String ls_ipConexion = ahsr_req.getRemoteAddr();
      String ls_forwarded_header = ahsr_req.getHeader("x-forwarded-for");
      if (ls_forwarded_header != null) {
         ll_logger.info("Cabecera forwarded con información de la IP real del cliente");
         String[] lsa_forwarded = ls_forwarded_header.trim().split(" "); 
         if (lsa_forwarded.length > 0 && lsa_forwarded[0].trim().length() > 0) {
            ls_ipConexion = lsa_forwarded[0];
         }
      } else {
         ll_logger.info("SIN cabecera forwarded se confía en la IP de conexión");
      }
         
      this.setIp(ls_ipConexion);
      this.setTime(new Timestamp(new Date().getTime()));
   }
}
