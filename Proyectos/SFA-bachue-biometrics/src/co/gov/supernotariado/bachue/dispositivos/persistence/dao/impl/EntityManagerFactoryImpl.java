package co.gov.supernotariado.bachue.dispositivos.persistence.dao.impl;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;

/**
 * Implementacion del EntityManagerFactory para el acceso a datos.
 */
@Singleton
@Local
public class EntityManagerFactoryImpl implements IEntityManagerFactory {
   /**
    * Factory de entidades
    */
   @PersistenceUnit(name="biometria-bachue")
   EntityManagerFactory iemf_factory;

   @Override
   public EntityManager getEntityManager(){
      return iemf_factory.createEntityManager();
   }
}
