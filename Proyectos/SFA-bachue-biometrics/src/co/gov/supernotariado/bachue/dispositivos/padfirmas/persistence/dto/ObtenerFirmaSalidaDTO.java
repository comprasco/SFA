package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto;

import javax.xml.bind.annotation.XmlElement;

/**
 * DTO de salida de la operación obtenerFirma
 */
public class ObtenerFirmaSalidaDTO extends FirmaSalidaDTO{

   /**
    * Id de firma
    */
   private String is_idFirma;
   
   /**
    * imágen de la firma en formato Base64
    */
   private String is_firma; 

   /**
    * Retorna el id de firma
    * @return id de firma
    */
   @XmlElement(name = "idFirma")
   public String getIdFirma() {
      return is_idFirma;
   }
   
   /**
    * Establece el id de firma
    * @param as_idFirma id de firma
    */
   public void setIdFirma(String as_idFirma) {
      this.is_idFirma = as_idFirma;
   }
   
   /**
    * Retorna la imagen de la firma den formato Base64
    * @return imagen de la firma en formato Base64
    */
   @XmlElement(name = "firma")
   public String getFirma() {
      return is_firma;
   }
   
   /**
    * Establece la imagen de la firma en formato Base64
    * @param as_firma imagen de la firma en base64
    */
   public void setFirma(String as_firma) {
      this.is_firma = as_firma;
   }	
}
