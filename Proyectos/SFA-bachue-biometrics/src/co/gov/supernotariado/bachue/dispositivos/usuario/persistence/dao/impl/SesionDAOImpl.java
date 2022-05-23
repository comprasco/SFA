package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ISesionDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Sesion;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementación de interface de DAO que permite la consulta de sesiones.
 */
@Stateless
@Local
public class SesionDAOImpl implements ISesionDAO {

	@EJB
	private IEntityManagerFactory iiemf_entityFactory;

	@Override
	public Sesion consultarSesion(String as_sesion) {
		EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
		Sesion ls_sesion = lem_entityManager.find(Sesion.class, as_sesion);
		SimpleDateFormat lsdf_format = new SimpleDateFormat("yyyyMMdd");
		if(ls_sesion != null && !lsdf_format.format(ls_sesion.getFechaCreacion()).equals(lsdf_format.format(new Date()))) {
			ls_sesion.setResultado(false);
		}
		lem_entityManager.close();
		return ls_sesion;
	}

	@Override
	public Boolean crearSesion(Sesion as_sesion) {
		EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
		
		try {
			lem_entityManager.persist(as_sesion);
			lem_entityManager.close();
		}catch (Exception le_e) {
			lem_entityManager.close();
			return false;
		}
		return true;
	}

	@Override
	public Boolean actualizarSesion(Sesion as_sesion) {
		EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
		try {
			Sesion ls_sesion = lem_entityManager.find(Sesion.class, as_sesion.getSesion());
			ls_sesion.setFechaModificacion(as_sesion.getFechaCreacion());
			ls_sesion.setIdUsuarioModificacion(as_sesion.getIdUsuarioCreacion());
			ls_sesion.setIpModificacion(as_sesion.getIpCreacion());
			ls_sesion.setResultado(as_sesion.getResultado());
			ls_sesion.setSesion(as_sesion.getSesion());
			lem_entityManager.persist(ls_sesion);
			lem_entityManager.close();
			return true;
		}catch(Exception e){
			lem_entityManager.close();
			return false;
		}
	}

}
