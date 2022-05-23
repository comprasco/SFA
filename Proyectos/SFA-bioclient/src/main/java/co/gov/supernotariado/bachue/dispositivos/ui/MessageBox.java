package co.gov.supernotariado.bachue.dispositivos.ui;


import javax.swing.JFrame;
import javax.swing.JOptionPane;;;

/**
 * Clase que muestra mensajes en pantalla.
 */
public class MessageBox {

  /**
   * Metodo que muestra los mensajes en pantalla y cierra la aplicacion.
   * @param as_message mensaje que se mostrara.
   * @param ai_type tipo de alerta mostrada.
   * @param ab_exitOrder indicador de terminación del programa despues de mostrar el mensaje
   */
  public static void display(String as_message, int ai_type, boolean ab_exitOrder) {
    JFrame ljf_jf = new JFrame();
    ljf_jf.setAlwaysOnTop(true);
    JOptionPane.showMessageDialog(ljf_jf, as_message, "Bachué", ai_type);
    if(ab_exitOrder)
      System.exit(0);
  }
}
