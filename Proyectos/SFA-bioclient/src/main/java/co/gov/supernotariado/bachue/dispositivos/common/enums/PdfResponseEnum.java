package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Respuestas para el evento de impresion de pdf.
 *
 */
public enum PdfResponseEnum {
    CANT_DOWNLOAD_FILE("No se ha podido descargar el archivo, por favor, verifique su conexión"),
    DOWNLOADED_AND_PRINTED("Archivo impreso exitosamente"),
    FILE_NOT_FOUND("No se ha podido encontrar el archivo"),
    PRINTER_ERROR("Ha ocurrido un error con su impresora"),
    PRINTING_CANCELED("Impresión cancelada"),
    UNKNOWN_ERROR("Ha ocurrido un error desconocido");

   /**
    * Valor de la enuneración
    */
  private final String cs_response;

  /**
   * Constructor del la enumeración
   * @param as_response valor de la enumeración
   */
  PdfResponseEnum(final String as_response) {
    this.cs_response = as_response;
  }

  /**
   * Obtiene el texto de la enumeración
   * @return texto de la enumeración
   */
  public String getResponse() {
    return cs_response;
  }
}
