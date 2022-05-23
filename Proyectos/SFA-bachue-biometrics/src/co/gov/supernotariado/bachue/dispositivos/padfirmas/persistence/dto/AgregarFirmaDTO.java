package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

/**
 * DTO de la operación AgregarFirma
 */
public class AgregarFirmaDTO extends BaseDTO implements Serializable{

   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Id de firma
    */
   @NotNull
   private String is_firma;
   
   /**
    * Id de trámite asociado a la firma
    */
   @NotNull
   private String is_idTramite;
   
   /**
    * Id de usuario
    */
   @NotNull
   private String is_idUsuario;
   
   /**
    * Número de documento del ciudadano que firma
    */
   @NotNull
   private Long il_numeroDocCiudadano;
   
   /**
    * Tipo de documento del ciudadano que firma
    */
   @NotNull
   private String is_tipoDocCiudadano;

   /**
    * Retorna el id de firma
    * @return id de firma
    */
   @XmlElement(required = true, name = "firma" )
   public String getFirma() {
      return is_firma;
   }

   /**
    * Establece el id de firma
    * @param as_firma id de firma
    */
   public void setFirma(String as_firma) {
      this.is_firma = as_firma;
   }

   /**
    * Retorna el id del trámite asociado a la firma
    * @return id del trámite asociado a la firma
    */
   @XmlElement(required = true, name = "idTramite" )
   public String getIdTramite() {
      return is_idTramite;
   }
   
   /**
    * Establece el id de trámite asociado a la firma
    * @param as_idTramite id de trámite asociado a la firma
    */
   public void setIdTramite(String as_idTramite) {
      this.is_idTramite = as_idTramite;
   }
   
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
    * Retorna el número de documento del ciudadano de la firma 
    * @return número de documento del ciudadano de la firma
    */
   @XmlElement(required = true, name = "numeroDocCiudadano" )
   public Long getNumeroDocCiudadano() {
      return il_numeroDocCiudadano;
   }

   /**
    * Establece el número de documento del ciudadano de la firma
    * @param al_numeroDocCiudadano número de documento del ciudadano de la firma
    */
   public void setNumeroDocCiudadano(Long al_numeroDocCiudadano) {
      this.il_numeroDocCiudadano = al_numeroDocCiudadano;
   }
   
   /**
    * Retorna el tipo de documento del ciudadano que firma 
    * @return tipo de documento del ciudadano que firma
    */
   @XmlElement(required = true, name = "tipoDocCiudadano" )
   public String getTipoDocCiudadano() {
      return is_tipoDocCiudadano;
   }
   
   /**
    * Establece el tipo de documento del ciudadano que firma
    * @param as_tipoDocCiudadano tipo de documento del ciudadano que firma
    */
   public void setTipoDocCiudadano(String as_tipoDocCiudadano) {
      this.is_tipoDocCiudadano = as_tipoDocCiudadano;
   }
}
