package org.yesidl12.centinelaseguraltda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.yesidl12.centinelaseguraltda.enums.Estado_Entrenamiento;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Entrenamiento;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (
    name = "entrenamiento", 
    indexes = {
        @Index(name = "idx_entrenamiento_fecha_inicio", columnList = "fecha_inicio")})
public class EntrenamientoEntity extends AuditableEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrenamiento_id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo_Entrenamiento tipo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column (name = "contenido", length = 1000)
    private String contenido;
    
    @Column (
        name = "intensidad_horas", 
        nullable = false,
        check = @CheckConstraint(
            name = "ck_entrenamiento_intensidad_horas",
            constraint = "intensidad_horas > 0"))
    private int intensidadHoras;

    @Column (
        name = "costo", 
        nullable = false,
        columnDefinition = "DECIMAL(14,2)",
        check = @CheckConstraint(
            name = "ck_entrenamiento_costo",
            constraint = "costo >= 0"))
    private BigDecimal costo;

    @Column (
        name = "cupo_maximo", 
        nullable = false,
        check = @CheckConstraint(
            name = "ck_entrenamiento_cupo_maximo",
            constraint = "cupo_maximo > 1"))
    private int cupoMaximo;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private InstructorEntity instructor;

    @Column(name = "fecha_inicio", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaInicio;

    @Column(
        name = "fecha_fin", 
        nullable = false,
        columnDefinition = "DATE",
        check = @CheckConstraint(
            name = "ck_entrenamiento_fecha_fin",
            constraint = "fecha_fin > fecha_inicio"))
    private LocalDate fechaFin;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column (name = "lugar")
    private String lugar;
    
    @Enumerated (EnumType.STRING)
    @Column (name = "estado", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'PROGRAMADO'")
    private Estado_Entrenamiento estado;
}
