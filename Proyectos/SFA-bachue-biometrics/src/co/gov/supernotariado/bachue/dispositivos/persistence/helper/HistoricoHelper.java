package co.gov.supernotariado.bachue.dispositivos.persistence.helper;

import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Historico;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioDTO;

/**
 * Helper de conversion entre DTO y DAO de histórico.
 */
public class HistoricoHelper {
   /**
    * Constructor de la clase
    */
   private HistoricoHelper() {}

  /**
   * Método que mapea una entidad a su DTO correspondiente.
   * @param aud_usuarioDTO que sera convertida a entidad.
   * @return entidad histórico.
   */
   public static Historico userToHistorico(UsuarioDTO aud_usuarioDTO) {
      Historico lh_historico = new Historico();
      lh_historico.setIdUsuario(Criptografia.encrypt(aud_usuarioDTO.getIdUsuario()));
      lh_historico.setClaveHash(Criptografia.encrypt(aud_usuarioDTO.getClave()));
      lh_historico.setFechaCreacion(aud_usuarioDTO.getTime());
      lh_historico.setIpCreacion(aud_usuarioDTO.getIp());
      lh_historico.setIdUsuarioCreacion(aud_usuarioDTO.getIdUsuarioCreacion());

      return lh_historico;
   }
}
