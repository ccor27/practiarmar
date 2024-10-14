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
import practiarmar.proyecto.modelo.Item;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class ItemJpaController {
    
    EntityManagerFactory emf = null;

    public ItemJpaController(EntityManagerFactory emf) {
        this.emf=emf;
    }
    public ItemJpaController() {
        this.emf=Persistence.createEntityManagerFactory("practiarmar_DB_PU");
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void create(Item item){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
        }finally{
            if(em!=null)
                em.close();
        }
    }
    public void edit(Item item) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            item = em.merge(item);
            em.getTransaction().commit();
              }catch(Exception ex){
                   String msg = ex.getLocalizedMessage();
                if (msg == null || msg.length() == 0) {
                    Long id = item.getId();
                    if (findItemById(id) == null) {
                        throw new NonexistentEntityException("El item con el id " + id + " no existe.");
                    }
                }
                throw ex;
            }
        }
    public Item findItemById(Long id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Item.class, id);
        }finally{
            em.close();
        }
    }
    public List<Item> findEntities(){
        return findItemEntity(true, -1, -1);
    }
    public List<Item> findEntities(boolean all, int maxResult, int firstResult){
        return findItemEntity(all, maxResult, firstResult);
    }
    private List<Item> findItemEntity(boolean all, int maxResult, int firstResult){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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
      public int getFotoCount(){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            em.getTransaction().commit();
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }
       public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item foto;
            try {
                foto = em.getReference(Item.class, id);
                foto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El item con el id " + id + " no existe.", enfe);
            }
            em.remove(foto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
   
