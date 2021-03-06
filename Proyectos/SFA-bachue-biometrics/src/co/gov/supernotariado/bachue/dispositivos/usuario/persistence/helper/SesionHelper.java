package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper;

import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ClaveDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.VerificacionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.SesionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Sesion;

/**
 * Helper de conversion entre DTO y DAO de sesion.
 *
 */
public class SesionHelper {

    private SesionHelper() {}

  /**
   * Método que mapea una entidad a su DTO correspondiente.
   * @param as_entidad que sera convertida a DTO.
   * @return DTO mapeado desde la entidad.
   */
    public static SesionDTO toDto(Sesion as_entidad) {

      SesionDTO lsd_sesion = new SesionDTO();

      lsd_sesion.setSesion(as_entidad.getSesion());
      lsd_sesion.setResultado(as_entidad.getResultado());
      lsd_sesion.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lsd_sesion.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

      return lsd_sesion;
    }

  /**
   * Método que recibe la peticion HTTP de enrolamiento y la mapea al DTO.
   * @param asd_sesionDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
    public static Sesion toEntity(SesionDTO asd_sesionDTO) {

      Sesion ls_sesion = new Sesion();

      ls_sesion.setSesion(asd_sesionDTO.getSesion());
      ls_sesion.setResultado(asd_sesionDTO.getResultado());
      ls_sesion.setFechaCreacion(asd_sesionDTO.getTime());
      ls_sesion.setIpCreacion(asd_sesionDTO.getIp());

      return ls_sesion;
    }

  /**
   * Método que recibe la peticion HTTP de enrolamiento y la mapea al DTO.
   * @param avd_verificacionDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
  public static Sesion createSesion(VerificacionDTO avd_verificacionDTO, boolean ab_resultado) {

    Sesion ls_sesion = new Sesion();

    ls_sesion.setSesion(avd_verificacionDTO.getSesion());
    ls_sesion.setResultado(ab_resultado);
    ls_sesion.setFechaCreacion(avd_verificacionDTO.getTime());
    ls_sesion.setIdUsuarioCreacion(avd_verificacionDTO.getIdUsuario());
    ls_sesion.setIpCreacion(avd_verificacionDTO.getIp());

    return ls_sesion;
  }

  /**
   * Método que recibe la peticion HTTP de enrolamiento y la mapea al DTO.
   * @param acd_claveDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
  public static Sesion crearSesionConClave(ClaveDTO acd_claveDTO, boolean ab_resultado) {

    Sesion ls_sesion = new Sesion();

    ls_sesion.setSesion(acd_claveDTO.getSesion());
    ls_sesion.setResultado(ab_resultado);
    ls_sesion.setFechaCreacion(acd_claveDTO.getTime());
    //24/02/2020 se agrega encryp para session
    ls_sesion.setIdUsuarioCreacion(Criptografia.encrypt(acd_claveDTO.getIdUsuario()));
    ls_sesion.setIpCreacion(acd_claveDTO.getIp());

    return ls_sesion;
  }
}
