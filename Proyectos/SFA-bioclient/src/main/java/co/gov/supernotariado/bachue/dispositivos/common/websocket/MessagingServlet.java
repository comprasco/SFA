package co.gov.supernotariado.bachue.dispositivos.common.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Servlet para enviar mensajes al cliente.
 */
public class MessagingServlet extends WebSocketServlet {
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;
  
  /**
   * Configura el websocket con el que se atenderán los mensajes con el navegador 
   */
   @Override
   public void configure(WebSocketServletFactory awssf_factory) {
      awssf_factory.register(MessagingAdapter.class);
   }
}
