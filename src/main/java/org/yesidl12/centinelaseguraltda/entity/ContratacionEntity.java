package org.yesidl12.centinelaseguraltda.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.EnumType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.yesidl12.centinelaseguraltda.enums.Estado_Contratacion;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Contratacion;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "contratacion",
    indexes = {
        @Index(name = "idx_contratacion_cliente_id", columnList = "cliente_id"),
        @Index (name = "idx_contratacion_estado", columnList = "estado")})
public class ContratacionEntity extends AuditableEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contratacion_id", nullable = false)
    private Long id;

    @Column(
        name = "numero_confirmacion", 
        nullable = false,
        length = 20,
        check = @CheckConstraint (
            name = "chk_numero_confirmacion_formato",
            constraint = "numero_confirmacion ~ '^[0-9]{20}$'"))
    private String numeroConfirmacion;
    
    @Enumerated(EnumType.STRING)
    @Column(
        name = "tipo",
        nullable = false,
        length = 20)
    private Tipo_Contratacion tipo;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(
        name = "servicio_id",
        check = @CheckConstraint(
            name = "ck_contratacion_servicio_id",
            constraint = "(tipo = 'SERVICIO'      AND servicio_id IS NOT NULL AND entrenamiento_id IS NULL) OR (tipo = 'ENTRENAMIENTO' AND entrenamiento_id IS NOT NULL AND servicio_id IS NULL)"))
    private ServicioEntity servicio;

    @ManyToOne 
    @JoinColumn(
        name = "entrenamiento_id",
        check = @CheckConstraint(
            name = "ck_contratacion_entrenamiento_id",
            constraint = "tipo = 'ENTRENAMIENTO' OR (fecha_inicio IS NOT NULL AND fecha_fin IS NOT NULL AND fecha_fin > fecha_inicio)"))
    private EntrenamientoEntity entrenamiento;

    @Column (name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column (name = "fecha_fin")
    private LocalDate fechaFin;

    @Column (name = "Sitio_presentacion", length = 150)
    private String sitioPresentacion;

    @Column (name = "valor_total", columnDefinition = "Decimal(14,2) NOT NULL")
    private BigDecimal valorTotal;

    @Column (name = "condiciones", length = 500)
    private String condiciones;
    
    @Enumerated(EnumType.STRING)
    @Column (name = "estado", length = 15, columnDefinition = "NOT NULL DEFAULT 'PENDIENTE'")
    private Estado_Contratacion estado;

    @Column (name = "motivo_cancelacion", length = 500)
    private String motivoCancelacion;

    @Column (name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;
}

