package co.gov.supernotariado.bachue.dispositivos.app;


import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaController;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaWS;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BorrarHuellasDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.StringSalidaDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.UsuarioEntradaDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.ui.EnrollWindow;
import co.gov.supernotariado.bachue.dispositivos.biometrico.ui.VerifyWindow;
import co.gov.supernotariado.bachue.dispositivos.common.enums.PdfResponseEnum;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;
import co.gov.supernotariado.bachue.dispositivos.common.websocket.MessagingAdapter;
import co.gov.supernotariado.bachue.dispositivos.common.websocket.MessagingServer;
import co.gov.supernotariado.bachue.dispositivos.impresora.pdf.PdfPrinter;
import co.gov.supernotariado.bachue.dispositivos.log.http.impl.LogRequestsImpl;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.ui.SignWindow;
import co.gov.supernotariado.bachue.dispositivos.ui.MessageBox;
import co.gov.supernotariado.bachue.dispositivos.ui.PasswordWindow;

import org.apache.log4j.Logger;

import static co.gov.supernotariado.bachue.dispositivos.common.enums.TypeEnum.PDF;

import java.util.List;

import javax.swing.JOptionPane;

/**
 * Clase que gestiona las diferentes tareas de la aplicacion.
 *
 */
public class App {
   /**
    * Indicador de que la aplicación está procesando una opecación
    */
   private static boolean ib_serverProcesingOperation = false;
   
   /**
    * Logger de la aplicación
    */
   private transient Logger il_log = LoggerFactory.getLogger (getClass ());
	
   /**
    * Establece el valor de estado de procesamiento de una petición en la App
    * @param value verdadero o falso
    */
   public static void setProcessingOperation(boolean value)
   {
      ib_serverProcesingOperation = value;
   }
   
   /**
    * Obtiene el estado de procesamiento de la clase App
    * @return
    */
   public static boolean getProcessingOperation()
   {
      return ib_serverProcesingOperation;
   }
   
   /**
    * Constructor de la clase
    * @param als_args argumentos de la petición
    */
   App() {
      il_log.debug("Leer configuración");
      Settings.readSettings();
      il_log.debug("Parámetro pdfWS :" + Settings.getPdfWS());
      MessagingServer lms_server = new MessagingServer();
      lms_server.setup();
      il_log.debug("Iniciar servidor");
      il_log.debug("Este software incluye la librería SourceAFIS 3.10.0, el cual es software libre, amparado por la licencia Apache 2.0, la cual otorga el derecho de uso en software comercial ");
      il_log.debug("  https://github.com/robertvazan/sourceafis-java/blob/master/LICENSE");
      new Thread(lms_server::start).start();
   }
   
