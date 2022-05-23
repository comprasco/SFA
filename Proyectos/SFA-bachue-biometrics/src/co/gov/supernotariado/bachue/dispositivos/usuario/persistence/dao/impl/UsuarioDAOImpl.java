package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.IUsuarioDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Usuario;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import java.sql.Timestamp;
/**
 * Implementación de interface de DAO que permite la gestion de usuarios.
 */
@Stateless
@Local
public class UsuarioDAOImpl implements IUsuarioDAO {

	@EJB
	private IEntityManagerFactory iiemf_entityFactory;

	@Override
	public Boolean crearUsuario(Usuario au_usuario) {
		try {
			EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
			lem_entityManager.persist(au_usuario);
			lem_entityManager.close();
		}catch (Exception le_exception) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean actualizarClave(Usuario au_usuario) {
		try {
			EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
			Usuario lu_usuario = lem_entityManager.find(Usuario.class, au_usuario.getIdUsuario());
			lu_usuario.setClaveHash(au_usuario.getClaveHash());
			lu_usuario.setFechaModificacion(au_usuario.getFechaModificacion());
			lu_usuario.setIpModificacion(au_usuario.getIpModificacion());
			lu_usuario.setIdUsuarioModificacion(au_usuario.getIdUsuarioModificacion());
			Timestamp fecha = new Timestamp((new Date()).getTime()+60L*(1000 * 60 * 60 * 24));
			lu_usuario.setFechaVencimiento(fecha);
			lu_usuario.setClaveActiva('1');
			lem_entityManager.merge(lu_usuario);
			lem_entityManager.close();
		}catch (Exception le_exception) {
			return false;
		}
		return true;
	}

	@Override
	public Usuario consultarUsuario(String idUsuario) {
		EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
		Usuario lu_usuario = lem_entityManager.find(Usuario.class, idUsuario);
		lem_entityManager.close();
		return lu_usuario;
	}

	@Override
	public Boolean borrarUsuario(String idUsuario) {
		EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
		Usuario lu_usuario = lem_entityManager.find(Usuario.class, idUsuario);
		if(lu_usuario != null)
			lem_entityManager.remove(lu_usuario);
		lem_entityManager.close();
		return true;
	}
}
