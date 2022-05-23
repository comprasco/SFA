package co.gov.supernotariado.bachue.dispositivos.biometrico.ui;

import com.Futronic.ScanApiHelper.Scanner;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaController;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaWS;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.VerificacionDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.Device;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;
import co.gov.supernotariado.bachue.dispositivos.common.websocket.MessagingAdapter;
import co.gov.supernotariado.bachue.dispositivos.ui.MessageBox;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase para la ventana de verificacion con huella
 *
 */
public class VerifyWindow extends Frame implements ActionListener {
   /**
    * Logger de la aplicación
    */
   private transient Logger il_log = LoggerFactory.getLogger (getClass ());
   
   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;
   /**
    * Id de usuario
    */
   private String is_idUsuario;
   /**
    * Id de sesión
    */
   private String is_sesion;
   
   /**
    * Gestor de terinación de procesos asincrónicos
    */
   private ExecutorService ies_service;
   /**
    * Template de la huella
    */
   private String is_template;
   /**
    * Imagen del dedo
    */
   private ImageDedo lid_imageDedo;

   /**
    * Constante de mensaje de resultado fallido
    */
   private final String resultFalse = "fail";
   /**
    * Constante de mensaje de resultado exitoso
    */
   private final String resultTrue = "success";
   /**
    * Resultado de la operación de la ventana
    */
   private String operacionResult = resultFalse; 

   /**
    * Establece el resultado de la operación
    * @param valor resultado de la operación: success/fail
    */
   private void setOperacionResult(String valor)
   {
      il_log.debug("Resultado " + valor);
      operacionResult = valor;
   }
   
   /**
    * Establece en resultado de la operación en fallido
    */
   private void operacionResultFalse()
   {
      setOperacionResult(resultFalse);
   }
   
   /**
    * Establece el resultado de la operación en exitoso
    */
   private void operacionResultTrue()
   {
      setOperacionResult(resultTrue);
   }

   /**
    * Constructor de la clase
    * @param as_sesion id de la sesión del usuario en bachué
    * @param as_idUsuario id del usuario al que se le verificará la huella
    */
   public VerifyWindow(String as_sesion, String as_idUsuario) {
     this.is_idUsuario = as_idUsuario;
     this.is_sesion = as_sesion;
     try {
        this.init();
        this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent awe_windowEvent) {
          MessagingAdapter.returnResponse("verify", operacionResult);
          dispose();
          }
        });
        ies_service = Executors.newFixedThreadPool(4);
        ies_service.submit(() -> capture());
     } catch(Exception le_exception) {
        il_log.fatal("No se pudo inicializar la ventana de captura de huella");
        operacionResultFalse();
        // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("verify", "fail");
        dispose();
    }
  }

   /**
    * Inicialización de componentes y apariencia de la ventana
    * @throws IOException
    */
  private void init() throws IOException {
    setLayout(new BorderLayout());
    setSize(200, 250);
    setTitle("Verificación");
    setResizable(false);
    setLocationRelativeTo(null);

    JPanel ljp_panel = new JPanel();
    ljp_panel.setLayout(new GridLayout(1, 1));
    ljp_panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    lid_imageDedo = new ImageDedo(ImageIO.read(new File("images/fingerprint.png")), 200, 250);

    Box lb_buttonBox = Box.createHorizontalBox();
    lb_buttonBox.add(lid_imageDedo);
    ljp_panel.add(lb_buttonBox);
    add(ljp_panel);
  }

  /**
   * Inicia la comunicación con el sensor y obtiene la información (template) de la huella
   */
  private void capture() {
     try {
        il_log.debug("Capturar la huella"); 
        Device ld_device = new Device();
        ld_device.setM_devScan(new Scanner());
        
        String ls_result = ld_device.btnGetFrameActionPerformed();
        
        il_log.debug("Huella capturada: " + ls_result);
        
        if (ls_result.contains("xito")) {
           is_template = ld_device.btnSaveActionPerformed();
           il_log.debug("PILAS Template: " + is_template);
           if(is_template != null) {
              lid_imageDedo.cambiarImagen(ImageIO.read(new File("images/fingerprint-load.png")), 200, 250);
              lid_imageDedo.repaint();
              validateSession();
           }
        } else {
           il_log.debug("Huella no se pudo capturar");
           MessageBox.display(ls_result, JOptionPane.ERROR_MESSAGE, false);
           operacionResultFalse();
           // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("verify", "fail");
        }
     } catch (UnsatisfiedLinkError lule_exception) {
    	il_log.fatal("Excepción capturando la huella");
    	PrintStackTrace(lule_exception);
        MessageBox.display("Lector no encontrado o driver no instalado", JOptionPane.ERROR_MESSAGE, false);
        operacionResultFalse();
        // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("verify", "fail");
     } catch (Exception le_exception) {
       il_log.info("Excepción: " + le_exception.getMessage());
     }
     finally {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
     }
  }

  /**
   * Envía el template de la huella al servidor para que sea validada su pertenencia al usuario
   */
  private void validateSession() {
    try {
       il_log.debug("Validar la huella");
       BiometriaController lbc_biometriaController = new BiometriaController(Settings.getBackendURL());
       BiometriaWS lbwswebService = lbc_biometriaController.getBiometriaWSPort();

       VerificacionDTO lvd_verificacionDTO = new VerificacionDTO();
       lvd_verificacionDTO.setIdUsuario(is_idUsuario);
       lvd_verificacionDTO.setSesion(is_sesion);
       lvd_verificacionDTO.setImagenHuella(is_template);

       lbwswebService.verificarUsuario(lvd_verificacionDTO);

       // FQ el mensaje se enviará el cerrar la ventana 
       operacionResultTrue();
       il_log.debug("Se validó la huella");
    } catch (Exception le_exception) {
    	 il_log.fatal("Excepción validando la huella");
    	 PrintStackTrace(le_exception);
       //FQ MessageBox.display("Ocurrió un error al verificar al usuario", JOptionPane.ERROR_MESSAGE, false);
       operacionResultFalse();
       // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("verify", "fail");
    }
    finally
    {
      this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
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
  /**
   * Listener de los eventos de la forma
   */
  @Override
  public void actionPerformed(ActionEvent aae_event) {}
}
