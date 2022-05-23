package co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto;

import co.gov.supernotariado.bachue.dispositivos.common.enums.DedosEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * DTO de huellas.
 */
public class HuellaDTO extends BaseDTO implements Serializable{

   /**
    * Serial de la versión
    */
  private static final long serialVersionUID = 1L;

  /**
   * Id de huella
   */
  private Integer ii_id;
  
  /**
   * posición del dedo
   */
  private DedosEnum ide_posicion;
  
  /**
   * Template de la huella en Base64
   */
  private String is_template;
  
  /**
   * Id de usuario
   */
  private String is_idUsuario;
  
  /**
   * Id de usuario de creación
   */
  protected String is_idUsuarioCreacion;

  /**
   * Retorna el id de huella
   * @return id de huella
   */
  @XmlTransient
  public Integer getId() {
    return ii_id;
  }

  /**
   * Establece el id de huella
   * @param ai_id id de huella
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
   * Retorna el template de la huella
   * @return template de la huella
   */
  @XmlElement(required = true, name = "imagenHuella")
  public String getTemplate() {
    return is_template;
  }

  /**
   * Establece el template de la huella en Base64
   * @param as_template template de la huella en Base64
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
   * @param as_usuarioCreacionId id de usuario de creación
   */
  public void setIdUsuarioCreacion(String as_usuarioCreacionId) {
    this.is_idUsuarioCreacion = as_usuarioCreacionId;
  }
}