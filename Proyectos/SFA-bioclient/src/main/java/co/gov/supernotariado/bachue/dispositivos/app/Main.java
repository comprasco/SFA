package co.gov.supernotariado.bachue.dispositivos.app;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;

/**
 * Punto de entrada del proyecto.
 *
 */
public class Main {
   /**
    * Objeto de que atiende todas las operaciones de la aplicación
    */
   private static App ia_app = null; 
   
   /**
    * Obtiene el objeto App
    * @return objeto App
    */
   public static App getApp()
   {
      return ia_app;
   }
   
   /**
    * Operación para detener la atención de peticiones
    * @throws Throwable
    */
   private static void stop() throws Throwable
   {
      URI uri = URI.create("ws://localhost:1104/");
      
      WebSocketClient client = new WebSocketClient();
      client.start();
      try {
         Session connection = null;
         try {
            WebSocketListener socket = new WebSocketListener() {
               @Override
               public void onWebSocketError(Throwable arg0) {
               }
               @Override
               public void onWebSocketConnect(Session arg0) {
               }
               @Override
               public void onWebSocketClose(int arg0, String arg1) {
               }
               @Override
               public void onWebSocketText(String arg0) {
               }
               @Override
               public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
               }
            };
            
            Future<Session> fut = client.connect(socket, uri);
            connection = fut.get();
            connection.getRemote().sendString("bachue://stop");
            int ii_contador = 0;
            while(connection.isOpen() && ii_contador < 15) {
               Thread.sleep(1000);
               ii_contador++;
            }
         } finally {
              if (connection!=null) connection.close();
         }
      } catch (Throwable t) {
          t.printStackTrace(System.err);
      } finally {
        client.stop();
      }
   }
  
   /**
    * punto de entrada de la aplicacíón
    * @param as_args parámetros de ejecución de la aplicación
    * @throws Throwable
    */
   public static void main(String[] as_args) throws Throwable  {
      Logger il_log = LoggerFactory.getLogger (Main.class);
      System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("c:/bioclient/logs/bioclient.out")), true));
      System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream("c:/bioclient/logs/bioclient.err")), true));
      
      il_log.info("Iniciando la aplicación");
      if (as_args.length > 0)
      {
         if (as_args[0].compareTo("stop") == 0)
         {
            il_log.info("Enviar solicitud de detención");
            stop();
            il_log.info("Y terminar");
            System.exit(0);
         }
         if (as_args[0].compareTo("restart") == 0)
         {
            il_log.info("Enviar solicitud de detención");
            stop();
            il_log.info("Y reiniciar el servicio");
         }
      }
      
      int if_iteracion = 0;
      
      while(if_iteracion < 10000) {
         if (ia_app == null) {
            String ifh_FechaHora = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            System.out.println(ifh_FechaHora + " - Creacion del procesador de operaciones (App)");
            ia_app = new App();
         }
         System.out.println(if_iteracion);
         if_iteracion = ((byte)(if_iteracion + 1));
         Thread.sleep(5000);
      }
   }
}