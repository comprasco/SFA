package co.gov.supernotariado.bachue.dispositivos.usuario.ejb.impl;

import co.gov.supernotariado.bachue.dispositivos.common.enums.SalidasEnum;
import co.gov.supernotariado.bachue.dispositivos.usuario.ejb.ISesionBusiness;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ISesionDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.SesionDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.helper.SesionHelper;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Implementacion de logica de negocio de sesiones.
 */
@Stateless(name="SesionBusiness")
@Local
public class SesionBusiness implements ISesionBusiness {
   /**
    * DAO de sesión
    */
  @EJB
  private ISesionDAO iisd_sesionDao;

  @Override
  public SesionDTO consultarSesion(String as_sesion) {
    try {
      return SesionHelper.toDto(iisd_sesionDao.consultarSesion(as_sesion));
    } catch (Exception le_exception) {
      SesionDTO lsd_sesion = new SesionDTO();
      lsd_sesion.setCodigo(SalidasEnum.RECURSO_NO_ENCONTRADO.consultarCodigo());
      lsd_sesion.setMensaje(SalidasEnum.RECURSO_NO_ENCONTRADO.consultarMensaje());
      return lsd_sesion;
    }
  }
}