   /**
    * Procesa una operación solicitada por el navegador
    * @param lmr_mainRequest trama con los parámetros de la operación
    */
   public void procesarOperacion(MainRequest lmr_mainRequest) {
	   il_log.debug("Iniciando proceso: " +lmr_mainRequest.getType().toString());
      try
      {
         if (Settings.getPdfWS() == null || Settings.getPdfWS() == "") {
            il_log.debug("Los parámetros deben cargarse para poder procesar");
            Settings.readSettings();
         }
    	   App.setProcessingOperation(true);
    	
	      List<String> lls_parsedArgs = lmr_mainRequest.getParsedArgs();
	      
	      // FQ validar que todos los parámetros hayan sido enviados
	      for(String arg : lls_parsedArgs)
	      {
	         il_log.debug("Argumento : " + arg);
	         if (arg.isEmpty()) {
	            MessageBox.display("Invocación incorrecta de la operación", JOptionPane.INFORMATION_MESSAGE, false);
	            MessagingAdapter.returnResponse(lmr_mainRequest.getType().toString(),"fail");
	         }
	      }
	      
	      switch (lmr_mainRequest.getType()) {
	      case STOP:
	         il_log.debug("Solicitud de detención");
	         System.exit(0);
	         break;
	      case PDF:
            il_log.debug("Procesando peticion: PDF para "+lls_parsedArgs.get(1));
	    	   PdfResponseEnum lpre_response = new PdfPrinter(lls_parsedArgs.get(1), lls_parsedArgs.get(2), lls_parsedArgs.get(5)).downloadAndPrint();
	    	   il_log.debug("Fin proceso descarga e impresion >" + lpre_response.getResponse());
	    	   
	         if(!lpre_response.equals(PdfResponseEnum.DOWNLOADED_AND_PRINTED)) {
	            il_log.debug("Proceso de impresion PDF fallido se registrara la falla de la impresión en la BD");
	            new LogRequestsImpl().saveLog(PDF, lpre_response.getResponse() + ". ID: " + lls_parsedArgs.get(1), lls_parsedArgs.get(1) + "-" + lls_parsedArgs.get(2) +"-" + lls_parsedArgs.get(4), lls_parsedArgs.get(3));
	            il_log.debug("Se informa el resultado fallido al browser");
	            MessagingAdapter.returnResponse("pdf",lpre_response.getResponse());
	         } else {
	            il_log.debug("Impresión terminada exitosamente");
	      	   Long ll_cantidadCopias = new Long(lls_parsedArgs.get(5));
	      	   //se almacena la cantidad de veces que se imprime el PDF
	            il_log.debug("Se Registrara el resultado de la impresion en la BD");
	            for(int li_indexCopia=0;li_indexCopia<ll_cantidadCopias.longValue();li_indexCopia++){
	              new LogRequestsImpl().saveLog(PDF, lpre_response.getResponse(), lls_parsedArgs.get(1) + "-" + lls_parsedArgs.get(2)+"-" + lls_parsedArgs.get(4), lls_parsedArgs.get(3));	  
	            }
	            il_log.debug("Se Informara al browser el resultado exitoso PDF:"+lls_parsedArgs.get(1));
	            MessagingAdapter.returnResponse("pdf","success");
	         }
	         App.setProcessingOperation(false);
	         break;
	      case SIGN:
            il_log.debug("Procesando peticion: SIGN cedula" +lls_parsedArgs.get(2) );
            String ls_padActivo = new String(Settings.getPAD());
            //se reciben los parametros del formulario web (idTramite, idNumeroDocumento, tipoDocumento, idUsaurio
            SignWindow lsw_singWindow = new SignWindow(lls_parsedArgs.get(1), new Long (lls_parsedArgs.get(2)),lls_parsedArgs.get(3),lls_parsedArgs.get(4),ls_padActivo);
            lsw_singWindow.setVisible(true);
            lsw_singWindow.setAlwaysOnTop( true );
            il_log.debug("Window SIGN cargada correctamente.");
	         break;
	      case RESET:
            il_log.debug("Procesando peticion: RESET id_usuario:"+lls_parsedArgs.get(1));
	         if(lls_parsedArgs.get(3).equals("1")) {
	            EnrollWindow lew_reset = new EnrollWindow(lls_parsedArgs.get(1), lls_parsedArgs.get(2), true, false);
	            lew_reset.setVisible(true);
	            lew_reset.setAlwaysOnTop( true );
	            il_log.debug("Window ENROLL-RESET PASSWORD cargada correctamente.");
	         } else if(lls_parsedArgs.get(3).equals("2")) {
	            BiometriaController lbc_biometriaController = new BiometriaController(Settings.getBackendURL());
	            BiometriaWS lbws_webService = lbc_biometriaController.getBiometriaWSPort();
	            BorrarHuellasDTO lbhd_borrarDto = new BorrarHuellasDTO();
	            lbhd_borrarDto.setIdUsuario(lls_parsedArgs.get(1));
	            lbhd_borrarDto.setIdUsuarioCreacion(lls_parsedArgs.get(2));
	            lbws_webService.borrarHuellas(lbhd_borrarDto);
	            EnrollWindow lew_window = new EnrollWindow(lls_parsedArgs.get(1), lls_parsedArgs.get(2), false, true);
	            lew_window.setVisible(true);
	            lew_window.setAlwaysOnTop(true);
	            il_log.debug("Window ENROLL-RESET HUELLAS cargada correctamente.");
	         }
	         break;
	      case ENROLL:
           il_log.debug("Procesando peticion: ENROL Id_usuario: "+lls_parsedArgs.get(1));
	        EnrollWindow lew_window = new EnrollWindow(lls_parsedArgs.get(1), lls_parsedArgs.get(2), false, false);
	        lew_window.setVisible(true);
	        lew_window.setAlwaysOnTop(true);
	        il_log.debug("Window ENROLL cargada correctamente.");
	        break;
	      case VERIFY:
            il_log.debug("Procesando peticion: VERIFY id_usuario:"+lls_parsedArgs.get(2));
	         BiometriaController lbc_biometriaController = new BiometriaController(Settings.getBackendURL());
	         BiometriaWS lbws_webService = lbc_biometriaController.getBiometriaWSPort();
	        
	         try {
	            UsuarioEntradaDTO lued_entrada = new UsuarioEntradaDTO();
	            lued_entrada.setIdUsuario(lls_parsedArgs.get(2));
	            StringSalidaDTO lssd_metodo = lbws_webService.obtenerTipoSegundoFactor(lued_entrada);
	           
	            if(lssd_metodo.getResultado().toUpperCase().equals("HUELLA")) {
	               VerifyWindow lvw_window = new VerifyWindow(lls_parsedArgs.get(1), lls_parsedArgs.get(2));
	               lvw_window.setVisible(true);
	               lvw_window.setAlwaysOnTop(true);
	               il_log.debug("Window VERIFY cargada correctamente.");
	            } else {
	               PasswordWindow lpw_window = new PasswordWindow(lls_parsedArgs.get(1), lls_parsedArgs.get(2));
	               lpw_window.setVisible(true);
	               lpw_window.setAlwaysOnTop(true);
	               il_log.debug("Window VERIFY PASSWORD cargada correctamente.");
	            }
	         } catch (Exception le_exception) {
	            ib_serverProcesingOperation=false;
               il_log.debug("Excepción :" +le_exception.getMessage());
               il_log.debug(le_exception.getStackTrace()[0]);
	            MessagingAdapter.returnResponse("verify", "fail");
	         }
	         break;
	      case INVALID:
	    	   il_log.debug("El tipo de Petición no es válida");
	    	  App.setProcessingOperation(false);
	         break;
	      default:
	         App.setProcessingOperation(false);
	         break;
	      }
      }
      catch(Exception ie_excepcion) {
         il_log.error("Excepcion en app-mensaje " + ie_excepcion.getMessage());
         il_log.error("Causas");
         Throwable ie_causa = ie_excepcion.getCause();
         while (ie_causa != null) {
            il_log.error("Causa en app-mensaje " + ie_causa.getMessage());
            ie_causa = ie_causa.getCause();
         }
         il_log.error("Pila de llamadas");
         for(StackTraceElement ist_stack : ie_excepcion.getStackTrace()) {
            il_log.error("pila :" + ist_stack.getFileName() + " - " + ist_stack.getClassName() + " - " + ist_stack.getMethodName() + " - " + Integer.toString(ist_stack.getLineNumber()) );
         }
      }
   }
}
