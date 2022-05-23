package co.gov.supernotariado.bachue.dispositivos.common.util;

import com.neurotec.biometrics.NMatchingSpeed;
import com.neurotec.biometrics.NTemplateSize;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.licensing.NLicense;
import com.neurotec.plugins.NDataFile;
import com.neurotec.plugins.NDataFileManager;

import weblogic.logging.LoggingHelper;

/**
 * Gestiona la instancia del motor biom�trico
 */
public class MotorBiometrico {
   /**
    * Logger de weblogic
    */
   static java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   
   /**
    * Objeto de acceso al motor biométrico
    */
   private static MotorBiometrico imb_instancia;
   
   /**
    * Cliente neurotechnology
    */
   private NBiometricClient inbc_cliente;

   /**
    * Inicializa el motor biom�trico
    */
   private MotorBiometrico() {
      this.inicializarMotor();
   }

   /**
    * Inicializa el motor biom�trico de NeuroTechnology
    * @return motor biom�trico de neurotechnology
    */
   public static MotorBiometrico getInstance() {
      if(imb_instancia == null) {
        imb_instancia = new MotorBiometrico();
      }
      imb_instancia.configurarLicencias();
      imb_instancia.reconfigurarCliente();
      return imb_instancia;
   } 
   
   /**
    * Configura lo necesario antes de iniciar el motor biom�trico
    */
   private void inicializarMotor() {
      ll_logger.info("NTEC Inicializar motor");
      ll_logger.info("NTEC Inicializar librerías");
      ManejadorDeLibrerias.inicializarLibrerias();
      ll_logger.info("NTEC configurar licencias");
      configurarLicencias();
      ll_logger.info("NTEC configurar cliente");
      configurarCliente();
   }

   /**
    * Verifica que las licencias de neurotechnology están debidamente instaladas y activadas
    */
   private  void configurarLicencias() {
      String[] ls_licencias = { "FingerMatcher", "FingerExtractor", "FingerClient" };
      boolean lb_existeAlgunaLicencia = false;
      try {
         for (String ls_licencia : ls_licencias) {
            if (NLicense.obtain("/local", 5000, ls_licencia)) {
               System.err.format("Obtained license: %s%n", ls_licencia);
               lb_existeAlgunaLicencia = true;
            }
         }
         
         if (!lb_existeAlgunaLicencia) {
           System.err.println("Could not obtain any matching license");
         }
      } catch (Exception le_excepcion) {
         ll_logger.severe("Error en la configuración de Licencias: " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
      }
   }

   /**
    * Configura los parámetros el cliente del motor biométrico
    */
   private  void configurarCliente() {
      this.inbc_cliente = new NBiometricClient();
      this.inbc_cliente.setDatabaseConnectionToSQLite("cache.db");
      this.inbc_cliente.setFingersTemplateSize(NTemplateSize.LARGE);
      this.inbc_cliente.setMatchingThreshold(40);
      this.inbc_cliente.setFingersMatchingSpeed(NMatchingSpeed.HIGH);
      this.inbc_cliente.setMatchingMaximalResultCount(1);
      reconfigurarCliente();
   }

   /**
    * Termina de configurar el cliente para establecer directorios para gestionar templates
    */
   private void reconfigurarCliente() {
      String domainDir = System.getenv("DOMAIN_HOME");
      NDataFileManager.getInstance().addFromDirectory(domainDir + Utils.SEPARADOR_DE_ARCHIVOS + "bin" + Utils.SEPARADOR_DE_ARCHIVOS + "data", false);
      NDataFile[] files = NDataFileManager.getInstance().getAllFiles();
      for(NDataFile ndf : files) {
         System.err.println("BRS Data files: " + ndf.getFileName());
      }
   }

   /**
    * Obtiene el cliente biométrico, primero debio inicializar el motor usango getInstance()
    * @return cliente del biométrico de NeuroTechnology
    */
   public NBiometricClient getCliente() {
      return inbc_cliente;
   }
}
