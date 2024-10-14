/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.exception.ConstraintViolationException;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.persistence.ClienteJpaController;
import practiarmar.proyecto.persistence.EmpleadoJpaController;
import practiarmar.proyecto.persistence.FacturaJpaController;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.EstadoPagoEmpleado;
import practiarmar.proyecto.modelo.EstadoProyecto;
import practiarmar.proyecto.modelo.PagoEmpleado;
import practiarmar.proyecto.modelo.Rol;
import practiarmar.proyecto.modelo.Herramienta;
import practiarmar.proyecto.modelo.Categoria;
import practiarmar.proyecto.modelo.Foto;
import practiarmar.proyecto.modelo.Item;
import practiarmar.proyecto.modelo.Proyecto;
import practiarmar.proyecto.persistence.FotoJpaController;
import practiarmar.proyecto.persistence.HerramientaJpaController;
import practiarmar.proyecto.persistence.ItemJpaController;
import practiarmar.proyecto.persistence.PagoEmpleadoJpaController;
import practiarmar.proyecto.persistence.ProyectoJpaController;
import practiarmar.proyecto.persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author crist
 */
public class ModelFactoryController {

    private ClienteJpaController clienteJpaController;
    private FacturaJpaController facturaJpaController;
    private EmpleadoJpaController empleadoJpaController;
    private ProyectoJpaController proyectoJpaController;
    private HerramientaJpaController herramientaJpaController; 
    private FotoJpaController fotoJpaController;
    private ItemJpaController itemJpaController;
    private PagoEmpleadoJpaController pagoEmpleadoJpaController;
    
    private List<Proyecto> proyectos = new ArrayList<>();
    private List<Empleado> empleados = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Herramienta> herramientas = new ArrayList<>();

