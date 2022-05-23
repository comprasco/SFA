package co.gov.supernotariado.bachue.dispositivos.padfirmas.ejb;

import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.ObtenerFirmaSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.consultarFirmaDTO;

/**
 * Interface de las operaciones de gestión de firma
 */
public interface IFirmaBusiness {

  /**
   * Método que extrae y envia los datos de la firma al DAO para ser almacenada.
   * @param ahd_firma DTO con la información de la firma.
   * @return true si la firma es guardada con éxito.
   */
  AgregarFirmaSalidaDTO crearFirma(AgregarFirmaDTO ahd_firma);

  /**
   * Método que para consultar la firma almacenada en BD.
   * @param avd_obtenerFirma DTO con la información de la firma a consultar.
   * @return true si la firma es procesada con éxito. Incluso si el resultado es negativo.
   */
  ObtenerFirmaSalidaDTO consultarFirma(consultarFirmaDTO avd_obtenerFirma);

  /**
   * Método que borra la firma.
   * @param bhd_usuario DTO con la información de la firma.
   * @return true si firma es borrada con éxito.
   */
  Boolean eliminarFirma(consultarFirmaDTO bhd_usuario);

  
}
