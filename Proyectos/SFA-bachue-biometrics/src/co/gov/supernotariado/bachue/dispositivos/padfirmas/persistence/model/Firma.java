package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.model;


import javax.persistence.*;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.BaseModel;

import java.io.Serializable;

/**
* Modelo de persistencia de firma.
*/
@Entity
@Table(name = "SDB_ACC_FIRMA")
public class Firma extends BaseModel implements Serializable {
   private static final long serialVersionUID = 1L;
	  
   /**
    * Constructor de la clase
    */
   public Firma() {}

   /**
    * Id de firma
    */
   @Id()
   @Column(name = "ID_FIRMA", length = 50)
   @GeneratedValue
   private String idFirma;

   /**
    * Bytes de la imagen
    */
   @Column(name = "IMAGEN", nullable = false)
   private byte[] imagen;

   /**
    * Retorna el id de firma
    * @return id de firma
    */
   public String getIdFirma() {
      return idFirma;
   }

   /**
    * Establece el id de firma
    * @param idFirma id de firma
    */
   public void setIdFirma(String idFirma) {
      this.idFirma = idFirma;
   }

   /**
    * Retorna la imagen de la firma
    * @return imagen de la firma
    */
   public byte[] getImage() {
      return imagen;
   }

   /**
    * Establece la imagen de la firma
    * @param image imagen de la firma
    */
   public void setImage(byte[] image) {
      this.imagen = image;
   }
}
