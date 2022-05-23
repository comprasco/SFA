package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper;

import co.gov.supernotariado.bachue.dispositivos.common.enums.ValidacionesEnum;

/**
 * Clase para validar la complejidad de la segunda clave
 *
 */
public class ValidadorHelper {

   /**
    * Método de validación de la clave
    * @param as_clave clave a validar
    * @param as_patron patrón de validación de la clave
    * @param as_minimo longitud mínima de la clave
    * @param as_maximo longitud máxima de la clave
    * @return resultado de la validación de la complejidad de la saegunda clave 
    */
  public static String validarClave(String as_clave, String as_patron, String as_minimo, String as_maximo) {

    if (as_clave.length() < Integer.parseInt(as_minimo)) {
      return ValidacionesEnum.CLAVE_CORTA.consultarMensaje();
    } else if (as_clave.length() > Integer.parseInt(as_maximo)) {
      return ValidacionesEnum.CLAVE_LARGA.consultarMensaje();
    } else if(!as_clave.matches(as_patron)) {
      return ValidacionesEnum.POCA_COMPLEJIDAD.consultarMensaje();
    }

    return ValidacionesEnum.VALIDACION_EXITOSA.consultarMensaje();
  }
}
