package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * DTO de logs.
 *
 */
public class LogDTO extends BaseDTO implements Serializable {

   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;

   /**
    * Id de log
    */
   private String is_id;

   /**
    * Detalle del log
    */
   private String is_detalle;

   /**
    * Evento del log
    */
   private String is_evento;

   /**
    * Entidad registrada
    */
   private String is_idEntidad;

   /**
    * Id de usuario
    */
   private String is_idUsuario;

   /**
    * Retorna el id del log
    * @return id del log
    */
   @XmlTransient
   public String getId() {
      return is_id;
   }

   /**
    * Establece el id del log
    * @param as_id id del log
    */
   public void setId(String as_id) {
      this.is_id = as_id;
   }

   /**
    * Retorna el detalle del log
    * @return detalle del log
    */
   @XmlElement(required = true)
   public String getDetalle() {
      return is_detalle;
   }

   /**
    * Establece el detalle del log
    * @param as_detalle detalle del log
    */
   public void setDetalle(String as_detalle) {
      this.is_detalle = as_detalle;
   }

   /**
    * Retorna el evento del log
    * @return evento del log
    */
   @XmlElement(required = true)
   public String getEvento() {
      return is_evento;
   }

   /**
    * Establece el evento del log
    * @param as_evento evento del log
    */
   public void setEvento(String as_evento) {
      this.is_evento = as_evento;
   }

   /**
    * Retorna la entidad del registro
    * @return entidad del registro
    */
   @XmlElement(required = true)
   public String getIdEntidad() {
      return is_idEntidad;
   }

   /**
    * Establece la entidad del registro
    * @param as_idEntidad entidad del registro
    */
   public void setIdEntidad(String as_idEntidad) {
      this.is_idEntidad = as_idEntidad;
   }

   /**
    * Retorna el id de usuario
    * @return id de usuario
    */
   @XmlElement(required = true)
   public String getIdUsuario() {
      return is_idUsuario;
   }

   /**
    * Establece el id de usuario
    * @param as_idUsuario id de usuario
    */
   public void setIdUsuario(String as_idUsuario) {
      is_idUsuario = as_idUsuario;
   }
}