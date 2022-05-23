package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Enum con los tipos de respuesta ante un request.
 */
public enum  ResponseEnum {
  ARGUMENTS_SUCCESSFULLY_PARSED(""),
  INVALID_ARGUMENTS_COUNT("Los argumentos de invocaci�n son insuficientes"),
  FAILED_SECOND_STEP("Ha ocurrido un error validando el m�todo de segundo factor de este usuario"),
  INVALID_COMMAND_ARGUMENT("Operaci�n no soportada");

   /**
    * Valor de la enuneraci�n
    */
  private final String cs_response;

  /**
   * Constructor del la enumeraci�n
   * @param as_response valor de la enumeraci�n
   */
  ResponseEnum(String as_response){
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
