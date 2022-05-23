package co.gov.supernotariado.bachue.dispositivos.ejb;


import co.gov.supernotariado.bachue.dispositivos.persistence.dto.ConstantesSalidaDTO;

/**
 * Interface de logica de negocio de constantes.
 */
public interface IConstanteBusiness {

  /**
   * Método que obtiene las constantes de la base de datos.
   * @return lista de las constantes.
   */
  ConstantesSalidaDTO consultarConstantes();
}
