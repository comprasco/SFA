package co.gov.supernotariado.bachue.dispositivos.common.settings;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Clase que inicializa el log de la aplicación, basada en log4j
 *
 */
public abstract class LoggerFactory {

   /**
    * Nombre del archivo de propiedades del log 
    */
   public static final String LOG4J_CONFIG_FILE = "log4j.properties";
   /**
    * Indicador de inicialización del log
    */
   private static boolean ib_log4jinitialized = false;
   /**
    * Logger de la aplicación
    */
   static Logger il_log = Logger.getLogger(LoggerFactory.class);
   
   /**
    * Inicializador de la clase del log
    * @param ac_class objeto que utilizará las funciones de log
    * @return un objeto Logger que serivirá al objeto para registrar en el log de la aplicación
    */
   public static Logger getLogger(Class ac_class) {
      if (ib_log4jinitialized == false) {
         createLogDirectory();
         init();
      }
      return Logger.getLogger(ac_class);
   }

   /**
    * Inicializador de la clase
    */
   private static synchronized void init() {
      if (!ib_log4jinitialized) {
         String ls_PropertieName ="log4j.properties";
         String ls_configurationFile = System.getProperty("user.dir")+"/src/" + ls_PropertieName;
         PropertyConfigurator.configureAndWatch(ls_configurationFile);
         ib_log4jinitialized = true;
         il_log.info("Logger inicializado log4j.properties Version: 1.0.0 05/04/2020");
      }
   }

   /**
    * Crea el directorio donde se guarda el log
    */
   private static void createLogDirectory() {
      File lf_directorio = new File("/LogsBioCliente");
      if (!lf_directorio.exists()) {
         if (lf_directorio.mkdirs()) {
            System.out.println("Directorio Creado correctamente :/LogsBioCliente");
         } else {
            System.out.println("Error al crear directorio :/LogsBioCliente");
         }
      }
   }
}
