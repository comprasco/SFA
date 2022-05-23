package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper;

import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Usuario;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Helper de conversion entre DTO y DAO de usuario.
 *
 */
public class UsuarioHelper {

   /**
    * Constructor de la clase
    */
    private UsuarioHelper() {}

  /**
   * Método que mapea una entidad a su DTO correspondiente.
   * @param au_entidad que sera convertida a DTO.
   * @return DTO mapeado desde la entidad.
   */
    public static UsuarioDTO toDto(Usuario au_entidad) {

      UsuarioDTO lud_usuario = new UsuarioDTO();

      lud_usuario.setIdUsuario(au_entidad.getIdUsuario());
      lud_usuario.setClave(au_entidad.getClaveHash());
      lud_usuario.setFechaVencimiento(au_entidad.getFechaVencimiento());
      lud_usuario.setClaveActiva(au_entidad.getClaveActiva());

      return lud_usuario;
    }

  /**
   * Método que mapea un DTO a su entidad correspondiente.
   * @param aud_usuarioDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
    public static Usuario toEntity(UsuarioDTO aud_usuarioDTO) {

      Usuario lu_usuario = new Usuario();

      lu_usuario.setIdUsuario(Criptografia.encrypt(aud_usuarioDTO.getIdUsuario()));
      lu_usuario.setClaveHash(Criptografia.encrypt(aud_usuarioDTO.getClave()));
      lu_usuario.setFechaVencimiento(Timestamp.valueOf(LocalDate.now().plusDays(45).atStartOfDay()));
      lu_usuario.setClaveActiva(aud_usuarioDTO.getClaveActiva());
      lu_usuario.setIdUsuarioCreacion(aud_usuarioDTO.getIdUsuarioCreacion());
      lu_usuario.setIpCreacion(aud_usuarioDTO.getIp());
      lu_usuario.setFechaCreacion(aud_usuarioDTO.getTime());

      return lu_usuario;
    }

  /**
   * Método que mapea un DTO a su entidad correspondiente.
   * @param aud_usuarioDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
  public static Usuario usuarioConClave(UsuarioDTO aud_usuarioDTO) {

    Usuario lu_usuario = new Usuario();

    lu_usuario.setIdUsuario(Criptografia.encrypt(aud_usuarioDTO.getIdUsuario()));
    lu_usuario.setClaveHash(Criptografia.encrypt(aud_usuarioDTO.getClave()));
    lu_usuario.setIdUsuarioModificacion(aud_usuarioDTO.getIdUsuarioCreacion());
    lu_usuario.setIpModificacion(aud_usuarioDTO.getIp());
    lu_usuario.setFechaModificacion(aud_usuarioDTO.getTime());

    return lu_usuario;
  }
}
