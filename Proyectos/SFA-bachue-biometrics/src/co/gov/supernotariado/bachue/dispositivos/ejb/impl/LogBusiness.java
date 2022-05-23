package co.gov.supernotariado.bachue.dispositivos.ejb.impl;

import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.ejb.ILogBusiness;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.ILogDAO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BooleanSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.EstadisticasSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.LogDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.helper.LogHelper;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.NoSuchElementException;

/**
 * Implementacion de logica de negocio de logs.
 */
@Stateless(name="LogBusiness")
@Local
public class LogBusiness implements ILogBusiness {
   /**
    * DAO del log de negocio
    */
   @EJB
   private ILogDAO iild_logDao;

   @Override
   public BooleanSalidaDTO registrarEvento(LogDTO ald_log) {
      BooleanSalidaDTO lbsd_salida = new BooleanSalidaDTO();
      lbsd_salida.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lbsd_salida.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());
      try {
         lbsd_salida.setResultado(iild_logDao.crearEvento(LogHelper.toEntity(ald_log)));
         return lbsd_salida;
      } catch (Exception le_exception) {
         lbsd_salida.setCodigo(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarCodigo());
         lbsd_salida.setMensaje(SalidasEnum.EXCEPCION_NO_CONTROLADA.consultarMensaje());
         return lbsd_salida;
      }
   }

   @Override
   public EstadisticasSalidaDTO consultarStats(String as_tipo, String as_id) {
      EstadisticasSalidaDTO lesd_estadisticas = new EstadisticasSalidaDTO();
      lesd_estadisticas.setCodigo(SalidasEnum.RECURSO_EXITOSO.consultarCodigo());
      lesd_estadisticas.setMensaje(SalidasEnum.RECURSO_EXITOSO.consultarMensaje());
      try {
         int li_contador = iild_logDao.consultarStats(as_tipo, as_id);
         if(li_contador > 0) {
            lesd_estadisticas.setContador(li_contador);
         } else {
            throw new NoSuchElementException();
         }
         return lesd_estadisticas;
      } catch (Exception le_exception) {
         lesd_estadisticas.setContador(0);
         lesd_estadisticas.setCodigo(SalidasEnum.RECURSO_NO_ENCONTRADO.consultarCodigo());
         lesd_estadisticas.setMensaje(SalidasEnum.RECURSO_NO_ENCONTRADO.consultarMensaje());
         return lesd_estadisticas;
      }
   }
}
