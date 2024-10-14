package practiarmar.proyecto.modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-21T13:22:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, String> descripcion;
    public static volatile SingularAttribute<Item, Double> precioPorUnidad;
    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, Integer> cantidad;
    public static volatile SingularAttribute<Item, String> nombre;

}