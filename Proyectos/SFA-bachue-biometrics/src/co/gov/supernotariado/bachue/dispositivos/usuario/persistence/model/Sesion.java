package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.BaseModel;

import java.io.Serializable;

/**
 * Modelo de persistencia de sesion.
 *
 */
@Entity
@Table(name = "SDB_ACC_SESION")
public class Sesion extends BaseModel implements Serializable {
   /**
    * versión del serial
    */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor de la clase
   */
  public Sesion() {}

  /**
   * Id de la sesión
   */
  @Id()
  @Column(name = "ID_SESION", length = 200, nullable = false)
  private String sesion;

  /**
   * Resultado de la verificación
   */
  @Column(name = "RESULTADO", nullable = false)
  private Boolean resultado;

  /**
   * Retorna el id de la sesíón
   * @return id de la sesión
   */
  public String getSesion() {
    return sesion;
  }

  /**
   * Establece el id de la sesión
   * @param sesion id de la sesión
   */
  public void setSesion(String sesion) {
    this.sesion = sesion;
  }

  /**
   * Retorna el resultado de la verificación
   * @return resultado de la verificación
   */
  public Boolean getResultado() {
    return resultado;
  }

  /**
   * Establece el resultado de la verificación
   * @param resultado resultado de la verificación
   */
  public void setResultado(Boolean resultado) {
    this.resultado = resultado;
  }
}
