package co.gov.supernotariado.bachue.dispositivos.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IHistoricoDAO;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Historico;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Implementacion de interface de DAO que permite la gestion de usuarios.
 *
 */
@Stateless
@Local
public class HistoricoDAOImpl implements IHistoricoDAO {
   /**
    * Factory de entidades
    */
   @EJB
   private IEntityManagerFactory iiemf_entityFactory;

   @Override
   public Boolean crearHistorico(Historico ah_historico) {
      try {
         EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
         lem_entityManager.persist(ah_historico);
         lem_entityManager.close();
      }catch (Exception le_exception) {
         return false;
      }
      return true;
   }

   @Override
   public List<Historico> consultarUltimasCincoClaves(String idUsuario) {
      EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();

      String query = "SELECT u FROM Historico u WHERE u.idUsuario =:usuarioId order by u.fechaCreacion desc ";
      List<Historico> llh_historico = lem_entityManager.createQuery(query, Historico.class).setParameter("usuarioId", idUsuario).setMaxResults(5).getResultList();
      lem_entityManager.close();
      return llh_historico;
   }
}
