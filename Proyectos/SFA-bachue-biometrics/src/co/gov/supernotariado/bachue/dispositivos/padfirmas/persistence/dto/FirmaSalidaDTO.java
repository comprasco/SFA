package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto;

import javax.xml.bind.annotation.XmlElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BaseSalidaDTO;

/**
 * DTO de salida para booleanos.
 */
public class FirmaSalidaDTO extends BaseSalidaDTO {

   /**
    * Resultado del la operación
    */
  private String is_resultado;

  /**
   * Retorna el resultado de la operación
   * @return resultado de la operación
   */
  @XmlElement(name = "resultado")
  public String getResultado() {
    return is_resultado;
  }

  /**
   * Establece el resultado de la operación
   * @param as_resultado resultado de la operación
   */
  public void setResultado(String as_resultado) {
    this.is_resultado = as_resultado;
  }
}
