/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.persistence;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.exception.ConstraintViolationException;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.PagoEmpleado;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class EmpleadoJpaController implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("practiarmar_DB_PU");

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EmpleadoJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
        }catch(Exception e){
             throw e;
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            empleado = em.merge(empleado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empleado.getId();
                if (findEmpleadoById(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq =cb.createQuery(Empleado.class);
            Root<Empleado> empleadoRoot = cq.from(Empleado.class);
            // Add WHERE clause to filter by 'isDeleted'
            cq.where(cb.equal(empleadoRoot.get("isDeleted"), false));  
            
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            em.getTransaction().commit();
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Empleado findEmpleadoById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public Empleado findEmpleadoByCodigo(String codigo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
            Root<Empleado> root = cq.from(Empleado.class);
            cq.where(cb.equal(root.get("codigo"), codigo));
            Empleado empleado = em.createQuery(cq).getSingleResult();
            em.getTransaction().commit();
            return empleado;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("No se encontró el empleado con el código " + codigo);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El código del empleado no puede ser nulo o vacío");
        } catch (PersistenceException e) {
            throw new RuntimeException("Ocurrió un error al acceder a la base de datos");
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            em.getTransaction().commit();
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Empleado loingEmpleado(String username, String contrasena) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            String query = "SELECT e FROM Empleado e WHERE e.username =:username "
                    + "AND e.contrasena =:contrasena";
            Query q = em.createQuery(query);
            q.setParameter("username", username);
            q.setParameter("contrasena", contrasena);
            Empleado e = (Empleado) q.getSingleResult();
            em.getTransaction().commit();
            return e;
        } finally {
            em.close();
        }
    }
    public List<PagoEmpleado> getPagosEmpleado(String codigo){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            Empleado emp = em.find(Empleado.class, codigo);
            List<PagoEmpleado> pagos = emp.getPagos();
            em.getTransaction().commit();
            return pagos;
        }finally{
            em.close();
        }
    }
}
