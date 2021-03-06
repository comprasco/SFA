package co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.helper;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.model.Huella;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Usuario;

import org.apache.commons.codec.binary.Base64;


/**
 * Helper de conversion entre DTO y DAO de sesion.
 */
public class HuellaHelper {

   /**
    * Constructor de la clase
    */
   private HuellaHelper() {}

  /**
   * Método que mapea una entidad a su DTO correspondiente.
   * @param ah_entidad que sera convertida a DTO.
   * @return DTO mapeado desde la entidad.
   */
   public static HuellaDTO toDto(Huella ah_entidad) {
      HuellaDTO lhd_huella = new HuellaDTO();
      return lhd_huella;
   }

  /**
   * Método que recibe la petición HTTP de enrolamiento y la mapea al DTO.
   * @param ahd_huellaDTO que sera convertido a la entidad correspondiente.
   * @return entidad mapeada desde el DTO recibido.
   */
   public static Huella toEntity(HuellaDTO ahd_huellaDTO, Usuario au_usuario) {
      Huella lh_huella = new Huella();

      lh_huella.setPosicion(Integer.parseInt(ahd_huellaDTO.getPosicion().consultarPosicion()));
      lh_huella.setTemplate(Base64.decodeBase64(ahd_huellaDTO.getTemplate()));
      lh_huella.setUsuario(au_usuario);
      lh_huella.setFechaCreacion(ahd_huellaDTO.getTime());
      lh_huella.setIpCreacion(ahd_huellaDTO.getIp());
      lh_huella.setIdUsuarioCreacion(ahd_huellaDTO.getIdUsuarioCreacion());

      return lh_huella;
   }
}
