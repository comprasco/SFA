package co.gov.supernotariado.bachue.dispositivos.persistence.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Modelo de persistencia de histórico de segunda clave.
 */
@Entity
@Table(name = "SDB_AUT_HISTORICO")
public class Historico extends BaseModel implements Serializable {
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructor de la clase
    */
   public Historico() {}

   /**
    * Id de historia de segunda clave
    */
   @Id()
   @Column(name = "ID_HISTORICO", length = 20)
   @GeneratedValue
   private String idHistorico;

   /**
    * Id de usuario
    */
   @Column(name = "ID_USUARIO_HASH", length = 200, nullable = false)
   private String idUsuario;

   /**
    * Clave cifrada
    */
   @Column(name = "CLAVE_HASH", length = 200, nullable = false)
   private String claveHash;

   /**
    * Retorna el id de usuario
    * @return id de usuario
    */
   public String getIdUsuario() {
      return idUsuario;
   }

   /**
    * Establece el id de usuario
    * @param idUsuario id de usuario
    */
   public void setIdUsuario(String idUsuario) {
      this.idUsuario = idUsuario;
   }

   /**
    * Retorna la segunda clave cifrada
    * @return segunda clave cifrada
    */
   public String getClaveHash() {
      return claveHash;
   }

   /**
    * Establece la segunda clave cifrada
    * @param claveHash segunda clave cifrada
    */
   public void setClaveHash(String claveHash) {
      this.claveHash = claveHash;
   }

   /**
    * Retorna el id de historia
    * @return id de historia
    */
   public String getIdHistorico() {
      return idHistorico;
   }

   /**
    * Establece el id de historia
    * @param idHistorico id de historia
    */
   public void setIdHistorico(String idHistorico) {
      this.idHistorico = idHistorico;
   }
}
