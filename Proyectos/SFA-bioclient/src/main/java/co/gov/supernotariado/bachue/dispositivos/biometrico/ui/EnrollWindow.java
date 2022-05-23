package co.gov.supernotariado.bachue.dispositivos.biometrico.ui;

import com.Futronic.ScanApiHelper.Scanner;

import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaController;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaWS;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BooleanSalidaDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BorrarHuellasDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.DedosEnum;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.HuellaDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.StringSalidaDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.UsuarioDTO;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.UsuarioEntradaDTO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.Device;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;
import co.gov.supernotariado.bachue.dispositivos.common.websocket.MessagingAdapter;
import co.gov.supernotariado.bachue.dispositivos.ui.MessageBox;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Clase para la ventana de enrolamiento, cambio de huella y cambio de clave
 */
public class EnrollWindow extends Frame implements ActionListener {
   /**
    * Logger de la aplicación
    */
   private transient Logger il_log = LoggerFactory.getLogger (getClass ());

   /**
    * Serial de la versión
    */
   private static final long serialVersionUID = 1L;
   /**
    * Constante de reinicio de clave
    */
   private static final String cs_reiniciar = "REINICIAR";
   /**
    * Constante de enrolamiento
    */
   private static final String cs_enrolar = "ENR";

   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoUno;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoDos;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoTres;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoCuatro;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoCinco;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoSeis;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoSiete;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoOcho;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoNueve;
   /**
    * Imagen de dedo
    */
   private static ImageDedo iid_imageDedoDiez;

   /**
    * Componente de la ventana
    */
   private JPanel ijp_paneltres, ijp_panelCuatro = null;
   /**
    * Componente de la ventana
    */
   private JTextField ijtf_clave = null;
   /**
    * Componente de la ventana
    */
   private JTextField ijtf_confirmacion = null;
   /**
    * Componente de la ventana
    */
   private JButton ijb_btnEnrroll = null;
   /**
    * Componente de la ventana
    */
   private JButton ijb_btnReiniciar = null;
   /**
    * Componente de la ventana
    */
   private boolean ib_dedosEnabled = true;
   /**
    * Componente de la ventana
    */
   private boolean ib_currentlyEnrolling = false;
   /**
    * Componente de la ventana
    */
   private JCheckBox ijcb_huellas = null;

   /**
    * Dedo actual que se está capturando
    */
   private int ii_currentFinger;
   /**
    * Posicion del dedo
    */
   private int ii_position;
   /**
    * Imagen del dedo
    */
   private ImageDedo iid_currentImageFinger;

   /**
    * Componente de la ventana
    */
   private static JLabel ijl_lblEstadoLector = null;

   /**
    * Componente de la ventana
    */
   private static Color ic_customColorForm = new Color(240, 240, 240);

   /**
    * Id de usuario que opera la ventana
    */
   private String is_idUsuario;
   /**
    * Id de usuario que se enrola o cambia se le cambia el segundo factor
    */
   private String is_idUsuarioEnrolando;
   /**
    * Indicador que la pantalla se está usando para cambiar el segundo factor
    */
   private boolean ib_isReset;
   /**
    * Indicador que la pantalla se está usando para cambiar las huellas
    */
   private boolean ib_resetHuellas;
   /**
    * Objeto de acceso a las funciones del sensor de huellas
    */
   private Device id_device;
   /**
    * Objeto de acceso a los servicios web del servidor de segundo factor de autenticación
    */
   private BiometriaWS ibws_webService;
   /**
    * 
    */
   private boolean ib_usuarioExiste = false;
   
   /**
    * Constante de mensaje de resultado fallido
    */
   private final String enrollFalse = "fail";
   /**
    * Constante de mensaje de resultado exitoso
    */
   private final String enrollTrue = "success";
   /**
    * Resultado de la operación de la ventana
    */
   private String enrollResult = enrollFalse; 

