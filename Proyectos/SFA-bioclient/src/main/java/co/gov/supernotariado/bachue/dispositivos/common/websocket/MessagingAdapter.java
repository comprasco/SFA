package co.gov.supernotariado.bachue.dispositivos.common.websocket;

import co.gov.supernotariado.bachue.dispositivos.app.Main;
import co.gov.supernotariado.bachue.dispositivos.app.MainRequest;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * Adapter para enviar mensajes al cliente.
 */
public class MessagingAdapter extends WebSocketAdapter {
   /**
    * Logger de la aplicaci�n
    */
   private transient Logger il_log = LoggerFactory.getLogger (getClass ());
   
   /**
    * Idd de sesi�n
    */
   private static Session is_session;
   
   /**
   * Evento que ocurre cuando el cliente se conecta al servidor de websocket
   * @param as_session sesion del websocket
   */
   @Override
   public void onWebSocketConnect(Session as_session) {
      super.onWebSocketConnect(as_session);
      
      il_log.debug("Nueva conexi�n entrante");
      
      // Verificar que no haya una conexi�n activa previa
      if (is_session != null && is_session.isOpen())
      {
         il_log.debug("Ya hay una conexi�n abierta, se cerrar� cuando la operaci�n responda");
         return;
      }
      is_session = as_session;
      String sAddress = is_session.getRemoteAddress().getAddress().getHostAddress();
      
      if (sAddress.equals("0:0:0:0:0:0:0:1") || sAddress.equals("127.0.0.1")) {
         il_log.debug("Conexi�n de " + is_session.getRemoteAddress().getAddress().getHostAddress() );
      } else {
         il_log.debug("Conexi�n de direcci�n no permitida " + sAddress);
         is_session.close(3010, "Conexi�n desde direcci�n no permitida");
      }
   }

   /**
   * Evento que ocurre cuando el cliente se desconecta del servidor de websocket
   * @param ai_statusCode codigo de estado
   * @param as_reason razon de cierre
   */
   @Override
   public void onWebSocketClose(int ai_statusCode, String as_reason) {
	   System.out.println("Cerrar websocket " + as_reason);
      super.onWebSocketClose(ai_statusCode, as_reason);
   }

   /**
   * Evento que ocurre cuando el cliente envia un mensaje al servidor de websocket
   * @param as_message mensaje recibido del cliente
   */
   @Override
   public void onWebSocketText(String as_message) {
      /*if (procesandoOperacion)
      {
         il_log.debug("Ya est� procesando una operaci�n");
         return;
      }
      procesandoOperacion = true;*/
      il_log.debug("Peticion entrante: " + as_message);
	   String[] ls_args = new String[1];
	   ls_args[0] = as_message;
	   
	   il_log.debug("Armar par�metros");
	   MainRequest lmr_mainRequest = new MainRequest();
      lmr_mainRequest.parseArgs(Arrays.asList(ls_args));
	
      il_log.debug("Procesar petici�n");
	   Main.getApp().procesarOperacion(lmr_mainRequest);
	   il_log.debug("Operaciones de ventana finalizan al cerrarse");
      super.onWebSocketText(as_message);
   }

   /**
   * Metodo que nos permite informar al cliente de un evento ocurrido
   * @param as_event Tipo de evento
   * @param as_response respuesta del evento
   */
   public static void returnResponse(String as_event, String as_response) {
      try {
         Logger ll_log = LoggerFactory.getLogger (MessagingAdapter.class);
         ll_log.debug("Entregar respuesta al cliente");
         is_session.getRemote().sendString("{\"event\": \""+as_event+"\", \"response\": \""+as_response+"\"}");
         ll_log.debug("Cerrar conexi�n");
         is_session.close();
      } catch (Exception le_excepcion) {
         Logger il_log = LoggerFactory.getLogger(MessagingAdapter.class);
         il_log.info("Excepci�n: " + le_excepcion.getMessage());
      }
   }
}