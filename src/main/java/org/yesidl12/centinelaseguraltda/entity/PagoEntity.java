package org.yesidl12.centinelaseguraltda.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;

import org.yesidl12.centinelaseguraltda.enums.Forma_Pago;
import org.yesidl12.centinelaseguraltda.enums.Estado_Pago;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "pago",
    indexes = {
        @Index(name = "idx_pago_fecha", columnList = "fecha_pago")
    }
)
public class PagoEntity extends AuditableEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "pago_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "contratacion_id", nullable = false)
    private ContratacionEntity contratacion;
    
    @Column (
        name = "monto", 
        nullable = false, 
        columnDefinition = "CHECK (monto > 0)")
    private Double monto;

    @Enumerated (EnumType.STRING)
    @Column (name = "forma_pago", nullable = false)
    private Forma_Pago formaPago;
    
    @Column (name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
    
    @Enumerated (EnumType.STRING)
    @Column (
        name = "estado", 
        nullable = false,
        columnDefinition = "CHECK (estado <> 'ANULADO' OR motivo_anulacion IS NOT NULL)")
    private Estado_Pago estado;
    
    @Column (name = "motivo_anulacion", length = 300)
    private String motivoAnulacion;

    @Column ( name = "fecha_anulacion")
    private LocalDateTime fechaAnulacion;

    @ManyToOne
    @JoinColumn( name = "anulado_por")
    private UsuarioEntity anuladoPor;
}
