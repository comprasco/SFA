package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto;

import javax.xml.bind.annotation.XmlElement;

/**
 * DTO de salida de la operación AgreagarFirma
 */
public class AgregarFirmaSalidaDTO extends FirmaSalidaDTO{

   /**
    * Id de firma
    */
   private String is_idFirma;

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
}
