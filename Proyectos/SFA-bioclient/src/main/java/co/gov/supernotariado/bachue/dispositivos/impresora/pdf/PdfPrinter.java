package co.gov.supernotariado.bachue.dispositivos.impresora.pdf;

import co.gov.supernotariado.bachue.dispositivos.common.enums.PdfResponseEnum;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;
import https.www_supernotariado_gov_co.schemas.bachue.co.busquedadocumentos.obtenerarchivo.v1.TipoEntradaObtenerArchivo;
import https.www_supernotariado_gov_co.schemas.bachue.co.busquedadocumentos.obtenerarchivo.v1.TipoSalidaObtenerArchivo;
import https.www_supernotariado_gov_co.services.bachue.co.busquedadocumentos.v1.SUTCOBusquedaDocumentos;
import https.www_supernotariado_gov_co.services.bachue.co.busquedadocumentos.v1.SUTCOBusquedaDocumentos_Service;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.xml.ws.WebServiceException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;

/**
 * Clase que descarga el pdf y lo gestiona hacia la impresora.
 *
 */
public class PdfPrinter {
   /**
    * Logger de la aplicación
    */
   private Logger il_log = LoggerFactory.getLogger (getClass ());
   
   /**
    * dId del documento en el SGD
    */
   private String is_idDocument;
   /**
    * docName del documento en el SGD
    */
   private String is_nameDocument;
   /**
    * Número de copias
    */
   private Long is_cantidadCopias;

   /**
    * Constructor de la clase
    * @param as_idDocument dID del documento, como se almacena en el SGD
    * @param as_nameDocument docName del documento, como se almancena en el SGD
    * @param as_cantidadCopias número de copias que se imprimiran del documento
    */
   public PdfPrinter(String as_idDocument, String as_nameDocument,String as_cantidadCopias) {
      this.is_idDocument = as_idDocument;
      this.is_nameDocument = as_nameDocument;
      //parametro de cantidad de copias
      this.is_cantidadCopias = new Long (as_cantidadCopias);
   }

   /**
   * Metodo que envia la orden de impresion.
   * @param apd_document archivo que sera impreso.
   */
   private void printWithDialog(PDDocument apd_document) throws IOException, PrinterException, InterruptedException {
      PrinterJob lpj_printerJob = PrinterJob.getPrinterJob();
      il_log.debug("PDF iniciar impresión, copias: " + is_cantidadCopias.toString());
      lpj_printerJob.setPageable(new PDFPageable(apd_document));
      for (int li_copia= 0;li_copia<is_cantidadCopias;li_copia++) {
         lpj_printerJob.print();
      }
      il_log.debug("PDF fin impresión");
   }

   /**
   * Metodo que descarga el pdf del webservice.
   * @return arreglo de bytes del PDF que se va a imprimir
   * @throws FileNotFoundException
   * @throws SocketException
   * @throws WebServiceException
   * @throws MalformedURLException
   */
   public byte[] downloadPDF() throws FileNotFoundException, SocketException, WebServiceException, MalformedURLException {
      il_log.debug("Setup endpoint para obtener el archivo: " + Settings.getPdfWS());
      URL lu_wsLocation = new URL(Settings.getPdfWS());
      
      il_log.debug("Configurar la conexión a la URL");
      SUTCOBusquedaDocumentos_Service lscbd_services = new SUTCOBusquedaDocumentos_Service(lu_wsLocation);
      
      il_log.debug("Establecer el puerto del endpoint");
      SUTCOBusquedaDocumentos lscbd_content = lscbd_services.getSUTCOBusquedaDocumentosPort();

      il_log.debug("Establecer parámetros del servicio");
      TipoEntradaObtenerArchivo lteoa_meta = new TipoEntradaObtenerArchivo();
      lteoa_meta.setDID(is_idDocument);
      lteoa_meta.setDDocName(is_nameDocument);

      il_log.debug("Invocar la obtención del archivo pdf");
      TipoSalidaObtenerArchivo ltsoa_archivo = lscbd_content.obtenerArchivo(lteoa_meta);
      
      if (ltsoa_archivo.getArchivo() == null) {
         il_log.debug("Archivo descargado está vacío");
         throw new FileNotFoundException();
      }
      
      il_log.debug("Archivo descargado");
      return ltsoa_archivo.getArchivo();
    }

    /**
    * Metodo que orquesta la descarga e impresion del archivo.
    * @return respuesta de la operción de imprimir documento 
    */
    public PdfResponseEnum downloadAndPrint() {
       il_log.debug("Inicio");
       PdfResponseEnum lpre_response;
       try {
          byte[] lb_document = downloadPDF();
          il_log.debug("Convertir el documento recibido a la estructura PDF");
          PDDocument pdf = PDDocument.load(lb_document);
          il_log.debug("Enviar el PDF a impresión");
          printWithDialog(pdf);
          il_log.debug("Cerrar el documento PDF");
          pdf.close();
          pdf = null;
          lpre_response = PdfResponseEnum.DOWNLOADED_AND_PRINTED;
       } catch (FileNotFoundException lfnfe_e) {
          il_log.fatal(">FileNotFoundException: " + lfnfe_e.getMessage());
          lpre_response = PdfResponseEnum.FILE_NOT_FOUND;
       } catch (SocketException | MalformedURLException lse_e) {
          il_log.fatal(">SocketException | MalformedURLException: " + lse_e.getMessage());
          lpre_response = PdfResponseEnum.CANT_DOWNLOAD_FILE;
       } catch (IOException | WebServiceException lioe_e) {
          il_log.fatal(">IOException | WebServiceException: " + lioe_e.getMessage());
          lpre_response = PdfResponseEnum.CANT_DOWNLOAD_FILE;
       } catch (PrinterException lpe_e) {
          il_log.fatal(">PrinterException: " + lpe_e.getMessage());
          lpre_response = PdfResponseEnum.PRINTER_ERROR;
       } catch (InterruptedException lie_e) {
          il_log.fatal(">InterruptedException: " + lie_e.getMessage());
          lpre_response = PdfResponseEnum.PRINTING_CANCELED;
          Thread.currentThread().interrupt();
       } catch (Exception le_e){
          il_log.fatal(">Exception: " + le_e.getMessage());
          lpre_response = PdfResponseEnum.UNKNOWN_ERROR;
       }
       il_log.debug("Fin");
       return lpre_response;
    }
}
