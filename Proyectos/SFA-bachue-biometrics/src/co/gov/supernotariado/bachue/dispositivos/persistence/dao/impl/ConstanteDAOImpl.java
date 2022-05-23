package co.gov.supernotariado.bachue.dispositivos.persistence.dao.impl;

import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IConstanteDAO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dao.IEntityManagerFactory;
import co.gov.supernotariado.bachue.dispositivos.persistence.model.Constante;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementacion de interface de DAO que permite la gestion de usuarios.
 */
@Stateless
@Local
public class ConstanteDAOImpl implements IConstanteDAO {

   /**
    * Factory de entidades
    */
   @EJB
   private IEntityManagerFactory iiemf_entityFactory;

   @Override
   public List<Constante> consultarConstantes() {
      EntityManager lem_entityManager = iiemf_entityFactory.getEntityManager();

      String ls_query = "SELECT p FROM Constante p WHERE p.idConstante =:primerValor OR p.idConstante =:segundoValor OR p.idConstante =:tercerValor OR p.idConstante =:cuartoValor OR p.idConstante =:quintoValor";
      Query lq_query = lem_entityManager.createQuery(ls_query, Constante.class);
      lq_query.setParameter("primerValor", "MATCHING_THRESHOLD_BIOMETRIA");
      lq_query.setParameter("segundoValor", "PATRON_CLAVE_SEGUNDO_FACTOR");
      lq_query.setParameter("tercerValor", "CARACTERES_MAXIMOS_CLAVE_SEGUNDO_FACTOR");
      lq_query.setParameter("cuartoValor", "CARACTERES_MINIMOS_CLAVE_SEGUNDO_FACTOR");
      lq_query.setParameter("quintoValor", "DESCARGA_DOCUMENTOS_PDF_ENDPOINT");
      @SuppressWarnings("unchecked")
      List<Constante> llc_constante = lq_query.getResultList();
      lem_entityManager.close();
      return llc_constante;
   }
}
