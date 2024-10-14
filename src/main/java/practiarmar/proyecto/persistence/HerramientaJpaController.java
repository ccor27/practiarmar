/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.persistence;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import practiarmar.proyecto.modelo.Herramienta;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class HerramientaJpaController {

    EntityManagerFactory emf = null;

    public HerramientaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public HerramientaJpaController() {
        emf = Persistence.createEntityManagerFactory("practiarmar_DB_PU");
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void create(Herramienta herramienta){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(herramienta);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    }
    public void edit(Herramienta herramienta) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            herramienta = em.merge(herramienta);
            em.getTransaction().commit();
        }catch(Exception ex){
               String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = herramienta.getId();
                if (findHerramienta(id) == null) {
                    throw new NonexistentEntityException("La herramienta con el id " + id + " no existe.");
                }
            }
            throw ex;
        }
    }
     public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Herramienta herramienta;
            try {
                herramienta = em.getReference(Herramienta.class, id);
                herramienta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La herramienta con el id " + id + " no existe.", enfe);
            }
            em.remove(herramienta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Herramienta findHerramienta(Long id){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            Herramienta hr = em.find(Herramienta.class, id);
            em.getTransaction().commit();
            return hr;
        }finally{
            em.close();
        }
    }
    public List<Herramienta> findEntities(){
        return findHerramientaEntity(true, -1, -1);
    }
    public List<Herramienta> findEntities(boolean all, int maxResult, int firstResult){
        return findHerramientaEntity(all, maxResult, firstResult);
    }
    private List<Herramienta> findHerramientaEntity(boolean all, int maxResult, int firstResult){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Herramienta.class));
            Query q = em.createQuery(cq);
            if(!all){
                q.setMaxResults(maxResult);
                q.setFirstResult(firstResult);
            }
            em.getTransaction().commit();
            return q.getResultList();
        }finally{
            em.close();
        }
    }
    public int getHerramientaCount(){
           EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Herramienta> rt = cq.from(Herramienta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            em.getTransaction().commit();
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }
     public List<Herramienta> getHerramientasPorCategoria(String categoria) {
       EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Herramienta> cq = cb.createQuery(Herramienta.class);
       Root<Herramienta> root = cq.from(Herramienta.class);
       cq.where(cb.equal(root.get("categoria"), categoria));
       List<Herramienta> herramientas = em.createQuery(cq).getResultList();
       em.getTransaction().commit();
       return herramientas;
       }finally{
           em.close();
       }
   }
}
