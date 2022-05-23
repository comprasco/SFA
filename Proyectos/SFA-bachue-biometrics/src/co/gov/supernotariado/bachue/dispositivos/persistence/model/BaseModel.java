package co.gov.supernotariado.bachue.dispositivos.persistence.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * Modelo de base con los campos de auditoria.
 *
 */
@MappedSuperclass
public class BaseModel {
   /**
    * Fecha de creación
    */
   @Column(name = "FECHA_CREACION", length = 6, nullable = false)
   protected Timestamp fechaCreacion;

   /**
    * Fecha de modificación
    */
   @Column(name = "FECHA_MODIFICACION", length = 6)
   protected Timestamp fechaModificacion;

   /**
    * IP de creación
    */
   @Column(name = "IP_CREACION", length = 100, nullable = false)
   protected String ipCreacion;

   /**
    * IP de modificación
    */
   @Column(name = "IP_MODIFICACION", length = 100)
   protected String ipModificacion;

   /**
    * Id de usuario de creación
    */
   @Column(name = "ID_USUARIO_CREACION", length = 30, nullable = false)
   protected String idUsuarioCreacion;

   /**
    * Id de usuario de modificación
    */
   @Column(name = "ID_USUARIO_MODIFICACION", length = 30)
   protected String idUsuarioModificacion;

   /**
    * Retorna la fecha de creación
    * @return fecha de creación
    */
   public Timestamp getFechaCreacion() {
      return fechaCreacion;
   }

   /**
    * Establece la fecha de creación
    * @param fechaCreacion fecha de creación
    */
   public void setFechaCreacion(Timestamp fechaCreacion) {
      this.fechaCreacion = fechaCreacion;
   }

   
   /**
    * Retorna la fecha de modificación
    * @return fecha de modificación
    */
   public Timestamp getFechaModificacion() {
      return fechaModificacion;
   }

   /**
    * Establece la fecha de modificación
    * @param fechaModificacion fecha de modificación
    */
   public void setFechaModificacion(Timestamp fechaModificacion) {
      this.fechaModificacion = fechaModificacion;
   }

   /**
    * Retorna la IP de creación
    * @return IP de creación
    */
   public String getIpCreacion() {
      return ipCreacion;
   }

   /**
    * Establece la IP de creación
    * @param ipCreacion IP de creación
    */
   public void setIpCreacion(String ipCreacion) {
      this.ipCreacion = ipCreacion;
   }

   /**
    * Retorna la IP de modificación
    * @return IP de modificación
    */
   public String getIpModificacion() {
      return ipModificacion;
   }

   /**
    * Establece la IP de modificación
    * @param ipModificacion IP de modificación
    */
   public void setIpModificacion(String ipModificacion) {
      this.ipModificacion = ipModificacion;
   }

   /**
    * Retorna el id de usuario de creación
    * @return id de usuario de creación
    */
   public String getIdUsuarioCreacion() {
      return idUsuarioCreacion;
   }

   /**
    * Estable el id de usuario de creación
    * @param idUsuarioCreacion id de usuario de creación
    */
   public void setIdUsuarioCreacion(String idUsuarioCreacion) {
      this.idUsuarioCreacion = idUsuarioCreacion;
   }

   /**
    * Retorna el id de usuario de modificación
    * @return id de usuario de modificación
    */
   public String getIdUsuarioModificacion() {
      return idUsuarioModificacion;
   }

   /**
    * Establece el id de usuario de modificación
    * @param idUsuarioModificacion id de usuario de modificación
    */
   public void setIdUsuarioModificacion(String idUsuarioModificacion) {
      this.idUsuarioModificacion = idUsuarioModificacion;
   }
}
