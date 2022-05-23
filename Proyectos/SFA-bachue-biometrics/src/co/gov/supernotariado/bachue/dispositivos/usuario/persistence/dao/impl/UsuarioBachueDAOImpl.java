package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.IUsuarioBachueDAO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.UsuarioBachue;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 * Implementación de interface de DAO que permite la gestion de usuarios.
 */
@Stateless
@Local
public class UsuarioBachueDAOImpl implements IUsuarioBachueDAO {

	@EJB
	private IEntityManagerFactory iiemf_entityFactory;

	@Override
	public String obtenerSegundoFactor(String as_usuarioId) {
		EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
		UsuarioBachue lub_usuario = lem_entityManager.find(UsuarioBachue.class, as_usuarioId);
		lem_entityManager.close();
		return lub_usuario.getSegundoFactorAutenticacion();
	}
}
