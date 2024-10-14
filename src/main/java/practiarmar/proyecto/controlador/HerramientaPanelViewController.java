/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import java.util.List;
import practiarmar.proyecto.modelo.Herramienta;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class HerramientaPanelViewController {

    private ModelFactoryController controller;

    public HerramientaPanelViewController() {
        this.controller = ModelFactoryController.getInstance();
    }

    public List<Herramienta> obtenerHerramientas() {
     return controller.obtenerHerramientas();
    }

    public void removeHerramienta(Long id) throws NonexistentEntityException {
     controller.removeHerramienta(id);
    }
}
