package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Enum con los tipos de request.
 *
 */
public enum TypeEnum {
  ENROLL("ENROLAMIENTO"),
  PDF("PDF"),
  SIGN("FIRMA"),
  VERIFY("VERIFICACION"),
  RESET("RESET"),
  STOP("STOP"),
  INVALID("NO SOPORTADO");

   /**
    * Valor de la enuneración
    */
  private final String cs_event;

  /**
   * Constructor de la enumerción
   * @param as_event valor de la enumeración
   */
  TypeEnum(String as_event) {
    this.cs_event = as_event;
  }

  /**
   * Obtiene el texto de la enumeración
   * @return texto de la enumeración
   */
  public String getEvent() {
    return cs_event;
  }
}
