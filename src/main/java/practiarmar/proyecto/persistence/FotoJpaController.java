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
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import practiarmar.proyecto.modelo.Foto;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;
/**
 *
 * @author crist
 */
public class FotoJpaController {
    
    private EntityManagerFactory emf = null;
    
    public FotoJpaController(EntityManagerFactory emf){
        this.emf=emf;
    }
    public FotoJpaController(){
        this.emf = Persistence.createEntityManagerFactory("practiarmar_DB_PU");
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void crear(Foto foto){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(foto);
            em.getTransaction().commit();
        }finally{
            if(em!=null){
                em.close();
            }
        }
    }
    public void edit(Foto foto) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            foto = em.merge(foto);
            em.getTransaction().commit();
            }catch(Exception ex){
                   String msg = ex.getLocalizedMessage();
                if (msg == null || msg.length() == 0) {
                    Long id = foto.getId();
                    if (findFoto(id) == null) {
                        throw new NonexistentEntityException("The foto with id " + id + " no longer exists.");
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
            Foto foto;
            try {
                foto = em.getReference(Foto.class, id);
                foto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La foto con el id " + id + " no existe.", enfe);
            }
            em.remove(foto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Foto findFoto(Long id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Foto.class, id);
        }finally{
            em.close();
        }
    }
    
    public List<Foto> findEntities(){
        return findFotoEntities(true, -1, 1);
    }
    public List<Foto> findEntities(boolean all,int maxResults, int firstResult){
        return findFotoEntities(all, maxResults, firstResult);
    }
    private List<Foto> findFotoEntities(boolean all, int maxResult, int firstResul){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foto.class));
            Query q = em.createQuery(cq);
            if(!all){
                q.setMaxResults(maxResult);
                q.setFirstResult(firstResul);
            }
            em.getTransaction().commit();
            return q.getResultList();
        }finally{
            em.close();
        }
    }
    
    public int getFotoCount(){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foto> rt = cq.from(Foto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            em.getTransaction().commit();
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }
    
}
