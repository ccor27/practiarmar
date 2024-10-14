/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import java.util.List;
import practiarmar.proyecto.modelo.Proyecto;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;
/**
 *
 * @author crist
 */
public class ProyectoPanelViewController {
    
     private ModelFactoryController controller;
    
    public ProyectoPanelViewController(){
        this.controller= ModelFactoryController.getInstance();
    }
    public List<Proyecto> obtenerProyectos(){
        return controller.obtenerProyectos();
    }

    public void removeProyecto(Proyecto proyecto) throws NonexistentEntityException {
       controller.removeProyecto(proyecto);
    }
}
