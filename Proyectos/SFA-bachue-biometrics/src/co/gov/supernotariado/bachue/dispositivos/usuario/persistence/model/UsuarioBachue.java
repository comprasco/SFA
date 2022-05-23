package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Modelo de persistencia de usuario.
 *
 */
@Entity
@Table(name = "SDB_AUT_USUARIO", schema = "ADM_SDB")
public class UsuarioBachue implements Serializable {
   /**
    * versión del serial
    */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor de la clase
   */
  public UsuarioBachue() {}

  /**
   * Id del usuario
   */
  @Id()
  @Column(name = "ID_USUARIO", length = 30, nullable = false)
  private String idUsuario;

  /**
   * Segundo factor de autenticación del usuario
   */
  @Column(name = "SEGUNDO_FACTOR_AUTENTICACION", length = 30)
  private String segundoFactorAutenticacion;

  /**
   * Retorna el Id del usuario
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
   * Retorna el segundo factor de autenticación
   * @return segundo factor de autenticación
   */
  public String getSegundoFactorAutenticacion() {
    return segundoFactorAutenticacion;
  }

  /**
   * Establece el segundo factor de autenticación 
   * @param segundoFactorAutenticacion segundo factor de autenticación
   */
  public void setSegundoFactorAutenticacion(String segundoFactorAutenticacion) {
    this.segundoFactorAutenticacion = segundoFactorAutenticacion;
  }
}
