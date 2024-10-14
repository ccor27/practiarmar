package practiarmar.proyecto.modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import practiarmar.proyecto.modelo.MedioPago;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, String> descripcion;
    public static volatile SingularAttribute<Factura, Double> precio;
    public static volatile SingularAttribute<Factura, String> nombreCliente;
    public static volatile SingularAttribute<Factura, String> codigoProyecto;
    public static volatile SingularAttribute<Factura, Long> id;
    public static volatile SingularAttribute<Factura, MedioPago> medioPago;

}