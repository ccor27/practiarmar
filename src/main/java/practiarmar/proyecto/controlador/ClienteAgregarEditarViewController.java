/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import practiarmar.proyecto.modelo.Cliente;

/**
 *
 * @author crist
 */
public class ClienteAgregarEditarViewController {
    
      private ModelFactoryController controller;
    
    public ClienteAgregarEditarViewController(){
        this.controller= ModelFactoryController.getInstance();
    }

    public void agregar(Cliente c) {
        controller.agregarCliente(c);
    }

    public void editar(Cliente c) throws Exception {
      controller.editarCliente(c);
    }
}
