package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "asignacion_empleado", 
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_asignacion_empleado", 
            columnNames = {"servicio_id", "empleado_id", "fecha_inicio", "hora_inicio"})})
public class AsignacionEmpleadoEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "asignacion_empleado_id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioEntity servicio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private EmpleadoEntity empleado;

    @Column (name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column (name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column (name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column (name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column (name = "observaciones", length = 200)
    private String observaciones;   
}

