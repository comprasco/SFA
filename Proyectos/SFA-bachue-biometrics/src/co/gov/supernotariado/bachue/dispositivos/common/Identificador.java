package co.gov.supernotariado.bachue.dispositivos.common;

import com.neurotec.biometrics.NBiometricOperation;
import com.neurotec.biometrics.NBiometricTask;
import com.neurotec.biometrics.NMatchingSpeed;
import com.neurotec.biometrics.NSubject;
import com.neurotec.io.NBuffer;

import co.gov.supernotariado.bachue.dispositivos.common.util.MotorBiometrico;
import weblogic.logging.LoggingHelper;

import java.util.EnumSet;
/**
 * Clase que identifica una huella en un conjunto de huellas
 */
public class Identificador {
   /**
    * Logger de weblogic
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();

   /**
    * Identifica una huella en un conjunto de huellas
    * @param as_uri directorio de huellas
    * @return verdadero solo si se logra identificar la hurlla
    */
   @SuppressWarnings("resource")
   public boolean identificar(String as_uri) {
      NSubject lns_subject = new NSubject();
      NBiometricTask lnbt_tarea;

      try {
         NBuffer lnb_buffer = Extractor.crearTemplate(as_uri);
         if(lnb_buffer != null){
            lns_subject.setTemplateBuffer(lnb_buffer);
         } else {
            return false;
         }

      } catch (Exception le_excepcion) {
         ll_logger.severe("Error en la verificación de huella (business): " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
         return false;
      }

      MotorBiometrico.getInstance().getCliente().setFingersMatchingSpeed(NMatchingSpeed.HIGH);
      lnbt_tarea = MotorBiometrico.getInstance().getCliente().createTask(EnumSet.of(NBiometricOperation.IDENTIFY), lns_subject);
      MotorBiometrico.getInstance().getCliente().performTask(lnbt_tarea);

      if (lns_subject.getMatchingResults().size() > 0) {
         System.err.println(lns_subject.getMatchingResults().get(0).getId());
         return true;
      } else {
         return false;
      }
   }
}
