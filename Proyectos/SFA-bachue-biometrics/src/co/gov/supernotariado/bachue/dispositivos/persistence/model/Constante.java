package co.gov.supernotariado.bachue.dispositivos.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Modelo de persistencia de constantes.
 */
@Entity
@Table(name = "SDB_PGN_CONSTANTES", schema = "ADM_SDB")

public class Constante implements Serializable {
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructor de la clase
    */
   public Constante() {}

   /**
    * Id de constante
    */
   @Id()
   @Column(name = "ID_CONSTANTE", length = 30, nullable = false)
   private String idConstante;

   /**
    * Valor caracter de la constante
    */
   @Column(name = "CARACTER", length = 400)
   private String caracter;

   /**
    * Retorna el id de constante
    * @return id de constante
    */
   public String getIdConstante() {
      return idConstante;
   }

   /**
    * Establece el id de constante
    * @param idConstante id de constante
    */
   public void setIdConstante(String idConstante) {
      this.idConstante = idConstante;
   }

   /**
    * Retorna el valor caracter de la constante
    * @return valor caracter de la constante
    */
   public String getCaracter() {
      return caracter;
   }

   /**
    * Establece el valor caracter de la constante
    * @param caracter valor caracter de la constante
    */
   public void setCaracter(String caracter) {
      this.caracter = caracter;
   }
}

