package co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dao.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.dao.IFirmaDAO;
import co.gov.supernotariado.bachue.dispositivos.padfirmas.persistence.model.Firma;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;

/**
 * Implementación DAO de operaciones de firmas digitalizadas.
 */
@Stateless
@Local
public class FirmaDAOImpl implements IFirmaDAO{
   
   /**
    * Factory de gestión de entidades
    */
   @EJB
   private IEntityManagerFactory iiemf_entityFactory;

   @Override
   public Boolean crearFirma(Firma af_firma) {
      try {
         EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
         lem_entityManager.persist(af_firma);
         lem_entityManager.close();
      }catch (Exception le_exception) {
         return false;
      }
      
      return true;
   }

   @Override
   public Boolean eliminarFirma (String as_idFirma) {
      try {
         EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
         String ls_query = "SELECT f FROM Firma f WHERE f.idFirma =:firmaId";
         List<Firma> llf_firma = lem_entityManager.createQuery(ls_query, Firma.class).setParameter("firmaId", as_idFirma).getResultList();
         if(llf_firma.size()==0){
            lem_entityManager.close();
            return false;
         }
         
         llf_firma.forEach(lem_entityManager::remove);
         lem_entityManager.close();
      }catch (Exception le_exception) {
         return false;
      }
      return true;
   }

   @Override
   public List<Firma> listarFirma(String as_idFirma) {
      EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();
      String ls_query = "SELECT f FROM Firma f WHERE f.idFirma =:firmaId";
      List<Firma> llf_firma =lem_entityManager.createQuery(ls_query, Firma.class).setParameter("firmaId", as_idFirma).getResultList();
      lem_entityManager.close();
      return llf_firma; 			
   }
} 
