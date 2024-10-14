package practiarmar.proyecto.modelo;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import practiarmar.proyecto.modelo.Cliente;
import practiarmar.proyecto.modelo.Empleado;
import practiarmar.proyecto.modelo.EstadoProyecto;
import practiarmar.proyecto.modelo.Foto;
import practiarmar.proyecto.modelo.Item;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Proyecto.class)
public class Proyecto_ { 

    public static volatile SingularAttribute<Proyecto, String> descripcion;
    public static volatile SingularAttribute<Proyecto, Double> precioMateriales;
    public static volatile SingularAttribute<Proyecto, LocalDate> fehcaModificacion;
    public static volatile ListAttribute<Proyecto, Empleado> empleados;
    public static volatile SingularAttribute<Proyecto, EstadoProyecto> estadoProyecto;
    public static volatile SingularAttribute<Proyecto, Double> precioManoDeObra;
    public static volatile SingularAttribute<Proyecto, Cliente> cliente;
    public static volatile SingularAttribute<Proyecto, Boolean> isDeleted;
    public static volatile SingularAttribute<Proyecto, LocalDate> fechaEntrega;
    public static volatile SingularAttribute<Proyecto, Double> precioPorEmpleado;
    public static volatile SingularAttribute<Proyecto, Long> id;
    public static volatile SingularAttribute<Proyecto, Double> precioTotal;
    public static volatile ListAttribute<Proyecto, Item> items;
    public static volatile ListAttribute<Proyecto, Foto> fotos;

}