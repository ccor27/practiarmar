package practiarmar.proyecto.modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import practiarmar.proyecto.modelo.PagoEmpleado;
import practiarmar.proyecto.modelo.Rol;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Empleado.class)
public class Empleado_ extends Persona_ {

    public static volatile SingularAttribute<Empleado, String> codigo;
    public static volatile SingularAttribute<Empleado, Boolean> isDeleted;
    public static volatile SingularAttribute<Empleado, String> contrasena;
    public static volatile ListAttribute<Empleado, PagoEmpleado> pagos;
    public static volatile SingularAttribute<Empleado, Rol> rol;
    public static volatile SingularAttribute<Empleado, String> username;

}