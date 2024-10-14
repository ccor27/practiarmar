/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.modelo.EstadoProyecto;
import practiarmar.proyecto.modelo.Foto;
import practiarmar.proyecto.modelo.Item;
import practiarmar.proyecto.modelo.Proyecto;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class ProyectoAgregarEditarViewController {

    private final ModelFactoryController controller;

    public ProyectoAgregarEditarViewController() {
        this.controller = ModelFactoryController.getInstance();
    }

    public List<Empleado> obtenerEmpleados() {
        return this.controller.obtenerListaEmpleados();
    }

    public List<Cliente> obtenerClientes() {
        return this.controller.obtenerListClientes();
    }

    public void agregarProyecto(
            Cliente cliente,
            double precioMateriales,
            double precioPorEmpleado,
            double precioManoDeObra,
            double precioTotal,
            LocalDate fechaEntrega,
            String descripcion,
            EstadoProyecto estadoProyecto,
            List<Empleado> empleados,
            List<Item> items,
            List<Foto> fotos
    ) {

        Proyecto proyecto = new Proyecto();
        proyecto.setCliente(cliente);
        proyecto.setPrecioMateriales(precioMateriales);
        proyecto.setPrecioPorEmpleado(precioPorEmpleado);
        proyecto.setPrecioManoDeObra(precioManoDeObra);
        proyecto.setPrecioTotal(precioTotal);
        proyecto.setFechaEntrega(fechaEntrega);
        proyecto.setFehcaModificacion(LocalDate.now());
        proyecto.setDescripcion(descripcion);
        proyecto.setEstadoProyecto(estadoProyecto);
        proyecto.setEmpleados(empleados);
        proyecto.setItems(items);
        proyecto.setFotos(fotos);
        controller.agregarProyecto(proyecto);
    }

    public void editarProyecto(Proyecto proyectoAEditar) throws NonexistentEntityException {
        controller.editarProyecto(proyectoAEditar);
    }

}
