/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.persistence;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import practiarmar.proyecto.modelo.Proyecto;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.Foto;
import practiarmar.proyecto.modelo.Item;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class ProyectoJpaController {
    
    EntityManagerFactory emf = null;

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf=emf;
    }
    public ProyectoJpaController() {
        this.emf=Persistence.createEntityManagerFactory("practiarmar_DB_PU");
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void create(Proyecto proyecto){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            em.persist(proyecto);
            em.getTransaction().commit();
        }finally{
            if(em!=null)
                em.close();
        }
    }
    public void edit(Proyecto proyecto) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            proyecto = em.merge(proyecto);
            em.getTransaction().commit();
              }catch(Exception ex){
                   String msg = ex.getLocalizedMessage();
                if (msg == null || msg.length() == 0) {
                    Long id = proyecto.getId();
                    if (findItemById(id) == null) {
                        throw new NonexistentEntityException("El proyecto con el id " + id + " no existe.");
                    }
                }
                throw ex;
            }
        }
    public Proyecto findItemById(Long id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Proyecto.class, id);
        }finally{
            em.close();
        }
    }
    public List<Proyecto> findEntities(){
        return findProyectoEntity(true, -1, -1);
    }
    public List<Proyecto> findEntities(boolean all, int maxResult, int firstResult){
        return findProyectoEntity(all, maxResult, firstResult);
    }
    private List<Proyecto> findProyectoEntity(boolean all, int maxResult, int firstResult){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(Proyecto.class);
            Root<Proyecto> proyectoRoot = cq.from(Proyecto.class );
            cq.where(cb.equal(proyectoRoot.get("isDeleted"),false));
            
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
       public List<Proyecto> getProyectosByEstado(String estadoProyecto) {
       EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Proyecto> cq = cb.createQuery(Proyecto.class);
       Root<Proyecto> root = cq.from(Proyecto.class);
       cq.where(cb.equal(root.get("estadoProyecto"), estadoProyecto));
       List<Proyecto> proyectos = em.createQuery(cq).getResultList();
       em.getTransaction().commit();
       return proyectos;
       }finally{
           em.close();
       }
   }
      public int getProyectoCount(){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El proyecto con el id " + id + " no existe.", enfe);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
       
    public Proyecto findProyectoByCodigo(String codigo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Proyecto> cq = cb.createQuery(Proyecto.class);
            Root<Proyecto> root = cq.from(Proyecto.class);
            cq.where(cb.equal(root.get("codigo"), codigo));
            Proyecto empleado = em.createQuery(cq).getSingleResult();
            em.getTransaction().commit();
            return empleado;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("No se encontró el proyecto con el código " + codigo);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El código del proyecto no puede ser nulo o vacío");
        } catch (PersistenceException e) {
            throw new RuntimeException("Ocurrió un error al acceder a la base de datos");
        } finally {
            em.close();
        }
    }
    public Cliente getClienteProyecto(String codigo){
       EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = findProyectoByCodigo(codigo);
           Cliente c = p.getCliente();
           em.getTransaction().commit();
           return c;
       }finally{
           em.close();
       }
    }
    public List<Empleado> getEmpleados(String codigo){
        EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = findProyectoByCodigo(codigo);
           List<Empleado> empleados = p.getEmpleados();
           em.getTransaction().commit();
           return empleados;
       }finally{
           em.close();
       }
    }
    public List<Foto> getFotos(String codigo){
         EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = findProyectoByCodigo(codigo);
           List<Foto> fotos = p.getFotos();
           em.getTransaction().commit();
           return fotos;
       }finally{
           em.close();
       }
    }
     public List<Item> getItmes(String codigo){
         EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = findProyectoByCodigo(codigo);
           List<Item> items = p.getItems();
           em.getTransaction().commit();
           return items;
       }finally{
           em.close();
       }
    }
     public void addCliente(Cliente cliente, String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = findProyectoByCodigo(codigo);
           p.setCliente(cliente);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
      public void removeCliente(String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = findProyectoByCodigo(codigo);
           p.setCliente(null);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
      public void addFoto(Foto foto,String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = em.find(Proyecto.class,codigo);
           p.getFotos().add(foto);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
        public void removeFoto(Foto foto,String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = em.find(Proyecto.class,codigo);
           p.getFotos().remove(foto);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
          public void addEmpleado(Empleado empleado,String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = em.find(Proyecto.class,codigo);
           p.getEmpleados().add(empleado);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
            public void removeEmpleado(Empleado empleado,String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = em.find(Proyecto.class,codigo);
           p.getEmpleados().remove(empleado);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
              public void addItem(Item item,String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = em.find(Proyecto.class,codigo);
           p.getItems().add(item);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
                public void removeItem(Item item,String codigo){
             EntityManager em = getEntityManager();
       try{
           em.getTransaction().begin();
           Proyecto p = em.find(Proyecto.class,codigo);
           p.getItems().remove(item);
           em.flush();
           em.getTransaction().commit();
       }finally{
           em.close();
       }
     }
}
