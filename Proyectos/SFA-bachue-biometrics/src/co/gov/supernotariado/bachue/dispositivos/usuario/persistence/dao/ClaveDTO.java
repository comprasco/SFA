package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao;

import javax.xml.bind.annotation.XmlElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

import java.io.Serializable;

/**
 * DTO de usuarios.
 */
public class ClaveDTO extends BaseDTO implements Serializable {
  /**
   * Serial de la versión
   */
  private static final long serialVersionUID = 1L;

  /**
   * Id del usuario
   */
  private String is_idUsuario;
  
  /**
   * Segunda clave
   */
  private String is_clave;
  
  /**
   * Id de sesión
   */
  private String is_sesion;

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
   * Retorna la segunda clave
   * @return segunda clave
   */
  @XmlElement(required = true)
  public String getClave() {
    return is_clave;
  }

  /**
   * Establece la segunda clave
   * @param as_clave segunda clave
   */
  public void setClave(String as_clave) {
    this.is_clave = as_clave;
  }

  /**
   * Retorna el id de sesión
   * @return id de sesión
   */
  @XmlElement(required = true)
  public String getSesion() {
    return is_sesion;
  }

  /**
   * Establece el id de sesión
   * @param as_sesion id de sesión
   */
  public void setSesion(String as_sesion) {
    this.is_sesion = as_sesion;
  }
}
