package co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.model;

import javax.persistence.*;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.BaseModel;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Usuario;

import java.io.Serializable;

/**
 * Modelo de persistencia de huella.
 */
@Entity
@Table(name = "SDB_AUT_HUELLA")
public class Huella extends BaseModel implements Serializable {
   /**
    * Serial de la versión
    */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor de la clase
   */
  public Huella() {}

  /**
   * Id de huella
   */
  @Id()
  @Column(name = "ID_HUELLA", length = 20)
  @GeneratedValue
  private String idHuella;

  /**
   * Template de huella
   */
  @Column(name = "TEMPLATE", nullable = false)
  private byte[] template;

  /**
   * Posición o número de dedo de las manos
   */
  @Column(name = "POSICION", length = 2, nullable = false)
  private int posicion;

  /**
   * Id de usuario
   */
  @ManyToOne(optional = false)
  @JoinColumn(name="ID_USUARIO_HASH")
  private Usuario usuario;

  /**
   * Retorna id de huella
   * @return id de huella
   */
  public String getIdHuella() {
    return idHuella;
  }

  /**
   * Establece el id de huella
   * @param idHuella id de huella
   */
  public void setIdHuella(String idHuella) {
    this.idHuella = idHuella;
  }

  /**
   * Retornam el template de huella
   * @return template de huella
   */
  public byte[] getTemplate() {
    return template;
  }

  /**
   * Establece el template de huella
   * @param template template de huella
   */
  public void setTemplate(byte[] template) {
    this.template = template;
  }

  /**
   * Retorna la posición de dedo
   * @return posición de dedo
   */
  public int getPosicion() {
    return posicion;
  }

  /**
   * Establece la posición de dedo
   * @param posicion posición de dedo
   */
  public void setPosicion(int posicion) {
    this.posicion = posicion;
  }

  /**
   * Retorna el id de usuario
   * @return id de usuario
   */
  public Usuario getUsuario() {
    return usuario;
  }

  /**
   * Establece el id de usuario
   * @param usuario id de usuario
   */
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