    // ------------------------------ Singleton
    // ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        // El constructor de Singleton puede ser llamado desde aquí al ser
        // protected

        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        this.clienteJpaController=new ClienteJpaController();
        this.facturaJpaController=new FacturaJpaController();
        this.empleadoJpaController=new EmpleadoJpaController();
        this.proyectoJpaController=new ProyectoJpaController();
        this.herramientaJpaController=new HerramientaJpaController();
        this.fotoJpaController=new FotoJpaController();
        this.itemJpaController=new ItemJpaController();
        this.pagoEmpleadoJpaController=new PagoEmpleadoJpaController();
        //iniciarDatos();
    }

    private void iniciarDatos() {
        try {
            PagoEmpleado pago1 = new PagoEmpleado(1L, "P1", 1200000, EstadoPagoEmpleado.PAGADO);
            PagoEmpleado pago2 = new PagoEmpleado(2L, "P2", 1200000, EstadoPagoEmpleado.PAGADO);
            PagoEmpleado pago3 = new PagoEmpleado(3L, "P3", 1200000, EstadoPagoEmpleado.SIN_PAGAR);
            Empleado emp1 = new Empleado("123", "juan", "juan123",false, Rol.ADMINISTRADOR,
                    Arrays.asList(pago1, pago2, pago3));
            emp1.setId(1L);
            emp1.setNombre("Juan");
            emp1.setTelefono("2345678123");
            emp1.setEmail("juan@gmail.com");
            PagoEmpleado pago4 = new PagoEmpleado(4L, "P4", 1200000, EstadoPagoEmpleado.PAGADO);
            PagoEmpleado pago5 = new PagoEmpleado(5L, "P5", 1200000, EstadoPagoEmpleado.PAGADO);
            PagoEmpleado pago6 = new PagoEmpleado(6L, "P6", 1200000, EstadoPagoEmpleado.SIN_PAGAR);
            Empleado emp2 = new Empleado("124", "Fabian", "fabian123",false, Rol.EMPLEADO,
                    Arrays.asList(pago4, pago5, pago6));
            emp2.setId(2L);
            emp2.setNombre("Fabian");
            emp2.setTelefono("2345678123");
            emp2.setEmail("Fabian@gmail.com");
            PagoEmpleado pago7 = new PagoEmpleado(7L, "P1", 1200000, EstadoPagoEmpleado.PAGADO);
            PagoEmpleado pago8 = new PagoEmpleado(8L, "P2", 1200000, EstadoPagoEmpleado.PAGADO);
            PagoEmpleado pago9 = new PagoEmpleado(9L, "P3", 1200000, EstadoPagoEmpleado.SIN_PAGAR);
            Empleado emp3 = new Empleado("125", "tavo", "tavo123",false, Rol.ADMINISTRADOR,
                    Arrays.asList(pago7, pago8, pago9));
            emp3.setId(3L);
            emp3.setNombre("Tavo");
            emp3.setTelefono("2345678123");
            emp3.setEmail("Tavo@gmail.com");
            empleados.add(emp1);
            empleados.add(emp2);
            empleados.add(emp3);
            
            Cliente c1 = new Cliente();
            c1.setId(1L);
            c1.setNombre("eduardo");
            c1.setTelefono("7891233123");
            c1.setEmail("eduardo@gmail.com");
            c1.setRecibirNotificaciones(true);
            c1.setDeleted(false);
            Cliente c2 = new Cliente();
            c2.setId(2L);
            c2.setNombre("jorge");
            c2.setTelefono("102926374");
            c2.setEmail("jorge@gmail.com");
            c2.setRecibirNotificaciones(true);
            c2.setDeleted(false);
            Cliente c3 = new Cliente();
            c3.setId(3L);
            c3.setNombre("jaime");
            c3.setTelefono("092637421");
            c3.setEmail("jaime@gmail.com");
            c3.setRecibirNotificaciones(true);
            c3.setDeleted(false);
            Cliente c4 = new Cliente();
            c4.setId(4L);
            c4.setNombre("laura");
            c4.setTelefono("1623812420");
            c4.setEmail("laura@gmail.com");
            c4.setRecibirNotificaciones(true);
            c4.setDeleted(false);
            clientes.add(c1);
            clientes.add(c2);
            clientes.add(c3);
            clientes.add(c4);
            System.out.println("va a crear la foto...");
            File file = new File("C:\\Users\\crist\\OneDrive\\Escritorio\\videos y project parctiarmar\\th-1529342444.png");
            byte[] imagenBytes = Files.readAllBytes(file.toPath());
             Foto foto1 = new Foto(1L,
            "th-1529342444.png",
            "png",
            13659L,
            new Date(),
            imagenBytes);
            Herramienta h1 = new Herramienta(1L, "martillo", "para clavar manualmente", Categoria.UNION, foto1);
            herramientas.add(h1);
            Herramienta h2 = new Herramienta(2L, "metro", "para medir manualmente", Categoria.MEDICION, null);
            Herramienta h3 = new Herramienta(3L, "orejeras", "para cubrir los oidos", Categoria.SEGURIDAD, null);
            Herramienta h4 = new Herramienta(4L, "gafas", "para protejer los ojos al momento de cortar", Categoria.SEGURIDAD, null);
            Herramienta h5 = new Herramienta(5L, "cierra de mano", "para cortes no muy grandes", Categoria.CORTE, null);
            
            herramientas.add(h2);
            herramientas.add(h3);
            herramientas.add(h4);
            herramientas.add(h5);
            
            Item i1 = new Item(1L, "laminas pino 2x3", "laminas de  pino color negro de 2 metros de ancho x 3 de largo", 3, 500000.0);
            Item i2 = new Item(2L, "chapas", "chapas para las 6 puertas", 6, 30000.0);
            Item i3 = new Item(3L, "laminas mdf 4x2", "laminas de mdf de 4 metros de ancho x 2 de largo", 2, 100000.0);
            Item i4 = new Item(4L, "pintura", "pintura color negra mate", 2, 5000.0);
            Item i5 = new Item(5L, "lamina pino", "lamina de pino colo cafe de 6 metros de largo x 6 de ancho", 1, 200000.0);
            Item i6 = new Item(6L, "productos de enchapado", "combo de chapas, tornillos y demas", 1, 30000.0);
            Item i7 = new Item(6L, "laca", "laca para pintura", 1, 6000.0);
            Proyecto p1 = new Proyecto(
                    1L, c1, 1680000.0,50000.0,200000.0, 1980000.0, 
                    LocalDate.of(2024, Month.MARCH, 20),LocalDate.of(2024, Month.MARCH, 20), 
                    "puertas para 6 habitaciones",false, EstadoProyecto.ESPERA_DE_PAGO,
                    Arrays.asList(emp1,emp2),
                     Arrays.asList(i1,i2), 
                    Arrays.asList(foto1));
             Proyecto p2 = new Proyecto(
                    2L,  c2, 116000.0,20000.0,150000.0, 306000.0, 
                    LocalDate.of(2024, Month.JULY, 11),LocalDate.of(2024, Month.JULY, 11),
                     "",false,EstadoProyecto.SIN_EMPEZAR, 
                    Arrays.asList(emp3,emp2),
                     Arrays.asList(i3,i4,i7), 
                    new ArrayList<>());
             Proyecto p3 = new Proyecto(
                    3L,  c2, 230000.0,20000.0,100000.0, 370000.0, 
                    LocalDate.of(2024, Month.JANUARY, 9),LocalDate.of(2024, Month.JANUARY, 9),
                     "Mueble para oficina ",false,EstadoProyecto.FINALIZADO, 
                    Arrays.asList(emp3,emp2),
                     Arrays.asList(i5,i6), 
                    new ArrayList<>());
             proyectos.add(p1);
             proyectos.add(p2);
             proyectos.add(p3);
             
            System.out.println("datos quemados");
        } catch (IOException ex) {
            Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    //--------Empleados-------------//
    List<Empleado> obtenerListaEmpleados() {
        return this.empleadoJpaController.findEmpleadoEntities();
    }
    public void agregarEmpleado(Empleado e) throws Exception{
        this.empleadoJpaController.create(e);
    }

    public void eliminarEmpleado(Empleado empleado) throws Exception {
        //Hacemos un soft delete con el fin de poder hacer reportes
        this.empleadoJpaController.edit(empleado);
    }
    public void editarEmpleado(Empleado empleadoAEditar) throws Exception {
       this.empleadoJpaController.edit(empleadoAEditar);
    }

    //--------Clientes-------------//
    public List<Cliente> obtenerListClientes() {
        return this.clienteJpaController.findClienteEntities();
    }

    public void agregarCliente(Cliente cliente) {
        this.clienteJpaController.create(cliente);
    }

    public void editarCliente(Cliente c) throws Exception {
       this.clienteJpaController.edit(c);
    }

    public void removeCliente(Cliente cliente) throws Exception   {
         //Hacemos un soft delete con el fin de poder hacer reportes
         this.clienteJpaController.edit(cliente);
    }

    //-------herramientas-----//
    public List<Herramienta> obtenerHerramientas() {
        return this.herramientaJpaController.findEntities();
    }

    public void agregarHerramienta(Herramienta h) {
        this.herramientaJpaController.create(h);
    }

    public void removeHerramienta(Long id) throws NonexistentEntityException {
        this.herramientaJpaController.destroy(id);
    }

    public void editarHerramienta(Herramienta herramienta) throws NonexistentEntityException {
        this.herramientaJpaController.edit(herramienta);
    }
   //---------Proyectos--------//
    public List<Proyecto> obtenerProyectos(){
      return this.proyectoJpaController.findEntities();
    }
    public void removeProyecto(Proyecto proyecto) throws NonexistentEntityException {
     //Hacemos un soft delete con el fin de poder hacer reportes
     this.proyectoJpaController.edit(proyecto);
    }
    public void agregarProyecto(Proyecto proyecto) {
     this.proyectoJpaController.create(proyecto);
    }
    public void editarProyecto(Proyecto proyectoAEditar) throws NonexistentEntityException {
        this.proyectoJpaController.edit(proyectoAEditar);
    }
     
}
