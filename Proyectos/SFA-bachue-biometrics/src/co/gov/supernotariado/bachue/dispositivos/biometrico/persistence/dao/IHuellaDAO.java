package co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dao;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.model.Huella;

import java.util.List;

/**
 * Interface de DAO de operaciones biometricas.
 */
@Local
public interface IHuellaDAO {

  /**
   * Método que agrega los campos de huella que son obtenibles desde el request.
   * @param ah_huella Request con la información HTTP de la peticion recibida.
   * @return true si la huella es creada con éxito.
   */
  Boolean crearHuella(Huella ah_huella);

  /**
   * Método que borra las huellas de la base de datos.
   * @param as_idUsuario String con el id del usuario.
   * @return true si la huella es eliminada con éxito.
   */
  Boolean borrarHuellas(String as_idUsuario);

  /**
   * Método que cuenta las huellas de un usuario en la base de datos.
   * @param as_idUsuario String con el id del usuario.
   * @return numero de huellas del usuario en el sistema.
   */
  int contarHuellas(String as_idUsuario);

  /**
   * Método que obtiene las huellas de un usuario de la base de datos.
   * @param as_idUsuario String con el id del usuario.
   * @return huellas del usuario en el sistema.
   */
  List<Huella> obtenerHuellas(String as_idUsuario);
}
