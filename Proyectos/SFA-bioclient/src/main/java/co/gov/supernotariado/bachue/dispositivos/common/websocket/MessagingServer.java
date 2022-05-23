package co.gov.supernotariado.bachue.dispositivos.common.websocket;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

//import sun.util.logging.resources.logging;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.ui.MessageBox;

import java.io.IOException;
import java.net.BindException;

import javax.swing.JOptionPane;


/**
 * Clase encargada de crear el servidor de websockets
 */
public class MessagingServer {
   /**
    * Logger de la aplicación
    */
   private Logger ll_log = LoggerFactory.getLogger (getClass ());
   /**
    * Servidor websocket
    */
   private Server is_server;


  /**
   * Metodo que configura las propiedades del servidor de websocket
   */
   public void setup() {
      is_server = new Server();
      ServerConnector lsc_connector = new ServerConnector(is_server);
      lsc_connector.setPort(1104);
      is_server.addConnector(lsc_connector);

      ServletContextHandler lsch_handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
      lsch_handler.setContextPath("/");
      is_server.setHandler(lsch_handler);

      lsch_handler.addServlet(MessagingServlet.class, "/");
   }

  /**
   * Metodo que inicializa el servidor de websocket
   */
   public void start() {
      try {
    	   ll_log.info("start - Iniciando Servidor");
    	   is_server.start();
         is_server.dump(System.err);
         ll_log.info("start - servidor en espera de peticiones");
         is_server.join();
      } catch (BindException e) {
         ll_log.error("Bioclient ya está ejecutándose");
         MessageBox.display("Bioclient ya está ejecutando ", JOptionPane.ERROR_MESSAGE, true);
      } catch (IOException e) {
         ll_log.error("Excepción IO iniciando bioclient, posiblemente se intentó iniciar por segunda vez: " + e.getMessage());
         MessageBox.display("Excepción IO iniciando bioclient, posiblemente se intentó iniciar por segunda vez, revise el log", JOptionPane.ERROR_MESSAGE, true);
      } catch (Exception e) {
         ll_log.error("Excepción no controlada iniciando bioclient: " + e.getMessage());
         MessageBox.display("Excepción no controlada iniciando bioclient, revise el log", JOptionPane.ERROR_MESSAGE, true);
      }
   }
}
