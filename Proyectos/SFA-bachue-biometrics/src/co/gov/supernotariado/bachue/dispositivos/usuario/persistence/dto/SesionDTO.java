package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto;

import java.io.Serializable;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

/**
 * DTO de huellas.
 */
public class SesionDTO extends BaseDTO implements Serializable {

   /**
    * Serial de la versión
    */
  private static final long serialVersionUID = 1L;

  /**
   * Id de sesión
   */
  private String is_sesion;
  
  /**
   * Resultado de la verificación de la sesión
   */
  private Boolean ib_resultado;
  
  /**
   * Código de respuesta
   */
  private String is_codigo;
  
  /**
   * Mensaje de respuesta
   */
  private String is_mensaje;

  /**
   * Retorna el id de sesión
   * @return id de sesión
   */
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

  /**
   * Retorna el resultado de verificar la sesión
   * @return resultado de verificar la sesión
   */
  public Boolean getResultado() {
    return ib_resultado;
  }

  /**
   * Establece el resultado de la verificación de la sesión
   * @param ab_resultado resultado de verificar la sesión
   */
  public void setResultado(Boolean ab_resultado) {
    this.ib_resultado = ab_resultado;
  }

  /**
   * Retorna el código de respuesta
   * @return
   */
  public String getCodigo() {
    return is_codigo;
  }

  /**
   * Establece el códido de respuesta
   * @param as_codigo código de respuesta
   */
  public void setCodigo(String as_codigo) {
    this.is_codigo = as_codigo;
  }

  /**
   * Retorna el mensaje de respuesta
   * @return mensaje de respuesta
   */
  public String getMensaje() {
    return is_mensaje;
  }

  /**
   * Establece el mensaje de respuesta
   * @param as_mensaje mensaje de respuesta
   */
  public void setMensaje(String as_mensaje) {
    this.is_mensaje = as_mensaje;
  }
}