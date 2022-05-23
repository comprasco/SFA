package co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

import java.io.Serializable;

/**
 * DTO de borrado de huellas.
 */
public class BorrarHuellasDTO extends BaseDTO implements Serializable {
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Id de usuario
    */
   @Valid
   @NotNull
   private String is_idUsuario;

   /**
    * Id de usuario de creación
    */
   @Valid
   @NotNull
   private String is_idUsuarioCreacion;

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
      this.is_idUsuario = as_idUsuario;
   }

   /**
    * Retorna el id de usuario de creación
    * @return id de usuario de creación
    */
   @XmlElement(required = true)
   public String getIdUsuarioCreacion() {
      return is_idUsuarioCreacion;
   }

   /**
    * Establece el id de usuario de creación
    * @param as_idUsuarioCreacion id de usuario de creación
    */
   public void setIdUsuarioCreacion(String as_idUsuarioCreacion) {
      this.is_idUsuarioCreacion = as_idUsuarioCreacion;
   }
}
