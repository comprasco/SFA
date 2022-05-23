package co.gov.supernotariado.bachue.dispositivos.usuario.ejb;

import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.SesionDTO;

/**
 * Interface de lógica de negocio de sesiones.
 */
public interface ISesionBusiness {
  /**
   * Método que consulta el estado de la sesion que se esta consultando.
   * @param asd_sesion String con la sesión consultada.
   * @return Sesion consultada y su estado.
   */
  SesionDTO consultarSesion(String asd_sesion);
}
