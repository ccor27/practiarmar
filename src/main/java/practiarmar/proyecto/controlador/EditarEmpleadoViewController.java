/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.Rol;

/**
 *
 * @author crist
 */
public class EditarEmpleadoViewController {
    
      private ModelFactoryController controller;
    
    public EditarEmpleadoViewController(){
        this.controller= ModelFactoryController.getInstance();
    }

    public void editar(Empleado empleadoAEditar) throws Exception {
        this.controller.editarEmpleado(empleadoAEditar);
    }

    
}
