package co.gov.supernotariado.bachue.dispositivos.common.settings;

import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaController;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaWS;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.ConstantesSalidaDTO;

import org.apache.log4j.Logger;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

/**
 * Clase que lee el archivo config.ini para cargar las opciones.
 *
 */
public class Settings {
   /**
    * Logger de la aplicaci�n
    */
   private static Logger il_log = LoggerFactory.getLogger (Settings.class);
   
   /**
    * Indicador de que la configuraci�n ya fue establecida
    */
   private static boolean ib_configurado = false;
   
   /**
    * Objeto con las preferencias
    */
   private static Preferences ip_prefs;
   
   /**
    * URL del servicio de segundo factor de autenticaci�n
    */
   private static URL is_backendUrl;
   /**
    * Cadena que indica que el pad de firmas Topaz es usado o no usado
    */
   private static String is_pad;
   /**
    * Cadena que indica que se utilizar�n templates en lugar de im�genes en las tramas de huellas
    */
   private static String is_templates;
   /**
    * URL del servicio web Cliente OWCC
    */
   private static String is_pdfWS;
   
   /**
    * Patr�n por defecto de complejidad de segunda clave
    */
   private static String is_clavePatron = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-*@#$%^&+=]).{8,}";
   /**
    * Longitud m�nima de segunda clave
    */
   private static String is_claveMinimo = "8";
   /**
    * Longitud m�xima de segunda clave
    */
   private static String is_claveMaximo = "32";

   /**
    * Retorna la cadena URL del servicio web Cliente OWCC 
    * @return URL servicio web Cliente OWCC
    */
   public static String getPdfWS() {
      return is_pdfWS;
   }
   
   /**
    * Retorna la cadena que indica que el pad de firmas Topaz es usado o no usado 
    * @return cadena que indica que el pad de firmas Topaz es usado o no usado
    */
   public static String getPAD() {
      return is_pad;
   }

   /**
    * Retorna URL del servicio de segundo factor de autenticaci�n
    * @return URL del servicio de segundo factor de autenticaci�n
    */
   public static URL getBackendURL() {
      return is_backendUrl;
   }
   
   /**
    * Retorna cadena que indica que se utilizar�n templates en lugar de im�genes en las tramas de huellas
    * @return cadena que indica que se utilizar�n templates en lugar de im�genes en las tramas de huellas
    */
   public static String getTemplates() {
      return is_templates;
   }
   
   /**
    * Retorna patr�n por defecto de complejidad de segunda clave
    * @return Patr�n por defecto de complejidad de segunda clave
    */
   public static String getClavePatron() {
      return is_clavePatron;
   }
   
   /**
    * Retorna la longitud m�nima de segunda clave
    * @return longitud m�nima de segunda clave
    */
   public static String getClaveMinimo() {
      return is_claveMinimo;
   }
   
   /**
    * Retorna la longitud m�xima de segunda clave
    * @return longitud m�xima de segunda clave
    */
   public static String getClaveMaximo() {
      return is_claveMaximo;
   }
   
   /**
   * Metodo que lee las opciones desde el archivo de configuracion y el webservice de constantes
   */
   public static void readSettings() {
      Logger il_log = LoggerFactory.getLogger (Settings.class);
      il_log.info("Inicializaci�n del lector de la configuraci�n");
      initialize();
      
      if (ib_configurado) {
         il_log.info("La configuraci�n ya fue cargada");
         return;
      }
      
      try {
         is_backendUrl = new URL(ip_prefs.node("bachue").get("backend", null));
         il_log.info("backend: " + is_backendUrl);
         
         is_pad = new String(ip_prefs.node("bachue").get("pad", null));
         il_log.info("pad: " + is_pad);
         
         is_templates = new String(ip_prefs.node("bachue").get("templates", null));
         il_log.info("templates: " + is_templates);
         
         BiometriaController lbc_biometriaController = new BiometriaController(Settings.is_backendUrl);
         BiometriaWS lbwswebService = lbc_biometriaController.getBiometriaWSPort();
         ConstantesSalidaDTO lcsd_constantes = lbwswebService.obtenerConstantes();
         lcsd_constantes.getConstante().forEach(e -> {
            switch (e.getIdConstante()) {
            case "DESCARGA_DOCUMENTOS_PDF_ENDPOINT":
               is_pdfWS = e.getCaracter();
               il_log.info("pdf ws: " + is_pdfWS);
               break;
            case "CARACTERES_MINIMOS_CLAVE_SEGUNDO_FACTOR":
               is_claveMinimo = e.getCaracter();
               break;
            case "CARACTERES_MAXIMOS_CLAVE_SEGUNDO_FACTOR":
               is_claveMaximo = e.getCaracter();
               break;
            case "PATRON_CLAVE_SEGUNDO_FACTOR":
               is_clavePatron = e.getCaracter();
               break;
            }
         });
         ib_configurado = true;
      } catch (Exception le_e) {
         il_log.error("Error cargando la configuraci�n: ");
         PrintStackTrace(le_e);
      }
   }

   /**
   * Inicializa la configuracion y carga el archivo a la memoria.
   */
   private static void initialize() {
      try {
         Ini li_ini = new Ini(new File("config.ini"));
         ip_prefs = new IniPreferences(li_ini);
     } catch (IOException lioe_e) {
        PrintStackTrace(lioe_e);
     }
   }

   /**
    * Registro del error y la causa en el log
    * @param exception Excepci�n que se va a registrar
    */
   private static void PrintStackTrace(Throwable exception)
   {
      il_log.error("Error " + exception.getMessage());
      while(exception.getCause() == null)
      {
         il_log.error("Causa :" + exception.getCause().getMessage());
         exception = exception.getCause();
      }
   }
}
   