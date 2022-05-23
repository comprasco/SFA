package co.gov.supernotariado.bachue.dispositivos.ejb.impl;

import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.common.util.MotorBiometrico;
import co.gov.supernotariado.bachue.dispositivos.ejb.IConstanteBusiness;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.*;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.ConstantesSalidaDTO;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.ArrayList;

/**
 * Implementacion de logica de negocio de usuario
 */
@Stateless(name="ConstanteBusiness")
@Local
public class ConstanteBusiness implements IConstanteBusiness {
   /**
    * DAO de constantes
    */
   @EJB
   private IConstanteDAO iicd_constanteDao;


   @Override
   public ConstantesSalidaDTO consultarConstantes() {
      MotorBiometrico.getInstance();
      ConstantesSalidaDTO lcsd_constantes = new ConstantesSalidaDTO();
      lcsd_constantes.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lcsd_constantes.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());

      try {
         lcsd_constantes.setConstantes(new ArrayList<>(iicd_constanteDao.consultarConstantes()));
         return lcsd_constantes;
      } catch (Exception le_exception) {
         lcsd_constantes.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lcsd_constantes.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
         return lcsd_constantes;
      }
   }
}
