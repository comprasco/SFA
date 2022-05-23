package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao;


import co.gov.supernotariado.bachue.dispositivos.common.enums.DedosEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 *  DTO de verificacion.
 *
 */
public class VerificacionDTO extends BaseDTO implements Serializable {
   /**
    * Serial de la versión
    */
  private static final long serialVersionUID = 1L;

  /**
   * Id de sesión
   */
  private String is_sesion;
  
  /**
   * Id de verificación
   */
  private Integer ii_id;
  
  /**
   * Posición del dedo
   */
  private DedosEnum ide_posicion;
  
  /**
   * Template de la huella
   */
  private String is_template;
  
  /**
   * Id de usuario
   */
  private String is_idUsuario;

  /**
   * Retorna el id de Sesión 
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

  /**
   * Retorna el id de la verificación
   * @return id de la verificación
   */
  @XmlTransient
  public Integer getId() {
    return ii_id;
  }

  /**
   * Establece el id de la verificación
   * @param ai_id id de verificación
   */
  public void setId(Integer ai_id) {
    this.ii_id = ai_id;
  }

  /**
   * Retorna la posición del dedo
   * @return posición del dedo
   */
  @XmlElement
  public DedosEnum getPosicion() {
    return ide_posicion;
  }

  /**
   * Establece la posición del dedo
   * @param ade_posicion posición del dedo
   */
  public void setPosicion(DedosEnum ade_posicion) {
    this.ide_posicion = ade_posicion;
  }

  /**
   * Retorna el tamplate extraído de la imagen de la huella
   * @return template de la imagen de la huella
   */
  @XmlElement(required = true, name = "imagenHuella")
  public String getTemplate() {
    return is_template;
  }

  /**
   * Establece el template de la huella
   * @param as_template template de la huella
   */
  public void setTemplate(String as_template) {
    this.is_template = as_template;
  }

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
   * @param as_usuarioId id de usuario
   */
  public void setIdUsuario(String as_usuarioId) {
    this.is_idUsuario = Criptografia.encrypt(as_usuarioId);
  }

}