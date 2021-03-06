package co.gov.supernotariado.bachue.dispositivos.biometrico.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Clase para mostrar las imagenes de los dedos
 *
 */
class ImageDedo extends JPanel {
   /**
    * Serial de la versi?n
    */
  private static final long serialVersionUID = 1L;
  /**
   * Imagen del dedo
   */
   private Image ii_img;

   /**
    * COnstructor de la clase
    * @param ai_img imagen del dedo
    * @param ai_nWidth ancho de la imagen
    * @param ai_nHeight alto de la imagen
    */
   ImageDedo(Image ai_img, int ai_nWidth, int ai_nHeight) {
    this.ii_img = ai_img;
   
    Dimension ld_size = new Dimension(ai_nWidth, ai_nHeight);
    setPreferredSize(ld_size);
    setMinimumSize(ld_size);
    setMaximumSize(ld_size);
    setSize(ld_size);
    setLayout(null);
    
  }

  /**
   * Metodo que cambia la imagen dentro del control
   * @param ai_img imagen nueva
   * @param ai_nWidth ancho de la imagen
   * @param ai_nHeight alto de la imagen
   */
  public void cambiarImagen(Image ai_img, int ai_nWidth, int ai_nHeight) {
	    this.ii_img = ai_img;
	   
	    Dimension ld_size = new Dimension(ai_nWidth, ai_nHeight);
	    setPreferredSize(ld_size);
	    setMinimumSize(ld_size);
	    setMaximumSize(ld_size);
	    setSize(ld_size);
	    setLayout(null);
	    
	  }


  /**
   * Metodo para cambiar la imagen del componente
   * @param ag_g nuevo grafico
   */
  public void paintComponent(Graphics ag_g) {
    ag_g.drawImage(ii_img, 0, 0, null);
  }

}
