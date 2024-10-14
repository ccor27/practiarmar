/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import java.util.List;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class ClientePanelViewController {
    
      private ModelFactoryController controller;
    
    public ClientePanelViewController(){
        this.controller= ModelFactoryController.getInstance();
    }

    public List<Cliente> obtenerClientes() {
      return controller.obtenerListClientes();
    }

    public void removeCliente(Cliente cliente) throws Exception {
      controller.removeCliente(cliente);
    }
}
