package co.gov.supernotariado.bachue.dispositivos.common.util;

import weblogic.logging.LoggingHelper;

/**
 * Esta clase sive para calcular y registrar en el log del servidor el tiempo entre dos puntos del código, con fines de evaluación de desempeño
 */
public class DuracionLogger {
   /**
    * Logger de servidor WebLogic
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   
   /**
    * Momento de inicio del cálculo de duración
    */
   long ll_inicio;
   
   /**
    * Registra en el log el inicio de medición del tiempo 
    * @param MensajeInicio mensaje que aparecerá en el log del servidor para el inicio de la medición
    */
   public DuracionLogger(String MensajeInicio) {
      ll_inicio = System.nanoTime();
      ll_logger.info("DuracionLogger-inicio: " + MensajeInicio);
   }
   
   /**
    * Pone una mensaje en el log del servidor que servirá como marca. Le agrega al mensaje el tiempo transcurrido desde el inicio hasta esta marca en términos de milisegundos  
    * @param mensajeMarca mense que aparecerá en el log del servidor para la marca
    */
   public void Marca(String mensajeMarca)
   {
      long ll_final = System.nanoTime();
      double ld_duracion = (ll_final - ll_inicio)/1000000.0;
      ll_logger.info("DuracionLogger-marca: " + mensajeMarca + ", duración (ms aprox): " + Double.toString(ld_duracion));
   }
}
