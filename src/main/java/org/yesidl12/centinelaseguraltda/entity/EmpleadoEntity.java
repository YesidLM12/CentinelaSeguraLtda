package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.yesidl12.centinelaseguraltda.enums.Cargo;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "empleado")
public class EmpleadoEntity extends AuditableEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "empleado_id", nullable = false, updatable = false)
   private Long id;

   @Column(name = "documento", nullable = false, unique = true, check = @CheckConstraint(name = "CK_empleado_documento", constraint = "documento REGEXP '^[0-9]+$'"))
   private String documento;

   @Column(name = "nombre", nullable = false)
   private String nombre;

   @Enumerated(EnumType.STRING)
   @Column(name = "cargo", nullable = false, length = 20)
   private Cargo cargo;

   @Column(name = "numero_credencial", nullable = false, length = 30, unique = true, check = @CheckConstraint(name = "CK_empleado_numero_credencial", constraint = "numero_credencial REGEXP '^[0-9]+$'"))
   private String numero_credencial;

   @Column(name = "vence_credencial", nullable = false)
   private LocalDate vence_credencial;

   @Column(name = "telefono", length = 15, check = @CheckConstraint(name = "CK_empleado_telefono", constraint = "telefono REGEXP '^[0-9]+$'"))
   private String telefono;

   @Column(name = "fecha_ingreso")
   private LocalDate fecha_ingreso;

   @Column(name = "activo", nullable = false)
   private Boolean activo = true;

   @ManyToOne
   @JoinColumn(name = "servicio_id")
   private ServicioEntity servicio;
}
