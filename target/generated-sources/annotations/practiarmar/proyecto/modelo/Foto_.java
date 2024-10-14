package practiarmar.proyecto.modelo;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Foto.class)
public class Foto_ { 

    public static volatile SingularAttribute<Foto, Date> fecha;
    public static volatile SingularAttribute<Foto, String> tipo;
    public static volatile SingularAttribute<Foto, byte[]> imagen;
    public static volatile SingularAttribute<Foto, Long> id;
    public static volatile SingularAttribute<Foto, String> nombre;
    public static volatile SingularAttribute<Foto, Long> tamano;

}