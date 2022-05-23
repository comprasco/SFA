package co.gov.supernotariado.bachue.dispositivos.persistence.helper;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.BorrarHuellasDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.consultarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Accion;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ClaveDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.VerificacionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioDTO;

/**
 * Helper de conversion entre DTO y DAO de log.
 */
public class LogHelper {
   /**
    * Constructor de la clase
    */
   private LogHelper() {}

  /**
   * Metodo que mapea una entidad a su DTO correspondiente.
   * @param al_entidad que sera convertida a DTO.
   * @return DTO mapeado desde la entidad.
   */
   public static LogDTO toDto(Accion al_entidad) {
      LogDTO lld_log = new LogDTO();

      lld_log.setId(al_entidad.getIdLog());
      lld_log.setEvento(al_entidad.getEvento());
      lld_log.setDetalle(al_entidad.getDetalle());
      lld_log.setIdEntidad(al_entidad.getIdEntidad());

      return lld_log;
   }

  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param ald_logDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion toEntity(LogDTO ald_logDTO) {
      Accion ll_log = new Accion();

      ll_log.setIdLog(ald_logDTO.getId());
      ll_log.setEvento(ald_logDTO.getEvento());
      ll_log.setDetalle(ald_logDTO.getDetalle());
      ll_log.setIdEntidad(ald_logDTO.getIdEntidad());
      ll_log.setIdUsuarioCreacion(ald_logDTO.getIdUsuario());
      ll_log.setIpCreacion(ald_logDTO.getIp());
      ll_log.setFechaCreacion(ald_logDTO.getTime());

      return ll_log;
   }

  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param avd_verificacionDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion crearLogDeVerificacion(VerificacionDTO avd_verificacionDTO, boolean ab_resultado) {
      Accion ll_log = new Accion();

      ll_log.setEvento("VERIFICACION");
      String ls_detalle = ab_resultado ? "Usuario verificado exitosamente" : "El usuario no se pudo verificar";
      ll_log.setDetalle(ls_detalle);
      ll_log.setIdEntidad(Criptografia.decrypt(avd_verificacionDTO.getIdUsuario()));
      ll_log.setIdUsuarioCreacion(avd_verificacionDTO.getIdUsuario());
      ll_log.setIpCreacion(avd_verificacionDTO.getIp());
      ll_log.setFechaCreacion(avd_verificacionDTO.getTime());

      return ll_log;
   }

  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param acd_claveDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion crearLogDeVerificacionConClave(ClaveDTO acd_claveDTO, boolean ab_resultado) {
      Accion ll_log = new Accion();

      ll_log.setEvento("VERIFICACION");
      String ls_detalle = ab_resultado ? "Usuario verificado exitosamente" : "El usuario no se pudo verificar";
      ll_log.setDetalle(ls_detalle);
      ll_log.setIdEntidad(acd_claveDTO.getIdUsuario());
      ll_log.setIdUsuarioCreacion(acd_claveDTO.getIdUsuario());
      ll_log.setIpCreacion(acd_claveDTO.getIp());
      ll_log.setFechaCreacion(acd_claveDTO.getTime());

      return ll_log;
   }

  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param ahd_huellaDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion crearLogDeEnrolamiento(HuellaDTO ahd_huellaDTO, boolean ab_resultado) {
      Accion ll_log = new Accion();

      ll_log.setEvento("ENROLAMIENTO");
      String ls_detalle = ab_resultado ? "Usuario enrolado exitosamente" : "El usuario no se pudo enrolar";
      ll_log.setDetalle(ls_detalle);
      ll_log.setIdEntidad(Criptografia.decrypt(ahd_huellaDTO.getIdUsuario()));
      ll_log.setIdUsuarioCreacion(ahd_huellaDTO.getIdUsuario());
      ll_log.setIpCreacion(ahd_huellaDTO.getIp());
      ll_log.setFechaCreacion(ahd_huellaDTO.getTime());
      return ll_log;
   }

   /**
    * Metodo que mapea un DTO a su entidad correspondiente.
    * @param ahd_huellaDTO que sera convertido a la entidad correspondiente.
    * @return entidad mapeada desde el DTO recibido.
    */
    public static Accion crearLogDeCrearUsuario(UsuarioDTO usuarioDTO, boolean ab_resultado) {
       Accion ll_log = new Accion();

       ll_log.setEvento("ENROLAMIENTO");
       String ls_detalle = ab_resultado ? "Usuario nuevo creado exitosamente" : "El usuario nuevo no se pudo crear";
       ll_log.setDetalle(ls_detalle);
       ll_log.setIdEntidad(usuarioDTO.getIdUsuario());
       ll_log.setIdUsuarioCreacion(usuarioDTO.getIdUsuarioCreacion());
       ll_log.setIpCreacion(usuarioDTO.getIp());
       ll_log.setFechaCreacion(usuarioDTO.getTime());
       return ll_log;
    }

  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param aud_usuarioDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion crearLogDeActualizacionDeClave(UsuarioDTO aud_usuarioDTO) {
      Accion ll_log = new Accion();

      ll_log.setEvento("CLAVE");
      ll_log.setDetalle("Cambio de clave exitoso");
      ll_log.setIdEntidad(aud_usuarioDTO.getIdUsuario());
      ll_log.setIdUsuarioCreacion(aud_usuarioDTO.getIdUsuarioCreacion());
      ll_log.setIpCreacion(aud_usuarioDTO.getIp());
      ll_log.setFechaCreacion(aud_usuarioDTO.getTime());

      return ll_log;
   }

  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param bhd_usuario que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion crearLogDeBorrado(BorrarHuellasDTO bhd_usuario) {
      Accion ll_log = new Accion();

      ll_log.setEvento("HUELLA");
      ll_log.setDetalle("Huellas borradas exitosamente");
      ll_log.setIdEntidad(bhd_usuario.getIdUsuario());
      ll_log.setIdUsuarioCreacion(bhd_usuario.getIdUsuarioCreacion());
      ll_log.setIpCreacion(bhd_usuario.getIp());
      ll_log.setFechaCreacion(bhd_usuario.getTime());

      return ll_log;
   }
  
  /**
   * Metodo que mapea un DTO a su entidad correspondiente.
   * @param obd_BorradoFirma DTO de la solicitud de borrado
   * @param lb_resultado resultado del borrado de la firma
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Accion crearLogDeBorradoFirma(consultarFirmaDTO obd_BorradoFirma, boolean lb_resultado) {
      Accion ll_log = new Accion();

      ll_log.setEvento("FIRMA");
      String ls_detalle = lb_resultado ? "Firma Borradas Exitosamente" : "Error Grave";
      ll_log.setDetalle(ls_detalle);
      ll_log.setIdEntidad(obd_BorradoFirma.getIdUsuario());
      ll_log.setIdUsuarioCreacion(obd_BorradoFirma.getIdUsuario());
      ll_log.setIpCreacion(obd_BorradoFirma.getIp());
      ll_log.setFechaCreacion(obd_BorradoFirma.getTime());

      return ll_log;
   }
}
