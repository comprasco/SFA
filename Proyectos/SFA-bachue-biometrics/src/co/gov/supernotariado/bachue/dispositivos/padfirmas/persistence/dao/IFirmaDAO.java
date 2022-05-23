package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dao;

import java.util.List;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.model.Firma;

/**
* Interface de DAO de operaciones de firmas digitalizadas.
*/
@Local
public interface IFirmaDAO {
   /**
    * Método que extrae y envia los datos de la firma al DAO para ser almacenada.
    * @param af_firma DTO con la información de la firma.
    * @return true si la firma es guardad con éxito.
    */
   Boolean crearFirma(Firma af_firma);

   /**
    * Método que para consultar la firma almacenada en BD.
    * @param as_idFirma id de firma
    * @return true si la firma es procesada con éxito. Incluso si el resultado es negativo.
    */
   Boolean eliminarFirma(String as_idFirma);

   /**
    * Método que borra la firma.
    * @param as_idFirma id de firma
    * @return true si firma es borrada con éxito.
    */
   List<Firma> listarFirma(String as_idFirma);

}
