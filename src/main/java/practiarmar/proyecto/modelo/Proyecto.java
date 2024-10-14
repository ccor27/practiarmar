/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practiarmar.proyecto.modelo;

import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author crist
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Cliente cliente;
    private double precioMateriales;
    private double precioPorEmpleado;
    private double precioManoDeObra;
    private double precioTotal;
    private LocalDate fechaEntrega;
    private LocalDate fehcaModificacion;
    private String descripcion;
    private boolean isDeleted;
    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoProyecto;
    @ManyToMany
    @JoinTable(name = "empleado_proyecto")
    private List<Empleado> empleados;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "item_proyecto")
    private List<Item> items;
    @ManyToMany(cascade = CascadeType.PERSIST )
    @JoinTable(name = "foto_proyecto")
    private List<Foto> fotos;
    
     /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return id != null && id.equals(empleado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/
    
}
