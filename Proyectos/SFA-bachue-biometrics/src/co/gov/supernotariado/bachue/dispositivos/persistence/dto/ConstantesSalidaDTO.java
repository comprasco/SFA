package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

import javax.xml.bind.annotation.XmlElement;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.Constante;

import java.util.ArrayList;

/**
 * DTO de salida de constantes.
 */
public class ConstantesSalidaDTO extends BaseSalidaDTO {
   /**
    * Arreglo de constantes
    */
   private ArrayList<Constante> ilc_constantes;

   /**
    * Retorna la lista de constantes
    * @return lista de constantes
    */
   @XmlElement(name = "constante")
   public ArrayList<Constante> getConstantes() {
      return ilc_constantes;
   }

   /**
    * Establece la lista de constantes
    * @param alc_constantes lista de constantes
    */
   public void setConstantes(ArrayList<Constante> alc_constantes) {
      this.ilc_constantes = alc_constantes;
   }
}
