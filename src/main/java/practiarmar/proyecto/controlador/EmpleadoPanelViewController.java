/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;


import java.util.List;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class EmpleadoPanelViewController {
    
      private ModelFactoryController controller;
    
    public EmpleadoPanelViewController(){
        this.controller= ModelFactoryController.getInstance();
    }
    public List<Empleado> obtenerListaEmpleados(){
        return controller.obtenerListaEmpleados();
    }

    public void eliminar(Empleado empleado) throws Exception {
       controller.eliminarEmpleado(empleado ) ;
    }
}
