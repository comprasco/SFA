package co.gov.supernotariado.bachue.dispositivos.persistence.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Modelo de persistencia de log de negocio.
 *
 */
@Entity
@Table(name = "SDB_AUD_ACCION")
public class Accion extends BaseModel implements Serializable {
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructor de la clase
    */
   public Accion() {}

   /**
    * Id de log
    */
   @Id()
   @Column(name = "ID_ACCION", length = 20)
   @GeneratedValue
   private String idLog;

   /**
    * Evento registrado
    */
   @Column(name = "EVENTO", nullable = false, length = 50)
   private String evento;

   /**
    * Id de la entidad registrada
    */
   @Column(name = "ID_ENTIDAD", nullable = false,  length = 50)
   private String idEntidad;

   /**
    * Detalle del log
    */
   @Column(name = "DETALLE", nullable = false)
   private String detalle;

   /**
    * Retorna el detalle del log
    * @return detalle del log
    */
   public String getDetalle() {
      return detalle;
   }

   /**
    * Establece el detalle del log
    * @param detalle detalle del log
    */
   public void setDetalle(String detalle) {
      this.detalle = detalle;
   }

   /**
    * Retorna el id del log
    * @return id del log
    */
   public String getIdLog() {
      return idLog;
   }

   /**
    * Establece el id del log
    * @param idLog id del log
    */
   public void setIdLog(String idLog) {
      this.idLog = idLog;
   }

   /**
    * Retorna el evento del log
    * @return evento del log
    */
   public String getEvento() {
      return evento;
   }

   /**
    * Establece el evento del log
    * @param evento evento del log
    */
   public void setEvento(String evento) {
      this.evento = evento;
   }

   /**
    * Retorna la entidad registrada
    * @return entidad registrada
    */
   public String getIdEntidad() {
      return idEntidad;
   }

   /**
    * Establece la entidad registrada
    * @param idEntidad entidad registrada
    */
   public void setIdEntidad(String idEntidad) {
      this.idEntidad = idEntidad;
   }
}
