package co.gov.supernotariado.bachue.dispositivos.biometrico;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.machinezoo.sourceafis.FingerprintCompatibility;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintTemplate;
/* Dependencias:
    commons-io-2.6.jar
    fastutil-8.3.0.jar
    fingerprintio-0.1.1.jar
    gson-2.8.6.jar
    jackson-annotations-2.10.2.jar
    jackson-core-2.10.2.jar
    jackson-databind-2.10.2.jar
    jackson-dataformat-cbor-2.10.2.jar
    jnbis-2.0.2.jar
    noexception-1.4.4.jar
    sanselan-0.97-incubator.jar
    slf4j-api-1.7.28.jar
    sourceafis-3.10.0.jar
*/

/**
 * Esta clase extrae la minucia usando el formato estándar recomendado de intercambio de minucias ANSI/INCITS 378:2004
 * INCITS 378-2004: Information Technology - Finger Minutiae Format for Data Interchange
 *
 */
public class INCITSExtractor {

   /**
    * Extrae la minucia o template en format INCITS378 a partir de una imágen BMP obtenida por el sensor Futronic
    * @param ai_hImage Imagen BMP obtenida directamente del sensor Futronic
    * @return arreglo de bytes con la minucia en formato INCITS378
    * @throws IOException
    */
   public byte[] extraerTemplate(BufferedImage ai_hImage) throws IOException {
      ByteArrayOutputStream lbaos_baos = new ByteArrayOutputStream();
      ImageIO.write(ai_hImage, "bmp", lbaos_baos);
      lbaos_baos.flush();
      byte[] lb_imageInByte = lbaos_baos.toByteArray();
      
      File lf_outputfile = new File("/bioclient/logs/huella.png");
      ImageIO.write(ai_hImage, "png", lf_outputfile);
      
      FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage()
                 .dpi(500)
                 .decode(lb_imageInByte)); // Files.readAllBytes(Paths.get("/tmp/archivo.bmp")))

      FileOutputStream lf_fos = null;
      try
      {
         lf_fos = new FileOutputStream("/bioclient/logs/huella.Ansi");
         lf_fos.write(FingerprintCompatibility.toAnsiIncits378v2004(probe));
         lf_fos.flush();
         lf_fos.close();
      }
      finally
      {
         lf_fos.close();
      }
      
      
      return FingerprintCompatibility.toAnsiIncits378v2004(probe);
   }
}
