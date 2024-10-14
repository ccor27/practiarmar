package practiarmar.proyecto.modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import practiarmar.proyecto.modelo.EstadoPagoEmpleado;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(PagoEmpleado.class)
public class PagoEmpleado_ { 

    public static volatile SingularAttribute<PagoEmpleado, Double> total;
    public static volatile SingularAttribute<PagoEmpleado, EstadoPagoEmpleado> estado;
    public static volatile SingularAttribute<PagoEmpleado, String> codigoProyecto;
    public static volatile SingularAttribute<PagoEmpleado, Long> id;

}