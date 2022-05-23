package co.gov.supernotariado.bachue.dispositivos.app;

import org.apache.commons.lang3.StringUtils;

import co.gov.supernotariado.bachue.dispositivos.common.enums.ResponseEnum;
import co.gov.supernotariado.bachue.dispositivos.common.enums.TypeEnum;

import java.util.Arrays;
import java.util.List;

/**
 * Clase que toma los argumentos de inicializacion del programa y los procesa.
 *
 */
public class MainRequest {

   /**
    * Tipo de evento
    */
  private TypeEnum ite_type;
  /**
   * Lista de argumentos de las operaciones
   */
  private List<String> ils_parsedArgs;

  /**
   * Resultado de la operación
   */
  private ResponseEnum ire_result = ResponseEnum.INVALID_ARGUMENTS_COUNT;

  /**
   * Metodo que procesa el request de inicializacion.
   * @param als_args argumentos sin procesar.
   */
  public void parseArgs(List<String> als_args) throws IllegalArgumentException {
    if (als_args.size() != 0) {
      try {
        ils_parsedArgs = parseAtSigns(als_args);
        ite_type = TypeEnum.valueOf(ils_parsedArgs.get(0).toUpperCase());
      } catch (IllegalArgumentException liae_e) {
        ite_type = TypeEnum.INVALID;
      }
      ire_result = ResponseEnum.ARGUMENTS_SUCCESSFULLY_PARSED;
    }
  }

  /**
   * Metodo que procesa el request y lo divide usando como delimitador el signo @.
   * @param als_args argumento sin procesar.
   */
  private List<String> parseAtSigns(List<String> als_args) {
    String ls_parsedArgument =  StringUtils.substringAfter(als_args.get(0), "//");
    ls_parsedArgument = StringUtils.substringBefore(ls_parsedArgument, "/");

    return Arrays.asList(ls_parsedArgument.split("@"));
  }

  /**
   * Retorna el tipo de petición
   * @return enumeración del tipo de petición
   */
  public TypeEnum getType() {
    return ite_type;
  }

  /**
   * Obtiene los parámetros de la petición
   * @return Lista con los parámetros de la petición
   */
  public List<String> getParsedArgs() {
    return ils_parsedArgs;
  }

  /**
   * obtiene la respuesta de la petición
   * @return respuesta ante un request
   */
  public ResponseEnum getResult() {
    return ire_result;
  }
}