   /**
    * Arreglo con los nombres de los dedos de las manos
    */
   private String[] is_fingersEnum = {"PULGAR_DERECHO", "INDICE_DERECHO", "CORAZON_DERECHO", "ANULAR_DERECHO", "MENIQUE_DERECHO",
       "PULGAR_IZQUIERDO", "INDICE_IZQUIERDO", "CORAZON_IZQUIERDO", "ANULAR_IZQUIERDO", "MENIQUE_IZQUIERDO"};

   /**
    * Arreglo con los templates capturados de los dedos
    */
   private List<String> ils_listaHuellas = new ArrayList<>(Arrays.asList("", "", "", "", "", "", "", "", "", ""));

   /**
    * Establece el resultado de la operación reset/enroll
    * @param valor resultado de la operación: success/fail
    */
   private void setEnrollResult(String valor)
   {
      if (ib_isReset)
      {
         il_log.debug("Resultado del reset = " + valor);
      }
      else
      {
         il_log.debug("Resultado del enroll = " + valor);
      }
      enrollResult = valor;
   }
   
   /**
    * Establece en resultado de la operación en fallido
    */
   private void enrollResultFalse()
   {
      setEnrollResult(enrollFalse);
   }
   
   /**
    * Establece el resultado de la operación en exitoso
    */
   private void enrollResultTrue()
   {
      setEnrollResult(enrollTrue);
   }

