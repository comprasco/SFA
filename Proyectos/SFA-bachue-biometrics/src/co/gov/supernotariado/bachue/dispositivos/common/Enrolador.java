package co.gov.supernotariado.bachue.dispositivos.common;

import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NFRecord;
import com.neurotec.biometrics.NFTemplate;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.NTemplate;
import com.neurotec.io.NBuffer;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.model.Huella;
import co.gov.supernotariado.bachue.dispositivos.common.util.Criptografia;
import co.gov.supernotariado.bachue.dispositivos.common.util.MotorBiometrico;
import weblogic.logging.LoggingHelper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Clase que getiona el enrolamiento de huellas
 */
public class Enrolador {
   /**
    * Logger de weblogic
    */
   java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   /**
    * Sujeto con huellas
    */
   private NSubject ins_subject;
   /**
    * Buffer de templates de huellas
    */
   private NBuffer inb_buffer;
   
   /**
    * Template de hiella
    */
   private NFTemplate inft_nfTemplate;
   
   /**
    * Huellas a enrolar
    */
   private HuellaDTO ihd_huellaDTO;

  /**
   * Constructor de la clase
   * @param ahd_huellaDTO conjunto de huellas a enrolar
   */
   public Enrolador(HuellaDTO ahd_huellaDTO) {
      this.ihd_huellaDTO = ahd_huellaDTO;
   }

  /**
   * Enrola el usuario en la base de datos
   * @return Resultado de la operacion de enrolamiento
   */
   @SuppressWarnings("resource")
   public boolean enrolarUsuario(List<Huella> alh_huellas) {
      boolean lb_estado = false;
      try {
         crearCarpeta(ihd_huellaDTO.getIdUsuario());
         inft_nfTemplate = new NFTemplate();

         for (Huella lh_huella: alh_huellas) {
            FileUtils.writeByteArrayToFile(new File("biometria/cache/" + Criptografia.decrypt(ihd_huellaDTO.getIdUsuario())+ "/cache.bmp"), lh_huella.getTemplate());
            inb_buffer = Extractor.crearTemplate("biometria/cache/" + Criptografia.decrypt(ihd_huellaDTO.getIdUsuario())+ "/cache.bmp");
            NTemplate template = new NTemplate(inb_buffer);
            if (template.getFingers() != null) {
               for (NFRecord record : template.getFingers().getRecords()) {
                  inft_nfTemplate.getRecords().add(record);
               }
            }
            template.dispose();
            inb_buffer.dispose();
         }

         crearSubject();
         lb_estado = enrolarTemplate();

      } catch (Exception le_excepcion) {
         ll_logger.severe("Error en la verificación de huella (business): " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
      } finally {
         limpiar();
      }
      return lb_estado;
   }

   /**
    * Elimina huellas previas del usuario
    * @param as_id id de usuario
    * @return verdadero solo si se pudieron eliminar las huellas anteriores
    */
   public boolean eliminarHuellas(String as_id) {
      NBiometricStatus lnbs_estado = MotorBiometrico.getInstance().getCliente().delete(as_id);
      return lnbs_estado != NBiometricStatus.OK;
   }

   /**
    * Crea un sujeto
    */
   private void crearSubject() {
      ins_subject = new NSubject();
      if (inft_nfTemplate != null) {
         ins_subject.setTemplate(new NTemplate(inft_nfTemplate.save()));
         ins_subject.setId(this.ihd_huellaDTO.getIdUsuario());
      }
   }

   /**
    * Enrola el template de las huellas
    * @return verdadero solo si se enrolan las huellas exitosamente
    * @throws Exception
    */
   private boolean enrolarTemplate() throws Exception {
      NBiometricStatus lnbs_estado = MotorBiometrico.getInstance().getCliente().enroll(ins_subject);
      if (lnbs_estado != NBiometricStatus.OK) {
         if (lnbs_estado == NBiometricStatus.DUPLICATE_ID || lnbs_estado == NBiometricStatus.DUPLICATE_FOUND) {
            MotorBiometrico.getInstance().getCliente().delete(this.ihd_huellaDTO.getIdUsuario());
            lnbs_estado = MotorBiometrico.getInstance().getCliente().enroll(ins_subject);
            return lnbs_estado == NBiometricStatus.OK;
         } else {
            throw new Exception("Enrollment was unsuccessful. Status: " + lnbs_estado.toString());
         }
      } else {
         return true;
      }
   }

   /**
    * Crea un directorio para gestionar temporalmente las huellas del usuario
    * @param as_idUsuario id de usuario
    */
   private void crearCarpeta(String as_idUsuario) {
      File lf_carpetaUsuario = new File("biometria/cache/" + Criptografia.decrypt(as_idUsuario));
      if (!lf_carpetaUsuario.exists()) lf_carpetaUsuario.mkdirs();
   }

   /**
    * Limpia el espacio de memoria utilizada por el sujeto
    */
   private void limpiar() {
      if (ins_subject != null) {
         ins_subject.dispose();
      }
   }
}
