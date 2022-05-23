package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.BaseModel;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Modelo de persistencia de usuario.
 *
 */
@Entity
@Table(name = "SDB_AUT_USUARIOBIO")
public class Usuario extends BaseModel implements Serializable {
   /**
    * versión del serial
    */
  private static final long serialVersionUID = 1L;

  /**
   * Constructur de la clase
   */
  public Usuario() {}

  /**
   * Id del usuario
   */
  @Id()
  @Column(name = "ID_USUARIO_HASH", length = 200, nullable = false)
  private String idUsuario;

  /**
   * Clave Hash del usuario
   */
  @Column(name = "CLAVE_HASH", length = 200, nullable = false)
  private String claveHash;

  /**
   * Fecha de vencimiento
   */
  @Column(name = "FECHA_VENCIMIENTO", length = 6)
  private Timestamp fechaVencimiento;

  /**
   * Indicador de clase activa
   */
  @Column(name = "CLAVE_ACTIVA", length = 1)
  private char claveActiva;

  /**
   * Retorna el ID del usuario
   * @return id del usuario
   */
  public String getIdUsuario() {
    return idUsuario;
  }

  /**
   * Establece el id del usuario
   * @param idUsuario id del usuario
   */
  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  /**
   * Obtiene la clave hash
   * @return clave hash
   */
  public String getClaveHash() {
    return claveHash;
  }

  /**
   * Establece la clave hash
   * @param claveHash la clave hash
   */
  public void setClaveHash(String claveHash) {
    this.claveHash = claveHash;
  }

  /**
   * Retorna la fecha de vencimiento
   * @return fecha de vencimiento
   */
  public Timestamp getFechaVencimiento() {
    return fechaVencimiento;
  }

  /**
   * Establece la fecha de vencimiento
   * @param fechaVencimiento fecha de vencimiento
   */
  public void setFechaVencimiento(Timestamp fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  /**
   * Retorna el indicador de la clave activa
   * @return indicador de clave activa
   */
  public char getClaveActiva() {
    return claveActiva;
  }

  /**
   * Establece el indicador de clave activa
   * @param claveActiva indicador de clave activa
   */
  public void setClaveActiva(char claveActiva) {
    this.claveActiva = claveActiva;
  }
}
