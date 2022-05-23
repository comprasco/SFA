package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.helper;

import org.apache.commons.codec.binary.Base64;

import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaDTO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.model.Firma;

/**
 * Helper de conversion entre DTO y DAO de la firma 
 */
public class FirmaHelper {
   /**
    * Constructor de la clase
    */
   private FirmaHelper() {}

   /**
    * Metodo que mapea una entidad a su DTO correspondiente.
    * @param af_entidad que sera convertida a DTO.
    * @return DTO mapeado desde la entidad.
    */
   public static AgregarFirmaDTO toDto(Firma af_entidad) {
      AgregarFirmaDTO lafd_firma = new AgregarFirmaDTO();
      return lafd_firma;
   }

   /**
    * Metodo que recibe la peticion HTTP de enrolamiento y la mapea al DTO.
    * @param aafd_firma DTO de la solicitud de firma.
    * @return entidad mapeada desde el DTO recibido.
    */
   public static Firma toEntity(AgregarFirmaDTO aafd_firma) {
      Firma lf_firma = new Firma();
      lf_firma.setIdFirma(aafd_firma.getIdTramite().toString()+aafd_firma.getNumeroDocCiudadano().toString());
      lf_firma.setImage(Base64.decodeBase64(aafd_firma.getFirma()));
      lf_firma.setFechaCreacion(aafd_firma.getTime());
      lf_firma.setIpCreacion(aafd_firma.getIp());
      lf_firma.setIdUsuarioCreacion(aafd_firma.getIdUsuario());

      return lf_firma;
   }
}
