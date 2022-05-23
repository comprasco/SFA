package co.gov.supernotariado.bachue.dispositivos.common.util;

import com.sun.jna.Platform;

import weblogic.logging.LoggingHelper;

import java.lang.reflect.Field;

/**
 * Clase que maneja la ubicación y carga en memoria de las librerías de Neurotechnology
 */
public class ManejadorDeLibrerias {
   /**
    * Logger de weblogic
    */
   static java.util.logging.Logger ll_logger = LoggingHelper.getServerLogger();
   // ===========================================================
   // Private static fields
   // ===========================================================

   /**
    * Connstante de plataforma Win32_x86
    */
   private static final String WIN32_X86 = "Win32_x86";
   /**
    * Connstante de plataforma Win64_x64
    */
   private static final String WIN64_X64 = "Win64_x64";
   /**
    * Connstante de plataforma Linux_x86
    */
   private static final String LINUX_X86 = "Linux_x86";
   /**
    * Connstante de plataforma Linux_x86_64
    */
   private static final String LINUX_X86_64 = "Linux_x86_64";
   /**
    * Connstante de plataforma Linux_armhf
    */
   private static final String ARM_32 = "Linux_armhf";
   /**
    * Connstante de plataforma Linux_arm64
    */
   private static final String ARM_64 = "Linux_arm64";

   /**
   * Cargar librerias de Neurotechnology.
   */
   public static void inicializarLibrerias() {
      String ls_libraryPath = obtenerRutaDelSistema();
      String ls_jnaLibraryPath = System.getProperty("jna.library.path");
      ll_logger.info("jna.library.path: " + ls_jnaLibraryPath);
      if (Utils.esNuloOVacio(ls_jnaLibraryPath)) {
         ll_logger.info("Se configura jna.library.path=" + ls_libraryPath);
         System.setProperty("jna.library.path", ls_libraryPath);
      } else {
         System.setProperty("jna.library.path",
               String.format("%s%s%s", ls_jnaLibraryPath, Utils.SEPARADOR_DE_CARPETAS, ls_libraryPath));
         ll_logger.info("Se RE-configura jna.library.path=" + String.format("%s%s%s", ls_jnaLibraryPath, Utils.SEPARADOR_DE_CARPETAS, ls_libraryPath));
      }
      
      System.setProperty("java.library.path",
            String.format("%s%s%s", System.getProperty("java.library.path"),
                    Utils.SEPARADOR_DE_CARPETAS, ls_libraryPath));
      ll_logger.info("Se configura java.library.path=" + String.format("%s%s%s", System.getProperty("java.library.path"), Utils.SEPARADOR_DE_CARPETAS, ls_libraryPath));

      try {
         Field lf_fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
         lf_fieldSysPath.setAccessible(true);
         lf_fieldSysPath.set(null, null);
      } catch (Exception le_excepcion) {
         ll_logger.severe("Error en la inicialización de librarías: " + le_excepcion.getMessage());
         for(StackTraceElement lest_causa : le_excepcion.getStackTrace()) {
            ll_logger.severe("Causa (inicialización):" + lest_causa.getClassName() + " - " + lest_causa.getMethodName() + " - " + lest_causa.getLineNumber() ); 
         }
      }
   }

   /**
    * Obtiene el directorio donde se ubican las librerías y licencias de Neurotechnology
    * @return directorio donde se ubican las librerías y licencias de Neurotechnology
    */
   public static String obtenerRutaDelSistema() {
      String domainDir = System.getenv("DOMAIN_HOME");
      StringBuilder lsb_path = new StringBuilder();
      lsb_path.append(domainDir).append(Utils.SEPARADOR_DE_ARCHIVOS).append("bin").append(Utils.SEPARADOR_DE_ARCHIVOS);
      if (Platform.isWindows()) {
         lsb_path.append(Platform.is64Bit() ? WIN64_X64 : WIN32_X86);
      } else if (Platform.isLinux()) {
         if (Platform.isARM()) {
            lsb_path.append(Platform.is64Bit() ? ARM_64 : ARM_32);
         } else {
            lsb_path.append(Platform.is64Bit() ? LINUX_X86_64 : LINUX_X86);
         }
      }
      ll_logger.info("Ruta base del sistema: " + lsb_path.toString());
      return lsb_path.toString();
   }
}
