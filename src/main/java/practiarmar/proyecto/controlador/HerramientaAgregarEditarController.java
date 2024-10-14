/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import practiarmar.proyecto.modelo.Herramienta;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class HerramientaAgregarEditarController {
    
       private ModelFactoryController controller;
    
    public HerramientaAgregarEditarController(){
        this.controller= ModelFactoryController.getInstance();
    }

    public void agregar(Herramienta nuevaHerramienta) {
     controller.agregarHerramienta(nuevaHerramienta);
    }

    public void editar(Herramienta herramienta) throws NonexistentEntityException {
     controller.editarHerramienta(herramienta);
    }
}
