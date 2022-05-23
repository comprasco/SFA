package co.gov.supernotariado.bachue.dispositivos.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.ILogDAO;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Accion;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Implementacion de interface de DAO que permite la gestion de logs.
 */
@Stateless
@Local
public class LogDAOImpl implements ILogDAO {
   /**
    * Factory de entidades
    */
   @EJB
   private IEntityManagerFactory iiemf_entityFactory;

   @Override
   public Boolean crearEvento(Accion al_log) {
		try {
			EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
			lem_entityManager.persist(al_log);
			lem_entityManager.close();
		}catch (Exception le_e) {
			return false;
		}
		return true;
   }

   @Override
   public int consultarStats(String as_tipo, String as_id) {
      EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
      Query lq_query = lem_entityManager.createQuery(
                     "SELECT COUNT(p) FROM Accion p WHERE p.evento =:tipo AND p.idEntidad =:id"
		);
      lq_query.setParameter("tipo", as_tipo);
      lq_query.setParameter("id", as_id);
      int ii_conteo = ((Number) lq_query.getSingleResult()).intValue();
      lem_entityManager.close();
      return ii_conteo;
   }
}
