package co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.dao.IHuellaDAO;
import co.gov.supernotariado.bachue.dispositivos.biometrico.persistence.model.Huella;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Implementacion de interface de DAO que permite la gestion de operaciones biometricas.
 */
@Stateless
@Local
public class HuellaDAOImpl implements IHuellaDAO {
   /**
    * Factory de gestión de entidades
    */
   @EJB
   private IEntityManagerFactory iiemf_entityFactory;

   @Override
   public Boolean crearHuella(Huella ah_huella) {
      try {
         EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
         lem_entityManager.persist(ah_huella);
         lem_entityManager.close();
      }catch (Exception le_exception) {
         return false;
      }
      return true;
   }

   @Override
   public Boolean borrarHuellas(String as_idUsuario) {
      try {
         EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
         String query = "SELECT u FROM Huella u WHERE u.usuario.idUsuario =:usuarioId";
         List<Huella> llh_huellas = lem_entityManager.createQuery(query, Huella.class).setParameter("usuarioId", as_idUsuario).getResultList();
         llh_huellas.forEach(lem_entityManager::remove);
         lem_entityManager.close();
      }catch (Exception le_exception) {
         return false;
      }
      return true;
   }

   @Override
   public int contarHuellas(String as_idUsuario) {
      try {
         EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
         String query = "SELECT u FROM Huella u WHERE u.usuario.idUsuario =:usuarioId";
         List<Huella> llh_huellas = lem_entityManager.createQuery(query, Huella.class).setParameter("usuarioId", as_idUsuario).getResultList();
         lem_entityManager.close();
         return llh_huellas.size();
      } catch (Exception le_exception) {
         return 0;
      }
   }

   @Override
   public List<Huella> obtenerHuellas(String as_idUsuario) {
      EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();

      String query = "SELECT u FROM Huella u WHERE u.usuario.idUsuario =:usuarioId";
      List<Huella> llh_huellas = lem_entityManager.createQuery(query, Huella.class).setParameter("usuarioId", as_idUsuario).getResultList();
      lem_entityManager.close();
      return llh_huellas;
   }
}
