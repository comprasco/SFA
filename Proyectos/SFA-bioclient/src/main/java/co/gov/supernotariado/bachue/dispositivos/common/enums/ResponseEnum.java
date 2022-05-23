package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Enum con los tipos de respuesta ante un request.
 */
public enum  ResponseEnum {
  ARGUMENTS_SUCCESSFULLY_PARSED(""),
  INVALID_ARGUMENTS_COUNT("Los argumentos de invocación son insuficientes"),
  FAILED_SECOND_STEP("Ha ocurrido un error validando el método de segundo factor de este usuario"),
  INVALID_COMMAND_ARGUMENT("Operación no soportada");

   /**
    * Valor de la enuneración
    */
  private final String cs_response;

  /**
   * Constructor del la enumeración
   * @param as_response valor de la enumeración
   */
  ResponseEnum(String as_response){
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
