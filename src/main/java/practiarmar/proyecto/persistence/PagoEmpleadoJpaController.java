/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.persistence;

import java.util.List;
import practiarmar.proyecto.modelo.PagoEmpleado;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class PagoEmpleadoJpaController {
     
    EntityManagerFactory emf = null;

    public PagoEmpleadoJpaController(EntityManagerFactory emf) {
        this.emf=emf;
    }
    public PagoEmpleadoJpaController() {
        this.emf=Persistence.createEntityManagerFactory("practiarmar_DB_PU");
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void create(PagoEmpleado pagoEmpleado){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(pagoEmpleado);
            em.getTransaction().commit();
        }finally{
            if(em!=null)
                em.close();
        }
    }
    public void edit(PagoEmpleado pagoEmpleado) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            pagoEmpleado = em.merge(pagoEmpleado);
            em.getTransaction().commit();
              }catch(Exception ex){
                   String msg = ex.getLocalizedMessage();
                if (msg == null || msg.length() == 0) {
                    Long id = pagoEmpleado.getId();
                    if (findPagoEmpleadoById(id) == null) {
                        throw new NonexistentEntityException("El pago con el id " + id + " no existe.");
                    }
                }
                throw ex;
            }
        }
    public PagoEmpleado findPagoEmpleadoById(Long id){
        EntityManager em = getEntityManager();
        try{
            return em.find(PagoEmpleado.class, id);
        }finally{
            em.close();
        }
    }
    public List<PagoEmpleado> findEntities(){
        return findPagoEmpleadoEntity(true, -1, -1);
    }
    public List<PagoEmpleado> findEntities(boolean all, int maxResult, int firstResult){
        return findPagoEmpleadoEntity(all, maxResult, firstResult);
    }
    private List<PagoEmpleado> findPagoEmpleadoEntity(boolean all, int maxResult, int firstResult){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoEmpleado.class));
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
      public int getPagosCount(){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoEmpleado> rt = cq.from(PagoEmpleado.class);
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
            PagoEmpleado pagoEmpleado;
            try {
                pagoEmpleado = em.getReference(PagoEmpleado.class, id);
                pagoEmpleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El pago con el id " + id + " no existe.", enfe);
            }
            em.remove(pagoEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
