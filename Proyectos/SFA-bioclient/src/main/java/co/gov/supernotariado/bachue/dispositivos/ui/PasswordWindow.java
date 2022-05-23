package co.gov.supernotariado.bachue.dispositivos.ui;

import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaController;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaWS;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BooleanSalidaDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.ClaveDTO;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;
import co.gov.supernotariado.bachue.dispositivos.common.websocket.MessagingAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Clase para la ventana de verificacion con segunda clave
 *
 */
public class PasswordWindow extends Frame implements ActionListener {
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
    * Componente de la ventana
    */
   private JPasswordField ijpf_clave;
   
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
    * @param as_sesion id de la sesión de bachué del usuario que se va a verificar
    * @param as_idUsuario id del usuario que se le va a verificar su segunda clave
    */
   public PasswordWindow(String as_sesion, String as_idUsuario) {
      this.is_idUsuario = as_idUsuario;
      this.is_sesion = as_sesion;
      this.init();
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent we_windowEvent) {
    	      MessagingAdapter.returnResponse("verify", operacionResult);
            dispose();
         }
         });
   }

   /**
   * Metodo para generar la interfaz grafica de la ventana
   */
   private void init() {
      setLayout(new BorderLayout());
    setSize(300, 150);
    setTitle("Verificación");
    setResizable(false);
    setLocationRelativeTo(null);

    JPanel ljp_panel = new JPanel();
    ljp_panel.setLayout(new GridLayout(3, 1));
    ljp_panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JLabel ljl_clave = new JLabel("Segunda Clave: ");
    ljp_panel.add(ljl_clave);
    ijpf_clave = new JPasswordField(50);
    ljp_panel.add(ijpf_clave);
    ljl_clave.setLabelFor(ijpf_clave);

    JPanel ljp_segundoPanel = new JPanel();
    ljp_segundoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    JButton btnEnrroll = new JButton("Verificar");
    btnEnrroll.addActionListener(e -> validateSession());
    ljp_segundoPanel.add(btnEnrroll);
    ljp_panel.add(ljp_segundoPanel);
    add(ljp_panel);
  }


  /**
   * Validar la sesion en el servidor de biometria
   */
  private void validateSession() {
    try {
       il_log.debug("Validar la contraseña del usuario");
       BiometriaController lbc_biometriaController = new BiometriaController(Settings.getBackendURL());
       BiometriaWS lbwswebservice = lbc_biometriaController.getBiometriaWSPort();

       ClaveDTO lcd_claveDTO = new ClaveDTO();
       lcd_claveDTO.setIdUsuario(is_idUsuario);
       lcd_claveDTO.setSesion(is_sesion);
       lcd_claveDTO.setClave(new String(ijpf_clave.getPassword()));
       BooleanSalidaDTO il_resultado = lbwswebservice.verificarClave(lcd_claveDTO);
       il_log.debug("Respuesta de la validación: código " +il_resultado.getCodigo() );
       il_log.debug("Respuesta de la validación: mensaje " +il_resultado.getMensaje() );

       // FQ el resultado al browser siempre es exitoso porque en el servidor está la verdadera respuesta
       operacionResultTrue();

       il_log.debug("Se completó la tarea de validación de contraseña");
    } catch (Exception le_exception) {
       il_log.debug("No se pudo completar la tarea de validación de contraseña");
       
       PrintStackTrace(le_exception);
       MessageBox.display("Ocurrió un error al verificar al usuario", JOptionPane.ERROR_MESSAGE, false);
       operacionResultFalse();
       // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("verify","fail");
    }
    finally
    {
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
  }

  /**
   * Listener de los eventos de la forma
   */
  @Override
  
  public void actionPerformed(ActionEvent aae_eevent) {}
  
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
