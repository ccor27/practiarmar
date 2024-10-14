package practiarmar.proyecto.modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import practiarmar.proyecto.modelo.Categoria;
import practiarmar.proyecto.modelo.Foto;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Herramienta.class)
public class Herramienta_ { 

    public static volatile SingularAttribute<Herramienta, String> descripcion;
    public static volatile SingularAttribute<Herramienta, Foto> foto;
    public static volatile SingularAttribute<Herramienta, Categoria> categoria;
    public static volatile SingularAttribute<Herramienta, Long> id;
    public static volatile SingularAttribute<Herramienta, String> nombre;

}