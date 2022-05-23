package co.gov.supernotariado.bachue.dispositivos.persistence.dao;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * Interface con los metodos del singleton del EntityManagerFactory.
 */
@Local
public interface IEntityManagerFactory {

  /**
   * Metodo que accede al singleton del entity manager.
   */
   EntityManager getEntityManager();

}
