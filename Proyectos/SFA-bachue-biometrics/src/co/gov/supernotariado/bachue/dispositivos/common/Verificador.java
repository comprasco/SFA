package co.gov.supernotariado.bachue.dispositivos.common;

import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NMatchingSpeed;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.standards.BDIFStandard;
import com.neurotec.biometrics.standards.FMRecord;
import com.neurotec.io.NBuffer;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.model.Huella;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.common.util.MotorBiometrico;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.VerificacionDTO;
import weblogic.logging.LoggingHelper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

/**
 * Clase con el método para verificar que una huella le pertenece al un usuario 
 */
public class Verificador {
   /**
    * Logger de weblogic
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
      
   /**
    * Verifica que el template de la huella que llega en el objeto avd_verificacion le pertenezca el usuario avd_verificacion.getIdUsuario()
    * @param avd_verificacion objeto que contiene el usuario y el template de que se verificar� que le pertenezca al usuario 
    * @param alh_huellas lista de templates de huellas del usuario previamente enroladas 
    * @return booleano con el resultado de la verificación. true significa que la huella de entrada si pertenece al usuario, falso es que no le pertenece
    */
   public boolean verificar(VerificacionDTO avd_verificacion, List<Huella> alh_huellas) {
      ll_logger.info("Verificador: inicio");
      NSubject lns_candidatoSubject = new NSubject();
      NSubject lns_enroladoSubject = new NSubject();
      try {
         String ls_usuario = avd_verificacion.getIdUsuario();
         NBuffer lnb_candidatoTemplateBuffer;
         
         ll_logger.info("Número de huellas enroladas: " + Integer.toString(alh_huellas.size()) );
         byte[] lb_candidatoTemplateBytes = Base64.decodeBase64(avd_verificacion.getTemplate()); // bytes del template a verificar
         
         ll_logger.info("Template de entrada de tamaño " + Integer.toString(lb_candidatoTemplateBytes.length));
         if (lb_candidatoTemplateBytes.length < 8000) {
            lnb_candidatoTemplateBuffer = new NBuffer(lb_candidatoTemplateBytes); // FQ cargar el template directamente en el buffer
            ll_logger.info("NTEC Cargar el template específico NCITS");
            FMRecord lr_candidatoFRecord = new FMRecord(lnb_candidatoTemplateBuffer, BDIFStandard.ANSI);
            lns_candidatoSubject.setTemplate(lr_candidatoFRecord); // FQ Template de entrada cargado y listo para comparar
            ll_logger.info("NTEC Iniciar el motor biométrico");
            MotorBiometrico lm_motorBiometrico =  MotorBiometrico.getInstance();
            for (Huella lh_huella: alh_huellas) {
               ll_logger.info("NTEC Probar con la huella " + lh_huella.getPosicion());
               // Preparar los datos del template en un subject
               byte[] lnb_enroladoTemplateBytes = lh_huella.getTemplate();
               
               ll_logger.info("NTEC Template enrolado de tamaño " + Integer.toString(lnb_enroladoTemplateBytes.length));
               
               NBuffer lnb_enroladoTemplateBuffer = new NBuffer(lnb_enroladoTemplateBytes);
               FMRecord lr_enroladoFRecord = new FMRecord(lnb_enroladoTemplateBuffer, BDIFStandard.ANSI);
               lns_enroladoSubject.setTemplate(lr_enroladoFRecord); // FQ Template enrolado cargado y listo para comparar
               
               ll_logger.info("NTEC Comparar la huella");
               NBiometricStatus lnbs_status = lm_motorBiometrico.getCliente().verify(lns_enroladoSubject, lns_candidatoSubject);
               if (lnbs_status == NBiometricStatus.OK) {
                  ll_logger.info("NTEC Huella OK");
                  return true;
               }
            }
         } else {  // verificación a partir de extracción de minucia desde las imagenes
            crearCarpeta(ls_usuario);
            
            FileUtils.writeByteArrayToFile(new File("biometria/cache/" + Criptografia.decrypt(avd_verificacion.getIdUsuario())+ ".bmp"), lb_candidatoTemplateBytes);
            NBuffer lnb_bufferCandidate = Extractor.crearTemplate("biometria/cache/" + Criptografia.decrypt(avd_verificacion.getIdUsuario()) + ".bmp");
            
            for (Huella lh_huella: alh_huellas) {
               FileUtils.writeByteArrayToFile(new File("biometria/cache/" + Criptografia.decrypt(avd_verificacion.getIdUsuario())+ "/cache.bmp"), lh_huella.getTemplate());
               NBuffer lnb_bufferProbe = Extractor.crearTemplate("biometria/cache/" + Criptografia.decrypt(avd_verificacion.getIdUsuario())+ "/cache.bmp");
               
               if(lnb_bufferProbe != null && lnb_bufferCandidate != null){
                  lns_candidatoSubject.setTemplateBuffer(lnb_bufferCandidate);
                  lns_enroladoSubject.setTemplateBuffer(lnb_bufferProbe);
   
                  MotorBiometrico.getInstance().getCliente().setFingersMatchingSpeed(NMatchingSpeed.LOW);
                  NBiometricStatus lnbs_status = MotorBiometrico.getInstance().getCliente().verify(lns_enroladoSubject, lns_candidatoSubject);
                  if (lnbs_status == NBiometricStatus.OK) {
                     return true;
                  }
               } else {
                  return false;
               }
            }
         }
         return false;
      } catch (Exception le_excepcion) {
         ll_logger.log(Level.SEVERE, "Excepción " + le_excepcion.getMessage());
         
         Throwable lt_error = le_excepcion.getCause();
         while(le_excepcion.getCause() != null)
         {
            ll_logger.log(Level.SEVERE, "Excepción " + lt_error.getMessage());
            lt_error = lt_error.getCause();
         }
         
         ll_logger.log(Level.SEVERE, "Excepción " + le_excepcion.getStackTrace()[0]);
         return false;
      } finally {
         lns_candidatoSubject.close();
         lns_enroladoSubject.close();
      }
   }
   
   /**
    * Crea un directorio en el servidor para guardar temporalmente un archivo con la imagen de la huella
    * @param as_idUsuario ID del usuario que se va a verificar
    */
   private void crearCarpeta(String as_idUsuario) {
      File lf_carpeta = new File("biometria/cache");
      File lf_carpetaUsuario = new File("biometria/cache/" + Criptografia.decrypt(as_idUsuario));
      if (!lf_carpeta.exists()) lf_carpeta.mkdirs();
      if (!lf_carpetaUsuario.exists()) lf_carpetaUsuario.mkdirs();
   }
}
