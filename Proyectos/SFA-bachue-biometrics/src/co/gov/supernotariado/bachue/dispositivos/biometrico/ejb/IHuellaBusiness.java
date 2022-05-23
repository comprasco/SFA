package co.gov.supernotariado.bachue.dispositivos.biometrico.ejb;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.BorrarHuellasDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.*;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.VerificacionDTO;

/**
 * Interface de lógica de operaciones biometricas.
 */
public interface IHuellaBusiness {
  /**
   * Método que extrae y envia los datos de la huella al DAO para ser almacenada.
   * @param ahd_huella DTO con la información de la huella.
   * @return true si la huella es enrolada con éxito.
   */
  Boolean enrolarHuella(HuellaDTO ahd_huella);

  /**
   * Método que compara los datos de la huella con los registros biometricos almacenados.
   * @param avd_verificacion DTO con la información de la huella.
   * @return true si la huella es procesada con éxito. Incluso si el resultado es negativo.
   */
  BooleanSalidaDTO verificarHuella(VerificacionDTO avd_verificacion);

  /**
   * Método que borra las huellas del usuario.
   * @param bhd_usuario DTO con la información del usuario.
   * @return true si el usuario es registrado con éxito.
   */
  StringSalidaDTO borrarHuellas(BorrarHuellasDTO bhd_usuario);

  /**
   * Método que crea el template del usuario.
   * @param ahd_huella DTO con la información del usuario.
   * @return true si el usuario es registrado con éxito.
   */
  BooleanSalidaDTO crearMegaTemplate(HuellaDTO ahd_huella);
}
