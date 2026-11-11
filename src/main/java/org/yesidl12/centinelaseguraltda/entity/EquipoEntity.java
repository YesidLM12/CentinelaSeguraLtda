package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yesidl12.centinelaseguraltda.enums.Estado_Equipo;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Equipo;

import java.time.LocalDate;

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
@Entity
@Table(name="equipo")
public class EquipoEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipo_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 15)
    private Tipo_Equipo tipo;

    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;

    @Column(name = "marca", length = 60)
    private String marca;

    @Column(
        name = "numero_permiso",
        length = 40,
        check = @CheckConstraint(
            name = "CK_equipo_numero_permiso",
            constraint = "numero_permiso REGEXP '^[0-9]+$'"))
    private String numeroPermiso;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private Estado_Equipo estado;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "fecha_adquision")
    private LocalDate fechaAdquision;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioEntity servicio;
}
