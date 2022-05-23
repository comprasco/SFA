package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO de usuarios.
 *
 */
@XmlRootElement(name = "entradaUsuario")
public class UsuarioDTO extends BaseDTO implements Serializable {

   /** serial de la versión */
  private static final long serialVersionUID = 1L;

  /**
   * Id del usuario
   */
  @Valid
  @NotNull
  private String is_idUsuario;

  /**
   * Segunda clave
   */
  @Valid
  @NotNull
  private String is_clave;

  /**
   * Fecha de vencimiento
   */
  private Date it_fechaVencimiento;
  
  /**
   * Indicador de clave activa
   */
  private char ic_claveActiva;

  /**
   * Id del usuario que crea el registro
   */
  @Valid
  @NotNull
  private String is_idUsuarioCreacion;

  /**
   * Retorna el id del usuario
   * @return id del usuario
   */
  @XmlElement(required = true)
  public String getIdUsuario() {
    return is_idUsuario;
  }

  /**
   * Establece el id del isiario
   * @param as_idUsuario id del usuaerio
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
   * Retorna la fecha de vencimiento
   * @return fecha de vencimiento
   */
  @XmlElement(required = true)
  public Date getFechaVencimiento() {
    return it_fechaVencimiento;
  }

  /**
   * Establece la fecha de vencimiento
   * @param at_fechaVencimiento fecha de vencimiento
   */
  public void setFechaVencimiento(Date at_fechaVencimiento) {
    this.it_fechaVencimiento = at_fechaVencimiento;
  }

  /**
   * Retorna el indicador de clave activa
   * @return indicador de clave activa
   */
  @XmlElement(required = true)
  public char getClaveActiva() {
    return ic_claveActiva;
  }

  /**
   * Establecer el indicador de clave activa
   * @param ac_claveActiva indicador de clave activa
   */
  public void setClaveActiva(char ac_claveActiva) {
    this.ic_claveActiva = ac_claveActiva;
  }

  /**
   * Retorna el id del usuario de creación
   * @return id del usuario de creación
   */
  @XmlElement(required = true)
  public String getIdUsuarioCreacion() {
    return is_idUsuarioCreacion;
  }

  /**
   * Establece el id del usuario de creación
   * @param as_idUsuarioCreacion id del usuario de creación
   */
  public void setIdUsuarioCreacion(String as_idUsuarioCreacion) {
    this.is_idUsuarioCreacion = as_idUsuarioCreacion;
  }
}
