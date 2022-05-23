package co.gov.supernotariado.bachue.dispositivos.biometrico;

import com.Futronic.ScanApiHelper.Scanner;

import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

/**
 * Clase que gestiona la interaccion nativa con el sensor
 *
 */
public class Device {
   /**
    * Logger de la aplicación
    */
   private transient Logger il_log = LoggerFactory.getLogger (getClass ());
   
   /**
    * Objeto de acceso al driver del sensor de huellas
    */
   private Scanner is_devScan;
   /**
    * Buffer de la imagen capturada de la huella
    */
   private BufferedImage ibi_hImage;
   
   /**
    * Indicador de huella no detectada en la imagen del sensor
    */
   private boolean ib_fingerNotDetected = false;

   /**
   * Metodo que detiene el dispositivo
   */
   public void stopDevice() {
      ib_fingerNotDetected = false;
   }

   /**
   * Metodo que captura la huella desde el sensor
   * @return resultado de la operacion
   */
   @SuppressWarnings("unused")
   public String btnGetFrameActionPerformed() {
      int li_nWidth, li_nHeight;
      int li_option;
      String ls_strInfo;

      if(!is_devScan.OpenDevice()) {
         return "Lector no encontrado o driver no instalado";
      }
    
      ls_strInfo = is_devScan.GetVersionInfo();
      if(ls_strInfo == null) {
         return is_devScan.GetErrorMessage();
      } else {
         System.out.println(ls_strInfo);
      }
    
      if(!is_devScan.GetImageSize()) {
         is_devScan.CloseDevice();
         return "Ha ocurrido un error durante la captura de la huella";
      }
    
      li_nWidth = is_devScan.GetImageWidth();
      li_nHeight = is_devScan.GetImaegHeight();
      li_option = 0;
      if(!is_devScan.SetOptions(is_devScan.FTR_OPTIONS_CHECK_FAKE_REPLICA, li_option)) {
         is_devScan.CloseDevice();
         return "Huella no válida";
      }
    
      li_option = is_devScan.FTR_OPTIONS_INVERT_IMAGE;
      if(!is_devScan.SetOptions(is_devScan.FTR_OPTIONS_INVERT_IMAGE, li_option)) {
         is_devScan.CloseDevice();
         return "No se ha podido procesar la imagen";
      }

      ib_fingerNotDetected = true;
      long ll_startTime = System.currentTimeMillis();

      while(ib_fingerNotDetected) {
         if((new Date()).getTime() - ll_startTime > 15000) {
            return "Tiempo de espera superado";
         }
         if(is_devScan.IsFingerPresent()) {
            ib_fingerNotDetected = false;
            try {Thread.sleep(200);} catch (Exception ie_excepcion){
               il_log.info("Excepción: " + ie_excepcion.getMessage());
            }
         }
      }

      byte[] lb_pImage = new byte[li_nWidth * li_nHeight];
      if(!is_devScan.GetFrame(lb_pImage)) {
         is_devScan.CloseDevice();
         return "Imagen no válida";
      }
      is_devScan.CloseDevice();
    
      if(lb_pImage != null) {
         ibi_hImage = new BufferedImage(li_nWidth, li_nHeight, BufferedImage.TYPE_BYTE_GRAY);
         DataBuffer ldb_db1 = ibi_hImage.getRaster().getDataBuffer();
         for(int i = 0; i < ldb_db1.getSize(); i++) {
            ldb_db1.setElem( i, lb_pImage[i] );
         }
         return "Huella enrolada con éxito";
      }
      return "Huella enrolada con éxito";
   }

   /**
   * Método que toma la imágen de huella capturada por el sensor Futronic y extrae las minucias en formato ANSI/NCITS 378:2004
   * @return template de la huella
   * @throws InterruptedException 
   */
   public String btnSaveActionPerformed() throws InterruptedException {
      if(ibi_hImage != null) {
         try {
            boolean lb_templates = (Settings.getTemplates().toLowerCase().compareTo("si") == 0);
            if (lb_templates) {
               INCITSExtractor le_extractor = new INCITSExtractor();
               byte[] ls_template = le_extractor.extraerTemplate(ibi_hImage);
               return Base64.getEncoder().encodeToString(ls_template);
            } else {
               ByteArrayOutputStream lbaos_baos = new ByteArrayOutputStream();
               ImageIO.write(ibi_hImage, "bmp", lbaos_baos);
               lbaos_baos.flush();
               byte[] lb_imageInByte = lbaos_baos.toByteArray();
        
               return Base64.getEncoder().encodeToString(lb_imageInByte);
            }
         } catch (IOException lioe_e) {
            PrintStackTrace(lioe_e);
            return null;
         }
      }
      return null;
   }

   /**
    * Obtiene el objeto que accede al sensor de huellas
    * @return objeto que accede al sensor de huellas
    */
   public Scanner getM_devScan() {
      return is_devScan;
   }
   
   /**
    * Establece el acceso al dispositivo sensor de huellas
    * @param as_devScan objeto que accede al sensor
    */
   public void setM_devScan(Scanner as_devScan) {
      this.is_devScan = as_devScan;
   }
  
   /**
    * Metodo que toma la firma captura y la convierte al formato de imágen requerido
    * @param abu_hImage imagen de la huella
    * @return Imagen de la huella en base 64
    */
   public String btnSaveActionSign(BufferedImage abu_hImage) {
      if(abu_hImage != null) {
         try {
            ByteArrayOutputStream lbaos_baos = new ByteArrayOutputStream();
            ImageIO.write(abu_hImage, "bmp", lbaos_baos);
            lbaos_baos.flush();
            byte[] lb_imageInByte = lbaos_baos.toByteArray();
            return Base64.getEncoder().encodeToString(lb_imageInByte);
         } catch (IOException lioe_e) {
            PrintStackTrace(lioe_e);
            return null;
         }
      }
      return null;
   }

   /**
    * Registro del error y la causa en el log
    * @param exception Excepción que se va a registrar
    */
   private void PrintStackTrace(Throwable exception)
   {
      il_log.error("Error " + exception.getMessage());
      while(exception.getCause() == null)
      {
         il_log.error("Causa :" + exception.getCause().getMessage());
         exception = exception.getCause();
      }
   }
}
