/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import java.sql.SQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import practiarmar.proyecto.modelo.Rol;
import practiarmar.proyecto.modelo.Empleado;

/**
 *
 * @author crist
 */
public class AgregarEmpleadoViewController {
    
    private ModelFactoryController controller;
    
    public AgregarEmpleadoViewController(){
        this.controller= ModelFactoryController.getInstance();
    }

    public void crear(String nombre, String codigo, String password, String telefono, String email, String username, Rol rol) throws Exception {
        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setCodigo(codigo);
        empleado.setContrasena(password);
        empleado.setTelefono(telefono);
        empleado.setEmail(email);
        empleado.setUsername(username);
        empleado.setRol(rol);
        controller.agregarEmpleado(empleado);
    }
    
}