   /**
    * Constructor de la clase
    * @param as_idUsuario id del usuario que inicia el proceso de enrolamiento
    * @param as_idUsuarioEnrolando id del usuario que será enrolado
    * @param ab_isReset indicador de que la pantalla será usada para cambiar el segundo factor o para enrolar un usuario
    * @param ab_resetHuellas indicador de que la pantalla cambiará los template de las huellas 
    */
   public EnrollWindow(String as_idUsuario, String as_idUsuarioEnrolando, boolean ab_isReset, boolean ab_resetHuellas) {
      BiometriaController lbc_biometriaController = new BiometriaController(Settings.getBackendURL());
      ibws_webService = lbc_biometriaController.getBiometriaWSPort();

      this.is_idUsuario = as_idUsuario;
      this.is_idUsuarioEnrolando = as_idUsuarioEnrolando;
      this.ib_isReset = ab_isReset;
      this.ib_resetHuellas = ab_resetHuellas;
      if(ab_isReset)
         this.setTitle("Cambio de contraseña");
      else
         this.setTitle("Enrolamiento");
      this.setResizable(false);
      try { this.init(); } catch (IOException lioe_exception){
         il_log.info("Excepción: " + lioe_exception.getMessage());
      }
      
      this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
         // FQ se reporta el resultado del enrolamiento de acuerdo con la actividad de la pantalla
         if (ib_isReset)
         {
            MessagingAdapter.returnResponse("reset", enrollResult);
         }
         else
         {
            MessagingAdapter.returnResponse("enroll", enrollResult);
         }
         dispose();
         }
      });
      
      this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowActivated(WindowEvent e)
      {
         if(checkExistence(as_idUsuario) && !ab_isReset) {
            MessageBox.display("Este usuario ya ha sido enrolado", JOptionPane.ERROR_MESSAGE, false);
            enrollResultFalse();
            closeWindow();
         }
      }
      });            
      this.setLocationRelativeTo(null);
      if(ab_isReset) {
         ijcb_huellas.setEnabled(false);
         ijcb_huellas.setSelected(false);
         ib_dedosEnabled = false;
         ijp_paneltres.setEnabled(false);
         ijp_panelCuatro.setEnabled(false);
         ijb_btnEnrroll.setEnabled(false);
      }
      if(ab_resetHuellas) {
         ijtf_clave.setEnabled(false);
         ijtf_confirmacion.setEnabled(false);
         ib_dedosEnabled = true;
         ijcb_huellas.setEnabled(false);
      }
   }

   /**
    * Cierra la ventana
    */
   private void closeWindow()
   {
      this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
   }

   /**
   * Metodo que comprueba el estado del enrolamiento del usuario
   * @param as_idUsuario id del usuario a enrolar
   * @return retorna el estado del usuario
   */
   private boolean checkExistence(String as_idUsuario) {
    try {
      UsuarioEntradaDTO lued_entrada = new UsuarioEntradaDTO();
      lued_entrada.setIdUsuario(as_idUsuario);
      StringSalidaDTO lssd_result = ibws_webService.obtenerUsuario(lued_entrada);

      if(lssd_result.getResultado().equals("USUARIO_NO_TIENE_HUELLAS")) {
        ib_usuarioExiste = true;
      }

      return lssd_result.getResultado().contains("USUARIO_EXISTE");
    } catch (Exception le_exception) {
       il_log.info("Excepción: " + le_exception.getMessage());
      return false;
    }
  }

   /**
    * Método para generar la interfaz grafica de la ventana
    * @throws IOException
    */
  public void init() throws IOException {
    setLayout(new BorderLayout());
    setSize(600, 400);
    setBackground(ic_customColorForm);

    JPanel ljp_panelUno = new JPanel();
    ljp_panelUno.setLayout(new GridLayout(1, 6));
    ljp_panelUno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel ljl_lblClave = new JLabel("Contraseña: ");
    ljp_panelUno.add(ljl_lblClave);
    ijtf_clave = new JPasswordField(50);
    ljp_panelUno.add(ijtf_clave);
    ljl_lblClave.setLabelFor(ijtf_clave);
    ljl_lblClave.setHorizontalAlignment(JLabel.CENTER);
    ijtf_clave.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
    ijtf_clave.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent ake_keyEvent) {
        if(ake_keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
          ijtf_confirmacion.grabFocus();
        }
      }
    });

    JLabel ljl_lblConfirmacion = new JLabel("Repetir: ");
    ljp_panelUno.add(ljl_lblConfirmacion);
    ijtf_confirmacion = new JPasswordField(50);
    ljp_panelUno.add(ijtf_confirmacion);
    ljl_lblConfirmacion.setLabelFor(ijtf_confirmacion);
    ljl_lblConfirmacion.setHorizontalAlignment(JLabel.CENTER);

    JLabel ljl_lblHuella = new JLabel("Usar Biometría");
    ljp_panelUno.add(ljl_lblHuella);
    ijcb_huellas = new JCheckBox();
    ljp_panelUno.add(ijcb_huellas);
    ljl_lblHuella.setLabelFor(ijcb_huellas);
    ijcb_huellas.setHorizontalAlignment(JCheckBox.CENTER);
    ijcb_huellas.setBorder(BorderFactory.createEmptyBorder(0  , 0, 0, 30));
    ljl_lblHuella.setHorizontalAlignment(JLabel.RIGHT);
    ijcb_huellas.setSelected(true);

    ijcb_huellas.addActionListener(e -> {
      if(ijcb_huellas.isSelected()) {
        ijp_paneltres.setEnabled(true);
        ijp_panelCuatro.setEnabled(true);
        ib_dedosEnabled = true;
      } else {
        ib_dedosEnabled = false;
        ijp_paneltres.setEnabled(false);
        ijp_panelCuatro.setEnabled(false);
        ijb_btnEnrroll.setEnabled(false);
      }
    });
    add(ljp_panelUno, BorderLayout.NORTH);

    ijp_paneltres = new JPanel();
    ijp_paneltres.setLayout(new GridLayout(1, 7));
    ijp_paneltres.setBackground(ic_customColorForm);
    ijp_paneltres.setBorder(BorderFactory.createEtchedBorder());
    ijp_paneltres.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mano Izquierda", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), Color.BLUE));

    ImageDedo lid_imageEspacioUno = new ImageDedo(ImageIO.read(new File("images/Espacio.jpg")), 40, 205);
    ijp_paneltres.add(lid_imageEspacioUno);

    iid_imageDedoUno = new ImageDedo(ImageIO.read(new File("images/Dedo_1.jpg")), 40, 205);
    iid_imageDedoUno.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoUno, 1, 9);
      }
    });
    iid_imageDedoUno.setToolTipText("Meñique Mano Izquierda");
    ijp_paneltres.add(iid_imageDedoUno);

    iid_imageDedoDos = new ImageDedo(ImageIO.read(new File("images/Dedo_2.jpg")), 40, 205);
    iid_imageDedoDos.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoDos, 2, 8);
      }
    });
    iid_imageDedoDos.setToolTipText("Anular Mano Izquierda");
    ijp_paneltres.add(iid_imageDedoDos);

    iid_imageDedoTres = new ImageDedo(ImageIO.read(new File("images/Dedo_3.jpg")), 40, 205);
    iid_imageDedoTres.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        seleccionarDedo(iid_imageDedoTres, 3,7);
      }
    });
    iid_imageDedoTres.setToolTipText("Corazón Mano Izquierda");
    ijp_paneltres.add(iid_imageDedoTres);


    iid_imageDedoCuatro = new ImageDedo(ImageIO.read(new File("images/Dedo_4.jpg")), 40, 205);
    iid_imageDedoCuatro.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoCuatro, 4, 6);
      }
    });
    iid_imageDedoCuatro.setToolTipText("Indice Mano Izquierda");
    ijp_paneltres.add(iid_imageDedoCuatro);

    iid_imageDedoCinco = new ImageDedo(ImageIO.read(new File("images/Dedo_5.jpg")), 40, 205);
    iid_imageDedoCinco.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoCinco, 5,5);
      }
    });
    iid_imageDedoCinco.setToolTipText("Pulgar Mano Izquierda");
    ijp_paneltres.add(iid_imageDedoCinco);


    ImageDedo lid_imageEspacioDos = new ImageDedo(ImageIO.read(new File("images/Espacio.jpg")), 40, 205);
    ijp_paneltres.add(lid_imageEspacioDos);


    add(ijp_paneltres, BorderLayout.WEST);

    ijp_panelCuatro = new JPanel();
    ijp_panelCuatro.setLayout(new GridLayout(1, 7));
    ijp_panelCuatro.setBackground(ic_customColorForm);
    ijp_panelCuatro.setBorder(BorderFactory.createEtchedBorder());
    ijp_panelCuatro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mano Derecha", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), Color.BLUE));

    ImageDedo lid_imageEspacioTres = new ImageDedo(ImageIO.read(new File("images/Espacio.jpg")), 40, 205);
    ijp_panelCuatro.add(lid_imageEspacioTres);

    iid_imageDedoSeis = new ImageDedo(ImageIO.read(new File("images/Dedo_6.jpg")), 40, 205);
    iid_imageDedoSeis.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoSeis, 6, 0);
      }
    });
    iid_imageDedoSeis.setToolTipText("Pulgar Mano Derecha");
    ijp_panelCuatro.add(iid_imageDedoSeis);

    iid_imageDedoSiete = new ImageDedo(ImageIO.read(new File("images/Dedo_7.jpg")), 40, 205);
    iid_imageDedoSiete.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoSiete, 7, 1);
      }
    });
    iid_imageDedoSiete.setToolTipText("Indice Mano Derecha");
    ijp_panelCuatro.add(iid_imageDedoSiete);

    iid_imageDedoOcho = new ImageDedo(ImageIO.read(new File("images/Dedo_8.jpg")), 40, 205);
    iid_imageDedoOcho.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoOcho, 8, 2);
      }
    });
    iid_imageDedoOcho.setToolTipText("Corazón Mano Derecha");
    ijp_panelCuatro.add(iid_imageDedoOcho);

    iid_imageDedoNueve = new ImageDedo(ImageIO.read(new File("images/Dedo_9.jpg")), 40, 205);
    iid_imageDedoNueve.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoNueve, 9, 3);
      }
    });
    iid_imageDedoNueve.setToolTipText("Anular Mano Derecha");
    ijp_panelCuatro.add(iid_imageDedoNueve);

    iid_imageDedoDiez = new ImageDedo(ImageIO.read(new File("images/Dedo_10.jpg")), 40, 205);
    iid_imageDedoDiez.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ame_mouseEvent) {
        seleccionarDedo(iid_imageDedoDiez, 10, 4);
      }
    });
    iid_imageDedoDiez.setToolTipText("Meñique Mano Derecha");
    ijp_panelCuatro.add(iid_imageDedoDiez);

    ImageDedo lid_imageEspacioCuatro = new ImageDedo(ImageIO.read(new File("images/Espacio.jpg")), 40, 205);
    ijp_panelCuatro.add(lid_imageEspacioCuatro);


    add(ijp_panelCuatro, BorderLayout.EAST);

    JPanel ljp_panelCinco = new JPanel();
    ljp_panelCinco.setLayout(new GridLayout(2, 2));
    ljp_panelCinco.setBorder(BorderFactory.createEtchedBorder());

    ijb_btnEnrroll = new JButton("Cancelar enrolamiento");
    ijb_btnEnrroll.setActionCommand(cs_enrolar);
    ijb_btnEnrroll.addActionListener(e -> reset());
    ljp_panelCinco.add(ijb_btnEnrroll);


    ijb_btnReiniciar = new JButton("Enviar Datos");
    ijb_btnReiniciar.setActionCommand(cs_reiniciar);
    ijb_btnReiniciar.addActionListener(e -> {
      ijb_btnReiniciar.setText("Enviando...");
      if(validateFields()) {
        if(ib_isReset) {
          resetPassword();
        } else {
           // FQ Validar que se hayan capturado huellas si el usuario marcó que usa biometría
           if(ijcb_huellas.isSelected()) {
              int li_i = 0;
              for( String ls_huella : ils_listaHuellas) {
                 if (!ls_huella.isEmpty()) {
                    li_i++;
                 }
              }
              if (li_i == 0) {
                 MessageBox.display("Usuario debe enrolar por lo menos una huella", JOptionPane.INFORMATION_MESSAGE, false);
                 return;
              }
           }
          sendToServer();
        }
      }
      ijb_btnReiniciar.setText("Enviar Datos");
    });

    ljp_panelCinco.add(ijb_btnReiniciar);

    ijb_btnEnrroll.setEnabled(false);
    if(!ib_isReset)
      ijl_lblEstadoLector = new JLabel("Por favor seleccione un dedo para enrolar.");
    else
      ijl_lblEstadoLector = new JLabel("Ingrese su nueva clave.");
    ijl_lblEstadoLector.setForeground(Color.RED);
    ijl_lblEstadoLector.setHorizontalAlignment(SwingConstants.LEFT);
    ljp_panelCinco.add(ijl_lblEstadoLector);

    add(ljp_panelCinco, BorderLayout.SOUTH);
    ijtf_clave.grabFocus();
  }

  /**
   *  Listener de los eventos de la forma   
   */
  @Override
  public void actionPerformed(ActionEvent aae_event) {}


  /**
   * Metodo que permite la seleccion del dedo
   * @param aid_imageDedo imagen del dedo
   * @param ai_nDedo numero del dedo
   * @param ai_currentDedo dedo actual seleccionado
   */
  private void seleccionarDedo(ImageDedo aid_imageDedo, int ai_nDedo, int ai_currentDedo) {
    try {
      if(ib_dedosEnabled && !ib_currentlyEnrolling) {
        ii_currentFinger = ai_currentDedo;
        ii_position = ai_nDedo;
        iid_currentImageFinger = aid_imageDedo;
        ijb_btnEnrroll.setEnabled(true);
        aid_imageDedo.cambiarImagen(ImageIO.read(new File("images/Dedo_" + ai_nDedo + "_Sel.jpg")), 40, 205);
        aid_imageDedo.repaint();
        ib_currentlyEnrolling = true;
        new Thread(this::enroll).start();
      }
    } catch (IOException lioe_exception) {
       PrintStackTrace(lioe_exception);
    }
  }


   /**
   * Metodo para resetear la interfaz grafica
   */
   private void reset() {
      try {
    	 il_log.debug("reset: imágen gráfica");
         if(ils_listaHuellas.get(ii_currentFinger).isEmpty()) {
           iid_currentImageFinger.cambiarImagen(ImageIO.read(new File("images/Dedo_" + ii_position + ".jpg")), 40, 205);
         }
         iid_currentImageFinger.repaint();
         ijb_btnEnrroll.setEnabled(false);
         ib_currentlyEnrolling = false;
         id_device.stopDevice();
    	 il_log.debug("reset: fin imágen gráfica");
      } catch (IOException lioe_exception) {
         il_log.debug("reset: excepción: " + lioe_exception.getMessage());
    	   PrintStackTrace(lioe_exception);
      }
   }


   /**
   * Metodo para iniciar el proceso de captura y enrolamiento
   */
   private void enroll() {
      ijl_lblEstadoLector.setText("Coloque su dedo sobre el sensor");
      try {
         id_device = new Device();
         id_device.setM_devScan(new Scanner());
         String ls_result = id_device.btnGetFrameActionPerformed();
         if (ls_result.contains("xito")) {
            String ls_template = id_device.btnSaveActionPerformed();
            if(ls_template != null) {
               ils_listaHuellas.set(ii_currentFinger, ls_template);
               MessageBox.display("Huella capturada con éxito", JOptionPane.INFORMATION_MESSAGE, false);
            }
         } else {
            reset();
            MessageBox.display(ls_result, JOptionPane.ERROR_MESSAGE, false);
         }
      } catch (Exception le_exception) {
         reset();
         MessageBox.display("Lector no encontrado o driver no instalado", JOptionPane.ERROR_MESSAGE, false);
      } catch (UnsatisfiedLinkError lule_exception) {
         PrintStackTrace(lule_exception);
         reset();
         MessageBox.display("Lector no encontrado o driver no instalado", JOptionPane.ERROR_MESSAGE, false);
         enrollResultFalse();
         // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("enroll", "fail");
      }
      int li_contador = 0;
      for(String ls_huella : ils_listaHuellas) {
         if(!ls_huella.isEmpty())
            li_contador++;
      }
      ijl_lblEstadoLector.setText("Huellas enroladas: " +  li_contador);
      ijb_btnEnrroll.setEnabled(false);
      ib_currentlyEnrolling = false;
   }


  /**
   * Metodo para validar la clave
   * @return resultado de la validacion
   */
  private boolean validateFields() {
     String ls_clave = ijtf_clave.getText();
     String ls_patron_clave = Settings.getClavePatron(); //"(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
     Pattern ls_regex = Pattern.compile(ls_patron_clave);
     Matcher ls_matcher = ls_regex.matcher(ls_clave);
     boolean ls_clave_valida = false;
     
     if (ls_matcher.find()) {
        ls_clave_valida = true;
     }
        
     System.out.println(ls_clave_valida);
     
    String ls_confirmacion = ijtf_confirmacion.getText();
    if(ib_resetHuellas) return true;
    if(ls_clave.isEmpty() || ls_confirmacion.isEmpty()) {
      MessageBox.display("Los campos de contraseña son obligatorios", JOptionPane.ERROR_MESSAGE, false);
      return false;
    } else if(!ls_clave.equals(ls_confirmacion)) {
      MessageBox.display("Las contraseñas deben ser iguales", JOptionPane.ERROR_MESSAGE, false);
      return false;
    } else if(ls_clave.length() < Integer.parseInt(Settings.getClaveMinimo())) {
      MessageBox.display("La contraseña no puede ser inferior a 8 caracteres", JOptionPane.ERROR_MESSAGE, false);
      return false;
    } else if(ls_clave.length() > Integer.parseInt(Settings.getClaveMaximo())) {
      MessageBox.display("La contraseña no puede ser superior a 32 caracteres", JOptionPane.ERROR_MESSAGE, false);
      return false;
    } else if(!ls_clave_valida) {
      MessageBox.display("La contraseña debe tener al menos un caracter en minúscula, uno en mayúscula y un símbolo especial", JOptionPane.ERROR_MESSAGE, false);
      return false;
    } else if(ijcb_huellas.isSelected() && ils_listaHuellas.size() < 1) {
      MessageBox.display("Si la biometría está activa, debe enrolar al menos un dedo", JOptionPane.ERROR_MESSAGE, false);
      return false;
    }
    return true;
  }


  /**
   * Metodo para enviar los datos del enrolamiento al servidor
   */
  private void sendToServer() {
     try {
        il_log.debug("Enviar datos de enrolamiento al servidor");
        StringSalidaDTO ls_result = new StringSalidaDTO();
        
        if(!ib_usuarioExiste) {
           il_log.debug("Usuario es nuevo");
           UsuarioDTO lud_usuarioDTO = new UsuarioDTO();
           lud_usuarioDTO.setIdUsuario(is_idUsuario);
           lud_usuarioDTO.setClave(ijtf_clave.getText());
           lud_usuarioDTO.setClaveActiva("1");
           lud_usuarioDTO.setIdUsuarioCreacion(is_idUsuarioEnrolando);

           // FQ: Se agrega el envío de la fecha como obligatorio
           LocalDateTime fecha = LocalDateTime.now();
           GregorianCalendar calendario = new GregorianCalendar();
           calendario.set(fecha.getYear()+1, fecha.getMonthValue(), fecha.getDayOfMonth(), 23, 59);
           XMLGregorianCalendar xmlCalendario = null;
           xmlCalendario = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendario);
           lud_usuarioDTO.setFechaVencimiento(xmlCalendario);

           il_log.debug("Envío de datos base del usuario " + lud_usuarioDTO.getIdUsuario());
           ls_result = ibws_webService.crearUsuario(lud_usuarioDTO);
           il_log.debug("codigo" + ls_result.getCodigo());
           il_log.debug("mensaje" + ls_result.getMensaje());
           il_log.debug("resultado" + ls_result.getResultado());
        } else {
           il_log.debug("Usuario ya existe");
           ls_result.setResultado("true");
        }
        
        il_log.debug("Resultado datos básicos " + ls_result.getResultado());
        
        if(ls_result.getResultado().contains("failed")) {
           il_log.debug("Error en la verificación/creación del usuario");
           MessageBox.display("Una de las huellas ya ha sido enrolada", JOptionPane.ERROR_MESSAGE, false);
           
           enrollResultFalse();
           // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("enroll", "fail");
        } else if(ls_result.getResultado().equals("true")) {
           il_log.debug("Envío de datos de enrolamiento de huellas");
           
           if(ijcb_huellas.isSelected()) { // enrolamiento incluyendo las huella
              List<HuellaDTO> lldh_fingerprints = new ArrayList<>();
              int li_i = 0;
              
              for( String ls_huella : ils_listaHuellas) {
                 if (!ls_huella.isEmpty()) {
                    HuellaDTO lhd_fingerprint = new HuellaDTO();
                    lhd_fingerprint.setIdUsuario(String.valueOf(is_idUsuario));
                    lhd_fingerprint.setIdUsuarioCreacion(String.valueOf(is_idUsuarioEnrolando));
                    lhd_fingerprint.setPosicion(DedosEnum.fromValue(is_fingersEnum[li_i]));
                    lhd_fingerprint.setImagenHuella(ls_huella);

                    lldh_fingerprints.add(lhd_fingerprint);
                 }
                 li_i++;
              }

              il_log.debug("Enviar datos de huellas");
              BooleanSalidaDTO lbsd_resultHuellas = ibws_webService.enrolarUsuario(lldh_fingerprints);
              
              if(lbsd_resultHuellas.isResultado()) {
            	 il_log.debug("Enrolamiento exitoso");
                 MessageBox.display("Usuario enrolado con éxito", JOptionPane.INFORMATION_MESSAGE, false);
                 enrollResultTrue();
                 // el mensaje se enviará al cerrar la pantalla MessagingAdapter.returnResponse("enroll", "success");
              } else {
            	 il_log.debug("Enrolamiento fallido");
                 BorrarHuellasDTO lbhd_borrarHuellasDTO = new BorrarHuellasDTO();
                 lbhd_borrarHuellasDTO.setIdUsuario(String.valueOf(is_idUsuario));
                 lbhd_borrarHuellasDTO.setIdUsuarioCreacion(String.valueOf(is_idUsuarioEnrolando));
                 ibws_webService.borrarHuellas(lbhd_borrarHuellasDTO);
                 MessageBox.display("Ha ocurrido un error al extraer el template de una huella", JOptionPane.ERROR_MESSAGE, false);
                 enrollResultFalse();
                 // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("enroll", "fail");
              }
           } else { // el enrolamiento se hace sin huellas
              il_log.debug("Enrolamiento exitoso");
              MessageBox.display("Usuario enrolado con éxito", JOptionPane.INFORMATION_MESSAGE, false);
              enrollResultTrue();
              // FQ el mensaje se enviará al cerra la ventana MessagingAdapter.returnResponse("enroll", "success");
           }
        }
     }catch (Exception le_exception) {
       il_log.fatal("Excepción " + le_exception.getMessage());
       PrintStackTrace(le_exception);
       enrollResultFalse();
       // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("enroll", "fail");
     }
     finally
     {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
     }
  }

   /**
   * Metodo para resetear la clave en el servidor
   */
   private void resetPassword() {
      try {
    	 il_log.debug("Cambiar la contraseña");
         UsuarioDTO lud_usuarioDTO = new UsuarioDTO();
         lud_usuarioDTO.setClaveActiva("1");
         lud_usuarioDTO.setIdUsuario(is_idUsuario);
         lud_usuarioDTO.setClave(ijtf_clave.getText());
         lud_usuarioDTO.setIdUsuarioCreacion(is_idUsuarioEnrolando);
         
         // FQ: Se agrega el envío de la fecha como obligatorio
         LocalDateTime fecha = LocalDateTime.now();
         GregorianCalendar calendario = new GregorianCalendar();
         calendario.set(fecha.getYear()+1, fecha.getMonthValue(), fecha.getDayOfMonth(), 23, 59);
         XMLGregorianCalendar xmlCalendario = null;
         xmlCalendario = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendario);
         lud_usuarioDTO.setFechaVencimiento(xmlCalendario);

         
         StringSalidaDTO lssd_result = ibws_webService.actualizarClave(lud_usuarioDTO);
         il_log.debug("resultado : codigo"  + lssd_result.getCodigo());
         il_log.debug("resultado : mensaje"  + lssd_result.getMensaje());
         il_log.debug("resultado : resultado"  + lssd_result.getResultado());
         
         if (lssd_result.getResultado().contains("true")) {
        	il_log.debug("Contraseña actualizada con éxito");
            MessageBox.display("Contraseña actualizada con éxito", JOptionPane.INFORMATION_MESSAGE, false);
            enrollResultTrue();
            // el mensaje se enviará al cerrar la ventana MessagingAdapter.returnResponse("reset", "success");
         } else {
            il_log.debug("Error actualizando la contraseña");
            enrollResultFalse();
            // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("reset", "fail");
            MessageBox.display(lssd_result.getResultado(), JOptionPane.ERROR_MESSAGE, false);
         }
      } catch (Exception le_exception) {
    	il_log.fatal("Excepción actualizando la contraseña: " + le_exception.getMessage());
    	   PrintStackTrace(le_exception);
         MessageBox.display("Ocurrió un error al actualizar los datos", JOptionPane.ERROR_MESSAGE, false);
         enrollResultFalse();
         // FQ el mensaje se enviará el cerrar la ventana MessagingAdapter.returnResponse("reset", "fail");
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
}
