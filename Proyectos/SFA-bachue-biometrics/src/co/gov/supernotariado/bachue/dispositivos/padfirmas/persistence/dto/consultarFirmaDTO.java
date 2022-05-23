package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

/**
 * DTO de entrada de la operación obtenerFirma
 */
public class consultarFirmaDTO extends BaseDTO implements Serializable{
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Id de firma
    */
   private String is_idFirma;
   
   /**
    * Id de usuario
    */
   @NotNull
   private String is_idUsuario;
   
   /**
    * Retorna el id de usuario
    * @return id de usuario
    */
   @XmlElement(required = true, name = "idUsuario" )
   public String getIdUsuario() {
      return is_idUsuario;
   }
   
   /**
    * Establece el id de usuario
    * @param as_idUsuario id de usuario
    */
   public void setIdUsuario(String as_idUsuario) {
      this.is_idUsuario = as_idUsuario;
   }

   /**
    * Retorna el id de firma
    * @return id de firma
    */
   @XmlElement(required = true, name = "idfirma" )
   public String getIdfirma() {
      return is_idFirma;
   }
   
   /**
    * Establece el id de firma
    * @param as_idFirma id de firma
    */
   public void setIdfirma(String as_idFirma) {
      this.is_idFirma = as_idFirma;
   }
}
