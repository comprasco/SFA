package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Respuestas para el evento de impresion de pdf.
 *
 */
public enum PdfResponseEnum {
    CANT_DOWNLOAD_FILE("No se ha podido descargar el archivo, por favor, verifique su conexi�n"),
    DOWNLOADED_AND_PRINTED("Archivo impreso exitosamente"),
    FILE_NOT_FOUND("No se ha podido encontrar el archivo"),
    PRINTER_ERROR("Ha ocurrido un error con su impresora"),
    PRINTING_CANCELED("Impresi�n cancelada"),
    UNKNOWN_ERROR("Ha ocurrido un error desconocido");

   /**
    * Valor de la enuneraci�n
    */
  private final String cs_response;

  /**
   * Constructor del la enumeraci�n
   * @param as_response valor de la enumeraci�n
   */
  PdfResponseEnum(final String as_response) {
    this.cs_response = as_response;
  }

  /**
   * Obtiene el texto de la enumeraci�n
   * @return texto de la enumeraci�n
   */
  public String getResponse() {
    return cs_response;
  }
}
