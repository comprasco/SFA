package co.gov.supernotariado.bachue.dispositivos.common.util;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dto.HuellaDTO;
import co.gov.supernotariado.bachue.dispositivos.common.Identificador;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dto.AgregarFirmaDTO;
import weblogic.logging.LoggingHelper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Clase con operaciones utilitarias
 */
public class Utils {
   /**
    * Logger de weblogic
    */
   static java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();

   /**
    * Constante del caracter de separador de archivos
    */
   public static final String SEPARADOR_DE_ARCHIVOS = System.getProperty("file.separator");
  /**
   * Constante del caracter de separador de directorios
   */
   public static final String SEPARADOR_DE_CARPETAS = System.getProperty("path.separator");


   /**
    * Constructor de la clase
    */
   private Utils() {}

  /**
   * Devuelve el directorio activo.
   * @return direccctorio activo.
   */
   static String obtenerDirectorioActivo() {
      return System.getProperty("user.dir");
   }

   /**
    * Verifica qwue un cadena se anulo o vació
    * @param as_value cadena a verificar
    * @return verdadero solo si la cadena está vacía o es nula
    */
   static boolean esNuloOVacio(String as_value) {
      return as_value == null || "".equals(as_value);
   }

   /**
    * Borra el contenido de un directorio
    * @param as_carpeta carpeta que se va a borrar
    */
   public static void limpiarDirectorio(String as_carpeta) {
      try {
         FileUtils.cleanDirectory(new File(as_carpeta));
      } catch (Exception le_excepcion){
         ll_logger.severe("Error en la verificación de huella (business): " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
      }
   }

   /**
    * Guarda las huellas en un directorio loca
    * @param ahd_huella lista de huellas
    * @return Verdadero solo si pudo guardar las huellas en el directorio
    */
   public static boolean crearImagen(HuellaDTO ahd_huella) {
      try {
         byte[] lb_data = Base64.decodeBase64(ahd_huella.getTemplate());
         FileUtils.writeByteArrayToFile(new File("biometria/cache/" + Criptografia.decrypt(ahd_huella.getIdUsuario()) + ".bmp"), lb_data);
         return !new Identificador().identificar("biometria/cache/" + Criptografia.decrypt(ahd_huella.getIdUsuario()) + ".bmp");
      } catch (Exception le_excepcion) {
         ll_logger.severe("Error en la verificación de huella (business): " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
         return false;
      }
   }
   
   /**
    * Guarda la imagen de la firma en un directorio
    * @param ahd_firma DTO con la imagen de la firma
    * @return verdadero solo si pudo guardar la imagen de la firma
    */
   public static boolean crearImagenFirma(AgregarFirmaDTO ahd_firma) {
      try {
         byte[] lb_data = Base64.decodeBase64(ahd_firma.getFirma());
         FileUtils.writeByteArrayToFile(new File("biometria/cache/" + Criptografia.decrypt(ahd_firma.getIdUsuario()) + ".bmp"), lb_data);
         return !new Identificador().identificar("biometria/cache/" + Criptografia.decrypt(ahd_firma.getIdUsuario()) + ".bmp");
      } catch (Exception le_excepcion) {
         ll_logger.severe("Error en la verificación de huella (business): " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (business):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
         return false;
      }
   }
}

